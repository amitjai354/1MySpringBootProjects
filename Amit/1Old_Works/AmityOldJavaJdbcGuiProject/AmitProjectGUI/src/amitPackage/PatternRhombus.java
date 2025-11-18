package amitPackage;
import java.util.Scanner;
class PatternRhombus
{
    public static void main(String[] args) {
    int i,j,r,c;
    Scanner s= new Scanner(System.in);
    System.out.println("enter row and column");//both should be same, on;y then rhombus possible
    r=s.nextInt();
    c=s.nextInt();

    for(i=0;i<=(r/2);i++)
    {
        for(j=0;j<(c/2)-i;j++)
        {
            System.out.print("   ");//3 space printed as * printed with 2 stars
        }
        for(j=(c/2)-i;j<=(c/2)+i;j++)
        {
            System.out.print(" * ");
        }
        System.out.println();
    }

    for(i=(r/2)+1;i<=r;i++)
    {
        for(j=0;j<(c/2)-(r-i);j++)
        {
            System.out.print("   ");
        }
        for(j=(c/2)-(r-i);j<=(c/2)+(r-i);j++)
        {
            System.out.print(" * ");
        }
        System.out.println();
    }
}

}