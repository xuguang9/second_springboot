package xg.inclass.second_springboot.exception;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/5/19 23:05
 * @Version: 1.0
 */
@Component
@Data
public class DaoException extends RuntimeException {
    private String code;


    public DaoException() {
    }

    public DaoException(String code, String message) {
        super(message);
        this.code = code;
    }


}