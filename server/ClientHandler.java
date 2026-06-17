import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    public String getUsername() {
        return this.username;
    }

    private void broadcastMessage(String messageToSend) {
        for (ClientHandler client : Server.activeClients) {
            if (!client.username.equals(this.username)) {
                client.sendMessage(messageToSend);
            }
        }
    }

    // New helper method to route a message to just one specific person
    private void sendPrivateMessage(String targetUser, String messageToSend) {
        boolean found = false;
        for (ClientHandler client : Server.activeClients) {
            if (client.username.equalsIgnoreCase(targetUser)) {
                client.sendMessage("[PM from " + this.username + "]: " + messageToSend);
                found = true;
                break;
            }
        }
        if (!found) {
            this.sendMessage("[SYSTEM ERROR]: User '" + targetUser + "' not found.");
        }
    }

    public boolean isUsernameTaken(String name){
       
        for(ClientHandler client : Server.activeClients){
            if(client.username.equalsIgnoreCase(name)){
                return true;
            }
        }

        return false;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

           while (true) {
                out.println("SUBMIT_NAME_REQ: Please enter a username:");
                String inputName = in.readLine();
                
                if (inputName == null) {
                    return; // Client disconnected early
                }
                
                inputName = inputName.trim();

                if (inputName.isEmpty()) {
                    out.println("[SYSTEM ERROR]: Username cannot be blank!");
                } else if (isUsernameTaken(inputName)) {
                    // Reject and loop again
                    out.println("[SYSTEM ERROR]: That username is already taken! Try another.");
                } else {
                  
                    this.username = inputName;
                    break;
                }
            }

            Server.activeClients.add(this);
            out.println("Welcome, " + this.username + "! Connection secured.");
            broadcastMessage("[SYSTEM]: " + this.username + " has entered the chat!");
            System.out.println("\n[SYSTEM]: " + this.username + " joined.");
            

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if ("quit".equalsIgnoreCase(inputLine.trim())) {
                    break;
                }

                // Check if the message is a Private Message command: /pm username message
                if (inputLine.trim().startsWith("/pm ")) {
                    String[] tokens = inputLine.trim().split(" ", 3);
                    if (tokens.length >= 3) {
                        String targetUser = tokens[1];
                        String privateMsg = tokens[2];
                        sendPrivateMessage(targetUser, privateMsg);
                    } else {
                        this.sendMessage("[SYSTEM]: Invalid format. Use: /pm <username> <message>");
                    }
                } else {
                    // Normal text goes to everyone
                    System.out.println("\n[" + this.username + "]: " + inputLine);
                    System.out.print("Server type message: ");
                    broadcastMessage("[" + this.username + "]: " + inputLine);
                }
            }
        } catch (IOException e) {
            System.err.println("Handler error: " + e.getMessage());
        } finally {
            Server.activeClients.remove(this);
            broadcastMessage("[SYSTEM]: " + this.username + " has left the chat.");
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
