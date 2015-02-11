package org.Azgalor.hadoop.service;

import java.io.IOException;

import org.apache.hadoop.mapreduce.Job;

public interface HService {
	public void execute(Job job) throws IOException, ClassNotFoundException, InterruptedException;
}
