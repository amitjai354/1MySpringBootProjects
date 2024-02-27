package myComparatorCollection;

import java.util.Comparator;

public class CityComparator implements Comparator<EmployeeComparator>{

    @Override
    public int compare(EmployeeComparator o1, EmployeeComparator o2) {
        return o1.getCity().compareTo(o2.getCity());
    }
}