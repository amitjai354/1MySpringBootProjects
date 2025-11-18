package genericStudentInfo;
import java.util.*;
public class StudentList<T> {
    //StudentList<String> stdList = new StudentList<>();//creating object of StudentList class same as arraylist
        //Write your code
    //if we want  variables in class to be generic.. T i;  here this can be string, integer, student type or any type
    //then we make class generic.. this way we can make all our methods inside this class as generic
        // if we want only our method to be generic and not need of any generic instance variable inside this method
    // //then we create generic method

    //we make class as generic then at time of creating object we define what type of data this class will handle..
    //once we declared let say <String> at time of creating object.. now this class willl not take any other type of data
    // if we want we can create another object with <Integer>.. now this object will accept integer only..
    //we can instanceOf to check if instance variable is string or integer opr what type
    // so this way we are making class as generic and type safe also..
    // but if we creat.. Object i; this will ofcourse contain any type of data but it will not be type safe..
    //after creating object.. we can pass Integer, string any data type

    //here in our code we have to make Arraylist generic.. if only arraylist we have to make generic we could have used
    // place holder <?> or we could have wriiten.. ArrayList al=new ArrayList();  but this it will be generic but not typesafe
    // but this way here not just one method or one arraylist.. complete class is now generic as well as type safe also

    ArrayList <T> al1_2_3 = new ArrayList<>();
    public void addElement(T t){
        al1_2_3.add(t);
    }
    public void removeElement(T t){
        al1_2_3.remove(t);
    }
    public void getElement(int i){
        al1_2_3.get(i);
    }
    public String beginsWith(String s){
            StringBuilder sb = new StringBuilder();
            al1_2_3.forEach(i -> {
                if(i instanceof String) {
                    if (i.toString().toLowerCase().startsWith("r")) {
                        sb.append(i.toString()).append(" ");
                    }
                }
            });
            return sb.toString();
    }
    public String bloodGroupOf(String[] sList, String s){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<sList.length;i++) {
            if (al1_2_3.get(i) instanceof String) {
                if (sList[i].equals(s)) {
                    sb.append(al1_2_3.get(i).toString()).append(" ");
                }
            }
        }
        return sb.toString();
    }
    public int thresholdScore(int i){
        int count=0;
        for(T j:al1_2_3){
            if(j instanceof Integer){
                if(((Integer) j).intValue()>=i){
                    count++;
                }
            }
        }
        return count;
    }
}
