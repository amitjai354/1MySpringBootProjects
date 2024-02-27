package com.example.jbdl.demobeans;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import jakarta.validation.Valid;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController// retention policy is RUNTIME as needed by dispatcher servlet to map request to this class at runtime
//this is just to make this controller visible to the dispatcher servlet so that
// servlet(dispatcher servlet) can see which method to invoke depending on the basis of api endpoint and http method
//RequestMappingHandlerMapping class is doing this mapping. dispatcher servlet redirects call to this class
//if fn is availble RMHM passes request to HandlerMethod class else 404 Not found
//HandlerMethod class invokes the fn in the respective controller. if there is any error then this will be resolved by
//DefaultHandlerExceptionResolver class and 400 status  bad request is given.
//but if no error is there.. it will take you to controilleer and starts executing whatever code is written there
//and at last dispatcher servlet gives 200 ok status

//@Controller
@RequestMapping("/api/v1")
//@ResponseBody
public class DemoController {


    //RestTemplate restTemplate = new RestTemplate();
    //new keyword here rersembles prototype scope
    @Autowired
    //@Qualifier("getTemplate")//as created two bean of same return type
    @Qualifier("bean1")
    RestTemplate restTemplate;
    //this object is created only once.. at class level.. but we are
    //creating this object so tight coupling/binding.. if spring creates it as bean.. can use in any class
    //but this code is written in Library.. this class is not created by us.. so how to create bean of this class
    //we need give @Component above this class to tell spring to create bean of this class
    //one way is we create our own class that extends this class then we can write @Component above this class
    //child class object can acess parent class propertues.. but what if this parent class has lots of abstract fn..
    //then we need to override all these methods.. so not good way..
    //using MyRestTemplate class  we can create only one bean but we cvan not create beans of other Library classes
    //use @Bean.. create new class.. but in this clas we can create beran of any library class we want..

    public DemoController(int count){
        //this parametrised constructor will cause error as we have defined @Restcontroller->@Component above this
        //class, so spring will try to create bean of this class but to do so it need to pass int value in constructor
        //can not pass value at boot up[ time
        System.out.println("count is = "+ count);
    }

    //@Autowired // field dependency injection of bean created demoConfig
    DemoConfig demoConfig;
    Child child;

    @Value("${jbdl.batch.number}")//property injection via field injection.. field injection happens after the
    int batchNumber; //constructor initialisation
    int lectureNumber;
    //int number = 20;//we can declare number here the3n why to write in application.properties
    //we can use property writtebn in application.properties in any number of classes, and easly change value at
    //one place then it will be changed at all places
    //it separtes configuration properties from actual code.. easy to handle in mnc company as no need to search
    //this property in all the classes

    // constructor dependency injection of bean created demoConfig.. just give the class reference in that we
    // want to inject in constructor of our class and initialise instance variable(class refernce of bean class)

    @Autowired
    // as we have created two constructor, spring does not know which one to call so use autowired
    //if only one constructor, then no need to use autowired
    public DemoController(DemoConfig demoConfig, @Value("${jbdl.lecture.number}") int number){
        //this is property injection.. injecting value from application.properties via constructor injection
        this.demoConfig=demoConfig;
        //since we have written @Configuration->@Component above democonfig class so spring will create its bean hence
        //spring can pass this bean at boot up time.. no error but if we pass reference variable of any class
        //which does not have @Component above it.. it will give error then..
        System.out.println("The number of lecture for jbdl batch is"+number);
        System.out.println("the batch number of jbdl is "+this.batchNumber);
        //this batchnumber is 0{default value) as this is rule of java hat first constructor will be executed then
        //fieled value will be initialised.. here we are not assiging any value to batch number
        //we are just printing it.. normally constructor are used to initialise instance variables and called when
        //we try to create object of the class
        this.lectureNumber=number;
    }
//    @Autowired//telling spring to use this default constructoe for creating bean
//    public DemoController(){
//        System.out.println("in democontroller constrctor, value of democonfig "+this.demoConfig);
//        //if we have autowired demopconfig via field injection, it prints nullnfor this value
//    }

    public DemoController(Child child){// this will give error at bean creation time
        //this one constructor we may be needing in our program.. eg below main()
        this.child=child;
    }
//
//    public static void main(String[] args) {
//        DemoController demoController = new DemoController(new Child());
//        //if we are creating this object, its our responsibility to manage lifecycle of this..
//        //if string creates object then spring manages its lifecycle
//    }

    private  static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @GetMapping("fetch_config") // http://localhost:8080/api/v1/fetch_config
    public List<Integer> fetchConfig(){
        return Arrays.asList(this.batchNumber, this.lectureNumber);
        //it prints 27 and 10 but in constructor, it prints 0 for instance variable..
        //if we directly inject property in constructor then it will print actuall value
    }

    //@ResponseBody //this annotation is applicable above Type(class, enum, interface) or METHOD not above Field
    //private int count=0;

