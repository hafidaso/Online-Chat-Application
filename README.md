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

