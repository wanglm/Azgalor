package org.Azgalor.hadoop.service;

import java.io.IOException;

import org.Azgalor.hadoop.entities.MongoBase;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;

public class DBaseHServiceImpl implements HService {

	@Override
	public void execute(Job job) throws IOException {
		DBInputFormat.setInput(job, MongoBase.class, "", "");
		DBOutputFormat.setOutput(job, "", "");

	}

}