    /**
     * Java -> Json : by @ResponseBody inside @RestController
     * Json -> Java : by @RequestBody .. we have @RequestParam and R@equestBody to accept parameter in method
     * eg.,  public Employee addEmployee(@RequestBody Employee employee){....}
     * RestController = Controller + ResponseBody
     * if do not give response body.. program starts but when hit api with value for name passwed..
     * it says error in forwarding "Hello usename"..404 not found.. as could not convert from java string object.. 404 to
     * other application content types like img, txt, html etc
     * if give @Controller at class level and give @responseBody not at class level but above greet api only
     * then greet api will work but bye api will not work as no responseBody there
     */

    //@GetMapping("/greet")
    @RequestMapping(value = "/greet", method = RequestMethod.GET)
    //@ResponseBody
    public String greetPerson(@RequestParam("name") String s){
        return "Hello "+s+" User!!";
    }

    @GetMapping("/bye")
    public String byePerson(@RequestParam("name") String p){
        return "bye "+ p +" !!!";
    }

    @GetMapping("/person")//will give 404 if do add @ResponseBody above this api or at class level
    public Person getrPerson(@Valid @RequestParam("id") int id, @RequestParam("name") String name){
        //return new Person(id, name);//we have later changed the constructor so giving error
        return new Person();
    }

    @GetMapping("/save")
    public void savePerson(@RequestParam("name") String name){  //still gives 404 if not usi ng @ResponseBody above this
        System.out.println("Got request to save person" + name);//void in java is not understood by browser or postman javascript
    }



    /*
    input- id(required=true), length, breadth (required = false) 200*200
    output - image to show
    we have to invoke/access/call below api from our api:
    https://picsum.photos/id/1/200/300

    RestTemplate class is use4d to invoke some other api from our api
     */


    //http://localhost:8080/api/v1/image?id=20
    @GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)//MediaType from org.springframework..
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
        //RestTemplate restTemplate1 = demoConfig.getTemplate();
        logger.info("restTemplate {}", restTemplate);
        byte[] response=restTemplate.getForObject(url, byte[].class);
        //getForObject is used call a particular url and response this url returns
        //byte[].class means i want to convert the response of url in the byte array
        //in java, image is stored as byte array
        return response;//currently response is application/octet-stream
        //content-length gives the number of bytes prresent in the response.. can see in response header in postman
        //if void->content-length=0,,, "Ron!"->content length = 4

    }

    //ques: we will take a csv file in input and we will parse the csv file using some parser and then print the result

    //every servlet container/embedded server has httpServletRequest interface in it
    @PostMapping("/file/parse")
    public List<Person> parseBulkFile(HttpServletRequest httpServletRequest) throws ServletException, IOException {
        //parse the file, extract the copntents
        //create person object and return the list of those obhjects
        //content type is multipart/form-data  as using form-data in postman to send csv file to our program
        //HttpServletRequest is coming from tomcat server but this interface is also pre3sent in Jetty server
        // this interface has many abstract method which are implementeede by many classes..
        // we are createing only its reference variable

        Part p1 = httpServletRequest.getPart("part1");//pass key name, returns part
        Part p2 = httpServletRequest.getPart("part2");

        logger.info("p1 ia {}", p1.toString());//this will not print actuall data but some information eg what file passed
        logger.info("p2 is {}", p2.toString());//check toString() implementation in Part class

        InputStream inputStream = p1.getInputStream();//whatever type of file you are giving in part, just get input stream of that
        //inputStream has data as byte array.. we can iterate over it and read data char by7 character.. but wit will read as normal text file this way
        //how to get data as csv file.. we need csv parser to this wrt to comma as value is comma separeted
        //otherwise will parse as 1, s, g,a,r.. now we need to combine this data as per id, firstname..
        // we need commons-csv library for this parser
        //CsvParser class contains parse() which takes input stream and parses it.. we need not to parse by ourselves
        //it takes Reader and CSVFormat as input

        List<Person> personList = new ArrayList<>();
        CSVFormat csvFormat = CSVFormat.DEFAULT.withRecordSeparator("\n").withDelimiter(',');
        CSVParser csvParser = new CSVParser(new InputStreamReader(inputStream), csvFormat);
        List< CSVRecord> records =csvParser.getRecords();
        for(CSVRecord csvRecord:records){
            logger.info("csvRecord : {}", csvRecord);//printing on console but we have to create person object

            if(csvRecord.getRecordNumber()==1){
                continue;
            }
            int id = Integer.parseInt(csvRecord.get(0));
            String firstname= csvRecord.get(1);// at first iteration, csv record has first line of our csv file
            String lastname= csvRecord.get(2);
            int age= Integer.parseInt(csvRecord.get(3));
            String dob = csvRecord.get(4);

            personList.add(new Person(id, firstname, lastname, age, dob));
        }

        return personList;

        // instead of . if we have : as delimeter in our csv file we need to pass this as delimeter the same result
        //otherwise if do not pass delimeter as :, it will consider whole line as 1 record,,
        // then eroor at csvRecord.get(2) as ther is no record at this positionj

        // we can use cml parser, pdf parser, xlsx parser
    }
}

// diff btw Controller and RestController: in RestController->@ResponseBody is present
//work of @ResponseBody is to convert Java response that we are sending from our api to a json response in postman