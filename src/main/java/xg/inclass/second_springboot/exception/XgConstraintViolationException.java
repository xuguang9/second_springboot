package xg.inclass.second_springboot.exception;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/10/18 13:33
 * @Version: 1.0
 */
public class XgConstraintViolationException extends ConstraintViolationException {
    public XgConstraintViolationException(String message, Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(message, constraintViolations);
    }
}