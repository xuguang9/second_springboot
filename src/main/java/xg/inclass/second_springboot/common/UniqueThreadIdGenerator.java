package xg.inclass.second_springboot.common;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author: xuguang
 * @CreateDate: 2018/5/19 21:15
 * @Version: 1.0
 */
public class UniqueThreadIdGenerator {
    private static AtomicInteger uniqueId = new AtomicInteger(0);

    private static ThreadLocal<Integer> uniqueNum = new ThreadLocal<Integer>() {
        @Override
        // 如果当前线程是第一次请求id的分配则给它赋一个初始值
        protected Integer initialValue() {
            return uniqueId.getAndIncrement();
        }
    };

    // 给当前线程返回它的id
    public static int getCurrentThreadId() {
        return uniqueNum.get();
    }

    // 设置当前线程的id
    public static void setCurrentThreadId(int id) {
        uniqueNum.set(id);
    }
}