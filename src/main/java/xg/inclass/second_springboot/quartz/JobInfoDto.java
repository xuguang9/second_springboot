package xg.inclass.second_springboot.quartz;

import lombok.Data;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/5/20 13:44
 * @Version: 1.0
 */
@Data
public class JobInfoDto {
    private Long id;

    private String cron;  //时间表达式

    private String status; //使用状态 0：禁用   1：启用

    private String jobName; //任务名称

    private String jobGroup; //任务分组

}