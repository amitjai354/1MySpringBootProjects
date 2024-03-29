package hashSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HSPresent {

    public static boolean isHSPresent() throws FileNotFoundException, IOException
    {
        String line,s="";
        File file=new File("/projects/challenge/HashSet/src/main/java/com/fresco/Hashset.java");
        FileReader fr=new FileReader(file);
        BufferedReader br=new BufferedReader(fr);
        while((line=br.readLine())!=null)
            s+=line;
        String str1 = null,str2=null;
        if(s.contains("import java.util.HashSet;") || s.contains("import java.util.*"))
        {
            try
            {
                str1=s.substring(s.indexOf("HashSet<"),s.indexOf("new HashSet"));
            }
            catch(Exception e)
            {
            }
            try
            {
                str2=s.substring(s.indexOf("Set<"),s.indexOf("new HashSet"));
            }
            catch(Exception e)
            {
            }
        }
        if(str1 != null) {
            if(str1.length()>10)
                return true;
        }
        if(str2 != null) {
            if(str2.length()>5)
                return true;
        }
        return false;
    }
}

