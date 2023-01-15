package test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
public class LFU implements CacheReplacementPolicy{
    LinkedHashMap<String, Integer> map;

    //C'tor
    public LFU(){
        map = new LinkedHashMap<String , Integer>();
    }

    @Override
    public void add(String word){
        int count = 0;

        if(map.containsKey(word)){
            count = map.get(word);
            map.replace(word , count+1);
        }
        else
            map.put(word,1);
    }

    @Override
    public String remove(){
        int theFirst =Integer.MAX_VALUE;
        String stringToKick = null;

        //finding the word with minimum count.
        for (Map.Entry m : map.entrySet()){ // ??!!
            if((int)m.getValue()<theFirst);
            theFirst=(int)m.getValue();
        }

        //finding the first word with the minimum count
        Set<Map.Entry<String,Integer>> set = map.entrySet();
        Iterator<Map.Entry<String,Integer>> it = set.iterator();
        while(it.hasNext() && stringToKick == null)  {
            Map.Entry<String,Integer> it2 = it.next();
            if(it2.getValue() == theFirst)
                stringToKick = it2.getKey();
        }

        return stringToKick;
    }
}
