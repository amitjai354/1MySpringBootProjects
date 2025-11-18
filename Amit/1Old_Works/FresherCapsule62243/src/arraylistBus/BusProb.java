package arraylistBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

class Passanger {
    int id;
    float fare;

    @Override
    public String toString() {
        return this.id + " " + this.fare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getFare() {
        return fare;
    }

    public void setFare(float fare) {
        this.fare = fare;
    }

    public Passanger(int id, float fare) {
        this.id = id;
        this.fare = fare;
    }
}

public class BusProb {

    public String output(int capacity, int stops, List<String> listOfInputStrings, String query) {
        String outstring = "";
        //Write your code here

        int totalNoOfPassInsideBus = 0;
        int totalNoOfPasPayingSixAsFare = 0;
        int totalNoOfPasPayingThreeAsFare = 0;
        int totalNoOfPasPayingNasFare = 0;
        float faree;
        List<Passanger> listPasIdFare = new ArrayList<>();
        List<Integer> listPasGotInBus = new ArrayList<>();
        List<Integer> listPasGotOutBus = new ArrayList<>();
        HashSet<Integer> setTotalPasGotInBus = new HashSet<>();
        HashSet<Integer> setTotalPasGotOutBus = new HashSet<>();
        for (int i = 0; i < listOfInputStrings.size(); i++) {
            listPasGotInBus.clear();
            listPasGotOutBus.clear();
            String[] str = listOfInputStrings.get(i).split(" ");
            for (String s : str) {
                if (s.startsWith("+")) {
                    listPasGotInBus.add(Integer.parseInt(s.substring(1)));
                } else {
                    listPasGotOutBus.add(Integer.parseInt(s.substring(1)));
                }
            }
            setTotalPasGotInBus.addAll(listPasGotInBus);
            setTotalPasGotOutBus.addAll(listPasGotOutBus);
            totalNoOfPassInsideBus = totalNoOfPassInsideBus + listPasGotInBus.size() - listPasGotOutBus.size();
            System.out.println("Pasngrs got on bus at stop " + i + ": " + listPasGotInBus);
            System.out.println("Pasngrs got out bus at stop " + i + ": " + listPasGotOutBus);
            System.out.println("totalNoOfPassInsideBus at stop " + i + ": " + totalNoOfPassInsideBus);
            System.out.println("set of overall psngrs who have got on bus till now: " + setTotalPasGotInBus);
            System.out.println("set of overall psngrs who have got out bus till now: " + setTotalPasGotOutBus);
            if (totalNoOfPassInsideBus % 2 != 0 || totalNoOfPassInsideBus % 4 != 0) {
                if (totalNoOfPassInsideBus <= Math.ceil(.25 * capacity)) {
                    totalNoOfPasPayingSixAsFare = totalNoOfPasPayingSixAsFare + listPasGotInBus.size();;
                    faree = (float) (capacity + capacity * 0.6);
                    for (Integer pasGotInBus : listPasGotInBus) {
                        listPasIdFare.add(new Passanger(pasGotInBus, faree));
                    }
                }
                else if (totalNoOfPassInsideBus >= Math.ceil(.25 * capacity) && totalNoOfPassInsideBus <= Math.ceil(.5 * capacity)) {
                    totalNoOfPasPayingThreeAsFare = totalNoOfPasPayingThreeAsFare + listPasGotInBus.size();;
                    faree = (float) (capacity + capacity * 0.3);
                    for (Integer pasGotInBus : listPasGotInBus) {
                        listPasIdFare.add(new Passanger(pasGotInBus, faree));
                    }
                }
                else {
                    totalNoOfPasPayingNasFare = totalNoOfPasPayingNasFare + listPasGotInBus.size();
                    faree = (float) (capacity);
                    for (Integer pasGotInBus : listPasGotInBus) {
                        listPasIdFare.add(new Passanger(pasGotInBus, faree));
                    }
                }
            }
            else {
                if (totalNoOfPassInsideBus <= .25 * capacity) {
                    totalNoOfPasPayingSixAsFare = totalNoOfPasPayingSixAsFare + listPasGotInBus.size();;
                    faree = (float) (capacity + capacity * 0.6);
                    for (Integer pasGotInBus : listPasGotInBus) {
                        listPasIdFare.add(new Passanger(pasGotInBus, faree));
                    }
                }
                else if (totalNoOfPassInsideBus >= .25 * capacity && totalNoOfPassInsideBus <= .5 * capacity) {
                    totalNoOfPasPayingThreeAsFare = totalNoOfPasPayingThreeAsFare + listPasGotInBus.size();;
                    faree = (float) (capacity + capacity * 0.3);
                    for (Integer pasGotInBus : listPasGotInBus) {
                        listPasIdFare.add(new Passanger(pasGotInBus, faree));
                    }
                }
                else {
                    totalNoOfPasPayingNasFare = totalNoOfPasPayingNasFare + listPasGotInBus.size();
                    faree = (float) (capacity);
                    for (Integer pasGotInBus : listPasGotInBus) {
                        listPasIdFare.add(new Passanger(pasGotInBus, faree));
                    }
                }
            }
            System.out.println("totalNoOfPasPayingSixAsFare till now in complete trip: "+totalNoOfPasPayingSixAsFare);
            System.out.println("totalNoOfPasPayingThreeAsFare till now in complete trip: "+totalNoOfPasPayingThreeAsFare);
            System.out.println("totalNoOfPasPayingNasFare till now in complete trip: "+totalNoOfPasPayingNasFare);
            System.out.println();
        }
        System.out.println(listPasIdFare);
        System.out.println();
        StringBuilder sb = new StringBuilder();
        if (query.startsWith("1")) {
            sb.append(setTotalPasGotInBus.size()).append(" passengers ").append(setTotalPasGotInBus);
            sb.append(" got on the bus and\n");
            sb.append(setTotalPasGotOutBus.size()).append(" passengers ").append(setTotalPasGotOutBus);
            sb.append(" got out of the bus");
            outstring = sb.toString();
        }
        else if (query.startsWith("2")) {
            int totalNoOfPasPayingSixAsFaree=0;
            int totalNoOfPasPayingThreeAsFaree=0;
            int totalNoOfPasPayingNasFaree=0;
            for(int i=0;i<listPasIdFare.size();i++){
                if (listPasIdFare.get(i).fare == capacity + capacity * 0.6) {
                    //++ count of pass paying 0.6 as fare
                    //but we have already calculated this value above so no need now..
                    totalNoOfPasPayingSixAsFaree++;
                }
                else if (listPasIdFare.get(i).fare==capacity+capacity*0.3){
                    totalNoOfPasPayingThreeAsFaree++;
                }
                else if (listPasIdFare.get(i).fare==capacity) {
                    totalNoOfPasPayingNasFaree++;
                }
            }
            sb.append(totalNoOfPasPayingSixAsFaree).append(" pasngr <25% .6 travelled with a fare of ");
            sb.append(capacity + capacity * 0.6).append("\n");
            sb.append(totalNoOfPasPayingThreeAsFare).append(" pasngr <50% .3 travelled with a fare of ");
            sb.append(capacity + capacity * 0.3).append("\n");
            sb.append(totalNoOfPasPayingNasFaree).append(" pasngr <100% n travelled with a fare of ");
            sb.append(capacity).append("\n");
            outstring = sb.toString();
        } else if (query.startsWith("3")) {
            int pasngrId = Integer.parseInt(query.substring(3));
            float totalFare = 0.0f;
            for (Passanger i : listPasIdFare) {
                if (i.id == pasngrId) {
                    totalFare = totalFare + i.fare;
                }
            }
            //outstring = String.valueOf(totalFare);
            outstring="total paid by this passenger in the overall trip: "+totalFare;
        } else if (query.startsWith("4")) {
            int psngerId = Integer.parseInt(query.substring(3));
            int count = 0;
            for (Passanger p : listPasIdFare) {
                if (p.id == psngerId) {
                    count++;
                }
            }
            //outstring = Integer.toString(count);
            outstring="No. of times this passenger got on the bus in the overall trip: "+count;
        } else if (query.startsWith("5")) {
            int psngrId = Integer.parseInt(query.substring(3));
            setTotalPasGotInBus.removeAll(setTotalPasGotOutBus);
            if (setTotalPasGotInBus.contains(psngrId)) {
                outstring = "Psngr " + psngrId + " was inside the bus at the end of the trip";
            } else {
                outstring = "Psngr " + psngrId + " was not inside the bus at the end of the trip";
            }
        }
        return outstring;
    }

    public static void main(String[] args) {
        int capacity = 10;
        int stops = 4;
        //List<String> listOfInputStrings=["+2501 +2502 +2503 +2504","-2501 -2504"];//list is interface so can not directly instantiate it
        //List<String> listOfInputStrings= Arrays.asList("+2501 +2502 +2503 +2504","-2501 -2504");//immutable
        List<String> listOfInputStrings = new ArrayList<>();
        listOfInputStrings.add("+2501 +2502 +2503 +2504");
        listOfInputStrings.add("-2501 -2504 +2505 +2506 +2507 +2509");
        listOfInputStrings.add("+2501 +2511 -2502 -2505");
        listOfInputStrings.add("+2513 -2507 -2503 -2511 -2509");
        //String query = "1";//no of pasngers got in and got out of the bus
        //String query = "2";//no of pasngrs who paid .6, .3, n fare
        //String query="3, 2501";//how much total paid by this passenger in the overall trip
        //String query="4, 2501";//how many times this passenger got on the busd in the overall trip
        //String query="5, 2501";//is this passenger inside the bus at the end of the trip
        String query = "5, 2513";

        BusProb bp = new BusProb();
        System.out.println(bp.output(capacity, stops, listOfInputStrings, query));
    }

}
