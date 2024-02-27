package com.examplesecurity.demosecurity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Integer> {
    @Query(value = "select a from MyUser a where a.username = ?1")
    MyUser findByMyUserName(String s);
}
