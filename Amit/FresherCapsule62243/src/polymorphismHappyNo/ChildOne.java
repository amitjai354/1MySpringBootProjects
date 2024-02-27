package polymorphismHappyNo;

public class ChildOne extends Parent{
    ChildOne(int start, int end) {
            this.startElement=start;
            this.endElement=end;
    }

    public String filter(){
        int i;
        StringBuilder sb = new StringBuilder();
        System.out.println(startElement);
        System.out.println(endElement);

        for(i=startElement;i<=endElement;i++) {
            if (checkPrime(i)) {
                sb.append(" ");
               sb.append(i);
            }
        }
        return sb.toString();
    }
    public static boolean checkPrime(int n){
        if(n==0||n==1 ||n==4){
            return false;
        }
        if(n==2||n==3){
            return true;
        }
        else {
            for (int i = 2; i <= (n / 2); i++) {
                if (n % i == 0){
                    return false;
                }
            }
            return true;
        }
    }
}
