package org.example.jbdl.demobeans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.UUID;

@Configuration("myConfig") //if comment this line.. @Autowired will cause fail to start at boot up time
// as autowired is used at class level not inside api
//@Scope("Singleton")//by default every bean is singleton so no need of this line
@Scope("prototype")// will create only on demand.. bean will be created only when needed
//whenever seen @Autowired.. each time creates a bean of this class.. now no matter how many times hit api of that
// autowured class..this same bean created for this autowired class will be used.. as
// in our autowired class.. bean is used at class level not at api level so created only once at class level
// we can check only by constructor printing..
//as this time will not print myConfig name
//but in sigleton.. only once creates bean of thus class

//if we comment autowired and prototype.. bean will not be created then

/*
for protypr beans, the object is initialesd when it is required/autowired  by some other class/beans
for singleton beans, spring will be creating the object in the begining itself irrespective of it is autowird or not
 */
public class DemoConfig {
    private static Logger logger = LoggerFactory.getLogger(DemoConfig.class);
    public DemoConfig(){
        //logger.info("in demo config constructor..");
        System.out.println("in demo config constructor..");
    }
    public String getConfig(){
        return "config::"+ UUID.randomUUID().toString();
    }
}
