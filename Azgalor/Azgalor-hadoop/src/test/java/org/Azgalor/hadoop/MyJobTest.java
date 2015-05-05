package org.Azgalor.hadoop;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.Azgalor.hadoop.entities.WapsWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.junit.Before;
import org.junit.Test;



public class MyJobTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		List<WapsWritable> list=new LinkedList<WapsWritable>();
		WapsWritable tmp=null;
		for(int i=0,n=10;i<n;i++){
			tmp=new WapsWritable(new Text("id_"+i), new Text("udid_"+i), new LongWritable(i));
			System.out.println(list.contains(tmp));
			list.add(tmp);
		}
		System.out.println(list.size());
		for(WapsWritable str:list){
			System.out.println(str.toString());
		}
		assertNotNull(list);
	}

}
