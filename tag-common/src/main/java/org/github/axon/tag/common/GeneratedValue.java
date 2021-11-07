package org.github.axon.tag.common;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ID自动生成注解
 * 定义一个注解来标识此字段需要自动增长ID
 * 有些场景下可能不需要自动增长，需要自动增长的时候我们需要加上这个注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface GeneratedValue {

}