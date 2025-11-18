package myComparatorCollection;

import java.util.ArrayList;
import java.util.Collections;

public class SortMain {
    public static void main(String[] args) {
        ArrayList<Integer> marks = new ArrayList<>();
        marks.add(23);
        marks.add(13);
        marks.add(30);
        Collections.sort(marks);
        System.out.println(marks);

        ArrayList<String> nameList=new ArrayList<>();
        nameList.add("Abc");
        nameList.add("abc");
        nameList.add("Fgh");
        nameList.add("Cde");
        Collections.sort(nameList);
        System.out.println(nameList);

        System.out.println();
        ArrayList<EmployeeComparable> alComparable = new ArrayList<>();
        alComparable.add(new EmployeeComparable(1,20,"Amit","Lko"));
        alComparable.add(new EmployeeComparable(5,24,"Sahil","Knpr"));
        alComparable.add(new EmployeeComparable(3, 18, "Shn","Knpr"));
        alComparable.add(new EmployeeComparable(2,19,"Suri","Canada"));
        alComparable.add(new EmployeeComparable(4,26,"Ethan","London"));
        System.out.println(alComparable);
        Collections.sort(alComparable);
        System.out.println(alComparable);

        //here either can sort on basis of id or on basis of name.. but not on basis of both.. each time we need to change logic
        //what if first i want to sort on basis of id then i want to sort on basis of name.. use comparator
        //but each time need to create a new class that imlements comparator to define our sorting logic
        //we do not have to do any changes in our Employee class
        System.out.println();
        ArrayList<EmployeeComparator> alComparator = new ArrayList<>();
        alComparator.add(new EmployeeComparator(1,20,"Amit","Lko"));
        alComparator.add(new EmployeeComparator(5,24,"Sahil","Knpr"));
        alComparator.add(new EmployeeComparator(3, 18, "Shn","Knpr"));
        alComparator.add(new EmployeeComparator(2,19,"Suri","Canada"));
        alComparator.add(new EmployeeComparator(4,26,"Ethan","London"));
        System.out.println(alComparator);
        System.out.println();

        //alComparator.sort(new AgeComparator());//this is best way to use sort
        Collections.sort(alComparator,new AgeComparator());
        System.out.println(alComparator);
        alComparator.sort(new CityComparator());
        System.out.println(alComparator);
        System.out.println();

        ArrayList<EmployeeComparator> alComparator1 = new ArrayList<>(alComparator);//creating new arraylist
        alComparator1.sort(new NameComparator());
        System.out.println(alComparator1);
        alComparator1.sort(new CityComparator());
        System.out.println(alComparator1);
    }
}
