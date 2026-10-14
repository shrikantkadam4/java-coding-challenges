import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListExample1 {
    public static void main(String[] args){
        List<Integer> list1=new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        System.out.println("list1-"+list1);
        System.out.println(list1.get(2));
        list1.remove(2);
        System.out.println("list1-"+list1);

        List<Integer> list2= Arrays.asList(1,2,3);
        System.out.println("list2-"+list2);

        List<Integer> list3= List.of(4,5,6);
        System.out.println("list3-"+list3);
    }
}
