package xg.inclass.second_springboot.dto.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @Description:T00049
 * @Author: xuguang
 * @CreateDate: 2018/10/15 21:44
 * @Version: 1.0
 */
@Data
public class BookRequsetParam {

    @NotBlank(message = "title标题不能为空")
    private String title;

}