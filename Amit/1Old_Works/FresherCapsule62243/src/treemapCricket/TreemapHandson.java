package treemapCricket;
import java.util.*;
public class TreemapHandson {
    //if you have 3 items to store in map.. break it in two map.. with same key
    //but one vale in one map and other value in second map

        public TreeMap<Integer,String> createPlayerPositionMap(String cricketDataset) {
            TreeMap<Integer, String> tm1 = new TreeMap<>();
            String [] str=cricketDataset.split("\\|");
            for(String s:str){
                String[] str1=s.split(",");//3,Dhoni,120
                tm1.put(Integer.parseInt(str1[0]),str1[1]);//3,Dhoni
            }
            //System.out.println(tm);
            return tm1;
        }
        public TreeMap<String,Integer> createPlayerScoreMap(String cricketDataset) {
            TreeMap<String, Integer> tm2 = new TreeMap<>();
            String[] str=cricketDataset.split("\\|");
            for(String s:str){
                String[] str1=s.split(",");//3,Dhoni,120
                tm2.put(str1[1],Integer.parseInt(str1[2]));//Dhoni,120
            }
            return tm2;
        }
        public TreeMap<?,?> createMatchesMap(String cricketDataset) {
            int sum;
            int count;
            double avg=0.0;
            TreeMap<String,Integer> tmp=new TreeMap<>();
            TreeMap<Integer, String> matchMapPlayerName = new TreeMap<>();
            TreeMap<Integer,Integer> matchMapScore=new TreeMap<>();
            TreeMap<Double, String> result = new TreeMap<>();//this is returned
            ArrayList<String> listReoccurPlayerName = new ArrayList<>();
            String[] str=cricketDataset.split("\n");
            //System.out.println(Arrays.toString(str));
            for(int i=0;i<str.length;i++){
                String[] str1=str[i].split("\\|");
                //System.out.println(Arrays.toString(str1));
                for (String s : str1) {
                    String[] str2 = s.split(",");
                    //System.out.println(Arrays.toString(str2));
                    if (Integer.parseInt(str2[0]) == 1) {//getting opener player
                        matchMapPlayerName.put(i, str2[1]);
                        matchMapScore.put(i, Integer.parseInt(str2[2]));
                        //if you have 3 items to store in map.. break it in two map.. with same key
                        //but one vale in one map and other value in second map
                    }
                }
            }
            System.out.println(matchMapPlayerName);
            System.out.println(matchMapScore);
            for(int i=0;i<matchMapPlayerName.size();i++){
                if(!(listReoccurPlayerName.contains(matchMapPlayerName.get(i)))) {//if list does not contain matchPlayer name
                    sum = matchMapScore.get(i);
                    count = 1;
                    avg = 0.0;
                    for (int j = i + 1; j < matchMapPlayerName.size(); j++) {
                        if (matchMapPlayerName.get(j).equals(matchMapPlayerName.get(i))) {
                            sum = sum + matchMapScore.get(j);
                            count++;
                            //matchMapPlayerName.remove(j);//removes key 3 as well..key left 0,1 ,2, 4
                            //matchMapScore.remove(j);
                            listReoccurPlayerName.add(matchMapPlayerName.get(j));
                        }
                    }
                    avg = (double) sum / count;
                    result.put(avg,matchMapPlayerName.get(i));
                    //System.out.println(result);
                }
                //System.out.println(result);
            }
            System.out.println(result);
            return result;
        }
        public String getQuery(String cricketDataset,String query)
        {
            if(query.startsWith("1")){
                TreeMap<Integer,String> tm1=createPlayerPositionMap(cricketDataset);
                String[] querySplit=query.split(" ");
                StringBuilder sb=new StringBuilder();
                tm1.forEach((k,v)->{
                    if(k>=Integer.parseInt(querySplit[1]) && k<=Integer.parseInt(querySplit[2])){
                        sb.append(k).append(" ").append(v).append("\n");
                    }
                });
                return sb.toString();
            }
            else if (query.startsWith("2")) {
                TreeMap<String, Integer> tm2=createPlayerScoreMap(cricketDataset);
                TreeMap<Integer,String> tm1=createPlayerPositionMap(cricketDataset);
                String[] querySplit=query.split(" ");
                StringBuilder sb=new StringBuilder();
                ArrayList<String> al=new ArrayList<>();
                tm2.forEach((k,v)->{
                    if(v>=Integer.parseInt(querySplit[1])){
                        al.add(k);
                    }
                });
                TreeMap<Integer, String> result = new TreeMap<>();
                al.forEach((x)->{
                    //System.out.println("x "+x);
                    tm1.forEach((k,v)->{
                        //System.out.println("v "+v);
                        if(x.equals(v)){
                            //sb.append(k).append(" ").append(v).append("\n");
                            result.put(k,v);
                        }
                    });
                });
                result.forEach((k,v)->{
                    sb.append(k).append(" ").append(v).append("\n");
                });
                return sb.toString();
                //return result.toString();
            }
            else if (query.startsWith("3")) {
                TreeMap<?,?> result3=createMatchesMap(cricketDataset);
                System.out.println(result3.lastEntry());
                System.out.println(result3.lastKey());
                System.out.println(result3.get(result3.lastKey()));
                return (String) result3.get(result3.lastKey());//sorted so can get last key directlhy
            }

            return null;
        }

    public static void main(String[] args) {
        String cricketDataset1and2="3,Dhoni,120|1,Virat,103|5,Jadeja,40|2,Rohit,70|4,Pandya,30";
        String cricketDataset3="3,Rohit,100|2,Virat,56|1,Dhoni,150\n3,Rahul,90|2,Virat,100|1,Rohit,99\n1,Rahul,50|2,Virat,64|3,Rohit,78\n1,Dhoni,95|2,Virat,30|3,Rohit,45\n3,Jadeja,43|2,Virat,54|1,Rohit,40|4,Dhoni,59";
        String query1and2="1 3 5";//start and end position given.. find players in between
        //String query1and2="2 50";//threshold score given.. find players who scored more than this
        String query3="3";
        TreemapHandson treemapHandson =new TreemapHandson();
        System.out.println(treemapHandson.getQuery(cricketDataset1and2, query1and2));
        System.out.println(treemapHandson.getQuery(cricketDataset3,query3));
    }

}
