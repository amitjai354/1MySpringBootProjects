package amitPackage;
import java.awt.event.*;
import javax.swing.*;

public class SwingSum extends JFrame{

    JLabel l1, l2, l3;
    JTextField t1,t2;
    JButton b1;

    SwingSum() {}
    SwingSum(String s){
        super(s);
    }

    public void setComponent(){
        l1=new JLabel("First Number");
        l2=new JLabel();
        l3=new JLabel();
        l2.setText("Second Number");
        t1=new JTextField();
        t2=new JTextField();
        b1=new JButton("Add");
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
           int a= Integer.parseInt(t1.getText());
           int b=Integer.parseInt(t2.getText());
           int s=a+b;
            l3.setText("Sum is "+s);
        }
    }

    public static void main(String[] args) {
        SwingSum jf=new SwingSum("SumProject");
        jf.setComponent();//if keep this line below setVisible line then gui elements does not appear on frame
        jf.setSize(500,500);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}