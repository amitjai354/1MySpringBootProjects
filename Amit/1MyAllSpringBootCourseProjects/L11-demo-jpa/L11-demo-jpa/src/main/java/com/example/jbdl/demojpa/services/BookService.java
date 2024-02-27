package com.example.jbdl.demojpa.services;


import com.example.jbdl.demojpa.models.Book;
import com.example.jbdl.demojpa.dao.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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
    public void insert (Book book){
        //we are recieving book object passed by book create in this book variable
        //and passing this object to repository to fetch data from this object that we send via json and push in db

        //here service is just making call to repository.. but later in other projects it will do many works
        //takes data from create request.. applies some filters(business logic) then transfers to repository layer
        //from there transferred to DB in models(Tables)
        //eg how library works that logic is written here.. what to do with data is writen here eg calculate fine
        //when data is returned from repository layer
        bookRepository.save(book);//save=insert
        //we recieved book object {without id) created by bookcreaterequest  and passed this to repository
        // so that we can fetch data from this book object and store in db
    }

    public List<Book> get() throws SQLException { // we can make some changes to this data recieved from db if we want
//        List<Book> bookList = new ArrayList<>();
//        bookList.addAll(bookRepository.get());
//        return bookList; //by me.. no need to create new list as get() returns list already

        return bookRepository.findAll();//get=findAll
        //Hibernate: select b1_0.id,b1_0.author_name,b1_0.cost,b1_0.created_on,b1_0.n_a_m_e,b1_0.updated_on from my_book b1_0
    }

    public Book get(int bookID){
        return bookRepository.findById(bookID).orElse(null);//findById returns Optional
        //select b1_0.id,b1_0.author_name,b1_0.cost,b1_0.created_on,b1_0.n_a_m_e,b1_0.updated_on from my_book b1_0
        // where b1_0.id=?
    }

//`    public Book update(Book book) throws SQLException {
//        //we are recieving book object passed by book update in this book variable
//        //and passing this object to repository to fetch data from this object that we send via json and push in db
//        return bookRepository.update(book);
//    }
//
//    public Book delete(int bookId) throws SQLException {
//        return bookRepository.delete(bookId);
//    }`
}
