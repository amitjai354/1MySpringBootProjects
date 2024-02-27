package classobjectFruitBilling;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Solution {
        public static void main(String[] args) throws IOException {

            Scanner readInput = new Scanner(System.in);
            String[] input=readInput.nextLine().split(" ");
            Map<String,Integer> myItems=new HashMap<String,Integer>();
            for(int i=0;i<input.length;i+=2){
                myItems.put(input[i],Integer.parseInt(input[i+1]));
            }
            Register regObj = Register.getInstance();

            System.out.println(regObj.getTotalBill(myItems));
            readInput.close();

        }
}
