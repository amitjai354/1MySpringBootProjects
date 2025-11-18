package amitPackage;


import java.awt.event.*;
import javax.swing.*;

public class Login extends JFrame{
    
    JLabel l1, l2, l3;
    JTextField t1,t2;
    JButton b1;

    Login() {}
    Login(String s){
        super(s);
    }

    public void setComponent(){
        l1=new JLabel("UserName");
        l2=new JLabel();
        l3=new JLabel();
        l2.setText("Password");
        t1=new JTextField();
        t2=new JTextField();
        b1=new JButton("Login");
        setLayout(null);
        l1.setBounds(50, 50, 100, 20);
        l2.setBounds(50, 80, 100, 20);
        t1.setBounds(180, 50, 150, 20);
        t2.setBounds(180, 80, 150, 20);
        b1.setBounds(160, 120, 100, 20);
        l3.setBounds(160, 160, 100, 20);
        b1.addActionListener(new ButtonClickListener());
        add(l1);
        add(l2);
        add(l3);
        add(t1);
        add(t2);
        add(b1);
    }

    class ButtonClickListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e){
            if(t1.getText().equals("user") && t2.getText().equals("password")){
            l3.setText("valid user");
            }
            else{
                l3.setText("Invalid user");
            }
        }
    }

    public static void main(String[] args) {
        Login jf=new Login("LogIn");
        jf.setSize(400,300);
        jf.setComponent();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}