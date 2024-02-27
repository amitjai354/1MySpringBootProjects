package com.example.jwt.demojwtauthentication.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Integer> {
 //@Query(value = "Select a.username, a.password, a.authority from MyUser a where a.username = ?1")
 // //@Query(value = "Select id, username, password, authority from MyUser where username = ?1")
 //these query always gives this error could not covert from java lang object to model object in class..
 // only a works,, a.username.. this does not work

 //@Query(value = "Select username, password, authority from my_user where username = ?1", nativeQuery = true)
 //this query is giving error could not find by column name.. as column:id is missing
 //we are not passing id but that is auto generated in db
 //we will id fetch from security context if needed


 //@Query(value = "Select new MyUser(a.username, a.password, a.authority) from MyUser a where a.username = ?1")
  //Could not determine appropriate instantiation strategy - no matching constructor found and one or
 // more arguments did not define alias for bean-injection



 //below queries are working fine.. we need to get as werll

 //@Query(value = "Select id, username, password, authority from my_user where username = ?1", nativeQuery = true)
 //@Query(value = "Select * from my_user where username = ?1", nativeQuery = true)
 //@Query(value = "select a from MyUser a where a.username = ?1")

 //@Query(value = "Select new com.example.jwt.demojwtauthentication.security.MyUser(id, username, password, authority) from MyUser where username = ?1")
 //@Query(value = "Select new MyUser(id, username, password, authority) from MyUser where username = ?1")
 @Query(value = "Select new MyUser(a.id, a.username, a.password, a.authority) from MyUser a where a.username = ?1")
 MyUser findByMyUserName(String s);
}
