package referenceTypeStringBuilderOperations;
import java.util.*;

import static java.util.stream.Collectors.joining;


class CipherDecipher{
    /**
     * This method is used to cipher the message (normal) by following steps.
     * Swap the cases of String - UpperCase letters to LowerCase letters and viceversa.
     * Reverse the String
     * Replace the spaces of string with a star character("*")
     * Replace the characters in the even positions of the string
     * Append any integer at the last in that String
     *
     * @param normal
     * @return the ciphered message
     */
    public String ciphering(String normal){
        //Write your code here

////-----------------------------------------------------------------------------
////        char[] ch=sb.toString().toCharArray();
//        char[] ch=normal.toCharArray();
//        for(int i=0;i<normal.length();i++){
//            if(ch[i]>='A' && ch[i]<='Z'){
//                ch[i]= (char) ('a'+(ch[i]-'A'));
//            }
//            else if(ch[i]>='a' && ch[i]<='z'){//instead of else if.. if use if only.. then it will convert W to w then again to W
//                ch[i]= (char) ('A'+(ch[i]-'a'));
//            }
//        }
//        System.out.println(ch);
//        String str=new String(ch);
//        System.out.println(str);
//---------------------------------------------------------------------------------
//        for(int i=0;i<normal.length();i++){
//            if(normal.charAt(i)>='A' && normal.charAt(i)<='Z'){
//                normal.replace(normal.charAt(i),(char) ('a'+(normal.charAt(i)-'A')));
//            }
//            else if(normal.charAt(i)>='a' && normal.charAt(i)<='z'){//instead of else if.. if use if only.. then it will convert W to w then again to W
//                normal.replace(normal.charAt(i),(char) ('A'+(normal.charAt(i)-'a')));
//            }
//        }
//----------------------------------------------------------------------------------
        StringBuilder sb1=new StringBuilder(normal);
        int flag=0;
        if (sb1 != null && !sb1.isEmpty())
        {
            for (int i=0;i<sb1.length();i++)
            {
                if (Character.isDigit(sb1.charAt(i)))//Charcter is wrapper class for char like Integer
                {
                    flag=1;
                }
            }
        }
        if(flag==1){
            System.out.println("string contains number");
        }
        else{
            System.out.println("string does not contains number");
            //write below all code inside this
        }

        //conver uppercase to lowercase and vice versa
        for(int i=0;i<normal.length();i++){
//            if(sb1.charAt(i)>='A' && sb1.charAt(i)<='Z'){ //A= 65
//                sb1.setCharAt(i,(char)('a'+(sb1.charAt(i)-'A')));
//            }
//            else if(sb1.charAt(i)>='a' && sb1.charAt(i)<='z'){//instead of else if.. if use if only.. then it will convert W to w then again to W
//                sb1.setCharAt(i,(char) ('A'+(normal.charAt(i)-'a')));
//            }
            if(Character.isUpperCase(sb1.charAt(i))){
                sb1.setCharAt(i,Character.toLowerCase(sb1.charAt(i)));
            }
            else if(Character.isLowerCase(sb1.charAt(i))){
                sb1.setCharAt(i,Character.toUpperCase(sb1.charAt(i)));
            }
        }
        System.out.println("sb1:"+sb1.toString());
        sb1.reverse();
//        String revStr=sb1.toString();
//        System.out.println(revStr);
//        char[] chRevrs = revStr.toCharArray();
//        for(int i=0;i<chRevrs.length;i++){
//            if(chRevrs[i]==' '){
//                chRevrs[i]='*';
//            }
//        }
//        System.out.println(chRevrs);
//        String strStar=new String(chRevrs);
        for(int i=0;i<sb1.length();i++){
            if(sb1.charAt(i)==' '){
                sb1.setCharAt(i,'*');
            }
        }
        System.out.println(sb1);
        StringBuilder sb2 = new StringBuilder();
        for(int i=0;i<sb1.length();i++){
            if(i%2!=0){
//                System.out.println(sb2.charAt(i));
//                System.out.println(sb2.charAt(i)+0);
//                count=Integer.bitCount((sb2.charAt(i)+0));
                //sb2.setCharAt(i,(Integer.toString((sb2.charAt(i)+0))).toCharArray());
                //sb2.setCharAt(i,Integer.(sb2.charAt(i)+0));

//                String substr=sb2.substring(0,i);
//                String s1=substr.concat((Integer.toString((sb2.charAt(i)+0))));
//                String s2=s1.concat(sb2.substring(i+1));
//                System.out.println(s2);
//                sb2= new StringBuilder(s2);
                sb2.append(sb1.charAt(i)+0);
            }
            else{
                sb2.append(sb1.charAt(i));
            }

        }

        System.out.println(sb2);
        sb2.append(3);
        System.out.println(sb2);
        return sb2.toString();
    }
    /**
     * This method is used to get the normal text by the reverse process of ciphering.
     *
     * @param ciphered
     * @return the deciphered message
     */
    public String deciphering(String ciphered){
        //Write your code here
        StringBuilder sb = new StringBuilder(ciphered);
        sb.deleteCharAt(sb.length()-1);
        System.out.println(sb);
        StringBuilder sb1= new StringBuilder();
        StringBuilder asciChars=new StringBuilder();
        for(int i=0;i<sb.length();i++){
//            if(i!=sb.length()-1){
//                if (!(sb.charAt(i)>='0' && sb.charAt(i)<='9')){
//                    sb1.append(sb.charAt(i));
//                }
//                if (sb.charAt(i)>='0' && sb.charAt(i)<='9' && (sb.charAt(i+1)>='0' && sb.charAt(i+1)<='9')){
//                    asciChars.append(sb.charAt(i));
//                }
//                else if(sb.charAt(i)>='0' && sb.charAt(i)<='9' && (!(sb.charAt(i+1)>='0' && sb.charAt(i+1)<='9'))){
//                    asciChars.append(sb.charAt(i));
//                    sb1.append((char) (Integer.parseInt(asciChars.toString())));
//                    asciChars.delete(0,asciChars.length());//end element is exclusive
//                    System.out.println(sb1);
//                    System.out.println(asciChars);
//                }
//            }
//            else if(i==sb.length()-1){
//                if (!(sb.charAt(i)>='0' && sb.charAt(i)<='9')){
//                    sb1.append(sb.charAt(i));
//                }
//                else if(sb.charAt(i)>='0' && sb.charAt(i)<='9'){
//                    asciChars.append(sb.charAt(i));
//                    sb1.append((char) (Integer.parseInt(asciChars.toString())));
//                    asciChars.delete(0,asciChars.length());//end element is exclusive
//                    System.out.println(sb1);
//                    System.out.println(asciChars);
//                }
//            }
            //current char is not digit
            if(!(Character.isDigit(sb.charAt(i)))){
                sb1.append(sb.charAt(i));
                //System.out.println(sb1);
            }
            //next char is also digit
            else if (i!=sb.length()-1 && Character.isDigit(sb.charAt(i)) && (Character.isDigit(sb.charAt(i+1)))){
                asciChars.append(sb.charAt(i));//ascii char is storing digits and later convert to ascii char
                System.out.println(sb1);//here simply get the digit and do not convert to ascii as nect char is also
                System.out.println(asciChars);//next char is also a digit
            }
            //next char is not digit
            else if (i!=sb.length()-1 && Character.isDigit(sb.charAt(i)) && (!(Character.isDigit(sb.charAt(i+1))))){
                asciChars.append(sb.charAt(i));
                sb1.append((char)(Integer.parseInt(asciChars.toString())));
                System.out.println(sb1);
                System.out.println(asciChars);
                asciChars.delete(0,asciChars.length());//delete elements from start , end
            }
            //last char is digit
            else if(i==sb.length()-1 && Character.isDigit(sb.charAt(i))){
                asciChars.append(sb.charAt(i));
                sb1.append((char)(Integer.parseInt(asciChars.toString())));//convert stringBuilder to string then to int then char
                System.out.println(sb1);
                System.out.println(asciChars);
                asciChars.delete(0,asciChars.length());
            }
        }
        for(int j=0;j<sb1.length();j++){
            if(sb1.charAt(j)=='*'){
                sb1.setCharAt(j, ' ');
            }
        }
        System.out.println(sb1);
        sb1.reverse();
        System.out.println(sb1);

        for(int i=0;i<sb1.length();i++){
            if(Character.isLowerCase(sb1.charAt(i))){
                sb1.setCharAt(i,Character.toUpperCase(sb1.charAt(i)));
            }
            else if (Character.isUpperCase(sb1.charAt(i))){
                sb1.setCharAt(i,Character.toLowerCase(sb1.charAt(i)));
            }
        }
        System.out.println(sb1);
        return sb1.toString();
    }
}


public class Solution {

        public static void main(String[] args){
            Scanner readInput = new Scanner(System.in);
            //String normal=readInput.nextLine();
            String normal="Welcome to hackerrank";
            //String ciphered=readInput.nextLine();
            String ciphered="?85O89*69R65*87O104*33I1043";

            CipherDecipher cipherOrDecipher = new CipherDecipher();
            System.out.println(cipherOrDecipher.ciphering(normal));
            System.out.println(cipherOrDecipher.deciphering(ciphered));
        }


}
