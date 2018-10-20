package xg.inclass.second_springboot.postProcessor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationInterceptor;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import xg.inclass.second_springboot.interceptor.XgValidationInterceptor;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/10/18 15:34
 * @Version: 1.0
 */
@Component
public class XgMethodValidationPostProcessor extends MethodValidationPostProcessor {


    @Override
    public void afterPropertiesSet() {
        Pointcut pointcut = new AnnotationMatchingPointcut(Validated.class, true);
        super.advisor = new DefaultPointcutAdvisor(pointcut, createMethodValidationAdvice( Validation.buildDefaultValidatorFactory().getValidator()));
    }



    protected Advice createMethodValidationAdvice(Validator validator) {
        return (validator != null ? new XgValidationInterceptor(validator) : new XgValidationInterceptor());
    }
}