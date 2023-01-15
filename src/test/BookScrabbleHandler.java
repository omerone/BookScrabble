package test;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class BookScrabbleHandler implements ClientHandler{

    String line;
    String[] words;

    @Override
    public void handleClient(InputStream inFromclient , OutputStream outToClient) {
        Scanner scanner = new Scanner(inFromclient);
        PrintWriter out = new PrintWriter(outToClient);
        try {
            if(scanner.hasNextLine()){
                line = scanner.nextLine();
                words = line.split(",");
            }
        } catch (Exception e){e.printStackTrace();}
        DictionaryManager dM = new DictionaryManager();

        String words2 [] = new String[words.length-1];
        for(int i =0 , s=1; s<words.length; i++ , s++){
            words2[i] = words[s];
        }

        boolean flag;
        if(words[0].equals("C")){
            flag = dM.challenge(words2);
        }
        else {
            flag = dM.query(words2);
        }

        if(flag){
            out.println("true");
            out.flush();
        }
        else {
            out.println("false");
            out.flush();
        }
        out.close();
    }

    @Override
    public void close() {

    }
}
