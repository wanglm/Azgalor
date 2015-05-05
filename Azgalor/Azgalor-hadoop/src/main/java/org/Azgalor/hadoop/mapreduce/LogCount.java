package org.Azgalor.hadoop.mapreduce;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

import org.Azgalor.hadoop.HadoopConfig;
import org.Azgalor.hadoop.entities.WapsWritable;
import org.Azgalor.hadoop.input.LogInputFormat;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class LogCount {
	private static final Log LOG = LogFactory.getLog(LogCount.class);

	/**
	 * udid去重
	 * 
	 * @author ming
	 *
	 */
	public static class ScreenMapper extends
			Mapper<Text, Text, WapsWritable, LongWritable> {

		private final static LongWritable one = new LongWritable(1L);
		private WapsWritable lw = new WapsWritable();
		private WapsWritable alw = new WapsWritable(new Text("all_id"),
				new Text("all"), new LongWritable(0L));// 不去重的总数

		public void map(Text key, Text value, Context context)
				throws IOException, InterruptedException {
			// 对象重用应该不会造成数据混乱的问题
			if (StringUtils.isBlank(key.toString())) {
				String[] strs = value.toString().split(",");
				LOG.info("第一个map： id=" + strs[0] + "---udid=" + strs[1]);
				lw.setId(new Text(strs[0]));
				lw.setUdid(new Text(strs[1]));
			} else {
				lw.setId(key);
				lw.setUdid(value);
			}
			context.write(lw, one);
			context.write(alw, one);
		}
	}

	public static class LongSumReducer extends
			Reducer<WapsWritable, LongWritable, WapsWritable, LongWritable> {
		private LongWritable result = new LongWritable();
		private long sum = 0l;

		public void reduce(WapsWritable key, Iterable<LongWritable> values,
				Context context) throws IOException, InterruptedException {
			for (LongWritable val : values) {
				sum += val.get();
			}
			result.set(sum);
			context.write(key, result);
			sum = 0l;
		}
	}

	/**
	 * 后处理mapper统计去重后的数量
	 * 
	 * @author ming
	 *
	 */
	public static class CountMapper extends
			Mapper<WapsWritable, LongWritable, Text, WapsWritable> {
		private final static Text FLAG = new Text("data");
		private final static LongWritable one = new LongWritable(1L);

		@Override
		protected void map(
				WapsWritable key,
				LongWritable value,
				Mapper<WapsWritable, LongWritable, Text, WapsWritable>.Context context)
				throws IOException, InterruptedException {
			// k-v反过来，方便reduce统计
			if (key.getId().toString().equals("all_id")
					& key.getUdid().toString().equals("all")) {
				key.setSum(value);
				context.write(new Text("data count"), key);
			} else {
				key.setSum(one);
				context.write(FLAG, key);
			}
		}
	}

	public static class CountReducer extends
			Reducer<Text, WapsWritable, WapsWritable, LongWritable> {
		private LongWritable result = new LongWritable();
		private int size = 0;
		private LinkedList<WapsWritable> _values =  new LinkedList<WapsWritable>();// 这个顺序执行效率更高，而且扩容貌似更快？
		private WapsWritable tmp=null;

		public void reduce(Text key, Iterable<WapsWritable> values,
				Context context) throws IOException, InterruptedException {
			// 妈蛋，巨坑！！！！iterable只能遍历一次！！！！
			//保存Iterable的对象必须add new 对象，否则重复输出最后一个！！！！google reudce阶段的对象重用
			for(WapsWritable val:values){
				tmp=new WapsWritable();
				tmp.setId(new Text(val.getId()));
				tmp.setUdid(new Text(val.getUdid()));
				tmp.setSum(new LongWritable(val.getSum().get()));
				_values.add(tmp);
				size++;
			}
			result.set((long) size);
			for (WapsWritable val : _values) {
				if (val.getId().toString().equals("all_id")
						||val.getUdid().toString().equals("all")) {
					context.write(val, val.getSum());
				} else {
					context.write(val, result);
				}
			}
			_values .clear();
			size = 0;
		}
	}

	public static void main(String[] args) throws Exception {
		String input = "/user/ming/input";
		String midOutput = "/user/ming/screen";
		String output = "/user/ming/logCount";
		Path screen = new Path(midOutput);
		Path logCount = new Path(output);
		Configuration con = HadoopConfig.INSRANCE.getConfiguration();
		FileSystem fs = FileSystem.get(con);
	if (fs.exists(screen)) {
			fs.delete(screen, true);
		}
		if (fs.exists(logCount)) {
			fs.delete(logCount, true);
		}
		Job job = Job.getInstance(con, "screen");
		job.setJar("Azgalor-hadoop-0.0.1.jar");
		job.setJarByClass(LogCount.class);
		job.setMapperClass(ScreenMapper.class);
		job.setCombinerClass(LongSumReducer.class);
		job.setReducerClass(LongSumReducer.class);
		job.setOutputKeyClass(WapsWritable.class);
		job.setOutputValueClass(LongWritable.class);
		job.setInputFormatClass(LogInputFormat.class);// 定义输入inputformat
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		FileInputFormat.addInputPath(job, new Path(input));
		FileOutputFormat.setOutputPath(job, screen);

		Job _job = Job.getInstance(con, "logCount");
		_job.setJar("Azgalor-hadoop-0.0.1.jar");
		_job.setJarByClass(LogCount.class);
		_job.setMapperClass(CountMapper.class);
		// 只有一个job的时候不需要，几个job顺序执行就需要了
		_job.setMapOutputKeyClass(Text.class);
		_job.setMapOutputValueClass(WapsWritable.class);

		// _job.setCombinerClass(CountReducer.class);
		// _job.setPartitionerClass(WapsLogPartition.class);//自定义的分区类,udid相同的分到同一个reduce中
		_job.setReducerClass(CountReducer.class);
		_job.setOutputKeyClass(WapsWritable.class);
		_job.setOutputValueClass(LongWritable.class);
		_job.setInputFormatClass(SequenceFileInputFormat.class);// 定义输入inputformat,读取hadoop专用序列文件的
		FileInputFormat.addInputPath(_job, screen);
		FileOutputFormat.setOutputPath(_job, logCount);

		ControlledJob job1 = new ControlledJob(con);
		job1.setJob(job);

		ControlledJob job2 = new ControlledJob(con);
		job2.setJob(_job);
		job2.addDependingJob(job1);// 设置依赖

		JobControl jcontrol = new JobControl("waps-log-count");
		jcontrol.addJob(job1);
		jcontrol.addJob(job2);

		Thread t = new Thread(jcontrol);
		t.start();
		// 这个等待线程执行完毕的方式好像不太正规的样子
		while (true) {
			if (jcontrol.allFinished()) {
				jcontrol.stop();
				break;
			}
		}
	}

}
