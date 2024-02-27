package myComparatorCollection;

import java.util.Comparator;

public class AgeComparator implements Comparator<EmployeeComparator> {
    @Override
    public int compare(EmployeeComparator o1, EmployeeComparator o2) {

        return o1.getAge()- o2.getAge();
    }
}
