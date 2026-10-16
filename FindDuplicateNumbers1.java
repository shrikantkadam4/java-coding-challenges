
import java.util.*;

public class FindDuplicateNumbers1 {
    public static void main(String[] args){
        List<Integer> numbers=Arrays.asList(1,2,3,2,1,4,2);
        Set<Integer> duplicates=new HashSet<>();

        numbers.stream()
                .filter(n -> !duplicates.add(n))
                .distinct()
                .sorted()
                .forEach(System.out::println);
    }
}
