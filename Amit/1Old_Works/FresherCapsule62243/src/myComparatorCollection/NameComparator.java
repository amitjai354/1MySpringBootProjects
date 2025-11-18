package myComparatorCollection;

import java.util.Comparator;

public class NameComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        EmployeeComparator e1=(EmployeeComparator) o1;
        EmployeeComparator e2= (EmployeeComparator) o2;
        return e1.getName().compareTo(e2.getName());
    }
}
