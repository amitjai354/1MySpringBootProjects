package lambdaStream;
import java.util.*;
import java.util.stream.Collectors;

public class LambdaFn {

        public List<Long> functionalProgramming(List<String> listOfIntegers) {
            //Write your code here
            List<Long> outputList = Collections.emptyList();//creating an empty list
            //System.out.println(checkNarcissisticNo("371"));
            outputList =listOfIntegers.stream()
                    //.filter(this::checkNarcissisticNo)
                    //.filter(x->checkNarcissisticNo(x))
                    .filter(x->{
                        //return checkNarcissisticNo(x); can write this single line instead of if else
                        if (checkNarcissisticNo(x)){
                            return true;
                        }
                        else {
                            return false;
                        }
                    })
                    .map(Long::parseLong)
//                    .map(x->{
//                        return Long.parseLong(x);
//                    })//return same element by doing some operation on it eg.. i->i*I
                    //.map(x->Long.parseLong(x))
                    //caN USE THIS SAME MAP BEFORE FILTER ALSO
                    .collect(Collectors.toList());//Collections and Collectors
//                    .forEach(x->{
//                        //outputList.add(Long.parseLong(x));
//                        //System.out.println(x);
//                    });

            System.out.println(outputList);
            return outputList;
        }
        public boolean checkNarcissisticNo(String s){
            int n=Integer.parseInt(s);
            int n1=n;
            int count =0;
            int i;
            int sum=0;
            while (n1>0){//finding nos of digits
                n1=n1/10;
                count++;
            }
            n1=n;
            while(n1>0){
                i=n1%10;
                sum=sum+(i*i*i);//
                //sum=sum+Math.pow(sum, count);
                n1=n1/10;
            }
            if(sum==n){
                return true;
            }
            else{
                return false;
            }
        }
    public static void main(String[] args) {
        List<String> listOfIntegers = new ArrayList<>();
        Collections.addAll(listOfIntegers,"300", "301", "370", "371", "372");//in list, add all tghese elements
        System.out.println(listOfIntegers);
        LambdaFn lfn = new LambdaFn();
        System.out.println(lfn.functionalProgramming(listOfIntegers));
    }

}
