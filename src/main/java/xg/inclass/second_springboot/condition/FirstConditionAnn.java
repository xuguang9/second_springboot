package xg.inclass.second_springboot.condition;

import org.springframework.context.annotation.Conditional;

@Conditional(FirstCondition.class)
public @interface FirstConditionAnn {
    Class<?>[] value()  default {};

}
