package xg.inclass.second_springboot.functionalInterface;

@FunctionalInterface
public interface MyConsumer<T> {

    /**
     * 消费类,只是提供参数,不返还
     * @param t
     */
    void accept(T t);

}
