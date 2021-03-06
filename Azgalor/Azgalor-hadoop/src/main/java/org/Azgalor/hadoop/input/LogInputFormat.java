package org.Azgalor.hadoop.input;

import java.io.IOException;

import org.Azgalor.hadoop.annotations.Where;
import org.Azgalor.hadoop.eumuns.Logic;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.Seekable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CodecPool;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.io.compress.Decompressor;
import org.apache.hadoop.io.compress.SplitCompressionInputStream;
import org.apache.hadoop.io.compress.SplittableCompressionCodec;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CompressedSplitLineReader;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.SplitLineReader;

import com.google.common.base.Charsets;

/**自定义的inputformat，主要源码来自默认的TextLineInputFormat
 * @author ming
 *
 */
@Where(key="date",value="xxx",logic=Logic.EQ)
public class LogInputFormat extends FileInputFormat<Text, Text> {
	private static final Log LOG = LogFactory.getLog(LogInputFormat.class);

	@Override
	public RecordReader<Text, Text> createRecordReader(InputSplit split,
			TaskAttemptContext context) throws IOException,
			InterruptedException {
		String delimiter = context.getConfiguration().get(
				"textinputformat.record.delimiter");
		byte[] recordDelimiterBytes = null;
		if (null != delimiter) {
			recordDelimiterBytes = delimiter.getBytes(Charsets.UTF_8);
		}
		LogRecordReader logReader=null;
		String regex=null;
		/*try {
			//注解的方式传值，当然要是动态改变的话，貌似只能动Configuration了。。。现在不实现
			Class<?> clz=context.getInputFormatClass();
			Where where=clz.getAnnotation(Where.class);
			if(where!=null){
				regex="";//想个方法弄出正则才行。。。现在想不出来...
			}
			logReader=this.new LogRecordReader(recordDelimiterBytes, regex);
		} catch (ClassNotFoundException e) {
			LOG.error("反射获取inputFormatClass出错：", e);
			throw new IOException(e);
		}*/
		logReader=this.new LogRecordReader(recordDelimiterBytes, regex);
		return logReader;
	}

	public class LogRecordReader extends RecordReader<Text, Text> {
		public static final String MAX_LINE_LENGTH = "mapreduce.input.linerecordreader.line.maxlength";
		private long start;
		private long pos;
		private long end;
		private SplitLineReader in;
		private FSDataInputStream fileIn;
		private Seekable filePosition;
		private int maxLineLength;
		private Text key;
		private Text value;
		private boolean isCompressedInput;
		private Decompressor decompressor;
		private byte[] recordDelimiterBytes;

		/**
		 * 自定义的成员变量=正则表达式
		 */
		private String regex;
		private String[] strs;

		public LogRecordReader() {
		}

		public LogRecordReader(byte[] recordDelimiterBytes, String regex) {
			this.recordDelimiterBytes = recordDelimiterBytes;
			this.regex = regex;
		}

		private int skipUtfByteOrderMark() throws IOException {
			// Strip BOM(Byte Order Mark)
			// Text only support UTF-8, we only need to check UTF-8 BOM
			// (0xEF,0xBB,0xBF) at the start of the text stream.
			int newMaxLineLength = (int) Math.min(3L + (long) maxLineLength,
					Integer.MAX_VALUE);
			int newSize = in.readLine(value, newMaxLineLength,
					maxBytesToConsume(pos));
			// Even we read 3 extra bytes for the first line,
			// we won't alter existing behavior (no backwards incompat issue).
			// Because the newSize is less than maxLineLength and
			// the number of bytes copied to Text is always no more than
			// newSize.
			// If the return size from readLine is not less than maxLineLength,
			// we will discard the current line and read the next line.
			pos += newSize;
			int textLength = value.getLength();
			byte[] textBytes = value.getBytes();
			if ((textLength >= 3) && (textBytes[0] == (byte) 0xEF)
					&& (textBytes[1] == (byte) 0xBB)
					&& (textBytes[2] == (byte) 0xBF)) {
				// find UTF-8 BOM, strip it.
				LOG.info("Found UTF-8 BOM and skipped it");
				textLength -= 3;
				newSize -= 3;
				if (textLength > 0) {
					// It may work to use the same buffer and not do the
					// copyBytes
					textBytes = value.copyBytes();
					value.set(textBytes, 3, textLength);
				} else {
					value.clear();
				}
			}
			return newSize;
		}

