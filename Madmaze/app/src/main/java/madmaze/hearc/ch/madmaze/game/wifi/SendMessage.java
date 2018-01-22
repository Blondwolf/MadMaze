package madmaze.hearc.ch.madmaze.game.wifi;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Dimension;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

import madmaze.hearc.ch.madmaze.fragments.GameFragment;

/**
 * Created by thomas on 19.01.2018.
 */

public class SendMessage extends AsyncTask<String, Void, Void> {

    private String host;
    private int port;
    private Socket client;
    private GameFragment game;

    public SendMessage(GameFragment game, String host, int port) {
        this.game = game;
        this.host = host;
        this.port = port;
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            if(client == null || !client.isConnected())
                client = new Socket(host, port);

            PrintWriter pw = new PrintWriter(client.getOutputStream(), true);
            pw.write(strings[0]);
            pw.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String msg = reader.readLine();
            game.update(true, msg);
            client.close();


        } catch (Exception e) {
        }
        return null;
    }
}
