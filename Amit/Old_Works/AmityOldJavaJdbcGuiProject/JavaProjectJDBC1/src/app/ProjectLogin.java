package app;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import net.proteanit.sql.DbUtils;// need rs2xml jar for this to work
// this is used to store result set from db in our JTable
//and we store ourJTable in JScrollPane so that we can scroll our result in small page

public class ProjectLogin extends JFrame{
    
    JLabel l1, l2, l3;
    JTextField t1,t2;
    JButton b1,b2,b3;
    JTable tb;

    ProjectLogin() {}
    ProjectLogin(String s){
        super(s);
    }

    public void setComponent(){
        l1=new JLabel("UserName");
        l2=new JLabel();
        l3=new JLabel();
        tb=new JTable();
        JScrollPane sp = new JScrollPane(tb);//scrollpane is now container of our table
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        l2.setText("Password");   // this makes table scrollable..so instead of adding table
        t1=new JTextField();     //  now add(sp} and set bounds for sp now: sp.setbounds()
        t2=new JTextField();
        b1=new JButton("Login");
        b2=new JButton("Signup");
        b3=new JButton("ShowUsers");
        setLayout(null);
        l1.setBounds(50, 50, 100, 20);
        l2.setBounds(50, 80, 100, 20);
        t1.setBounds(180, 50, 150, 20);
        t2.setBounds(180, 80, 150, 20);
        b1.setBounds(180, 120, 100, 20);
        b2.setBounds(60, 120, 100, 20); 
        l3.setBounds(150, 150, 150, 20);
        b3.setBounds(130, 180, 100, 20);
        sp.setBounds(120, 220, 180, 100);
        b1.addActionListener(new LoginButtonClickListener());
        b2.addActionListener(new SignupButtonClickListener());
        b3.addActionListener(new ShowUsersButtonClickListener());
        add(l1);
        add(l2);
        add(l3);
        add(t1);
        add(t2);
        add(b1);
        add(b2);
        add(b3);
        add(sp);
    }


    class SignupButtonClickListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Driver loaded");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Amit","root","1234");
                System.out.println("connection established");
                PreparedStatement ps=con.prepareStatement("INSERT INTO users (name, password) VALUES(?,?);");
                ps.setString(1, t1.getText());//what was typed in t1 now set it in sql table column1
                ps.setString(2, t2.getText());
                System.out.println("statement prepared");
                ps.execute();
                System.out.println("query executed");
                l3.setText("signup successfull");
            }
            catch(ClassNotFoundException e){
                System.out.println("Driver not loaded "+e.getMessage());
            }
            catch(SQLException e){
                System.out.println("connection or query execution problem:- "+e.getMessage());
            }        
        }
    }


    class LoginButtonClickListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Driver loaded");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Amit","root","1234");
                System.out.println("connection established");
                PreparedStatement ps=con.prepareStatement("SELECT name, password FROM users;");
                System.out.println("statement prepared");
                ResultSet rs = ps.executeQuery();
                System.out.println("query executed");

                while( rs.next()){
                 if( t1.getText().equals( rs.getString("name") ) && t2.getText().equals( rs.getString("password") ) )
                    {
                    l3.setText("valid user");
                    break;
                    }
                    else{
                        l3.setText("invalid user");
                    }
                }
            }
            catch(ClassNotFoundException e){
                System.out.println("Driver not loaded "+e.getMessage());
            }
            catch(SQLException e){
                System.out.println("connection or query execution problem:- "+e.getMessage());
            }        
        }
    }


    class ShowUsersButtonClickListener implements ActionListener
    {
        public void actionPerformed(ActionEvent a){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Driver loaded");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Amit","root","1234");
                System.out.println("connection established");
                PreparedStatement ps=con.prepareStatement("SELECT * FROM users;");
                
                System.out.println("statement prepared");
                ResultSet rs = ps.executeQuery();
                
                    tb.setModel(DbUtils.resultSetToTableModel(rs)); //need this to store result set
                    //that we get from Database into our JTable and we have stored our JTable in JScrollPane
                    
                

                System.out.println("query executed");
                
            }
            catch(ClassNotFoundException e){
                System.out.println("Driver not loaded "+e.getMessage());
            }
            catch(SQLException e){
                System.out.println("connection or query execution problem:- "+e.getMessage());
            }        
        }
    }



    public static void main(String[] args) {
        ProjectLogin jf=new ProjectLogin("ProjectLogIn");
        jf.setSize(600,600);
        jf.setComponent();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}