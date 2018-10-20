package xg.inclass.second_springboot.annotation;

import org.hibernate.validator.constraints.NotBlank;
import xg.inclass.second_springboot.validator.XgNotBlankValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Description:T00051
 * @Author: xuguang
 * @CreateDate: 2018/10/18 16:46
 * @Version: 1.0
 */
@Documented
@Constraint(validatedBy = {XgNotBlankValidator.class})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface XgNotBlank {

    String message() default "许光的不能为空";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    /**
     * Defines several {@code @NotBlank} annotations on the same element.
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        XgNotBlank[] value();
    }
}