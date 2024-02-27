package hashmapLibrary;

public class MyMain {
    public static void main(String[] args) {
        String booksInLibrary="125,C,abc|126,Java,James Gosling|127,DS,Adam|128,DS,Adam|128,JavaS,James Gosling|129,DS,Adam";
        String borrowedUsers="125,101|127,102|128,101";
        String query="1,125";//2,101 ,  3,DS , 4,James Gosling , 5,Java
        //String query="1,126";
        //String query="2,101";
        //String query="3,DS";
        //String query="4,James Gosling";
        //String query="5,Java";

        Library lb= new Library();
        System.out.println(lb.createLibraryMap(booksInLibrary));
        System.out.println(lb.createUserMap(borrowedUsers));
        System.out.println(lb.getQuery(booksInLibrary,borrowedUsers,query));

    }
}
