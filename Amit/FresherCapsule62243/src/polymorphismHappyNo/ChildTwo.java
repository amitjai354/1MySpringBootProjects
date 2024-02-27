package polymorphismHappyNo;

public class ChildTwo extends Parent {
    ChildTwo(int start, int end) {
        this.startElement=start;
        this.endElement=end;
    }

    public String filter() {
        int i;
        StringBuilder sb = new StringBuilder();

        for(i=startElement;i<=endElement;i++) {
            if (checkHappyNo(i)) {
                sb.append(" ");
                sb.append(i);
            }
        }
        return sb.toString();
    }

//    public static boolean checkHappyNo(int n) {
//        while (n != 1 && n != 4) { //OR=>n==1 means False but n!=4 so True.. F and T means T
//            //System.out.println(n); //
//            n = digitSum(n);
////            if(n==1 || n==4){
////                break;
////            }
//        }
//        if (n == 1)
//            return true;
//        else {
//            return false;
//        }
//    }


    public static boolean checkHappyNo(int n) {
        //int orignalNum=n;
        while (true) { //for(int i=0; true ; i==){}   infinite for loop
            n = digitSquareSum(n);
            if (n == 1)
              return true;

            //if(n==4 || n==orignalNum)
            if(n==4) {
              return false;
          }
        }
    }


    public static int digitSquareSum(int m){
        int summ = 0;
        int x;
        while (m > 0) {
            x = m % 10;
            summ = summ + (x * x);
            m = m / 10;
        }
        return summ;
    }
}
