package test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {

    private int port;
    private ClientHandler ch;
    private volatile boolean stop;

    MyServer(int port , ClientHandler clientHandler){
        this.port = port;
        this.ch = clientHandler;
        this.stop = false;
    }

    public void start (){
        new Thread(()-> {
            try {
                runServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

	public void close() {
        stop = true;
    }

    private void runServer()throws Exception{
        ServerSocket server = new ServerSocket(port);
        server.setSoTimeout(1000);
        while (!stop) {
            try {
                Socket aClient = server.accept();
                try {
                    ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());

                    aClient.getInputStream();
                    aClient.getOutputStream();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            }
        }
        server.close();
    }

    public void readInputAndSend(BufferedReader in , PrintWriter out , String exit_str){
        try {
            String line;
            while (!(line = in.readLine()).equals(exit_str)){
                out.println(line);
                out.flush();
            }
        }
        catch (Exception e){e.printStackTrace();}
    }
}
