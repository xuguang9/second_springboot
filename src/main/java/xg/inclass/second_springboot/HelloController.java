package xg.inclass.second_springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Conditional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Predicate;

@RestController(value = "helloController")
//@Conditional(FirstCondition.class)
//@FirstConditionAnn 测试自定义条件注解
@EnableConfigurationProperties({AmazonProperties.class})
public class HelloController {

    @Value("${myName}")
    private String name;
    @Autowired
    private AmazonProperties amazonProperties;

    @RequestMapping(value = "/hello",  method = RequestMethod.GET)
    public String sayHello() {
        return "这个第一个spring boot demo,热部署加上的,现在"+name+"," +
                "amazon="+amazonProperties.getAssociateId();
    }


    public static void main(String[] args) {
        Predicate predicate = (s)->s.equals("xu");
        Negate negate1= new Negate() {
            @Override
            public Predicate getNegate() {
                return predicate.negate();
            }
        };
        Negate negate2= () ->predicate.negate();
        Negate negate3= predicate::negate;
        Predicate negate = predicate.negate();

    }

}
