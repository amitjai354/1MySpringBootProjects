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
@Builder//gives error only if add noargconstructor but do not add allargsconstructor
//if do not add both then no error
@Entity//telling hibernate to map this class in db.. create table of this class in db
//earlier we had created ctreateTable().. writen queries and called this in costructor and constructor was called
//by spring at boot up time in order to create bean of the class
/*
before application starts:
-Spring will search for all the Components in all the packages
-Hibernate will serch for al the entities in all the packages
 */
@Table(name = "my_book")//we can give name to the table in db.. by default table with clas name
public class Book {
    //we need to provide primary key for this table otherwise error

    //should be from jakarta.persistence.. for relationa dbs
    //id from springframework.data.annoatation is for non relational dbs
    //primary key is now present but it will not be auto incre4mented.. we need to give annotation for that

    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)//suto generation of id by sql
    @GeneratedValue(strategy = GenerationType.AUTO)
    //if we make author id also as AUTO.. and let inseryt 1 record in author then if we insert first record in book.. id =2
    //because there is only one table hibernate_sequence in db.. this same table will be used to pass id in all quesries
    //means Auto gives total no of insertion done at ORM/server level not at table level individually

    //let we insert 1 data using api/hibernate.. then hibernate_sequence will have next value as 1
    // //we maqnually insert 2nd record from sql workbech.. but this way hibernate called.. so next val is still 2
    //now if we insert using api call/hibernate.. duplicate key error then next val increments to +1.. otherwise
    //if not inc val then it will cause infinite loop fail for this failure..

    //but now hibernate uses different naming structure from version>6.. it tablename_seq instead of hibernate_sequence
    //now creates seq table for each table separately instead of just one table
    //and in next val instead of 1, 2 so on it is 1, 51, 101 so on.. but id is inserted 2 for second entry in a table
    //now it also passes id table wise.. not overall no. of insertions
    private int id;
    //id integer not null auto_increment ..in sql query.. this auto_increment is not responsibility of us or hibernate..
    //this is responsibility of sql only
    //hibernate is not giving primary key here then how is it working as primary key.. but in table it is Primary key
    // actually it giving this at the end of the query.. primary key (id).. not column level but table level
    private String n_a_m_e;//refactored name to n_a_m_e.. same in query and table by hibernate
    //if we refactor and run as DDlauto=update.. it will create new column n_a_m_e
    //named will be there and when we call post api.. null will be updated in namae..
    //run ddl auto as create then namae will be removed and n_a_m_e will be there only

    //we need not to worry what name hibernate gives until we write custom query that hibernbate does not support
    //select * from my_book where author_name is = 'Peter';

    //Spring data jpa provides two types of query..
    //1. wrt to coulumn name in sql table..      select * from my_book where author_name is = 'Peter';
    //2.wrt to java object(instance variables).. select * from my_book where authorName is = 'Peter';

    private String author_name;// changes camelCase.. authorName to author_name in db but done by hibernate not by sql..
    // can see in hibernate query //let refactor this to author_name.. then in hibernte same name author_name
    private int cost;
    //in desc my_book:we see cost can not be null and type is int.. this is not primary key then why
    //int can not be null,, Integer can be null
    //hibernate will write... cost integer not null,
    //but if make this as Integer here then hibernate writes.. cost integer,

   // @Valid @Positive works only when we take data from json in postman.. it does not work at db level

    @CreationTimestamp//coming from hibernate
    private Date createdOn; // created_on in db but done by hibernate not by sql.. can see in hibernate query
    //similar to id i dont want to pass date.. i want sql to put this value by itself
    //as let we pass date and time.. but let there is some delay when actually data is inserted due to some issue
    //with this.. whwn hibernate will insert data then only timestamp will be updated

    //spring data jpa is using both hibernate-core and jakarta-persistence-api as its parent
    //id, entity, table, GeneratedValue from jakarta, timestamp from hibernate
    //hibernate is just mapping, table creation is task of jakarta

    @UpdateTimestamp// updated_on in db but done by hibernate not by sql.. can see in hibernate query
    private Date updatedOn; //Alter table add column updatedOn DATE;
    //if ddl auto is update
    //Hibernate: alter table my_book add column updated_on datetime(6)

    //update willbe same as create time if we call post api.. it will be different when we call update/Put api
}
