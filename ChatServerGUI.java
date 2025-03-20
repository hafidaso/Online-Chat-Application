import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Set;

public class ChatServerGUI {
    private static final int PORT = 12345;

    private JFrame frame;
    private JTextArea logArea;
    private Set<ClientHandler> clientHandlers = new HashSet<>();
    private int userIdCounter = 1;

    public ChatServerGUI() {
        // Initialize the GUI components
        frame = new JFrame("Chat Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);

        // Log Area (Scrollable)
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Arial", Font.PLAIN, 14));
        logArea.setBackground(new Color(240, 240, 240)); // Light gray background
        JScrollPane scrollPane = new JScrollPane(logArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);

        // Start the server
        startServer();
    }

    private void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                appendLog("Chat Server is running on port " + PORT, Color.BLUE);

                while (true) {
                    Socket socket = serverSocket.accept();
                    appendLog("New client connected!", Color.GREEN);

                    ClientHandler clientHandler = new ClientHandler(socket, userIdCounter++);
                    clientHandlers.add(clientHandler);

                    new Thread(clientHandler).start();
                }
            } catch (IOException e) {
                appendLog("Error in the server: " + e.getMessage(), Color.RED);
            }
        }).start();
    }

    private void appendLog(String message, Color color) {
        SwingUtilities.invokeLater(() -> {
            logArea.setForeground(color);
            logArea.append(message + "\n");
        });
    }

    private class ClientHandler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private int clientId;

        public ClientHandler(Socket socket, int clientId) {
            this.socket = socket;
            this.clientId = clientId;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                out.println("Welcome! Your user ID is: " + clientId);
                appendLog("Assigned user ID " + clientId + " to a new client.", Color.BLUE);

                String clientMessage;
                while ((clientMessage = in.readLine()) != null) {
                    appendLog("Received from client " + clientId + ": " + clientMessage, Color.BLACK);
                    broadcastMessage("User " + clientId + ": " + clientMessage, this);
                }
            } catch (IOException e) {
                appendLog("Error handling client " + clientId + ": " + e.getMessage(), Color.RED);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    appendLog("Error closing connection with client " + clientId, Color.RED);
                }
                clientHandlers.remove(this);
                appendLog("Client " + clientId + " disconnected.", Color.ORANGE);
            }
        }

        private void broadcastMessage(String message, ClientHandler sender) {
            for (ClientHandler client : clientHandlers) {
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }

        private void sendMessage(String message) {
            out.println(message);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatServerGUI::new);
    }
}