package com.example.jbdl.demodb.repositories;
//Repository Layer is DAOLayer.. Data Access Object
//used to write all code related to connect to Boook Table in underlying db

import com.example.jbdl.demodb.controllers.BookController;
import com.example.jbdl.demodb.models.Book;
import com.example.jbdl.demodb.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository//alias for @Component.. child annotationof component
public class BookRepository {
    //this is the place where we will write sql queries and code related to db integration
    private static Connection connection;
    private static Logger logger = LoggerFactory.getLogger(BookController.class);
    public BookRepository() throws SQLException {
        logger.info("Inside BookRepository constructor..");
        connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/jbdl27_bookdb","root", "1234");
        //creating connection in constructor because if create this at class level as property.. as java rule first
        //constructor will run then values will be intialised.. but we need this connection in constructor to create tsable
        createTable();
    }

    private void createTable() throws SQLException {//private so that no one outside this class can use create table
        logger.info("Inside create table function, trying to create a table..");
        //static query
        String sql="create table if not exists book(id int primary key auto_increment, name varchar(40)," +
                " cost int, author_name varchar(40))";
        //Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/jbdl27_bookdb","root","1234");
        //create this connection at class level
        Statement statement=connection.createStatement();
        boolean result=statement.execute(sql);//just creating table not fetching rows
//        ResultSet resultSet=statement.executeQuery(sql);//fetching rows
//        while(resultSet.next()){
//            System.out.println(resultSet.getInt(1)+resultSet.getInt(2)
//                    +resultSet.getInt("cost")+resultSet.getInt(4));
//        }

        //table should be created in begining as let say program starts and we are calling api to insert row..
        // but table is not created yet so it will give error.. call this method in default constructor..
        //so when bean of this class will be created at boot up time.. same timer table will be created
        //but let we stop our app and again restart it.. it will give error table already exist
        //use create table if not exists jbdl27_book...
        logger.info("Result of create table query {}", result);
        logger.info("table created");

    }
    public void insert(Book book) throws SQLException {
        //once the book is inserted in repository, it will return back to service layer
        //then service layer will return back to controller layer.. controller returns back to FE client via api call
        //this layer is just used to do crud operation.. fetch update delete etc..
        //this layer will send data only.. will not calculate fine
        //ToDo: add sql queries here

        //create table book(id int primary key, name varchar(30), authorname varchar(40), cost int)
        //create table book_auto(id int primary key auto_increment, name varchar(30), authorname varchar(40), cost int)
        //insert into book (name, authorname, cost) values('Java', 'John, 1000) -> this will cause eroor as not passing id
        //insert into book_auto (name, authorname, cost) values('Java', 'John, 1000) ->
        // this will not cause eroor even if not passing id

        //let say we have id 1, 2... now i manually pass id 30.. it will overide auto inc id 3 to 30..
        //insert into book_auto (id, name, authorname, cost) values(30, 'c', 'Jin, 800) -> 1, 2, 30 id is prresent

        // now i again insert record..         //insert into book_auto (name, authorname, cost) values('py', 'hn, 600) ->
        // what will be id 3 or 4 or 31-> 31 will be the id
        //if give 3 as id.. let say we insert record till id 29.. then at next rercord auto inc to 30
        //but 30 is already there so error.. hence 31 id is given

        //now let say again we pass id as 5.. 1, 2 , 5, 30, 31
        //now if we insert record but do not pass id--> 32 will be id not 6..  1, 2 , 5, 30, 31, 32

        // it is largest id seen so far +1
        //it is not number of rows+1
        //it is not last inserted id +1

        //Dynamic query.. used by hibernate also
        logger.info("inserting the book {}", book);
        String sql="insert into book(name, author_name, cost) values (?,?,?)";
        PreparedStatement preparedStatement= connection.prepareStatement(sql);
        preparedStatement.setString(1, book.getName());
        preparedStatement.setString(2, book.getAuthorName());
        preparedStatement.setInt(3,book.getCost());

        //boolean result=preparedStatement.execute();
        int result = preparedStatement.executeUpdate();
        //query execution 2 process:1. compilation of query to check if any error, 2. execution of this compiled query
        //static query is compiled every time when we execute this query in mysql server.. both steps happen every time
        //but dynamic query is compiled only once as query is same only parameters are changed that we pass
        //just replace the parameter and execute..
        // in case of jopins static query are very slow
        logger.info("result of the insert query is {}", result);
        //execute() returns true only if resut is ResultSet.. resultset is given only in select * query
        //resultset is not given in insert and update query
        //ececuteUpdate() gives no. of rows updated in dml
        //executeQuery() gives Result Set.. it is never null.. used with Select query
    }

