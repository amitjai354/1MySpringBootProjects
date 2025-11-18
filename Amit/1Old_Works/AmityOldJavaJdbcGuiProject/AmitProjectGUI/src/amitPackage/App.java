package amitPackage;

import java.sql.*;
public class App {
    public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Amit","root","1234");
            System.out.println("connection established");
            PreparedStatement ps=con.prepareStatement("CREATE TABLE usersss(name VARCHAR(50));");
            System.out.println("statement prepared");
            ps.execute();
            System.out.println("query executed");
        }
        catch(ClassNotFoundException e){
            System.out.println("Driver not loaded: "+e.getMessage());
        }
        catch(SQLException e){
            System.out.println("connection or query execution problem: "+e.getMessage());
        }
    }
}