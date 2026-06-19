import java.io.*;
import java.net.*;

public class Client2 {
    public static void main(String[] args) {
        try (Socket clientSocket = new Socket("192.168.194.223", 5001)) { // replace IP with server's IP
            System.out.println("Connected to server!");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Thread to read from server
            new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println("Server: " + msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Thread to write to server
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
