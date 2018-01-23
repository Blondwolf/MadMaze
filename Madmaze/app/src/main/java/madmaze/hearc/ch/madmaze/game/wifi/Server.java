package madmaze.hearc.ch.madmaze.game.wifi;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import madmaze.hearc.ch.madmaze.MainActivity;
import madmaze.hearc.ch.madmaze.fragments.GameFragment;

/**
 * Created by thomas on 19.01.2018.
 */

public class Server {

    private MainActivity activity;
    private ServerSocket server;

    public Server(MainActivity activity, int port) {
        this.activity = activity;
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void open() {
        Thread t = new Thread(new Runnable(){
            public void run(){
                while(true){
                    try {
                        Socket client = server.accept();
                        Thread t = new Thread(new ClientConnected(activity, client));
                        t.start();
                    } catch (IOException e) {
                        System.out.println("ERROR");
                        break;
                    }
                }
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    server = null;
                }
            }
        });
        t.start();
    }

    public void stop() {
        if (server != null) {
            try {
                server.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}