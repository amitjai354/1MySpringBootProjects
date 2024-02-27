package com.example.jbdl.minorproject1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity  //javax.persistance
@NoArgsConstructor  //lambok
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    private String name;
    private int age;

    @Column(unique = true, nullable = false)//validation at db side, insertion in table will fail if not unique or value is null.. javax.persistance
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    /* Remember it in this way, mapped is always the current class' reference
       attribute in the other class
     */
    @OneToMany(mappedBy = "student")//one student can issue many books.. in book clas we have private Student student so we are telling this book is referring to which attribute in book class
    @JsonIgnoreProperties(value = "student")
    private List<Book> bookList; //Bidirectional Relation - Java Back Reference, Book is tightly coupled to Student so faster execution. This will not be a column in Student Table
    //java.util.list
   
    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties(value = "student")
    private List<Transaction> transactionList;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    // All the books issued to student 1 --> GET Student info
}
