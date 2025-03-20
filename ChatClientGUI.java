import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatClientGUI {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;

    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton, disconnectButton, clearButton;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ChatClientGUI() {
        // Initialize the GUI components
        frame = new JFrame("Chat Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);

        // Chat Area (Scrollable)
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        chatArea.setBackground(new Color(240, 240, 240)); // Light gray background
        JScrollPane scrollPane = new JScrollPane(chatArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sendButton = createStyledButton("Send", new Color(46, 204, 113)); // Green
        disconnectButton = createStyledButton("Disconnect", new Color(231, 76, 60)); // Red
        clearButton = createStyledButton("Clear Chat", new Color(52, 152, 219)); // Blue

        buttonsPanel.add(clearButton);
        buttonsPanel.add(disconnectButton);
        buttonsPanel.add(sendButton);

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(buttonsPanel, BorderLayout.EAST);

        frame.add(inputPanel, BorderLayout.SOUTH);

        // Action Listeners
        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());
        disconnectButton.addActionListener(e -> disconnect());
        clearButton.addActionListener(e -> chatArea.setText(""));

        frame.setVisible(true);

        // Connect to the server
        connectToServer();
    }

    private void connectToServer() {
        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Start a thread to listen for incoming messages
            new Thread(this::listenForMessages).start();

            appendMessage("Connected to the server. Type 'exit' to quit.", Color.BLUE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Failed to connect to the server.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            out.println(message);
            appendMessage("You: " + message, Color.BLACK);
            inputField.setText("");
            if ("exit".equalsIgnoreCase(message)) {
                disconnect();
            }
        }
    }

    private void disconnect() {
        try {
            if (socket != null) {
                socket.close();
            }
            appendMessage("Disconnected from the server.", Color.RED);
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenForMessages() {
        try {
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                appendMessage(serverMessage, Color.BLACK);
            }
        } catch (IOException e) {
            appendMessage("Error reading from server.", Color.RED);
        }
    }

    private void appendMessage(String message, Color color) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        SwingUtilities.invokeLater(() -> {
            chatArea.append("[" + timestamp + "] ");
            chatArea.setForeground(color);
            chatArea.append(message + "\n");
        });
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClientGUI::new);
    }
}