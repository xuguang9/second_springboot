package xg.inclass.second_springboot.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/5/20 17:26
 * @Version: 1.0
 */
@Slf4j
public class DemoThread implements Runnable {

    private String threadName;

    public DemoThread() {
    }

    public DemoThread(String threadName) {
        super();
        this.threadName = threadName;
    }

    @Override
    public void run() {
        log.info("threadName="+threadName);
    }
}