package madmaze.hearc.ch.madmaze.game.wifi;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import madmaze.hearc.ch.madmaze.MainActivity;

/**
 * Created by thomas on 23.01.2018.
 */

public class ClientConnected implements Runnable {

    private Socket client;
    private PrintWriter writer;
    private BufferedReader reader;
    private MainActivity activity;
    private boolean isStarted;

    public ClientConnected(MainActivity activity, Socket client) {
        this.activity = activity;
        this.client = client;
        this.isStarted = false;
    }

    @Override
    public void run() {

        try {
            while(true) {
                writer = new PrintWriter(client.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                if(!isStarted) {
                    String message;
                    while((message = reader.readLine()) != null ) {
                        activity.update(message);
                    }
                    isStarted = true;
                } else {
                    String pos = activity.getClientPos();
                    Log.e("CO", "READ2"+pos);
                    writer.println(pos);
                    writer.flush();

                    String message;
                    while( (message = reader.readLine()) != null ) {
                        activity.update(message);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
