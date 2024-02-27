package com.example.jbdl.demojpa.requests;

import com.example.jbdl.demojpa.models.Book;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCreateRequest {
    //no need to pass date as well because similar to id we are passing date from sql automatically
    @NotNull
    private String name;
    @NotNull
    private String authorName;
    @Positive
    private int cost;

    public Book to(){
        return Book.builder()
                .n_a_m_e(this.name)
                .author_name(this.authorName)
                .cost(this.cost)
                .build();
    }
}
