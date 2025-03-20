# Online Chat Application

## Overview
This project is a simple Online Chat Application built using Java. It allows multiple users to connect to a central server, send messages, and receive messages from other users in real-time. The application features a Graphical User Interface (GUI) for both the client and server components, providing an enhanced user experience.

## Features

### 1. Server Features
- Handles multiple client connections
- Assigns unique IDs to each connected client
- Logs connection/disconnection events and broadcasted messages in a scrollable text area
- Displays color-coded logs for better readability:
  - System messages in blue
  - Errors in red
  - Client disconnections in orange

### 2. Client Features
- Connects to the server and exchanges messages in real-time
- Displays chat history with timestamps
- Includes a clean and modern GUI with:
  - A scrollable chat area
  - A dedicated input field for sending messages
  - Styled buttons (Send, Disconnect, Clear Chat) with hover effects and custom colors
  - Color-coded messages for system notifications and errors

## How to Run the Application

### Prerequisites
- Java Development Kit (JDK) installed on your machine
- Basic knowledge of running Java programs from the command line

### Steps

#### Compile the Code
Open a terminal or command prompt and navigate to the directory containing the source files. Compile the server and client classes:

```bash
javac ChatServerGUI.java
javac ChatClientGUI.java

```

#### Start the Server

Run the server application:

```bash
java ChatServerGUI

```


The server will start listening for incoming client connections on port 12345.

#### Start the Clients

Open multiple terminal windows and run the client application:

```bash
java ChatClientGUI

```


#### Chat with Other Users

*   Type a message in the input field and press Enter or click Send
    
*   Messages will be broadcasted to all connected clients in real-time
    
*   Use Disconnect button to leave the chat
    
*   Use Clear Chat button to reset the chat history
    

Implementation Details
----------------------

### Technologies Used

*   **Java**: Core programming language
    
*   **Swing**: For creating the graphical user interface (GUI)
    
*   **Socket Programming**: For client-server communication
    

### Folder Structure

project/
├── ChatServerGUI.java    # Server implementation with GUI
├── ChatClientGUI.java    # Client implementation with GUI
└── README.md             # This file


### Key Classes

*   **ChatServerGUI**:
    
    *   Manages client connections and broadcasts messages
        
    *   Displays logs in a GUI window
        
*   **ChatClientGUI**:
    
    *   Connects to the server and provides a GUI for sending/receiving messages
        
    *   Includes styled buttons and a scrollable chat area
        

Enhancements
------------

*   Styled Buttons with custom colors, rounded corners, and hover effects
    
*   Color-Coded Messages:
    
    *   System messages: Blue
        
    *   Errors: Red
        
    *   Disconnections: Orange
        
*   Timestamps for messages
    
*   Clear Chat functionality
    

    

Contributors
------------

\[Hafida Belayd\]Email: \[hafidabelaidagnaoui@gmail.com\]

License
-------

This project is licensed under the MIT License. Feel free to use, modify, and distribute the code as needed.

**Note**: Ensure the server is running before starting any clients. Check console output for potential error messages.

