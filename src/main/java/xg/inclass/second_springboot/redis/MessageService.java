package xg.inclass.second_springboot.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/5/29 21:55
 * @Version: 1.0
 */
@Slf4j
//@Component
public class MessageService {

    public void doService(String msg) {
        System.out.println("消息服务="+msg);
        log.info("msg="+msg);
    }
}