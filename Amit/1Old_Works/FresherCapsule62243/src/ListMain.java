import java.util.ArrayList;
import java.util.List;

public class ListMain {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        //*****************************************

        List lst = new ArrayList();// eatrlier before java 1.4, <> Diamond was not used
        lst.add(1);
        lst.add("abc");
        System.out.println(lst);  //output: [1, abc]

        List list1 = new ArrayList<>();
        list1.add(1);
        list1.add("abc");
        System.out.println(list1);  //output: [1, abc]

        List<String>  list2 = new ArrayList<>();
        //list2.add(1); // error
        System.out.println(list2);

        List<Object> list3 = new ArrayList<>();
        list3.add(2);
        list3.add("abcd");
        System.out.println(list3);

        List<?> list4 = new ArrayList<>();
        //list4.add(2); //java: incompatible types: int cannot be converted to capture#1 of ?
        //list4.add("abcd");//java: incompatible types: java.lang.String cannot be converted to capture#2 of ?
        System.out.println(list4);//output: [2, abcd]
        //this is used in case of method return type.. as later we can retutrn any type of list..eg String
        // these are place holders.. we can replce this value with String or Integer.. or Object or make it generic
       List list = methodList();
        System.out.println(list);
        //****************************************
    }
    public static List<?> methodList(){
        List listM = new ArrayList<>();
        listM.add(1);
        listM.add("MethodReturnedList?");
        System.out.println(listM);  //output: [1, abc]
        return listM;
    }
}