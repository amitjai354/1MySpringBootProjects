package abstractClassCGPA;

public class CGPA {
    public static void main(String[] args) {
        String str1="100 5,100 5,100 5|1,100,5|0,100,5";
        String str2="100 5,100 5,53 5,76 3|0,100,5";
        System.out.println("Aided Cgpa:");
        Aided a = new Aided();
        System.out.println(a.result(str1));

        System.out.println("Self Finance Cgpa:");
        SelfFinance sf=new SelfFinance();
        System.out.println(sf.result(str2));
    }
}
