package org.Azgalor.hadoop.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

/**声明mapreduce必须的类
 * @author ming
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HMapReduce {
	public Class<? extends Mapper<?, ?, ?, ?>> map();

	public Class<? extends Reducer<?, ?, ?, ?>> reduce();

	public Class<? extends Reducer<?, ?, ?, ?>> combiner();

}
