package org.Azgalor.solr.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.Azgalor.solr.enums.Solr;

/**声明solr的组织类型：单机还是集群
 * @author ming
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SolrType {
	Solr value() default Solr.SIMPLE;
}
