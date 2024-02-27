package com.example.jbdl.demobeans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DemoController2 {
    @Autowired
    //@Qualifier("getTemplate2")//as created two bean of same return type
    @Qualifier("bean2")
    private RestTemplate restTemplate;
    //DemoController-> restTemplate org.springframework.web.client.RestTemplate@1d42a596
    //DemoController2->restTemplate org.springframework.web.client.RestTemplate@1d42a596
    private static Logger logger = LoggerFactory.getLogger(DemoController2.class);


    //http://localhost:8080/image2?id=20
    @GetMapping(value = "/image2", produces = MediaType.IMAGE_PNG_VALUE)//MediaType from org.springframework..
    // it converts byte[] to image response otherwise symbols in output on postman and everwhere
    //contentType is now image/png
    //@GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE) // image/jpeg
    //@GetMapping(value = "/image", produces = MediaType.IMAGE_GIF_VALUE) //image/gif but shows static image
    //@GetMapping(value = "/image", produces = MediaType.APPLICATION_PDF_VALUE)//aplication/pdf
    //in browser, pdf viewer opened but then says failed to load pdfs as proper encoding not done
    public byte[] getImage(@RequestParam(value="id") int id,
                           @RequestParam(value = "l", required = false, defaultValue = "200") int l,
                           @RequestParam(value = "b", required = false, defaultValue = "300") int b){
        String url="https://picsum.photos/id/"+id+"/"+l+"/"+b;//we can use string builder to optimise code
        //RestTemplate restTemplate = new RestTemplate();//every time we hit api, new object is created..
        // old object needs to be destroyed by garbage collector.. extra overhead is going on here..
        // we can create this at class so that same object will be used for every api call
        logger.info("restTemplate {}", restTemplate);
        byte[] response=restTemplate.getForObject(url, byte[].class);
        //getForObject is used call a particular url and response this url returns
        //byte[].class means i want to convert the response of url in the byte array
        //in java, image is stored as byte array
        return response;//currently response is application/octet-stream
        //content-length gives the number of bytes prresent in the response.. can see in response header in postman
        //if void->content-length=0,,, "Ron!"->content length = 4

    }

}
