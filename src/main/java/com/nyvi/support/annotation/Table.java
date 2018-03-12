package com.nyvi.support.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <p>
 * 表名注解
 * </p>
 * @author czk
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Table {

	/**
	 * <p>
	 * 字段值（驼峰命名方式，该值可无）
	 * </p>
	 * @return 表名称
	 */
	String value() default "";
}
