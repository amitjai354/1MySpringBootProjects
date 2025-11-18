package myComparatorCollection;

public class EmployeeComparable implements Comparable<EmployeeComparable>{
    private int id;
    private int age;
    private String name;
    private String city;

//    @Override
//    public int compareTo(Object o) {//if do not pass <Employee> above at class implements comparable
//        return 0;
//    }
    @Override
    public int compareTo(EmployeeComparable o) {
        //return this.id-o.id;
        return (this.name).compareTo(o.name);
    }

    //ArrayList<Employee> al = new ArrayList<>();  //can not create here as it becomes instance variable
    // so cant access in main static
    public EmployeeComparable(){//need to create this otherwise if try to create object of Employee without passing anything-error

    }
    public EmployeeComparable(int id, int age, String name, String city) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.city = city;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "EmployeeComparable{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
