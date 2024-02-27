package com.example.jbdl.demojpa.dao;

import com.example.jbdl.demojpa.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    //earlier we were writing our insert, get, delete, update fn and queries for db
    //but now jpa provides contract for these fns.. we can write and hibernate will query from them when executing code
    //but where do I find this contract.. JpaRepository interface
    /*
    spring boot starter data jpa:
    -jakarta-persistence-api:is used to provide sql related config eg @ID,@Entity, @Column, @GeneratedValue
    -spring data jap:provides the contract for db operations which the hibernate implements eg:find(), save(), delete()
    -Hibernate-core:it is the imp[lementation ORM framework for spring data jpa. this is the one which internally
                    communicates with under;ying db via network call and gets the stuff done
     */

    //in this JpaRepository(spring data jpa) we have methods: findAll(), findAllById(), saveAll() etc
    //JpaRepository extends PagingAndSortingRepository.. (spring data commons.. common for all dbs)
    // it has findAll(Sort sort).. returns sortyed result
    //also it has findAll(Pageable pageble).. it returns result page by page not all at one
    //select * from program limit 20; ... faster

    //now this PageAndSortRepository is extending from Cr5udRepository(spring data commons.. common for all dbs)
    //crudrepository extends fromn Repository((spring data commons.. common for all dbs)

    //spring-boot-starter-data-mongodb has two parent dependency:
    //-mongodb-driver-sync-this connects the driver to connect to underlying mongodb server eg mysql driver
    //-spring data mongodb- this contains contract for mongo db operation which the querydsl-mongodb implements and
    //executes the queries
    //we have MongoRepository interface (spring data mongodb) which is extending PageAndSortRepository..same as jpa now

    //same way cassandra is also no sql db-? spring data for apache cassandra -> spring data cassandra
    //it misses PageAndSortRepository only.. rest all same

    //for all relational dbs eg mysql, postgrace, oracle.. we have single dependency jpa repository.. all thses dbs
    //store data in form of relation.. rows and column
    //but we need diffrent different dependecy for diffrenrt no sql dbs.. these stores data in unstructured way..
    // mongodb storesw data as json.. casandra stores data different way

    // we have @Transactional above relation db save().. means either this fn will execute completely or
    // it will not execute at all

    //for no sql dbs, we can extend the class containing this save() and add code to make it transactional

}
//let we have to find all the books starting with 'P' and cost >100;
//this way lots of permutation and combinations can be there.. jpa does not give methods for these
//such custom quesries we need to write by ourselves..
//but if we give names of our these methods in a specific pattern then hibernate writes query for us