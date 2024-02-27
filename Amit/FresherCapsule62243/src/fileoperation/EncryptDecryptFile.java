package fileoperation;

import java.io.*;
import java.util.Scanner;

public class EncryptDecryptFile {
    public void writeDecryptionFile(String message) throws IOException {
        //File file = new File("D:\\Intellij Project\\FresherCapsule62243\\DecryptionFile.txt");
        // above method creates file in memory.. to create file in hard disk call createNewFiler()
        File file = new File("D:\\Intellij Project\\FresherCapsule62243\\DecryptionFile.txt");
        file.createNewFile();
        System.out.println(file.getName());

        BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\Intellij Project\\FresherCapsule62243\\DecryptionFile.txt"));
        bw.write(message);
        bw.flush();
        bw.close();
    }
    public String readEncryptionFile() throws IOException {
        File file = new File("D:\\Intellij Project\\FresherCapsule62243\\DecryptionFile.txt");
        //Scanner sc = new Scanner(file);
        StringBuilder sb= new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine())!=null) {//while(br.readLine()!=null).. here read line one time
                                            //then while ab.append(br.readLine().. read 2nd line as well
                                        //so in sb only 2nd line stored.. and now no more line left so loop ends
            //System.out.println(line);
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
    public static void main(String[] args) throws IOException {
        String msg= "Hello World\nHi There";
        EncryptDecryptFile edf = new EncryptDecryptFile();
        edf.writeDecryptionFile(msg);
        System.out.println(edf.readEncryptionFile());
    }
}
