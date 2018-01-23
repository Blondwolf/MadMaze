package madmaze.hearc.ch.madmaze.game.wifi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import madmaze.hearc.ch.madmaze.fragments.GameFragment;

/**
 * Created by thomas on 19.01.2018.
 */

public class Server {

    private GameFragment game;
    private ServerSocket server;
    private int port;
    private SocketServerThread socketServerThread;

    public Server(GameFragment game, int port) {
        this.game = game;
        this.port = port;
    }

    public void send(String msg) {
        if(socketServerThread == null) {
            socketServerThread = new SocketServerThread();
            socketServerThread.start();
        }
        socketServerThread.send(msg);
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

    public class SocketServerThread extends Thread {

        private Socket client;

        public void send(String data) {
            if(client == null || !client.isConnected()) {
                return;
            }
            try {
                OutputStream outputStream = client.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                printStream.print(data);
                printStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                server = new ServerSocket(port);

                while (true) {
                    if(client == null || !client.isConnected()) {
                        client = server.accept();
                    }
                    try {

                        BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        String msg = reader.readLine();
                        game.update(false, msg);

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}