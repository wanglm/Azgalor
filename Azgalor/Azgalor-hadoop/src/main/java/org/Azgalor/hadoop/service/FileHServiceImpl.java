package org.Azgalor.hadoop.service;

import java.io.IOException;
import java.util.Properties;

import org.Azgalor.hadoop.HadoopConfig;
import org.Azgalor.hadoop.annotations.HMapReduce;
import org.Azgalor.hadoop.annotations.HOutput;
import org.Azgalor.hadoop.util.HadoopPop;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FileHServiceImpl implements HService {

	@Override
	public <T> void execute(Class<T> clz, String name) throws IOException,
			ClassNotFoundException, InterruptedException {
		Properties pop = HadoopPop.INSTANCE.get();
		String input = pop.getProperty("inputPath");
		String output = pop.getProperty("outputPath");
		if (StringUtils.isBlank(input)) {
			input = "/input/";
		}
		if (StringUtils.isBlank(output)) {
			output = "/output/";
		}
		Job job = Job.getInstance(HadoopConfig.INSRANCE.getConfiguration(),
				name);
		job.setJar(pop.getProperty("jar"));
		job.setJarByClass(clz);
		job.setMapperClass(clz.getAnnotation(HMapReduce.class).map());
		job.setCombinerClass(clz.getAnnotation(HMapReduce.class).combiner());
		job.setReducerClass(clz.getAnnotation(HMapReduce.class).reduce());
		job.setOutputKeyClass(clz.getAnnotation(HOutput.class).key());
		job.setOutputValueClass(clz.getAnnotation(HOutput.class).value());
		Path in = new Path(input + name + "/");
		Path out = new Path(output + name + "/");
		FileInputFormat.addInputPath(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.submit();
	}

}
