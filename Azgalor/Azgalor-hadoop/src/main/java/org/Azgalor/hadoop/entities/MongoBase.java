package org.Azgalor.hadoop.entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.mapreduce.lib.db.DBWritable;

public class MongoBase implements DBWritable {

	@Override
	public void write(PreparedStatement statement) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFields(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub

	}

}
