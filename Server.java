import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started. Waiting for client...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected: " + socket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Thread to read from client
            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println("Client: " + msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Thread to write to client
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String msgOut;
            while ((msgOut = keyboard.readLine()) != null) {
                out.println(msgOut);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
