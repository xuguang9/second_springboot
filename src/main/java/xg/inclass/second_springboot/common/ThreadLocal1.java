package xg.inclass.second_springboot.common;

/**
 * @Description:T00009
 * @Author: xuguang
 * @CreateDate: 2018/5/19 21:16
 * @Version: 1.0
 */
public class ThreadLocal1 implements Runnable{
    @Override
    public void run() {
        // 线程的id是在它第一次run的时候才分配的，它run，它请求分配id，系统给它一个id
        int id = UniqueThreadIdGenerator.getCurrentThreadId();
        System.out.println(Thread.currentThread().getName() + " is running, its ID is: " + id);

        // 三次向系统请求数据
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is asking for data, my ID is:" + id);
        }
        System.out.println(Thread.currentThread().getName() + " is over!----------");
    }

    public static void main(String[] args) {
        // 新建3个线程
        Thread tA = new Thread(new ThreadLocal1(), "A");
        Thread tB = new Thread(new ThreadLocal1(), "B");
        Thread tC = new Thread(new ThreadLocal1(), "C");
        tA.start();
        tB.start();
        tC.start();
    }
}