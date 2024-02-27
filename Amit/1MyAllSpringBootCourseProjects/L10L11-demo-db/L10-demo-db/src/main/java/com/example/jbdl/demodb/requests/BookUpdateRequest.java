package com.example.jbdl.demodb.requests;

import com.example.jbdl.demodb.models.Book;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//if we do not give getter and setter when in api call pass json.. null is passed to name, authorname and 0 to cost
//as could not get and set value of these private instance variables.. default value passed
public class BookUpdateRequest {
        //we can use Book class itself as it has id.. but we want to keep book separate hence create class
        //bookupdaterequest with all the instance variables including id
        //as we need id to verify later which book we want to update.. is same id exist in db then only update..
        //do not update any other id otherwise uniques primary key conbstraint may fail

        @Positive
        private int id;
        @NotNull
        private String name;
        @NotNull
        private String authorName;
        @Positive
        private int cost;

        public Book to() {
            return Book.builder()
                    .id(this.id)
                    .authorName(this.authorName)//can give in any order as using setter internally
                    .name(this.name)
                    .cost(this.cost)
                    .build();
        }
}
