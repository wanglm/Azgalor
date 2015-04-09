package org.Azgalor.hadoop.service;

import java.io.IOException;

/**执行mapreduce任务的接口
 * @author ming
 *
 */
public interface HService {
	/**简化mapreduce的job执行
	 * @param clz mapreduce主类，注意要有注解
	 * @param name job的名字，输出文件夹的与此相同
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InterruptedException
	 */
	public <T> void execute(Class<T> clz,String name) throws IOException, ClassNotFoundException, InterruptedException;
}
