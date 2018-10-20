package xg.inclass.second_springboot.jdk8;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import xg.inclass.second_springboot.dto.Book;

import java.util.*;
import static  java.util.function.Function.*;

import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * @Description: T00027
 * 2,stream的四大终端方法(即处理完后就是流的结尾)
    foreach,collect,reduce,toarray
    其中,对于collect方法的入参collectors,collectors的分组,统计,排序,取极值等等多种操作都有
 * @Author: xuguang
 * @CreateDate: 2018/5/19 16:03
 * @Version: 1.0
 */
public class TestStream {


    @Test
    public void funInterface(){
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
        filter(languages,name ->name.startsWith("J"),name->name.endsWith("a"));

    }
    public void filter(List<String > names, Predicate<String> first, Predicate<String > second){
        //下面两种表示一样效果
//        names.stream().filter(second).forEach(name-> System.out.println("筛选出来的结果="+name));
//        names.stream().filter(name->second.test(name)).forEach(name-> System.out.println("筛选出来的结果="+name));


        //用and把多条件合并
//        names.stream().filter(first.and(second)).forEach(name-> System.out.println("筛选出来的结果="+name));

        //map用法
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
        //costBeforeTax.stream().map(a->a+a*0.1).forEach(System.out::println);
        //reduce 聚合操作
        Double sum = costBeforeTax.stream().map(a -> a + a * 0.1).reduce((a, b) -> a + b).get();
        //用collectors转换新的list
        List<Integer> collect = costBeforeTax.stream().filter(a -> a > 200).
                collect(Collectors.toList());
        //聚合为一个字符串
        String collect1 = languages.stream().map(a -> a.toUpperCase()).
                collect(Collectors.joining(", "));
        //去平方再去重
        List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4,3);
        numbers.stream().map(a->a*a).distinct().collect(Collectors.toList());
//        numbers.stream().reduce()

        //非常实用的collectors的使用
        //统计元素出现的次数
        Map<Integer, Long> collect3 = numbers.stream()
                .collect(Collectors.groupingBy(identity(), Collectors.counting()));
        //统计元素的信息
        Map<Integer, IntSummaryStatistics> collect4 = numbers.stream()
                .collect(Collectors.groupingBy(identity(), Collectors.summarizingInt(p -> (Integer) p)));
//        找到拥有最长作者名字的书籍
        List<Book> list2 = new ArrayList<>();
        list2.add(new Book(1L,"xuguang111","duzhe1"));
        list2.add(new Book(2L,"xuguang2","duzhe2"));
        list2.add(new Book(2L,"xugua3","duzhe3"));
        list2.add(new Book(2L,"xugua3","duzhe3"));

        //collectingAndThen操作,就是在下面的maxBy方法执行过后,执行optional.get方法(即then的含义)
//      Book collect5 = list2.stream().collect(Collectors.collectingAndThen(
//               maxBy((a, b) -> a.getAuthor().length() - b.getAuthor().length()), Optional::get)); Book collect5 = list2.stream().collect(Collectors.collectingAndThen(
        Book collect5 = list2.stream().collect(Collectors.collectingAndThen(
               maxBy(Comparator.comparingInt(a -> a.getAuthor().length())), Optional::get));
        //统计所有id之和
        list2.stream().collect(summingInt(b->b.getId().intValue()));

        //分割,通过boolean条件分两个部分
        Map<Boolean, List<Book>> collect8 = list2.stream().collect(partitioningBy(a -> a.getId() > 1));


        //分组map
        //map的value就是对象
        Map<Long, List<Book>> collect6 = list2.stream().collect(
                groupingBy(b -> b.getId()));
        //map的value是自定义的集合
        Map<Long, List<Long>> collect7 = list2.stream().collect(
                groupingBy(b -> b.getId(), mapping(b -> b.getId(), toList())));
        //多次分组,嵌套使用,配合fastjson感觉更好
        Map<Long, Map<String, List<Book>>> collect9 = list2.stream().collect(
                groupingBy(b -> b.getId(),
                groupingBy(b -> b.getReader())));
        String st = JSONObject.toJSONString(collect9);
        System.out.println("st="+st);

        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        IntSummaryStatistics statistics = primes.stream().mapToInt(a -> a).summaryStatistics();
        statistics.getAverage();
        statistics.getCount();

        //list转换map
        List<Book> list = new ArrayList<>();
        list.add(new Book(1L,"xuguang"));
        list.add(new Book(2L,"xuguang2"));
        list.add(new Book(2L,"xuguang3"));
//        Map<Long, Book> collect2 = list.stream().collect(toMap(a -> a.getId(), v -> v));
        Map<Long, Book> collect2 = list.stream().collect(toMap(a -> a.getId(), v -> v,(a,b)->b));
        //System.out.println(collect2);


    }

}