package com.example.jbdl.demodb.models;

import jakarta.validation.constraints.NotNull;
import lombok.*;

//models means entity.. which table we have to create in our db

//our application will flow from controllers -> services-> DAO/repository-> actual db(models/tables) and vice cersa
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Book {
    //ideally it is preffered not to use your entity class as Request POJO(Plain old java object)
    //use another package for requests
    //here just define entity name and all properties (instance variable) that it has
    private int id;//this we will autoincrement, whenever inserting in db auto increment id
    @NotNull
    private String name;
    private String authorName;
    private int cost;
}
