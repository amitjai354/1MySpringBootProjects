package controlflowJavaif;
import java.io.*;
import java.math.*;
import java.util.*;

class Result {

    /*
     * Complete the 'calculateGrade' function below.
     *
     * The function is expected to return a STRING_ARRAY.
     * The function accepts 2D_INTEGER_ARRAY students_marks as parameter.
     */

    public static String[] calculateGrade(int[][] students_marks) {
        System.out.println(students_marks.length);
        String grade[]=new String [students_marks.length];
        for (int i=0; i<students_marks.length;i++){//students_marks.length.. total number of rows
            Double sum=0.0;
            for(int j=0; j< students_marks[i].length;j++){//students_marks[i].length.. length of each row
                System.out.println(students_marks[i].length);
                sum=sum + students_marks[i][j];
            }
            Double avg = sum/5;
            System.out.println(avg);
            if (avg>=90.0){
                grade[i]="A+";
            }
            if (avg>= 80.0 && avg<90.0){
                grade[i]="A";
            }
            if (avg>= 70.0 && avg<80.0){
                grade[i]="B";
            }
            if (avg>= 60.0 && avg<70.0){
                grade[i]="C";
            }
            if (avg>= 50.0 && avg<60.0){
                grade[i]="D";
            }
            if (avg<50.0){
                grade[i]="F";
            }
            System.out.println(grade[i]);
        }
        return grade;

    }

}

 public class Solution {
        public static void main(String[] args) throws IOException {
            Scanner sc = new Scanner(System.in);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

            int students_marksRows = Integer.parseInt(sc.next().trim());
            int students_marksColumns = Integer.parseInt(sc.next().trim());


            int[][] students_marks = new int[students_marksRows][students_marksColumns];
            for(int i = 0; i < students_marksRows; i++)
            {
                for(int j = 0; j < students_marksColumns; j++)
                {
                    students_marks[i][j] = Integer.parseInt(sc.next().trim());
                }
            }

            String[] result = Result.calculateGrade(students_marks);

            for(int i = 0; i < result.length; i++)
            {
                System.out.println(result[i]);
                bufferedWriter.write(result[i]+"\n");
            }
            bufferedWriter.close();
        }
}

