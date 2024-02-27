package com.example.jbdl.demojpa.requests;

import com.example.jbdl.demojpa.models.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorCreateRequest {
    //as we are auto generating id so willl not pass that in wuthor ctreate request
    //same we are auto passing date in table so will not pass date in create request
    @NotBlank
    private String name;
    @Positive
    private int age;
    private String country;//country can be blank.. no issue

    public Author to(){
        return Author.builder()
                .name(this.name)
                .authorAge(this.age)
                .country(this.country)
                .build();
    }
}
