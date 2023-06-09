package com.example.jbdl.demoserver;

import org.springframework.web.bind.annotation.*;

@RestController//make this class visible to servlet container and it is acting as a point of interaction
public class SampleResource {
    // API
    // 1. Method
    // 2. Request
    // 3. Response
    // 4. Endpoint

    // HTTP server
    // HTTP Methods

    //types of api==rest api and soap api
    /** Characteristics of rest APIs
     * Data can be in any format
     * Stateless or idempotent
     **/

    /**
     Soap APIs depends on the state, they are not stateless
     **/

    /** types of api request methods or http methods (this is not type of api)
     1. GET --> A method which is used to retrieve some response from the BE -- Safe method

     2. POST --> A method which is used to save/store some data on the BE    -- Unsafe method
     3. PUT --> A method which is used to update the details on BE           -- Unsafe method
     4. DELETE --> A method which is used to delete some data on the BE      -- Unsafe method
     5. PATCH, HEAD, OPTIONS
     **/

    /**  Comparison b/w Spring web and Jax-rs
     Spring web                   Jax-rs
     GetMapping                   GET
     RequestParam                 QueryParam
     PathVariable                 PathParam

     **/

    // spring web - @RequestParam
    // jax-rs - @QueryParam

//    @RequestMapping(method = RequestMethod.GET)
    @GetMapping("/greet")
    public String greetMsg(@RequestParam("n") String name){
        return "Hi!There "+name;
    }
    @RequestMapping(value = "/greet2/{var}", method= RequestMethod.GET)
    public String greetMsg2(@PathVariable("var") String name){
        return "Hi "+name;
    }

    /** HTTP Status code
     * 200 -- OK
     * 500 -- Internal server error
     * 401 -- Unauthorized / Unauthenticated - Invalid entity / user (Spring security)
     * 403 -- Forbidden access - Valid entity but the operation you are trying to do is forbidden (Spring security)
     * 404 -- Not found (Trying to access something which is not present as a resource / API in your server )
     * 405 -- HTTP method not allowed
     **/



}
