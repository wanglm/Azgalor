package org.Azgalor.base.extract;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

/**
 * @Description 使用tika抽取文本工具类
 * @author ming
 * @date 2014-9-29
 * 
 */
public class ExtractUtil {

	/**
	 * 文件抽取核心方法
	 * 
	 * @param f
	 *            文件
	 * @param parser
	 *            自动检测文档类型，自动创建相应的解析器
	 * @param metadata
	 *            元数据，比如自定义文件标题，编码什么的
	 * @param textNumber
	 *            抽取文件的字符最大数,默认10000
	 * @return String
	 * @throws Exception
	 */
	public static String getText(File f, AutoDetectParser parser,
			Metadata metadata, int textNumber) throws Exception {
		InputStream is = null;
		metadata.add(Metadata.CONTENT_ENCODING, "utf-8");
		is = new BufferedInputStream(new FileInputStream(f));
		ContentHandler handler = new BodyContentHandler(textNumber);// 文件抽取类，还有专门针对html展示的
		try {
			parser.parse(is, handler, metadata);
		} finally {
			is.close();
		}
		return handler.toString();// 转换成字符流
	}

}
