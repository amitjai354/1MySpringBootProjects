package com.example.jbdl.demodb.services;

//we write business logics in this related to our tasbles in the db
//BookService responds to all the operations that we can do with book object
//eg get all the books, get books with cost higher than given cost
//eg we have some filters on ecommerce sites like which color, whart discount.. all these business logics comes in this

import com.example.jbdl.demodb.models.Book;
import com.example.jbdl.demodb.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service//child annotation of @Component.. just an alias for  @Component nothing else in this annotation
//we can give @RestController also.. bean will be cre4ated of this class but also this class be visible to
// dispatcher servlet.. but that is not required as we are not writing any api here

public class BookService {

    @Autowired
    BookRepository bookRepository;

//    public BookService(BookRepository bookRepository){
//        this.bookRepository=bookRepository;
//    }
    public void insert (Book book) throws SQLException {
        //we are recieving book object passed by book create in this book variable
        //and passing this object to repository to fetch data from this object that we send via json and push in db

        //here service is just making call to repository.. but later in other projects it will do many works
        //takes data from create request.. applies some filters(business logic) then transfers to repository layer
        //from there transferred to DB in models(Tables)
        //eg how library works that logic is written here.. what to do with data is writen here eg calculate fine
        //when data is returned from repository layer
        bookRepository.insert(book);
        //we recieved book object {without id) created by bookcreaterequest  and passed this to repository
        // so that we can fetch data from this book object and store in db
    }

    public List<Book> get() throws SQLException { // we can make some changes to this data recieved from db if we want
//        List<Book> bookList = new ArrayList<>();
//        bookList.addAll(bookRepository.get());
//        return bookList; //by me.. no need to create new list as get() returns list already

        return bookRepository.get();
    }

    public Book get(int bookID) throws SQLException {
        return bookRepository.get(bookID);
    }

    public Book update(Book book) throws SQLException {
        //we are recieving book object passed by book update in this book variable
        //and passing this object to repository to fetch data from this object that we send via json and push in db
        return bookRepository.update(book);
    }

    public Book delete(int bookId) throws SQLException {
        return bookRepository.delete(bookId);
    }
}
