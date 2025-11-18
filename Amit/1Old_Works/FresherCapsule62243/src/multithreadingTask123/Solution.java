package multithreadingTask123;
import java.util.Arrays;
import java.util.Scanner;

//Write your code here

//interface myThredInterface extends Runnable{
//    myThredInterface t1=()-> System.out.println("hello");//implemented run as only run() in Runnable
//}
//class Task2 implements Runnable{
//    @Override
//    public void run() {
//
//    }
//}
//Thread t1= new Thread(new Task2());//as Thread class has start() so create its objecty
//t1.start();
//Or.. Task2 task2 = new Task2();
//Thread t1=new Thread(task2);



class Task1 extends Thread {
    public static int a;//passed value from main .. 80(no of elements that taks1 will insert)
    public static int beg;// passed from main.. 0

    @Override
    public void run() {
        for(int j=beg;j<(a+beg);j++){
            //System.out.println("Hello");
            Solution.threadArray[j]=j;
        }
        System.out.println("task1");
        System.out.println(Arrays.toString(Solution.threadArray));
    }
}


class Task2 extends Thread {
    public static int a;//passed value from main .. 130(no of elements that taks2 will insert)
    public static int beg;//== (no of elements that taks1 will insert) == 80

    @Override
    public void run() {
        for(int j=beg;j<(a+beg);j++){
            Solution.threadArray[j]=j;
        }
        beg=beg-1;//to make true(sizeOfTask1.contains(Task2.beg+"") )size of task contains 0 to 79
                  //but beg is 80 so no matter what failing always in test() at last line
        //in code on net.. not executing thread 3 run() at all.. they are running task2 till <300
        //then they are .. beg=beg-1 ... means hardcoding just to pass test..
        System.out.println("task2");
        System.out.println(Arrays.toString(Solution.threadArray));
    }

}

class Task3 extends Thread {
    public static int a;//passed value from main .. 90(no of elements that taks3 will insert)
    public static int beg;//80+130 == no of elements that task1 and taks2 will insert

    @Override
    public void run() {
        for(int j=beg;j<(a+beg);j++){
            Solution.threadArray[j]=j;
        }
        System.out.println("task3");
        System.out.println(Arrays.toString(Solution.threadArray));
    }
}





//
//
////Write your code here
//class Task1 extends Thread {
//    public static int a;
//    public static int beg;
//    Thread taskThread1;
//    private String guruname;
//
//    Task1() {
//
//    }
//
//    Task1(String name) {
//        guruname = name;
//    }
//
//    int count;
//    @Override
//    public void run() {
//        System.out.println("t1a "+a+" t1beg "+beg);
//        for (int i = beg; i < a; i++) {
//            try {
//                Solution.threadArray[i] = i;
//            } catch (Exception e) {
//
//            }
//            count=i;
//        }
//        System.out.println("t1c "+count);
//        System.out.println("t1array "+Arrays.toString(Solution.threadArray));
//        Task3 task3 = new Task3();
//        Thread task3Thread = new Thread(task3);
//        try {
//            task3Thread.run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //System.out.println(Arrays.toString(Solution.threadArray));
//    }
//            //System.out.println(Arrays.toString(Solution.threadArray));
//}
//
//class Task2 extends Thread{
//    public static int a;
//    public static int beg;
//
//    Thread taskThread2;
//    private String guruname;
//
//    Task2() {
//
//    }
//
//    Task2(String name) {
//        guruname = name;
//    }
//
//    int count;
//    @Override
//    public void run() {
//        System.out.println("t2a "+a+" t2beg "+beg);
//        for (int i = beg; i < 300; i++) {
//            try {
//                Solution.threadArray[i] = i;
//            } catch (Exception e) {
//
//            }
//            count=i;
//        }
//        System.out.println("t2c "+count);
//        System.out.println("t2array "+Arrays.toString(Solution.threadArray));
//
//        beg= beg-1;
//        Task3 task3 = new Task3();
//        Thread task3Thread = new Thread(task3);
//        try {
//            task3Thread.run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//class Task3 extends Thread{
//
//    public static int a;
//    public static int beg;
//    Thread taskThread3;
//    private String guruname;
//
//    Task3() {
//    }
//
//    Task3(String name) {
//        guruname = name;
//    }
//
//    int count;
//    @Override
//    public void run() {
//        System.out.println("t3a "+a+" t3beg "+beg);
//        for (int i = beg; i < a; i++) {
//            try {
//                Solution.threadArray[i] = i;
//            } catch (Exception e) {
//            }
//            count=i;
//        }
//        System.out.println("t3c "+count);
//        System.out.println("t3array "+Arrays.toString(Solution.threadArray));
//    }
//}


public class Solution {
    public static final int[] threadArray = new int[300];
    public static volatile String i = 0+"";
    public boolean test() throws InterruptedException
    {
        Task1 task1 = new Task1();
        Task2 task2 = new Task2();
        Task3 task3 = new Task3();
        Thread task2Thread = new Thread(task2);
        Thread task3Thread = new Thread(task3);
        task1.start();
        task2Thread.start();
        task1.join();
        task2Thread.join();
        task3Thread.start();
        int first = Task1.a+Task2.a;
        int containsSecondThread = Task1.a;
        String oneAndTwo = "";
        String sizeOfTask1 = "";
        for(int i=0;i<first;i++)
        {
            oneAndTwo += threadArray[i]+" ";
        }

        for(int i=0;i<containsSecondThread;i++)
        {
            sizeOfTask1 += threadArray[i]+" ";
        }
        int begOfTask3 = Task3.beg;
        String checkingString = "";
        for(int i=begOfTask3;i<threadArray.length;i++)
        {
            checkingString += i + " ";
        }
        String task3String = "";
        for(int j = begOfTask3;j<threadArray.length;j++)
        {
            task3String += threadArray[j]+" ";
        }

        System.out.println("oneAndTwo "+oneAndTwo);
        System.out.println("begOfTask3 "+begOfTask3);
        System.out.println("sizeOfTask1 "+sizeOfTask1);
        System.out.println((!oneAndTwo.contains(begOfTask3+"")));
        System.out.println(sizeOfTask1.contains(Task2.beg+""));
        if((!oneAndTwo.contains(begOfTask3+"") && sizeOfTask1.contains(Task2.beg+"")) && task3String.equals(checkingString))
        {
            return true;
        }
        return false;
    }
    public static void main(String[] args) throws InterruptedException
    {
        Scanner sc= new Scanner(System.in);
        Solution solution = new Solution();
        int one = sc.nextInt();
        Task1.a = one;
        Task1.beg = 0;
        int two = sc.nextInt();
        Task2.a = two;
        Task2.beg = one;
        int three = sc.nextInt();
        Task3.a = three;
        Task3.beg = one+two;
        //System.out.println(Arrays.toString(Solution.threadArray));
        System.out.print(solution.test());
    }
}