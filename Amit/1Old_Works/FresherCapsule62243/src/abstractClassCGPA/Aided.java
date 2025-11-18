package abstractClassCGPA;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Aided extends Student{
    @Override
    public String result(String allMarks) {
        System.out.println(allMarks);
        String [] str=allMarks.split("\\|");
        for(String i:str) {
            System.out.println(i);
        }

        //calculating subject score
        String[] subject=str[0].split(",");
        for(String i:subject) {
            System.out.println(i);
        }
        double creditPointMax=5* subject.length;

        //Map<String, String> hm=new HashMap<>();
        ArrayList<Double> lst=new ArrayList<Double>();
        for (String s : subject) {
            String[] sub = s.split(" ");
            lst.add(Double.parseDouble(sub[0]));
            lst.add(Double.parseDouble(sub[1]));
        }
        for (Double i:lst) {
            System.out.println(i);
        }
        double sum=0.0;
        DecimalFormat df=new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.DOWN);
        for (int i=0; i<lst.size();i+=2){
            if(lst.get(i)>=75 && lst.get(i)<=100){
                System.out.println(Double.parseDouble(df.format((((lst.get(i)-75)*((11.0/26.0)/10))+9.0))));
                sum=sum+(Double.parseDouble(df.format((((lst.get(i)-75)*((11.0/26.0)/10))+9.0)))*lst.get(i+1));
            }
            else if(lst.get(i)>=60 && lst.get(i)<=74){
                sum=sum+(Double.parseDouble(df.format(((lst.get(i)-60)*((10.0/15.0)/10))+8.0))*lst.get(i+1));
            }
            else if(lst.get(i)>=50 && lst.get(i)<=59){
                sum=sum+(Double.parseDouble(df.format(((lst.get(i)-50)*((10.0/10.0)/10))+7.0))*lst.get(i+1));
            }
            else if(lst.get(i)>=40 && lst.get(i)<=49){
                sum=sum+(Double.parseDouble(df.format(((lst.get(i)-40)*((10.0/10.0)/10))+6.0))*lst.get(i+1));
            }
            else{
                sum=sum+0;
            }
        }
        System.out.println(sum);
        //calculating ncc score
        String[] ncc=str[1].split(",");
        for(String i:ncc) {
            System.out.println(i);
        }
        if(ncc[0].equals("1")){
            creditPointMax=creditPointMax+5;
            double d1=Double.parseDouble(ncc[1]);
            double d2=Double.parseDouble(ncc[2]);
            if(d1>=75 && d1<=100){
                sum=sum+(Double.parseDouble(df.format((((d1-75)*((11.0/26.0)/10))+9.0)))*d2);
            }
            else if(d1>=60 && d1<=74){
                sum=sum+(Double.parseDouble(df.format(((d1-60)*((10.0/15.0)/10))+8.0))*d2);
            }
            else if(d1>=50 && d1<=59){
                sum=sum+(Double.parseDouble(df.format(((d1-50)*((10.0/10.0)/10))+7.0))*d2);
            }
            else if(d1>=40 && d1<=49){
                sum=sum+(Double.parseDouble(df.format(((d1-40)*((10.0/10.0)/10))+6.0))*d2);
            }
            else{
                sum=sum+0;
            }
        }
        System.out.println(sum);
        String[] sports=str[2].split(",");
        for(String i:sports) {
            System.out.println(i);
        }
        if(sports[0].equals("1")){
            creditPointMax=creditPointMax+5;
            double d1=Double.parseDouble(sports[1]);
            double d2=Double.parseDouble(sports[2]);
            if(d1>=75 && d1<=100){
                sum=sum+(Double.parseDouble(df.format((((d1-75)*((11.0/26.0)/10))+9.0)))*d2);
            }
            else if(d1>=60 && d1<=74){
                sum=sum+(Double.parseDouble(df.format(((d1-60)*((10.0/15.0)/10))+8.0))*d2);
            }
            else if(d1>=50 && d1<=59){
                sum=sum+(Double.parseDouble(df.format(((d1-50)*((10.0/10.0)/10))+7.0))*d2);
            }
            else if(d1>=40 && d1<=49){
                sum=sum+(Double.parseDouble(df.format(((d1-40)*((10.0/10.0)/10))+6.0))*d2);
            }
            else{
                sum=sum+0;
            }
        }
        System.out.println(sum);
        double cgpa=sum/creditPointMax;
        System.out.println(cgpa);
        //DecimalFormat df1=new DecimalFormat("#.##");//if 10.0 it prints 10
        DecimalFormat df1=new DecimalFormat("#.00");//if 10.0 it prints 10.00
        //df1.setRoundingMode(RoundingMode.DOWN);

        return df1.format(cgpa);
    }
}
