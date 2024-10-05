package hashmapLibrary;
import java.util.*;
public class Library {

        String bookName;
        String author;
        Library()
        {
        }
    Library(String bookName,String author)
    {
        this.bookName=bookName;
        this.author=author;
    }

    @Override
    public String toString() {
        return this.bookName+","+this.author;
    }

    @Override
        public int hashCode() {
            int hash = 3;
            hash = 83 * hash + Objects.hashCode(this.bookName);
            hash = 83 * hash + Objects.hashCode(this.author);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Library other = (Library) obj;
            if (!Objects.equals(this.bookName, other.bookName)) {
                return false;
            }
            if (!Objects.equals(this.author, other.author)) {
                return false;
            }
            return true;
        }



        public HashMap<Integer,Library> createLibraryMap(String booksInLibrary)
        {
            HashMap<Integer,Library> hm= new HashMap<>();
            String[] str=booksInLibrary.split("\\|");
            for(int i=0;i<str.length;i++){
                String[] str1= str[i].split(",");
                hm.put(Integer.parseInt(str1[0]),new Library(str1[1],str1[2]));
            }
            return hm;
        }
        public HashMap<Integer,Integer> createUserMap(String borrowedUsers)
        {
            String[] str=borrowedUsers.split("\\|");
            HashMap<Integer, Integer> hm = new HashMap<>();
            for(int i=0;i< str.length;i++){
                String[] str1=str[i].split(",");
                hm.put(Integer.parseInt(str1[0]), Integer.parseInt(str1[1]));
            }
            return hm;
        }


        public String getQuery(String booksInLibrary,String borrowedUsers,String query)
        {
            String[] s= query.split(",");
            HashMap<Integer, Integer> hmUser = this.createUserMap(borrowedUsers);
            HashMap<Integer, Library> hmBook = this.createLibraryMap(booksInLibrary);

            if(query.startsWith("1")){
                if(hmUser.containsKey(Integer.parseInt(s[1]))){
                    return ("No Stock\nIt is owned by "+hmUser.get(Integer.parseInt(s[1])));
                }
                else {
                    return ("It is available\nAuthor is "+(hmBook.get(Integer.parseInt(s[1]))).author);
                }
            }

            else if (query.startsWith("2")) {
                StringBuilder sb=new StringBuilder();
                hmUser.forEach((k,v)->{
                    if(v==Integer.parseInt(s[1])){
                        sb.append(k).append(" ");
                    }
                });
//                StringBuilder sb1= new StringBuilder();
//                for(Map.Entry<Integer, Integer> entry:hmUser.entrySet()){
//                    if(entry.getValue()==Integer.parseInt(s[1])){
//                        sb1.append(entry.getKey()+" ");
//                    }
//                }
//                return sb1.toString();

                String[] s1=sb.toString().split(" ");
                StringBuilder sb2=new StringBuilder();
                for (String value : s1) {
                    for (Map.Entry<Integer, Library> entry : hmBook.entrySet()) {
                        if (entry.getKey() == Integer.parseInt(value)) {
                            sb2.append(entry.getKey()).append(" ").append(entry.getValue().bookName).append("\n");
                        }
                    }
//                    hmBook.forEach((k,v)->{
//                        if(k==Integer.parseInt(s1[j])){//local variable j should be final inside lambda
//                            sb2.append(k+" "+v.bookName+"\n");
//                        }
//                    });

                }
                    return sb2.toString();
            }

            else if (query.startsWith("3")){
                StringBuilder sb= new StringBuilder();
                hmBook.forEach((k,v)->{
                    if(v.bookName.equals(s[1])){
                        sb.append(k).append(" ");
                    }
                });
                String[] s1= sb.toString().split(" ");
                int count=0;
                for (String value : s1) {
                    for (Map.Entry<Integer, Integer> entry : hmUser.entrySet()) {
                        if (entry.getKey() == Integer.parseInt(value)) {
                            count++;
                        }
                    }
                }
                //return ""+count+" out\n"+(s1.length-count)+" in\n";
                return Integer.toString(count)+" out\n"+(s1.length-count)+" in\n";


            }

            else if (query.startsWith("4")){
                StringBuilder sb= new StringBuilder();
                hmBook.forEach((k,v)->{
                    if(v.author.equals(s[1])){
                        sb.append(v.bookName).append("\n");
                    }
                });
                return sb.toString();

            }

            else if (query.startsWith("5")){
                StringBuilder sb= new StringBuilder();
                hmBook.forEach((k,v)->{
                    if(v.bookName.contains(s[1])){
                        sb.append(k).append(" ").append(v.bookName).append("\n");
                    }
                });
                return sb.toString();
            }

            return null;
        }


        public static void main(String[] args) {
            String booksInLibrary="125,C,abc|126,Java,James Gosling|127,DS,Adam|128,DS,Adam|128,JavaS,James Gosling|129,DS,Adam";
            String borrowedUsers="125,101|127,102|128,101";
            String query="1,126";//2,101 ,  3,DS , 4,James Gosling , 5,Java
            //String query="1,125";
            //String query="2,101";
            //String query="3,DS";
            //String query="4,James Gosling";
            //String query="5,Java";
            Library lb= new Library();
            System.out.println("createLibraryMap: "+lb.createLibraryMap(booksInLibrary));
            System.out.println("createUserMap: "+lb.createUserMap(borrowedUsers));
            System.out.println("query: "+query);
            System.out.println(lb.getQuery(booksInLibrary,borrowedUsers,query));
        }

}
