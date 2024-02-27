package org.example.jbdl.demobeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo3")//giving a common name at class level for api(need to give this annotation above class name)
//now endpint is.. /demo3/config3
public class DemoController3 {
    @Autowired
    DemoConfig demoConfig3;


    @GetMapping("/config3")
        public String getConfig(){
        return demoConfig3.getConfig();
        }
}
