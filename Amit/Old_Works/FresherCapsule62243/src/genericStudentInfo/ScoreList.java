package genericStudentInfo;
import java.util.*;
public class ScoreList <T>{
    //ScoreList<Number> scList = new ScoreList<>();//Number means can store integer, float, double

    //Write your code
    ArrayList <T> al4_5 = new ArrayList<>();
    public void addElement(T t){
        al4_5.add(t);
    }
    public void removeElement(T t){
        al4_5.remove(t);
    }
    public void getElement(int i){
        al4_5.get(i);
    }
    public double averageValues(){
        double sum=0.0;
        for(T t:al4_5){
            if(t instanceof Number){//Number means can store integer, float, double
                sum=sum+((Number) t).doubleValue();
            }
        }
        return sum/(al4_5.size());
    }

}
