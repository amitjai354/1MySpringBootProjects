package com.example.jbdl.demobeans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@Configuration(value = "myConfig")//contains @Controller to tell spring to create bean of this class
//@Scope("prototype")
//if make this as prototype but inside beans are sigleton.. beans work as singleton only.. created once only
public class DemoConfig {

    //@Component:ElementTarget is Type.. can use above class, interfacew, enum..
    //Component annotation is used to define our own class as a bean
    //@Bean: Element Target is Method and above some annotation.. can not use above class, interfacew, enum
    //Bean annotation is used to define beans of objects which are part of source code

    // bean is created of DemoConfig as well as getTemkplate also..
    //Actually when @Autowired restTemplate is seen in DemoController class then spring searches for restTemplate bean
    //by scanning @Component in all clkasses.. so bean is created of any class having this annotation..

    //bean is stored as key - value pair in ioc container
    //key is the bean name and value is object reference ( object type)
    //@Component- by default bean name is class name in cammel case
    //@Bean- by default bean name is the method name in camel case

    private static Logger logger = LoggerFactory.getLogger(DemoConfig.class);


    //@Bean(name = "myTemplate")//just check inside source code of this annotation to see if there is name or value..
    // to give name
    //@Scope("prototype")
    //but scope of democonfig class is singlrtyin.. but still workinhg
    //now a different bean will be created for myTemplate in Democontroller2 than in democontroller
    //but inside democontroller2, no matter how many times hit api.. same bean as autowired at class level
    @Bean("bean1")
    //@Primary//we can not create two beans of same return type.. make one as primary
    //or use @Qualifier and provide bean name that you want to use at the time of autowiring in all the classes
    //even if we give different names to beans but still error as in key value pair.. value is same(same type of bean)
    //this issue will not come with @Component as two classes are alwways different.. in one package there can not be
    // two classes with same name.. and if we change package.. then this bean will be different now even is same class
    //name.. as beans considers complete path also
    public RestTemplate getTemplate(){
        logger.info(("In getTemplate function...."));//singleton bean: this log will be printed only at time of
        // boot up.. once bean is created, spring will not go inside this method, instead fetch from ioc container
        //but if scope is prototype and we are not autowiring at class level, instead autowiring inside api..
        //then for each api call this log will be printed.. if done at class level then only once this log will bw
        //printed at boot up time as initially spring scans all autowired annotation and creates bean
        //if we have autowired tyhius class demopconfig at class level in democontroller and aiutowired this bean
        //at api level.. then also one log will be printed at boot up time then log will be printed at each api call
        return new RestTemplate();
    }

    @Bean("bean2")
    public RestTemplate getTemplate2(){
        logger.info(("In getTemplate2 function...."));
        return new RestTemplate();
    }
}
