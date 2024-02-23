package org.Amit;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jbdl27","root","1234");
            Statement statement=con.createStatement();
            //statement.execute("create table jbdl_students(id int primary key, name varchar(40))");
            statement.execute("insert into jbdl_students values (2, 'abc')");
            ResultSet rs=statement.executeQuery("Select * from jbdl_students");
            while(rs.next()){
                System.out.println(rs.getInt("id")+" "+rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}