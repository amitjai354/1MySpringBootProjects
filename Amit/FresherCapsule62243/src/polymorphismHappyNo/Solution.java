package polymorphismHappyNo;
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static void main(String[] args) throws Exception {
            /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        Scanner sc= new Scanner(System.in);
        //System.out.println("Enter start and end element");
        //int startElement=sc.nextInt();
        //int endElement=sc.nextInt();
        int startElement=1;
        int endElement=100;
        Parent p = new Parent();


        ChildOne ch1= new ChildOne(startElement, endElement);
        System.out.println("Prime Numbers: "+ch1.filter());
        ChildTwo ch2= new ChildTwo(startElement, endElement);
        System.out.println("Happy Numbers: "+ch2.filter());
    }
}
