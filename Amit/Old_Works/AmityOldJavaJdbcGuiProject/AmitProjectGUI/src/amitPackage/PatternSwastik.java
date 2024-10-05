package amitPackage;
import java.util.Scanner;
class PatternSwastik
{
    public static void main(String[] args) 
    {
    int i,j,r,c;
    Scanner s= new Scanner(System.in);
    System.out.println("enter row and column");//both should be same, on;y then rhombus possible
    r=s.nextInt();
    c=s.nextInt();

    for(i=0;i<r;i++)
    {
        for(j=0;j<c;j++)
        {
            if(i<(r/2))
            {
                if(j==0||j==(c/2)){
                    System.out.print(" * ");
                }

                if(j>0&&j<(c/2)){
                    System.out.print("   ");
                }

                if(j>(c/2)&&i==0){
                 System.out.print(" * ");
                }
            }

           if(i==(r/2)){
                System.out.print(" * ");
            }

            if(i>(r/2) && i<r-1)
            {   
                if(j>=0 && j<(c/2) ){
                   System.out.print("   ");
                }

                if( j>(c/2)&&j<c-1 ){
                  System.out.print("   ");
                }

                if(j==(c/2)||j==c-1){
                    System.out.print(" * ");
                }
            }

            if(i==(r-1))
            {
                if(j<=(c/2)||j==c-1)
                    System.out.print(" * ");
                else
                    System.out.print("   ");
            }

        }// j loop ends
        System.out.println();
    }// i loop ends
    }// main() ends
}