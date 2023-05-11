package com.example.jbdl.apis;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

//@Component
public class Employee {
    //private int id;//default value is 0 so lets check no id passed.. if(employee.getId()==0).. but id can be 0
    private Integer id;
//    private String employeeId;
//
//    public String getEmployeeId() {
//        return employeeId;
//    }
//
//    public void setEmployeeId(String employeeId) {
//        this.employeeId = employeeId;
//    }


    // if the request attribute is not found in the reflection search
    // done by the convertor, it will be ignored
   @NotNull// Retention Policy is at class Level.. //this will not work if http method is GET as not sending any data to server
    private String name;
   @Min(18)
   @Max(60)
    private int age;
    private Gender gender;
    //@.com/@.in/@.tech  some regex are there in email format
    @Email
    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
