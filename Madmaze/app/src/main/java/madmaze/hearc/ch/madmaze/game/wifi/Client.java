package madmaze.hearc.ch.madmaze.game.wifi;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import madmaze.hearc.ch.madmaze.MainActivity;
import madmaze.hearc.ch.madmaze.fragments.GameFragment;

/**
 * Created by thomas on 19.01.2018.
 */

public class Client implements Runnable {

    private Socket socket;
    private PrintWriter writer = null;
    private BufferedReader reader = null;
    private MainActivity activity;
    private String host;
    private int port;
    private boolean first;

    public Client(MainActivity activity, String host, int port) {
        this.activity = activity;
        this.host = host;
        this.port = port;
        this.first = true;
    }

    @Override
    public void run() {
        int count =0;
        while(count < 10000) { //Hyper moche
            try {
                socket = new Socket(host, port);
                if(socket.isConnected()) {
                    break;
                }
            } catch (IOException e) {
                count++;
            }
        }
        try {
            if(socket != null) {
                while (!socket.isClosed()) {
                    writer = new PrintWriter(socket.getOutputStream(), true);
                    reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    if(activity.isStarted()) {
                        if(first) {
                            writer.println("start");
                            writer.flush();
                            first = false;
                        }
                        String response;
                        while( (response = reader.readLine()) != null ) {
                            activity.update(response);
                        }

                        String message = activity.getServerPos();
                        writer.println(message);
                        writer.flush();
                    }

                }
            }
        } catch (IOException e) {
            Log.e("test", "BOH");
            e.printStackTrace();
        }
    }


    public void stop() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
