package exceptionns;
import java.util.Scanner;

class Validator {
    public static boolean validate(String message) {
        return message.matches("[A-Za-z0-9 ]+");
    }
}
public class Solution {
        private static final Scanner INPUT_READER = new Scanner(System.in);

        public static void main(String[] args) {
            String message = INPUT_READER.nextLine();

            try {
                String encrypted_message = Encrypter.encryptMessage(message);
                if(! encrypted_message.startsWith("InvalidMessageException"))
                    System.out.println(encrypted_message);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

}
