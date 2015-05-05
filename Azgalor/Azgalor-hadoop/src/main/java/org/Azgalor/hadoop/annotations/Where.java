package org.Azgalor.hadoop.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.Azgalor.hadoop.eumuns.Logic;

/**查询条件注解
 * @author ming
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Where {
	/**查询条件key
	 * @return String
	 */
	public String key();
	/**查询条件value
	 * @return String
	 */
	public String value();
	/**逻辑not,默认false
	 * @return boolean
	 */
	public boolean not() default false;
	/**逻辑枚举
	 * @return enum
	 */
	public Logic logic();

}
