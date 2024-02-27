package app;

import java.sql.*;
public class FetchLoginRecord {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Amit","root","1234");
            System.out.println("connection established");
            PreparedStatement ps=con.prepareStatement("SELECT * FROM users;");
            System.out.println("statement prepared");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("name") + " " + rs.getString("password") );
            }
            System.out.println("query executed successfully");
        }
        catch(ClassNotFoundException e){
            System.out.println("Driver not loaded "+e.getMessage());
        }
        catch(SQLException e){
            System.out.println("connection or query execution problem:- "+e.getMessage());
        }
    }
}