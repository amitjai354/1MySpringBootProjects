package org.example.jbdl.demobeans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController2 {
    //DemoConfig demoConfig2=new DemoConfig();
    @Autowired//loose coupling of objects
    DemoConfig demoConfig2;
    private static Logger logger = LoggerFactory.getLogger(DemoController2.class);
    @GetMapping("/config2")
    public String getConfig(){
        logger.info("demoConfig2 is {}", demoConfig2);
        return demoConfig2.getConfig();//null.something is NULL Pointer exception if we comment autowired and scope is prototype
    }
    //demoConfig2 is org.example.jbdl.demobeans.DemoConfig@216d37
    //demoConfig2 is org.example.jbdl.demobeans.DemoConfig@216d37
    //demoConfig is org.example.jbdl.demobeans.DemoConfig@50372cf5

    //demoConfig is  org.example.jbdl.demobeans.DemoConfig$$SpringCGLIB$$0@2712c5dc
    //demoConfig is  org.example.jbdl.demobeans.DemoConfig$$SpringCGLIB$$0@2712c5dc
    //demoConfig2 is org.example.jbdl.demobeans.DemoConfig$$SpringCGLIB$$0@2712c5dc
    //demoConfig2 is org.example.jbdl.demobeans.DemoConfig$$SpringCGLIB$$0@2712c5dc
}
