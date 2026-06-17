import java.io.*;
import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 5000;
    
    // A thread-safe list to hold all active client handlers
    public static final CopyOnWriteArrayList<ClientHandler> activeClients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        // Start a dedicated thread for the server operator's keyboard typing
       

        // Main thread manages incoming client connections
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("\nServer started. Waiting for clients on port " + PORT + "...");
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
               System.out.println("\nNew client connected from: " + clientSocket.getRemoteSocketAddress());
                
               
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                threadPool.execute(clientHandler); 
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
