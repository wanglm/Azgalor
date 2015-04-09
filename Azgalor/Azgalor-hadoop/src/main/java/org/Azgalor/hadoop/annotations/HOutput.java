package org.Azgalor.hadoop.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**声明mapreduce输出的key,vlaue
 * @author ming
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HOutput {
	public Class<?> key();

	public Class<?> value();
}
