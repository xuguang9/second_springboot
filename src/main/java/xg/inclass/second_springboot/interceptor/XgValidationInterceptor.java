package xg.inclass.second_springboot.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.validator.HibernateValidator;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.annotation.Validated;
import xg.inclass.second_springboot.exception.XgConstraintViolationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/10/18 15:30
 * @Version: 1.0
 */
public class XgValidationInterceptor implements MethodInterceptor {
    private static Method forExecutablesMethod;

    private static Method validateParametersMethod;

    private static Method validateReturnValueMethod;

    static {
        try {
            forExecutablesMethod = Validator.class.getMethod("forExecutables");
            Class<?> executableValidatorClass = forExecutablesMethod.getReturnType();
            validateParametersMethod = executableValidatorClass.getMethod(
                    "validateParameters", Object.class, Method.class, Object[].class, Class[].class);
            validateReturnValueMethod = executableValidatorClass.getMethod(
                    "validateReturnValue", Object.class, Method.class, Object.class, Class[].class);
        }
        catch (Exception ex) {
            // Bean Validation 1.1 ExecutableValidator API not available
        }
    }


    private volatile Validator validator;


    /**
     * Create a new MethodValidationInterceptor using a default JSR-303 validator underneath.
     */
    public XgValidationInterceptor() {
        this(forExecutablesMethod != null ? Validation.buildDefaultValidatorFactory() :
                Validation.byProvider(HibernateValidator.class).configure().buildValidatorFactory());
    }

    /**
     * Create a new MethodValidationInterceptor using the given JSR-303 ValidatorFactory.
     * @param validatorFactory the JSR-303 ValidatorFactory to use
     */
    public XgValidationInterceptor(ValidatorFactory validatorFactory) {
        this(validatorFactory.getValidator());
    }

    /**
     * Create a new MethodValidationInterceptor using the given JSR-303 Validator.
     * @param validator the JSR-303 Validator to use
     */
    public XgValidationInterceptor(Validator validator) {
        this.validator = validator;
    }


    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Class<?>[] groups = determineValidationGroups(invocation);

        Object execVal;
        try {
            execVal = ReflectionUtils.invokeMethod(forExecutablesMethod, this.validator);
        }
        catch (AbstractMethodError err) {
            // Probably an adapter (maybe a lazy-init proxy) without BV 1.1 support
            Validator nativeValidator = this.validator.unwrap(Validator.class);
            execVal = ReflectionUtils.invokeMethod(forExecutablesMethod, nativeValidator);
            // If successful, store native Validator for further use
            this.validator = nativeValidator;
        }

        Method methodToValidate = invocation.getMethod();
        Set<ConstraintViolation<?>> result;

        try {
            result = (Set<ConstraintViolation<?>>) ReflectionUtils.invokeMethod(validateParametersMethod,
                    execVal, invocation.getThis(), methodToValidate, invocation.getArguments(), groups);
        }
        catch (IllegalArgumentException ex) {
            // Probably a generic type mismatch between interface and impl as reported in SPR-12237 / HV-1011
            // Let's try to find the bridged method on the implementation class...
            methodToValidate = BridgeMethodResolver.findBridgedMethod(
                    ClassUtils.getMostSpecificMethod(invocation.getMethod(), invocation.getThis().getClass()));
            result = (Set<ConstraintViolation<?>>) ReflectionUtils.invokeMethod(validateParametersMethod,
                    execVal, invocation.getThis(), methodToValidate, invocation.getArguments(), groups);
        }
        getExceptionMsg(result);

        Object returnValue = invocation.proceed();
        result = (Set<ConstraintViolation<?>>) ReflectionUtils.invokeMethod(validateReturnValueMethod,
                execVal, invocation.getThis(), methodToValidate, returnValue, groups);
        getExceptionMsg(result);
        return returnValue;
    }



    private void getExceptionMsg(Set<ConstraintViolation<?>> result) {
        if (!result.isEmpty()) {
            StringBuilder sb = new StringBuilder("");
            for (Iterator<ConstraintViolation<?>> it = result.iterator(); it.hasNext();) {
                ConstraintViolation<?> violation = it.next();
                sb.append(violation.getMessage());
                if (it.hasNext()) {
                    sb.append("; ");
                }
            }
            throw new XgConstraintViolationException(sb.toString(),result);
        }
    }

    protected Class<?>[] determineValidationGroups(MethodInvocation invocation) {
        Validated validatedAnn = AnnotationUtils.findAnnotation(invocation.getMethod(), Validated.class);
        if (validatedAnn == null) {
            validatedAnn = AnnotationUtils.findAnnotation(invocation.getThis().getClass(), Validated.class);
        }
        return (validatedAnn != null ? validatedAnn.value() : new Class<?>[0]);
    }

}