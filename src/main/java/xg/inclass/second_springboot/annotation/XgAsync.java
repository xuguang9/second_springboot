package xg.inclass.second_springboot.annotation;

import org.springframework.retry.annotation.Retryable;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XgAsync {

    String value() default "";

}

