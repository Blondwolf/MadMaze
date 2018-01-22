package madmaze.hearc.ch.madmaze.game.wifi;

import java.io.IOException;
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

    public Server(GameFragment game, int port) {
        this.game = game;
        this.port = port;
        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();
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

    private class SocketServerThread extends Thread {

        @Override
        public void run() {
            try {
                server = new ServerSocket(port);

                while (true) {
                    Socket socket = server.accept();
                    try {
                        String message = game.getPos();
                        OutputStream outputStream = socket.getOutputStream();
                        PrintStream printStream = new PrintStream(outputStream);
                        printStream.print(message);
                        printStream.close();

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