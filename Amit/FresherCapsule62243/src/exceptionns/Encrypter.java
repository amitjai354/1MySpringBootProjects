package exceptionns;

public class Encrypter {
    public static String encryptMessage(String name) throws InvalidMessageException {//for checked exception either throws or
        // tryCatch.. but for runtime exception no need of throws
        //Exception class is parent of checked and runtime exception so need to use throws
        //Throwable class is parent of Exception and Error class
        if(Validator.validate(name)) {
            name.toLowerCase();
            StringBuilder sb = new StringBuilder(name);
            sb.reverse();
            return sb.toString();
        }
        else{
            //we manually throw exception when exception is not from java prelisted exceptions eg age<18 is not exception for
            //java but for driving class it is
            //can manualy throw both checked and runtime excptn.. only thing is that for checked we need to use throws in
            //method signature.. or throw exception inside try catch tghen no need of throws..
            //this way we asre throwing and handling exception as well
            throw new InvalidMessageException("Try again with valid message");
            //return "InvalidMessageException:Try again with valid message";
           // return ime.getMessage().toString();
        }
    }
}
