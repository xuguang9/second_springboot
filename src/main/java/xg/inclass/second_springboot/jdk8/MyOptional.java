package xg.inclass.second_springboot.jdk8;

import java.util.Optional;

/**
 * @Description: T00012
 * @Author: xuguang
 * @CreateDate: 2018/5/14 22:56
 * @Version: 1.0
 */
public class MyOptional {
     public static void main(String[] args) {
       MyOptional.testOptional("tom");
      //  MyOptional.testOptional(null);

      }

      public static void testOptional(String option){
          Optional<String> option1 = Optional.ofNullable(option);
//          System.out.println("option1 is set ? ="+option1.isPresent());
//          System.out.println("option1 name ? ="+option1.orElseGet(()->"[none]"));
//          String s1 = option1.orElse("xuugang");
         System.out.println("optionl map="+option1.map(s -> "hey "+s +"!").map(s->s+"再加!").orElse("hey none!"));
          Optional<String> s2 = option1.filter(s -> s.contains("包含内容"));
          //System.out.println("s2"+s2.isPresent());
          option1.ifPresent(System.out::print);
      }
}
