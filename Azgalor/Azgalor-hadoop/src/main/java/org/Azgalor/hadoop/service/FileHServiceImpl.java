package org.Azgalor.hadoop.service;

import java.io.IOException;
import java.util.Properties;

import org.Azgalor.hadoop.util.HadoopPop;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FileHServiceImpl implements HService {

	@Override
	public void execute(Job job) throws IOException, ClassNotFoundException, InterruptedException {
		Properties pop = HadoopPop.INSTANCE.get();
		String input = pop.getProperty("inputPath");
		String output = pop.getProperty("outputPath");
		if (StringUtils.isBlank(input)) {
			input = "/input/";
		}
		if (StringUtils.isBlank(output)) {
			output = "/output/";
		}
		Path in = new Path(input);
		Path out = new Path(output);
		FileInputFormat.addInputPath(job, in);
		FileOutputFormat.setOutputPath(job, out);
		job.submit();
	}

}
