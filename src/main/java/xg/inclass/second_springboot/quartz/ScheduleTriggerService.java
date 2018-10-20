package xg.inclass.second_springboot.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xg.inclass.second_springboot.dao.BookDao;

import java.util.List;

/**
 * @Description:T00014
 * @Author: xuguang
 * @CreateDate: 2018/5/20 13:52
 * @Version: 1.0
 */
@Service
@Slf4j
public class ScheduleTriggerService {

    //此处的Scheduler是 QuartzConfig 配置的那个
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private BookDao bookDao;

    //    @Scheduled(cron = "0/1 * * * * ?")  //每天晚上11点调用这个方法来更新quartz中的任务
    public void refreshTrigger() {
        try {
            //查询出数据库中所有的定时任务
            List<JobInfoDto> jobList = bookDao.getAllJobs();
            if (jobList != null) {
                for (JobInfoDto jobInfo : jobList) {
                    String status = jobInfo.getStatus(); //该任务目前的状态
                    TriggerKey triggerKey = TriggerKey.triggerKey(
                            jobInfo.getJobName(), jobInfo.getJobGroup());
                    CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                    //说明本条任务还没有添加到quartz中
                    if (null == trigger) {
                        if (status.equals("0")) { //如果是禁用，则不用创建触发器
                            continue;
                        }

                        JobDetail jobDetail = null;
                        try {
                            //创建JobDetail（数据库中job_name存的任务全路径，这里就可以动态的把任务注入到JobDetail中）
                            jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName( jobInfo.getJobName()))
                                    .withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup()).build();

                            //表达式调度构建器
                            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobInfo.getCron());
                            ///设置定时任务的时间触发规则
                            trigger = TriggerBuilder.newTrigger().
                                    withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup()).withSchedule(scheduleBuilder).build();
                            //把trigger和jobDetail注入到调度器
                            scheduler.scheduleJob(jobDetail, trigger);
                        } catch (ClassNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            log.error(e.getLocalizedMessage());
                        }

                    } else {  //说明查出来的这条任务，已经设置到quartz中了
                        // Trigger已存在，先判断是否需要删除，如果不需要，再判定是否时间有变化
                        if (status.equals("0")) { //如果是禁用，从quartz中删除这条任务
                            JobKey jobKey = JobKey.jobKey(jobInfo.getJobName(), jobInfo.getJobGroup());
                            scheduler.deleteJob(jobKey);
                            continue;
                        }
                        String searchCron = jobInfo.getCron(); //获取数据库的
                        String currentCron = trigger.getCronExpression();
                        if (!searchCron.equals(currentCron)) {  //说明该任务有变化，需要更新quartz中的对应的记录
                            //表达式调度构建器
                            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(searchCron);

                            //按新的cronExpression表达式重新构建trigger
                            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

                            //按新的trigger重新设置job执行
                            scheduler.rescheduleJob(triggerKey, trigger);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("定时任务每日刷新触发器任务异常，在ScheduleTriggerServiceImpl的方法refreshTrigger中，异常信息：", e);
        }
    }
}