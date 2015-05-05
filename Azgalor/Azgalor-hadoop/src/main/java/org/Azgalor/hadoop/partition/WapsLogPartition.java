package org.Azgalor.hadoop.partition;

import org.Azgalor.hadoop.entities.WapsWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class WapsLogPartition extends Partitioner<LongWritable, WapsWritable>{

	@Override
	public int getPartition(LongWritable key, WapsWritable value, int numPartitions) {
		return value.getUdid().hashCode()%numPartitions;
	}

}
