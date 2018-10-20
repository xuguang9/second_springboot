package xg.inclass.second_springboot.jdk8;

import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @Description:T00021 函数接口,方法引用
 * 1,四大常用函数接口 function,consumer,supplier,predicate
 * @Author: xuguang
 * @CreateDate: 2018/5/17 21:16
 * @Version: 1.0
 */
public class Lambda {

    static class Car {
        public static Car create( final Supplier< Car > supplier ) {
            return supplier.get();
        }

        public static void collide( final Car car ) {
            System.out.println( "Collided " + car.toString() );
        }

        public void follow( final Car another ) {
            System.out.println( "Following the " + another.toString() );
        }

        public void repair() {
            System.out.println( "Repaired " + this.toString() );
        }
    }

    @Test//方法引用
    public void testCar(){
        //1,supplier 无入参,但有返回,且规定返回是car对象
        Car.create(new Supplier<Car>() {
            @Override
            public Car get() {
                return new Car();
            }
        });
        Car car1 =  Car.create(()-> new Car());
        Car car = Car.create(Car::new);

        List<Car> cars = Arrays.asList(car);

        //2,consumer ,有入参,但无返回,且入参是car对象,
        cars.forEach(Car::collide);

        //3,consumer ,有入参,但无返回,且入参是car对象
        cars.forEach(Car::repair);
        cars.forEach(new Consumer<Car>() {
            @Override
            public void accept(Car car) {
                Car.collide(car);
                car.repair();
            }
        });

        //4,consumer ,有入参,但无返回,且入参是car对象,
        cars.forEach(car::follow);
        cars.forEach(new Consumer<Car>() {
            @Override
            public void accept(Car car) {
                car1.follow(car);
            }
        });

    }

    @Test
    public void simpleTest(){
        //下面1,2含义相同,与3含义不同, 方法引用只能是简单的参数处理,
        Arrays.asList("a","b","c").forEach(System.out::println);//1
        Arrays.asList("a","b","c").forEach(s -> System.out.println(s));//2

        Arrays.asList("a","b","c").forEach(s -> System.out.println("s"+s));//3


        Arrays.asList("a","b","c").sort(String::compareTo);
    }


}
