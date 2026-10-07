import java.util.LinkedHashMap;
import java.util.Map;

public class FindFirstNonRepeatingCharacter {
    public static void main(String[] args){
        String string="swiss";
        Map<Character,Integer> map=new LinkedHashMap<>();

        for(char ch:string.toCharArray()){
            map.put(ch, map.getOrDefault(ch,0)+1);
        }

        Character result=null;
        for(Map.Entry<Character,Integer> entry:map.entrySet()){
            if (entry.getValue()==1){
                result=entry.getKey();
                break;
            }
        }
        System.out.println(result!=null
        ? "First Non-Repeating Character- "+result
        : "Non-Repeatin Chracter Not Found");
    }
}
