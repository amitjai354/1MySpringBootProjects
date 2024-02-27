package com.example.jbdl.demojpa.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Author {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto inc by sql not by us or hibernate
    @GeneratedValue(strategy = GenerationType.AUTO)//auto inc by hibernate not by us or sql
    //now hibernate will create one more table hibernate_sequence in db to manage index that what is last max value
    // put till now.. hibernate will not put auto inc in query now with id
    //hibernatew_sequence has next_val as only column.. contains what will be next value for id.. value is 1 when no records initially
    //now at insertion time, id will be passed in query and id value will be passed by  hibernate internally

    //if we make book id also as AUTO.. and let inseryt 1 record in author then if we insert first record in book.. id =2
    //because there is only one table hibernate_sequence in db.. this same table will be used to pass id in all quesries
    //means Auto gives total no of insertion done at ORM/server level not at table level individually
    private int id;
    //id integer not null auto_increment ..in sql query.. this auto_increment is not responsibility of us or hibernate..
    //this is responsibility of sql only
    //hibernate is not giving primary key here then how is it working as primary key.. but in table it is Primary key
    // actually it giving this at the end of the query.. primary key (id).. not column level but table level

    @Column(name = "authorName")
    private String name;

    //private int age;
    //in desc author:we see age can not be null and type is int.. this is not primary key then why
    //int can not be null,, Integer can be null
    //hibernate will write... age integer not null,
    private Integer authorAge;//refacored age as authorAge and if run on ddlauto as update.. new column will be added
    //age will also be there but now we are not passing value for age so this will be null.. and author_age will
    //be null for all the previous records
    //alter table author add column author_age integer;

    //this is int here but if make this as Integer here then hibernate writes.. age integer,
    //java data type is int and Integer but sql data type is integer only.. with null or not null

    private String country;

    @CreationTimestamp //created_on in db but done by hibernate not by sql.. can see in hibernate query
    private Date createdOn;  //datetime data type in sql

    @UpdateTimestamp
    private Date updatedOn;// updated_on in db but done by hibernate not by sql.. can see in hibernate query
    //if ddl auto is update
    //Hibernate: alter table author add column updated_on datetime(6)
    //update willbe same as create time if we call post api.. it will be different when we call update/Put api

}
