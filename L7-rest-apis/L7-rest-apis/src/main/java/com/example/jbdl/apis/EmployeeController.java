package com.example.jbdl.apis;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController// @RestController -> @Controller -> @Component
public class EmployeeController {
    /**
     * TRACE - 1000
     * DEBUG - 300
     * INFO - 100
     * WARN - 10
     * ERROR - 1
     **/
    private static int count=1;
    private HashMap<Integer,Employee> employeeMap = new HashMap<>();
    /**
     Hashmap - {employee.id, employee}

     * Add a new employee --> input : employee object, output: void
     * Get the details of a particular employee --> input: employee id, output: employee
     * Get details of all the employees present --> input: <>, output: List<employee>
     * Update details of an employee --> input: id, (details to be updated) , output: employee
     * Delete the employee --> input: id, output : boolean | employee | void
     **/

    //@RequestBody accepts json and is converted to java object as json is key value pqir.. not understood by java
    // conversion from json object to a java object and vice versa is done by some spring-web utility
    //java object returned from get is not understood by postman so it is converted back to json
    @PostMapping("/employee/add")// returns java object employee which is shown as json on postman in response
    public Employee addEmployee(@Valid @RequestBody Employee employee){  //@Valid will not work if http method is GET as not sending any data to server
        System.out.println(this);//objects in all the apis will be same
        //employee.setEmployeeId(UUID.randomUUID().toString());
        System.out.println("Inside add employee Post fn as valid request passed");
        employee.setId(count++);//inc when add employee but do not dec when delete empl so same id will not be given new employee as count is same
        // but if use map size to set id then on delete size will decrease so same id will be set to new employee
        employeeMap.putIfAbsent(employee.getId(), employee);//will add employee to hm only if same employee not present
        return employee;
    }
    @GetMapping("/employee/get")
    public Employee getEmployee(@RequestParam("id") int id){
        System.out.println(this);
        return employeeMap.get(id);
        //return Optional.ofNullable(employeeMap.get(id)).orElse(new Employee());//will create new employee with default values
    }
    @GetMapping("/employee/get/all")
    public List<Employee> getAllEmployee(){
        System.out.println(this);
//        ArrayList<Employee> al = new ArrayList<>();
//        employeeMap.forEach((k,v)->{
//            al.add(employeeMap.get(k));//al.add(v); //is also correct
//        });
//        return al;
        //return employeeMap.values().stream().toList();// values is returning Collection.. we can typecast it to set, list anything we want.. this is not key value
        return employeeMap.values().stream().collect(Collectors.toList());
        //return new ArrayList<>(employeeMap.values());
    }

    @PutMapping("/employee/update")
    //public Employee updateEmployee(@RequestParam("id") int id, @RequestBody Employee employee) throws Exception{
    //this way we are not passing id in json at conversion time of json to java employee object, id gets default value 0
    //we can simply do is.. employee.setId(id);  or we can take id in json as below
    public Employee updateEmployee(@RequestBody Employee employee) throws Exception{
        System.out.println(this);
        if(employee.getId()==null){
            throw new Exception("Id is not present for the employee to be updated");//furthure lines will not execute if throwed exception but server will not stop
        }
        if(!employeeMap.containsKey(employee.getId())){
            throw new Exception("Employee is not present");
        }
        else{
            employeeMap.put(employee.getId(),employee);
        }
        return employee;
        //return employeeMap.putIfAbsent(id,employee);//it will add if employee not present.. but we have to update if employee is present
    }
    @DeleteMapping("/employee/delete/{id}")//localhost:8080/employee/delete/1
    public Employee deleteEmployee(@PathVariable("id") int id){
        System.out.println(this);
        return employeeMap.remove(id);//returns value
    }

    @GetMapping("/dummy")//localhost:8080/dummy?start=2&end=8
    public List<Integer> getListOfNumbers(@RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                          @RequestParam("end") int end){
        System.out.println(this);
        return IntStream.range(start, end+1)
                .boxed()
                .collect(Collectors.toList());
    }
}
