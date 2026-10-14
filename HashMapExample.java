import java.util.*;

public class HashMapExample {
    public static void main(String[] args){
        Map<String, Integer> myMap=new HashMap<>();
        myMap.put("Test1",1);
        myMap.put("Test2",2);

        System.out.println(myMap);

        myMap.entrySet().forEach(System.out::println);
    }
}
