package org.Azgalor.mongodb.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.Azgalor.mongodb.enums.MongoType;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mongoz {
	public MongoType value() default MongoType.SIMPLE;
}
