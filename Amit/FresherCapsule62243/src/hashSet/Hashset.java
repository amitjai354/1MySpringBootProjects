package hashSet;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Hashset
{
    public static String getOut(int numberOfMatches, String squads, int squad1, int squad2)
    {
        //write your code here
        String [] matches = squads.split("#");
        HashSet<String> unionSett = new HashSet<>();
        HashSet<String> intersectionSett = new HashSet<>();
        HashSet <String> tempSet = new HashSet<>();
        HashSet<String> setNotPlayedSquad1 = new HashSet<>();
        HashSet<String> setPlayedSquad2= new HashSet<>();
        for (int i=0; i<matches.length;i++) {
            String[] str = matches[i].split(" ");
            if(i==0 && matches.length==1){
                Collections.addAll(intersectionSett, str);
            }
            else if(i == 0 && i+1==squad1){
                Collections.addAll(unionSett, str);
                setNotPlayedSquad1.addAll(List.of(str));
            } else if (i==0 && i+1==squad2) {
                Collections.addAll(unionSett, str);
                setNotPlayedSquad1.addAll(List.of(str));
            } else if (i==0) {
                Collections.addAll(unionSett, str);
            } else{
                Collections.addAll(tempSet, str);
                tempSet.retainAll(unionSett);//tempet will retain only elements present in unionsett.. it removes other elements from itself
                intersectionSett.addAll(tempSet);
                tempSet.clear();
                unionSett.clear();
                unionSett.addAll(intersectionSett);
            //hs.addAll(List.of(str));

            if(i+1==squad1){
                setNotPlayedSquad1.addAll(List.of(str));
            }
            if(i+1==squad2){
                setPlayedSquad2.addAll(List.of(str));
            }
        }
        }
        System.out.println(intersectionSett);
        //System.out.println(setPlayedSquad2);
        setPlayedSquad2.removeAll(setNotPlayedSquad1);//setPlayedSquad2 will remove all elemets from itself which are present in setnotPlayedSquad1
        System.out.println(setPlayedSquad2);
        return null;
    }
}

