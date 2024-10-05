package multipleinheritanceHockeyFootball;
import java.util.Arrays;
import java.util.Scanner;

    interface HockeyTeam{
        int calculateHockeyScore();
        int findHighestGoalByIndividualInHockey();
    }

    interface FootballTeam{
        int calculateFootballScore();
        int findHighestGoalByIndividualInFootball();
    }



    class Sport implements HockeyTeam, FootballTeam{
        int[] hockeyPlayers = new int[11];
        int[] footballPlayers = new int[11];

        Sport(int[] paramHockeyPlayers, int[] paramFootballPlayers){
            for(int i = 0; i < 11; i++)
            {
                //System.out.println(paramHockeyPlayers[i]);
                hockeyPlayers[i] = paramHockeyPlayers[i];
            }

            for(int i = 0; i < 11; i++)
            {
                footballPlayers[i] = paramFootballPlayers[i];
            }
        }

        public int calculateHockeyScore(){
            int sum=0;
            for(int i = 0; i < 11; i++)
            {
                sum=sum+hockeyPlayers[i];
            }
            return sum;
        }

        public int calculateFootballScore(){
            int sum=0;
            for(int i = 0; i < 11; i++)
            {
                sum=sum+footballPlayers[i];
            }
            return sum;
        }

        public int findHighestGoalByIndividualInHockey(){
            int max=0;
            for(int i = 0; i < 11; i++)
            {
                if (max<hockeyPlayers[i]){
                    max=hockeyPlayers[i];
                }
            }
            return max;
        }

        public int findHighestGoalByIndividualInFootball(){
            int max=0;
            for(int i = 0; i < 11; i++)
            {
                if (max<footballPlayers[i]){
                    max=footballPlayers[i];
                }
            }
            return max;
        }
    }



    public class Solution{
        public static void main(String args[])
        {
            Scanner sc = new Scanner(System.in);
            int[] hockeyPlayers = new int[11];
            int[] footballPlayers = new int[11];

            for(int i = 0; i < 11; i++)
            {
                hockeyPlayers[i] = sc.nextInt();
            }

            for(int i = 0; i < 11; i++)
            {
                footballPlayers[i] = sc.nextInt();
            }

            Sport s = new Sport(hockeyPlayers, footballPlayers);
            try{
                HockeyTeam.class.getMethod("calculateHockeyScore");
                HockeyTeam.class.getMethod("findHighestGoalByIndividualInHockey");
                FootballTeam.class.getMethod("calculateFootballScore");
                FootballTeam.class.getMethod("findHighestGoalByIndividualInFootball");

                if(s instanceof HockeyTeam && s instanceof FootballTeam)
                {
                    System.out.println(s.calculateHockeyScore());
                    System.out.println(s.calculateFootballScore());
                    System.out.println(s.findHighestGoalByIndividualInHockey());
                    System.out.println(s.findHighestGoalByIndividualInFootball());
                }
            }
            catch (NoSuchMethodException ex)
            {
                System.out.println("No such function is exits");
            }
        }
}
