package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Dictionary {

    CacheReplacementPolicy LRU;
    CacheReplacementPolicy LFU;
    CacheManager cacheForExist;
    CacheManager cacheForNonExist;
    String[] fileNames;
    BloomFilter bf;
    //each fileName is reprasent a book.
    public Dictionary(String... fileNames){
        LRU = new LRU();
        LFU = new LFU();
        this.cacheForExist = new CacheManager(400, new LRU());
        this.cacheForNonExist = new CacheManager(100, new LFU());
        this.bf = new BloomFilter(256, "MD5", "SHA1");
        this.fileNames = fileNames.clone();

        for(String s : fileNames){
            enterWordToBf(s);
        }

    }

    public boolean query(String word){

        if (cacheForExist.query(word))
            return true;

        if(cacheForNonExist.query(word))
            return false;

        if (bf.contains(word)){
            cacheForExist.add(word);
            return true;
        }
        else {
            cacheForNonExist.add(word);
            return false;
        }
    }

    public boolean challenge(String word){
        if(IOSearcher.search(word, fileNames)){
            cacheForExist.add(word);
            return true;
        }
        else {
            cacheForNonExist.add(word);
            return false;
        }
    }


    public void enterWordToBf(String fileName){
        try {
            Scanner scan = new Scanner(new File(Paths.get(fileName).toUri()));
            while (scan.hasNext()) {
                bf.add(scan.next());
            }
        }
        catch (FileNotFoundException e){
            System.out.println("file not found");
        }
    }
}
