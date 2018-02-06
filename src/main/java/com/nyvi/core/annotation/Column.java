package com.nyvi.core.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 字段注解
 * @author czk
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Column {

	/**
	 * 不必填
	 * <p>
	 * 默认字段的名称
	 * @return 表字段名称
	 */
	String name() default "";

	/**
	 * 不必填,是否为主键,
	 * @return 主键true,否则false
	 */
	boolean isKey() default false;
}
