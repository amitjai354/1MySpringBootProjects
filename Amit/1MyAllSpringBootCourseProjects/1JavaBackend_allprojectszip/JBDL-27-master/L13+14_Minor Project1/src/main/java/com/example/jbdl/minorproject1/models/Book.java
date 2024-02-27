package com.example.jbdl.minorproject1.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity //javax.persistance... @Entity will create Table with attributes as columns in that table
@NoArgsConstructor  //lambok
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Book {

    /** JPA Relationships
     * Unidirectional -> Having only the foreign key attribute in the current class
     * Bidirectional -> Having foreign key attribute in the current class as well the
                        back reference in the other class
     **/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // INT
    private String name;  // VARCHAR

    @Enumerated(value = EnumType.STRING)
    private Genre genre; //

    // This is going to be a foreign key column

    /**
     * Current class   Other class (Opposite of current class)
     * ManyToOne  --> OneToMany
     * ManyToMany  --> ManyToMany
     * OneToOne --> OneToOne
     * OneToMany --> ManyToOne
     **/

    @ManyToOne
    @JoinColumn //to add foreign key in our table
    @JsonIgnoreProperties(value = "bookList")
    private Author myAuthor; // Optimal way (Do not add FK in both the table).. this is going to be column in Book Table.. authorId in Book Table is foreign key in this book table

    @ManyToOne//javax.persistance.. current class to other class relation... many book can be issued by 1 student
    @JoinColumn
    @JsonIgnoreProperties(value = "bookList")
    private Student student;

    @OneToMany(mappedBy = "book")
    @JsonIgnoreProperties(value = "book")
    private List<Transaction> transactionList;

    @CreationTimestamp
    private Date createdOn; //java.util.Date

    @UpdateTimestamp
    private Date updatedOn; //java.util.Date
}
