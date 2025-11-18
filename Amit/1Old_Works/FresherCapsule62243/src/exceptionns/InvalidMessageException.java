package exceptionns;

public class InvalidMessageException extends Exception{

    public InvalidMessageException(String s) {

        super(s);// pass msg s to parent class constructor
    }
}
