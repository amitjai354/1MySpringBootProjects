import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello world!");
        // q1. you need to create a table in mysql db using your java application

        // JDBC: Java Database Connectivity (Protocol)
        // Used to connect to relational dbs via java application
        // SMTP - Simple mail transfer protocol
        // HTTP -

        // CREATE a table
        // Adding records in a table
        // Reading records from a table
        // Updating some records in a table
        // Deleting some records in a table

        // 1 - ~65535    ports number range
        //    //localhost=//127.0.0.1

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jbdl27", "root", "1234");
        Statement statement = connection.createStatement();
        //statement.execute("create table L5_attendance(id int primary key, name varchar(40))");
        //statement.execute("insert into L5_attendance values(3,'ghi')");
        ResultSet rs=statement.executeQuery("select * from L5_attendance");
        while (rs.next()){
            System.out.println(rs.getInt("id")+" "+rs.getString(2));
        }
    }
}