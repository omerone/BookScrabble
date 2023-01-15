package test;

import java.util.HashSet;
import java.util.Set;

public class CacheManager {
	int size;
    int cacheMax;
    CacheReplacementPolicy crp;
    public Set<String> cache;

    public CacheManager(int size, CacheReplacementPolicy crp){
        this.size = size;
        this.cacheMax = 0;
        this.crp = crp;
        this.cache = new HashSet<String>();
    }

    //check if word is in cache
    boolean query(String word){
        return cache.contains(word);
    }

    void add(String word){

        String wordTokick = null;
        this.crp.add(word);

        if (this.cacheMax == this.size){
            wordTokick = this.crp.remove();
            this.cache.remove(wordTokick);
        }

        else
            this.cacheMax ++;

        this.cache.add(word);
    }
}
