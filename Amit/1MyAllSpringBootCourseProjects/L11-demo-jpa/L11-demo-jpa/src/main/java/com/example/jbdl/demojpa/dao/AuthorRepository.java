package com.example.jbdl.demojpa.dao;

import com.example.jbdl.demojpa.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    //JpaRepository takes Generics <T, ID> where T is for class type and Id is for primary key type
    // T is object that we are operating on.. we need data type of id as we have methods that use collection of id
    //in this JpaRepository interfce.. getbyId
    //Once we have given T=Author now this is type safe.. we can not assign anything other than Author
    //but we use Object.. then this becomes generic but not becomes type safe as we can assign any object

    //after extending this interface we have got all the methods of JpaRepository so we can use these methods in our
    //code now
    //We are not implementing methods of JPARepository.. instead there is a class SimpleJpaRepository that implements
    //all these methods.. so may be hibernate uses that class for executing query

    //we are not giving @Repository then how it is Autowired in other classes..
    //JpaRepository is implemented by SimpleJpaRepository class.. this class has @Repository and @Transactioal

}
