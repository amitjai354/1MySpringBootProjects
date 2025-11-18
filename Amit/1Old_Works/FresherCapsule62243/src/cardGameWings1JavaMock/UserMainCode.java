package cardGameWings1JavaMock;

public class UserMainCode {
    public int Cards(int input1, int[] input2) throws Exception {
        //instead of flipping one by one.. find min sum and flip that we will get max sum
        //from total sum, subtract min 3 times as it was deducted once
        if (input1>=1 && input1<=500){
            for (int i=0; i<input1; i++){
                if (input2[i]<=-1000 || input2[i]>=1000){
                    throw new Exception("input2 is not valid. Please enter in range -1000 to 1000");
                    //dont do this way not worked in wings 1 exam.. put positive conditions in if block and at last in else throw exception
                }
            }

            //todo
            int i,j,k;
            int sum=0;//normal sum of numbers we have faced till now
            int max=0;
            int s=0;
            int min=0;
            for(i=0;i<input1;i++){
                s=0;
//                if(i==0){
//                    sum=0;
//                }else {
//                    sum=sum+input2[i];
//                }
                sum=sum+input2[i];//finding total sum
                System.out.println("sum"+sum);
                for(j=i;j<input1;j++){
                    s=s+input2[j];//
                    System.out.println("s"+s);

                    if (min>s){
                        min=s;
                        System.out.println("min"+min);
                    }
                }
//                if(i==0){
//                    sum=0;
//                }else if(i==(input1-1) && input2[i]>0){
//                        sum=sum+input2[i-1];//normal sum of numbers we have faced till now
//                        sum=sum+input2[i];
//                }
//                else {
//                    sum=sum+input2[i-1];//normal sum of numbers we have faced till now
//                }
//                System.out.println(sum);
//                if (input2[i]>0){
//                    sum=sum+input2[i];
//                }
//                if(input2[i]<0){
//                    s=0;
//                    for (j=i;j<input1;j++){//getting sum of only consecutive negative nos after making them positive
//                        if(input2[j]<0){
//                            s=s+Math.abs(input2[j]);
//                        }
//                        else if (input2[j]>0){
//                            break;
//                        }
//                    }
//                    s=s+sum;//adding sum of numbers that occured before we got this negative number
//                    for (k=j;k<input1;k++){//once we got a positive number, now we will not flip, just normal addition
//                        s=s+input2[k];
//                    }
//                    if(max<s){
//                        max=s;
//                    }
//                }//block is ended now for the case if we get negative no
            }

            max=sum-min-min;
            return max;
        }
        else {
            throw new UnsupportedOperationException("input1 is not valid. Please enter in range 1 to 500");
        }
    }


    public static void main(String[] args) throws Exception {
        int input1=5;
        //int [] input2={-2, 3, -1, -4, -2};//8
        int [] input2={-1, 2, 3, 4, -5};//13
        UserMainCode userMainCode=new UserMainCode();
        int maxSum=userMainCode.Cards(input1,input2);
        System.out.println(maxSum);
    }
}
