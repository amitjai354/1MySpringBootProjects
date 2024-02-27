package com.example.jbdl.demojpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoJpaApplication.class, args);
	}

}

//hibernate, jpa are for relational dbs only
//spring starter data jpa has spring-commons as parent dependency .. this is for all types of db eg mongo db
//it also has jakarta- transaction api... this is to make this as transactional as relational dbs are tarnsactional
//relational dbs are structured.. non relation are non structured
//Rdb are not scalable as they are structured so complete structure need to move to next db.. but NRdb are scalable as no\
//structure so can easily increase size of db
//let say we added new db for relational to increase size.. half schema in first db and jhalf in second.. when trying
// top store data.. store in which db