package org.Azgalor.hadoop;

import java.io.IOException;

import org.Azgalor.hadoop.annotations.HMapReduce;
import org.Azgalor.hadoop.annotations.HOutput;
import org.Azgalor.hadoop.service.FileHServiceImpl;
import org.Azgalor.hadoop.service.HService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**简单实例
 * @author ming
 *
 */
@HMapReduce(combiner = MyJob.Reduce.class, map = MyJob.MapClass.class, reduce = MyJob.Reduce.class)
@HOutput(key = Text.class, value = Text.class)
public class MyJob {

	public static class MapClass extends Mapper<Text, Text, Text, Text> {

		@Override
		protected void map(Text key, Text value,
				Mapper<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.map(key, value, context);
		}

	}

	public static class Reduce extends Reducer<Text, Text, Text, Text> {

		@Override
		protected void reduce(Text arg0, Iterable<Text> arg1,
				Reducer<Text, Text, Text, Text>.Context arg2)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
			super.reduce(arg0, arg1, arg2);
		}
	}

	public static void main(String... arg0) throws Exception {
		/*
		 * Configuration conf = new Configuration(); Job job =
		 * Job.getInstance(conf, "myJob"); job.setJarByClass(MyJob.class);
		 * job.setMapperClass(MapClass.class);
		 * job.setCombinerClass(Reduce.class);
		 * job.setReducerClass(Reduce.class); job.setOutputKeyClass(Text.class);
		 * job.setOutputValueClass(Text.class);
		 * 
		 * Path in = new Path(arg0[0]); Path out = new Path(arg0[1]);
		 * FileInputFormat.addInputPath(job, in);
		 * FileOutputFormat.setOutputPath(job, out);
		 * System.exit(job.waitForCompletion(true) ? 0 : 1);
		 */
		HService service = new FileHServiceImpl();
		service.execute(MyJob.class, "myJob");
	}

}
