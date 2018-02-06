package com.nyvi.core.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 表名注解
 * @author czk
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {

	/**
	 * 不必填
	 * <p>
	 * 默认实体类的名称
	 * @return 表名称
	 */
	String name() default "";
}
