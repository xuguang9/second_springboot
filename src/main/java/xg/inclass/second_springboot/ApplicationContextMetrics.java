package xg.inclass.second_springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/***
 * 自定义信息收集器
 */
@Component
public class ApplicationContextMetrics {

    private ApplicationContext context;
    @Autowired
    public ApplicationContextMetrics(ApplicationContext context) {
        this.context = context;
    }
    /*public Collection<Metric<?>> metrics() {
        List<Metric<?>> metrics = new ArrayList<Metric<?>>();
        metrics.add(new Metric<Long>("spring.context.startup-date",
                context.getStartupDate()));
        metrics.add(new Metric<Integer>("spring.beans.definitions",
                context.getBeanDefinitionCount()));
        metrics.add(new Metric<Integer>("spring.beans",
                context.getBeanNamesForType(Object.class).length));
        metrics.add(new Metric<Integer>("spring.controllers",
                context.getBeanNamesForAnnotation(Controller.class).length));
        return metrics;
    }*/
}
