package xg.inclass.second_springboot.quartz;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Description:T00014
 * @Author: xuguang
 * @CreateDate: 2018/5/20 12:41
 * @Version: 1.0
 */
@Slf4j
public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        boolean isExecute = false;  //是否已执行业务逻辑
        boolean flag = false;  //业务逻辑执行后返回结果
        try {
            //可以通过context拿到执行当前任务的quartz中的很多信息，如当前是哪个trigger在执行该任务
            CronTrigger trigger = (CronTrigger) context.getTrigger();
            String corn = trigger.getCronExpression();
            String jobName = trigger.getKey().getName();
            String jobGroup = trigger.getKey().getGroup();
            log.info("corn:"+corn);
            log.info("jobName:"+jobName);
            log.info("jobGroup:"+jobGroup);
        }catch (Exception e){
            e.printStackTrace();
        }
       log.info("执行任务中");
    }
}