package com.nyvi.support.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.nyvi.support.enums.Operate;


/**
 * <p>
 * 查询字段注解
 * </p>
 * @author czk
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface Query {

	/**
	 * 不必填
	 * <p>
	 * 默认字段的名称
	 * @return 表字段名称
	 */
	String value() default "";

	/**
	 * 操作符
	 * <p>
	 * 默认执行 等于 =
	 * @return 基本操作符
	 */
	Operate operate() default Operate.EQ;

	/**
	 * 不必填
	 * <p>
	 * @return 参数内容前缀
	 */
	String prefix() default "";

	/**
	 * 不必填
	 * <p>
	 * @return 参数内容后缀
	 */
	String suffix() default "";
}
