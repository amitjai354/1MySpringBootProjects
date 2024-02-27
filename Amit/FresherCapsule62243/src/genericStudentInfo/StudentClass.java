package genericStudentInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class StudentClass {

        public String getQuery(String studentData,String query){
            if(query.startsWith("1")) {
                String[] str = query.split(",");
                String[] stdData=studentData.split(" ");
                StudentList<String> stdList = new StudentList<>();//creating object of StudentList class same as arraylist
                for(String s:stdData){
                    stdList.addElement(s);
                }
                return stdList.beginsWith(str[1]);
            }
            else if (query.startsWith("2")){
                String[] str = query.split(",");
                String[] stdData=studentData.split(" ");
                String[] bloodGroup = str[1].split(" ");
                StudentList<String> stdList2=new StudentList<>();
                for(String s:stdData){
                    stdList2.addElement(s);
                }
                return stdList2.bloodGroupOf(bloodGroup,str[2]);
            }
            else if(query.startsWith("3")){
                int count;
                String[] queryList = query.split(",");
                String[] stdData=studentData.split(" ");
                StudentList<Integer> stdList3 = new StudentList<>();
                for(String s:stdData){
                    stdList3.addElement(Integer.parseInt(s));
                }
                count=stdList3.thresholdScore(Integer.parseInt(queryList[1]));;
                return Integer.toString(count);
            }
            else if (query.startsWith("4")) {
                String[] stdData = studentData.split(" ");
                ScoreList<Number> scList = new ScoreList<>();//Number means can store integer, float, double
                for(String s:stdData){
                    scList.addElement(Integer.parseInt(s));// storing integer in arrayList
                }
                DecimalFormat df =new DecimalFormat("0.00");
                return df.format(scList.averageValues());
            }
            else if (query.startsWith("5")) {
                String[] stdData = studentData.split(" ");
                ScoreList<Number> scList5 = new ScoreList<>();
                for(String s:stdData){
                    scList5.addElement(Double.parseDouble(s));// storing double in same class arrayList
                }
                DecimalFormat df =new DecimalFormat("0.00");
                return df.format(scList5.averageValues());
            }
            return null;
        }

    public static void main(String[] args) {
        String studentData1_2 = "Raja Ravi Vinay Rahul Arun";
        String query1="1,r";
        String query2="2,B- AB- B+ O+ B+,B+";
        String studentData3_4 ="96 78 93 45 90 42 69";
        String query3="3,90";
        String query4="4";
        String studentData5="5.6 6.7 8.9 5.8";
        String query5="5";
        StudentClass stdc = new StudentClass();
        System.out.println(stdc.getQuery(studentData1_2,query1));//return name starting with given string
        System.out.println(stdc.getQuery(studentData1_2,query2));//return people contaoing provided blood group
        System.out.println(stdc.getQuery(studentData3_4,query3));//return count of people with score more than threshold value
        System.out.println(stdc.getQuery(studentData3_4,query4));//return avg of all
        System.out.println(stdc.getQuery(studentData5,query5));
    }

}
