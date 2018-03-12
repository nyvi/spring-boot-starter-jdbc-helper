package com.nyvi.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 表字段注解
 * </p>
 * @author czk
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

	/**
	 * <p>
	 * 字段值（驼峰命名方式，该值可无）
	 * </p>
	 * @return 表字段名称
	 */
	String value() default "";

}
