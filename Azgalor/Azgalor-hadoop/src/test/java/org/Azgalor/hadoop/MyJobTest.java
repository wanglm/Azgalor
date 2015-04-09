package org.Azgalor.hadoop;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MyJobTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String s = null;
		for (Class<?> clz : MyJob.class.getClasses()) {
			s = clz.getSimpleName();
			System.out.println(s);
		}
		assertNotNull(s);
	}

}
