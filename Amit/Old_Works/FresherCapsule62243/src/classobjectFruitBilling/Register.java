package classobjectFruitBilling;
import java.util.*;
public class Register {

        private static Register register =  new Register();
        /*
         * Complete the 'getTotalBill' function below.
         *
         * The function is expected to return a STRING.
         * The function accepts MAP itemDetails as parameter.
         */
        public static Register getInstance(){
            return register;
        }

        public Double getTotalBill(Map<String,Integer>  itemDetails) {

            // Write your code here
            Map <String, Double> hm = new HashMap<>();
            hm.put("apple", 2.0);
            hm.put("orange", 1.5);
            hm.put("mango", 1.2);
            hm.put("grape", 1.0);

            System.out.println(hm.get("apple"));

            Double sum=0.0;
            // for (String i : itemDetails.keySet())
            for (Map.Entry<String, Integer> entry : itemDetails.entrySet()){
                Double d = hm.get(entry.getKey());
                if (d!= null){
                    sum = sum + d*entry.getValue();
                }
            }
            return sum;
        }
    }
