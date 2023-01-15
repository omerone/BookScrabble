package test;

import java.util.LinkedList;

public class LRU implements CacheReplacementPolicy{

    LinkedList<String> words;
    public LRU(){
        words = new LinkedList<String>();
    }

    @Override
    public void add(String word) {
        if(words.contains(word)){
            words.remove(word);
            words.add(word);
            }
        else
            words.add(word);
    }

    @Override
    public String remove(){
        return words.removeFirst();
    }
}
