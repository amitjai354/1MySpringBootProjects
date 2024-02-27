package org.example.jbdl.demobeans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController("myController")
public class DemoController {
    //DemoConfig demoConfig=new DemoConfig();
    @Autowired//this annotation tells spring to inject already created bean in this class
    DemoConfig demoConfig;
    /**
     * IOC-Instead of you creating object yourself, spring will be creating one for you
          but you need to notify spring that by writing annotations or xml files
     After the object is created by Spring it will be stored in IOC Container/Application Context(Interface)
     **/

    private static Logger logger= LoggerFactory.getLogger(DemoController.class);
    @GetMapping("/dummy")
    public String generateRandomStr(){
        System.out.println("inside generateRandomStr function"+System.currentTimeMillis());//+ takes time but in logger no need of +
        String randomString = UUID.randomUUID().toString();
        logger.error("random string is {} at time {}",randomString,System.currentTimeMillis());
        logger.warn("random string is {} at time {}",randomString,System.currentTimeMillis());
        logger.info("random string is {} at time {}",randomString,System.currentTimeMillis());
        logger.debug("random string is {} at time {}",randomString,System.currentTimeMillis()); //this will not work
        // as by default logging level is info for all the packages.. so  debug will not work
        //logger.trace("random string is {} at time {}",randomString,System.currentTimeMillis());
        /*
        use logger.error in case of exception inside catch block..
        if some value is null.. we can use warn.. if this is not stooping program
        use trace in case teling some informatio like what below codes are doing..
         */
        return randomString;
    }

    @GetMapping("/config")
    public String getConfig(){
        //DemoConfig demoConfig = new DemoConfig();
        logger.info("demoConfig is {}",demoConfig);
        return demoConfig.getConfig();
    }
    //on hitting congig api multiple times if we are creating object inside this api
    //demoConfig is org.example.jbdl.demobeans.DemoConfig@3b85d955
    //demoConfig is org.example.jbdl.demobeans.DemoConfig@744bb41f

    //on hitting congig api multiple times if we are creating object outside this api at class level
    //demoConfig is org.example.jbdl.demobeans.DemoConfig@12eeb2c7
    //demoConfig is org.example.jbdl.demobeans.DemoConfig@12eeb2c7
}
