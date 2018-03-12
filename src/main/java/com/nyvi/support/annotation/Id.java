package com.nyvi.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.nyvi.support.enums.IdType;

/**
 * <p>
 * 表主键注解
 * </p>
 * @author czk
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {

	/**
	 * <p>
	 * 字段值（驼峰命名方式，该值可无）
	 * </p>
	 * @return id名称
	 */
	String value() default "";

	/**
	* <p>
	* 主键ID
	* </p>
	* {@link IdType}
	* @return id生成策略
	*/
	IdType type() default IdType.ID_WORKER;
}
