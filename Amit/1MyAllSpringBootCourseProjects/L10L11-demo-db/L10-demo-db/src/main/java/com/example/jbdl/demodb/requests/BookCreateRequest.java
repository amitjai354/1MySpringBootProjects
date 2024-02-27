package com.example.jbdl.demodb.requests;

import com.example.jbdl.demodb.models.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter//if we do not give getter and setter when in api call pass json.. null is passed to name, authorname and 0 to cost
//as could not get and set value of these private instance variables.. default value passed
public class BookCreateRequest {
    //id will be auto incremented so not requesting id  from front end
    @NotNull
    private String name;
    @NotNull
    private String authorName;
    @Positive
    private int cost;

    public Book to(){
        //as id is updated by db itself.. so no need to pass id at all at any time
        //bookcreaterequest we do not have id.. we have only 3 attributr4s so passiung bookcreaterequest object not
        //book object,, because in book object we need to pass id as well
        return Book.builder()
                .authorName(this.authorName)//cangiv e in any order as using setter internally
                .name(this.name)
                .cost(this.cost)
                .build();

//        public Book(String name, String authorName, int cost){
//            this.name=name;
//            this.authorName=authorName;
//            this.cost=cost;
//        }
        //we can create parametric constructor using any number of instance variable.. need not to pass all
        //the instance variables
        //later in repository layer we will fetch name, authorname, cost from this book object
        //and id will be given by db
    }
}