    public List<Book> get() throws SQLException {//getting data from db.. earlier pushed data to db in insert()
        List<Book> bookList = new ArrayList<>();
        String sql="Select * from book";
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery(sql);//resultset contains data in foem of row and column
        while(resultSet.next()){
            //column names as per in db table
            //better to use column name as let someone alter table so column index may change
//            bookList.add(new Book(resultSet.getInt("id"), resultSet.getString("name"),
//                    resultSet.getString("author_name"), resultSet.getInt("cost"))); //my way

            int id=resultSet.getInt("id");
            String bookName=resultSet.getString("name");
            String authorName=resultSet.getString("author_name");
            int cost=resultSet.getInt("cost");

            Book book=Book.builder()
                    .id(id)
                    .name(bookName)
                    .authorName(authorName)
                    .cost(cost)
                    .build();
            bookList.add(book);

        }
        return bookList;
    }

    public Book get(int bookId) throws SQLException {//getting data from db.. earlier pushed data to db in insert()

        String sql="Select * from book where id = "+bookId;
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery(sql);
        while(resultSet.next()){
            int id=resultSet.getInt("id");
            String bookName=resultSet.getString("name");
            String authorName=resultSet.getString("author_name");
            int cost=resultSet.getInt("cost");

            return Book.builder()
                    .id(id)
                    .name(bookName)
                    .authorName(authorName)
                    .cost(cost)
                    .build();
        }
        return null;
        //if pass id as 20 but do not have id 20 in db.. nothing on screen as null response returned in repository code
        //if no record  fetched in result set.. return null as will not enter in  while loop
    }

    public Book update(Book book) throws SQLException {
//        String sql="update book Set name = "+book.getName()+", author_name ="+book.getAuthorName()
//        +", cost = "+book.getCost()+"where bookId = "+book.getId();
//        Statement statement=connection.createStatement();
//        statement.executeUpdate(sql);
        //return book;
        //done by me using statement

        String sql= "update book set cost = ?, name=?, author_name=? where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,book.getCost());
        preparedStatement.setString(2,book.getName());
        preparedStatement.setString(3, book.getAuthorName());
        preparedStatement.setInt(4,book.getId());
        int result = preparedStatement.executeUpdate();
        logger.info("no. of rows affected {}", result);// if do not write where clause.. all rows will be updated
        //or we can on basis of name then more than rows may be affected
        return get(book.getId());//using above get method to get data from db
        //we may return book that we got in argument but may be book is not updated if id is not present then we should
        //return null.. but return book will always return book
    }

    public Book delete(int bookId) throws SQLException {
        Book book = get(bookId);// storing book before deleteing it from db
        String sql = "Delete from book where id ="+bookId;//delete entire one row not just one column from a row
        Statement statement= connection.createStatement();
        int result = statement.executeUpdate(sql);
        logger.info("number of records deleted -{}",result);
//        sql = "select * from book where bookId ="+bookId;
//        ResultSet resultSet = statement.executeQuery(sql);
//        while(resultSet.next()){
//            return Book.builder()
//                    .id(resultSet.getInt("id"))
//                    .name(resultSet.getString("name"))
//                    .authorName(resultSet.getString("author_name"))
//                    .cost(resultSet.getInt("cost"))
//                    .build();
//        }
//        return null;
//        return get(bookId);
        //above code will return null only as record is deleted from the table
        //we can get record before deleting the record then delete
        return book;

    }



    //while fetching data from db.. go from repository to service to controller
    //while pushing to db.. go from controller to service to repository to table(db)

    //Disadvantage of this ad hoc way..
    //error prone
    //our quesries may not be optimised much
    //not scalable..let we have 25 attributes in our book class.. mapping these to db column is not easy
    //we are acting as mapper from java object to
    //Hibernate os ORM frasmework.. object Relation Mapping
    //opbject=java object, relation= sql table
    //now hibernate comes automatic bundled with spring boot but can use stand alone also
    // spring-boot-starter-data-jpa.. JPA= java persistant api
    //JPA is just a contract like interface which is implemented by Hibernate by default, other implentors are openJPA
    //this contract tell what operations we need to perform related to database operations
    //eg get() in jpa-> retrive data from db and convert to java object.. save(), flush() in jpa
    //Hiberrnate os ORM framework that maps java object to sql table and vice cersa.. also it internally executes query
    //with help of database driver
    //insteaed of we writing the query now hibernate will write the quesry for us.. we just need to call methods
    //mentioned in jp[a contract.. it will convert the result in java object.. this all will be done  optimised way
    // orm + executing/implementing jpa contract

    //open jpa same4 asw hibernate.. implementor of jpa contract
    //struts: a framework to devolop mvc web application.. we are using spring web.. internally it uses spring mvc
    //struts used earlier.. now spring boot is famous

}
