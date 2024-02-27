package com.example.demo.amitSbProject.models;

//import jakarta.persistence.Entity; for spring boot version 3 and above
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
//@Table(name = "CATEGORY")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore//in tcs code, may be need not to give this category id in response
    private Integer categoryId;

    //@Column(name = "CATEGORY_NAME")
    @Column(unique = true)
    private String categoryName;
}
