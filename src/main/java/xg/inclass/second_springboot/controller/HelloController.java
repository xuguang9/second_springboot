package xg.inclass.second_springboot.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.quartz.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xg.inclass.second_springboot.annotation.ValidParam;
import xg.inclass.second_springboot.annotation.XgNotBlank;
import xg.inclass.second_springboot.common.XgConstants;
import xg.inclass.second_springboot.dao.BookDao;
import xg.inclass.second_springboot.dto.Book;
import xg.inclass.second_springboot.dto.dto.BookRequsetParam;
import xg.inclass.second_springboot.exception.DaoException;
import xg.inclass.second_springboot.properties.AmazonProperties;
import xg.inclass.second_springboot.properties.OuterApplicationProperties;
import xg.inclass.second_springboot.properties.ValueList;
import xg.inclass.second_springboot.quartz.HelloJob;
import xg.inclass.second_springboot.quartz.JobInfoDto;
import xg.inclass.second_springboot.quartz.ScheduleTriggerService;
import xg.inclass.second_springboot.service.BookService;
import xg.inclass.second_springboot.thread.DemoThread;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

@RestController(value = "helloController")
//@Conditional(FirstCondition.class)
//@FirstConditionAnn //测试自定义条件注解
@EnableConfigurationProperties({AmazonProperties.class})
@Slf4j
@Validated
public class HelloController {


    @Value("${myName}")
    private String name;

    @Autowired
    private AmazonProperties amazonProperties;

    @Autowired
    @Qualifier("bookService")
    private BookService bookService;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private OuterApplicationProperties outerApplicationProperties;

    @Autowired
    private ValueList valueList;

    @Autowired //这个调度器来自于myconfig类的multitaskScheduler
    private Scheduler scheduler;

    @Autowired(required = false) //调持久化到数据库的job
    private ScheduleTriggerService scheduleTriggerService;

    @Autowired(required = false)
    @Qualifier("threadPoolTaskExecutor")
    private Executor executor;

    @Autowired(required = false)
    @Qualifier("rmiService")
    private  RmiProxyFactoryBean rmiService;

    @Autowired
    private RedisTemplate stringRedisTemplate;

    @Autowired(required = false)
    private JobLauncher jobLauncher;
    @Autowired(required = false) //T00041
    private Job demoJob;

    //入参string校验自定义的注解
    //T00051
    @PostMapping("/inputParam3")
    public Object inputParam3( @RequestParam @XgNotBlank(message = "许光接口处信息") String  title) throws Exception {
        return   "ok";
    }


    //入参string校验
    @PostMapping("/inputParam2")
    public Object inputParam2( @RequestParam @NotBlank(message = "书籍标题不能为空") String  title) throws Exception {
        return   "ok";
    }

    //自定义包装抛出的校验异常
    /*ConstraintViolationException exception = (ConstraintViolationException) e;
    Set<ConstraintViolation<?>> result = exception.getConstraintViolations();
    StringBuilder sb = new StringBuilder("");
    if (!result.isEmpty()) {
        for (Iterator<ConstraintViolation<?>> it = result.iterator(); it.hasNext();) {
            ConstraintViolation<?> violation = it.next();
            sb.append(violation.getPropertyPath()).append(" - ").append(violation.getMessage());
            if (it.hasNext()) {
                sb.append("; ");
            }
        }
    }
    throw  new RuntimeException(sb.toString());*/


    //入参对象校验,对象bean上加这种 @NotBlank, 入参的时候,加 @Valid T00049
    @PostMapping("/inputParam1")
    public Object inputParam1(@RequestBody@Valid BookRequsetParam  bookRequsetParam) throws Exception {
        Book book = bookDao.getBook(bookRequsetParam.getTitle());
        return book;
    }

    //<update 标签if=0报错,T00048
    @GetMapping("/updateIf/{id}")
    public Object updateIf(@PathVariable Long  id) throws Exception {
        int i = bookDao.updateBookById(id);
        return i;
    }

    //自定义异步
    @GetMapping("/testXgAsync")
    public Object testXgAsync() throws Exception {
        bookService.runAsync(1L);
        System.out.println("现在执行controller");
        return "ok";
    }


    //批处理执行
    @GetMapping("/startBatch")
    public Object startBatch() throws Exception {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addDate("now", new Date());
        jobLauncher.run(demoJob,jobParametersBuilder.toJobParameters());
        return "ok";
    }

    //查询所有job列表
    @GetMapping("/getList")
    public Object getList() throws Exception {
        List<JobInfoDto> jobs = bookDao.getAllJobs();
        return jobs;
    }

    //redis订阅发布
    @GetMapping("/msgPublish")
    public Object msgPublish() {
        stringRedisTemplate.convertAndSend(XgConstants.MSG,"这是发布的消息");
        return "ok";
    }

    //测试缓存
    @GetMapping("/getBookByCache/{id}")
    public Object getBookByCache(@PathVariable Long id){
        return bookService.getBookById(id);
    }

    //测试redis连接
    @GetMapping("/getRedisByKey/{key}")
    public Object getRedisByKey(@PathVariable String key) {
        stringRedisTemplate.opsForValue().set(key,"xuguangg");
        Object o = stringRedisTemplate.opsForValue().get(key);
        return o;
    }

    //远程调用
    @GetMapping("/getRmi")
    public Object getRmi() {
        BookService byId = (BookService)rmiService.getObject();
        return byId.getBookById(1L);
    }

    //第一个hello请求
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello() {
        return "这个第一个spring boot demo,热部署加上的,现在" + name + "," +
                "amazon=" + amazonProperties.getAssociateId();
    }

    //获取外部的配置properties值
    @GetMapping("/getOuterConfig")
    public String getOuterConfig() {
        return "姓名=" + outerApplicationProperties.getName() + ",年龄=" + outerApplicationProperties.getAge();
    }

    //测试sql级别的日志
    @GetMapping("/getBookById")
    public Object getBookById() throws SQLException {
        return bookService.getBookById(1L);
    }

    //数据库配置valuelist值
    @GetMapping("/getValueList")
    public Object getValueListByKey(String key) {
        return valueList.getStoreByKey(key);
    }

    //返回异常信息的message转换
    @GetMapping("/getError")
    public Object getError(String name) {
        if (name.equals("Y")) {
            return "true";
        } else {
            throw new DaoException("1001", "controller层抛出");
        }
    }

    //登录拦截器,拦截/login下面的
    @GetMapping("/login/{name}")
    public Object loginByName(@PathVariable String name) {
        if (name.startsWith("xg")) {
            return "OK";
        } else {
            return "NO_OK";
        }
    }

    //session过滤器,过滤/filter下面的
    @GetMapping("/filter/{name}")
    public Object filterSession(@PathVariable String name) {
        if (name.startsWith("xg")) {
            return "OK";
        } else {
            return "NO_OK";
        }
    }


    //启动job的接口,这只是了解原理的demo,没有持久化到数据库
    @GetMapping("/startJob")
    public Object startJob() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).
                withIdentity("job2", "group1").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/3 * * * * ?");
        // 每3s执行一次
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1").withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
        return "start Ok";
    }

    //启动持久化到数据库的job
    @GetMapping("/startDBJob")
    public Object startDBJob() {
        scheduleTriggerService.refreshTrigger();
        return "start Ok";
    }

    //spring线程池调用T00019
    @GetMapping("/threadPool")
    public Object runThreadPool() {
        executor.execute(new DemoThread("myFirstThread"));
        return "runThreadPool ok";
    }

    public static void main(String[] args) {

    }

}