		private long getFilePosition() throws IOException {
			long retVal;
			if (isCompressedInput && null != filePosition) {
				retVal = filePosition.getPos();
			} else {
				retVal = pos;
			}
			return retVal;
		}

		private int maxBytesToConsume(long pos) {
			return isCompressedInput ? Integer.MAX_VALUE : (int) Math.max(
					Math.min(Integer.MAX_VALUE, end - pos), maxLineLength);
		}

		@Override
		public void initialize(InputSplit genericSplit,
				TaskAttemptContext context) throws IOException,
				InterruptedException {
			FileSplit split = (FileSplit) genericSplit;
			Configuration job = context.getConfiguration();
			this.maxLineLength = job.getInt(MAX_LINE_LENGTH, Integer.MAX_VALUE);
			start = split.getStart();
			end = start + split.getLength();
			final Path file = split.getPath();

			// open the file and seek to the start of the split
			final FileSystem fs = file.getFileSystem(job);
			fileIn = fs.open(file);

			CompressionCodec codec = new CompressionCodecFactory(job)
					.getCodec(file);
			if (null != codec) {
				isCompressedInput = true;
				decompressor = CodecPool.getDecompressor(codec);
				if (codec instanceof SplittableCompressionCodec) {
					final SplitCompressionInputStream cIn = ((SplittableCompressionCodec) codec)
							.createInputStream(
									fileIn,
									decompressor,
									start,
									end,
									SplittableCompressionCodec.READ_MODE.BYBLOCK);
					in = new CompressedSplitLineReader(cIn, job,
							this.recordDelimiterBytes);
					start = cIn.getAdjustedStart();
					end = cIn.getAdjustedEnd();
					filePosition = cIn;
				} else {
					in = new SplitLineReader(codec.createInputStream(fileIn,
							decompressor), job, this.recordDelimiterBytes);
					filePosition = fileIn;
				}
			} else {
				fileIn.seek(start);
				in = new SplitLineReader(fileIn, job, this.recordDelimiterBytes);
				filePosition = fileIn;
			}
			// If this is not the first split, we always throw away first record
			// because we always (except the last split) read one extra line in
			// next() method.
			if (start != 0) {
				start += in.readLine(new Text(), 0, maxBytesToConsume(start));
			}
			this.pos = start;

		}

		@Override
		public boolean nextKeyValue() throws IOException, InterruptedException {
			if (key == null) {
				key = new Text();
			}
			if (value == null) {
				value = new Text();
			}
			int newSize = 0;
			// We always read one extra line, which lies outside the upper
			// split limit i.e. (end - 1)
			while (getFilePosition() <= end
					|| in.needAdditionalRecordAfterSplit()) {
				if (pos == 0) {
					newSize = skipUtfByteOrderMark();
				} else {
					Text lineText = new Text();
					newSize = in.readLine(lineText, maxLineLength,
							maxBytesToConsume(pos));
					if (StringUtils.isBlank(regex)) {
						// 读取一行数据，分割成数组获取想要的数据，但是以后估计会改成正则表达式选取,而且格式化数据切分选取会有错误问题，无法解决目前
						strs = lineText.toString().trim().split(",");
						if(strs.length>=2){
							key.set(strs[0]);
							value.set(strs[1]);
						}else{
							for(String s:strs){
								LOG.info("特殊的长度>2的行："+s);
							}
						}
					}
					pos += newSize;
				}

				if ((newSize == 0) || (newSize < maxLineLength)) {
					break;
				}

				// line too long. try again
				LOG.info("Skipped line of size " + newSize + " at pos "
						+ (pos - newSize));
			}
			if (newSize == 0) {
				key = null;
				value = null;
				return false;
			} else {
				return true;
			}
		}

		@Override
		public Text getCurrentKey() {
			return key;
		}

		@Override
		public Text getCurrentValue() {
			return value;
		}

		/**
		 * Get the progress within the split
		 */
		public float getProgress() throws IOException {
			if (start == end) {
				return 0.0f;
			} else {
				return Math.min(1.0f, (getFilePosition() - start)
						/ (float) (end - start));
			}
		}

		public synchronized void close() throws IOException {
			try {
				if (in != null) {
					in.close();
				}
			} finally {
				if (decompressor != null) {
					CodecPool.returnDecompressor(decompressor);
				}
			}
		}
	}

}
