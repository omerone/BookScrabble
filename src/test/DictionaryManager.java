package test;

import java.util.HashMap;

public class DictionaryManager {
    HashMap<String , Dictionary> hashMap = new HashMap<>();
    private static DictionaryManager singleTnDm = null;

    DictionaryManager(){
        hashMap = new HashMap<>();
    }


    public boolean query(String ... args){
        for(int i=0; i<args.length-1; i++){
            if(!hashMap.containsKey(args[i])){
                hashMap.put(args[i], new Dictionary(args[i]));
            }
        }

        boolean flag = false;
        for(Dictionary d : hashMap.values()){
            if(d.query(args[args.length-1])){
               flag = true;
            }
        }
        return flag;

    }


    public boolean challenge(String ... args){
        boolean flag = false;
        for(Dictionary d : hashMap.values()){
            if(d.challenge(args[args.length-1])){
                flag =true;
            }
        }
        return flag;
    }


    public int getSize() {
        return hashMap.size();
    }


    public static DictionaryManager get(){
        if(singleTnDm == null){
            singleTnDm = new DictionaryManager();
        }
        return singleTnDm;
    }

}
