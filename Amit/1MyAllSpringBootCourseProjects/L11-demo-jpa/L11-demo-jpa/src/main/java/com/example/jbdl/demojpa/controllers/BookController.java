package com.example.jbdl.demojpa.controllers;


import com.example.jbdl.demojpa.models.Book;
import com.example.jbdl.demojpa.requests.BookCreateRequest;
import com.example.jbdl.demojpa.services.BookService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookService bookService;
//    @Autowired
//    public BookController(BookService bookService){
//        this.bookService=bookService;
//    }

    private static Logger logger = LoggerFactory.getLogger(BookController.class);
    @GetMapping("/book") // localhost:8080/book?id=2
    public Book getBook(@RequestParam("id") int id){
        return bookService.get(id);
        //first push in db then get from db
        //if pass id as 20 but do not have id 20 in db.. nothing on screen as null response returned in repository code
        //if no record  fetched in result set.. return null
    }

    @GetMapping("/book/all")  //localhost:8080/book/all
    public List<Book> getBooks() throws SQLException {
        return bookService.get();
    }

    @PostMapping("/book") // localhost:8080/book and pass json
    public void insertBook(@Valid @RequestBody BookCreateRequest bookCreateRequest){
        //public void insertBook(@RequestParam("id") int id, @RequestParam("name") String name,
//                           @RequestParam("author") String authorName, @RequestParam("cost") int cost){

        bookService.insert(bookCreateRequest.to());
        //we are sending Book object without id to book service so that later we can fetch data from this object
        //and push in db
        //as id is updated by db itself.. so no need to pass id at all
        //bookcreaterequest we do not have id.. we have only 3 attributr4s so passiung bookcreaterequest object not
        //book object,, because in book object we need to pass id as well
        //We will call book service as this is the class that is doing business logic
    }

//    @PutMapping("/book")
//    public Book updateBook(@RequestBody BookUpdateRequest bookUpdateRequest) throws SQLException {
//        //which book we need to update.. either give separe request param for id or pass id in json in bookupdaterequest
//        //as at later we will check if this id is present in db then only we will update the book content
//        //we can not update id as unique id may get disturb.. so first check if same id exist in db or not then
//        //update same id only
//        //we can use Book class itself as it has id.. but we want to keep book separate hence create class
//        //bookupdaterequest with all the instnce variables including id
//
//        return bookService.update(bookUpdateRequest.to());
//        //book object with id we got from book update class and passed to book service to use this book object
//
//    }
//
//    @DeleteMapping("/book")
//    public Book deleteBook(@RequestParam("id") int id) throws SQLException {
//        return bookService.delete(id);
//    }
}
