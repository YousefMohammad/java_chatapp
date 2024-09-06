package App;

import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.*;

public class ChatServer extends JFrame {

    private ServerSocket serverSocket;
    private Socket socket;

    private Scanner scanner;
    private PrintWriter printWriter;
    private JButton SendButton;
    private JLabel Title;
    private JTextArea ChatArea;
    private JTextField ChatBox;
    private JPanel ChatAppPanel;

    public ChatServer() {
        setContentPane(ChatAppPanel);
        setTitle("ChatApp");
        setSize(450, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    serverSocket = new ServerSocket(9999);
                    socket = serverSocket.accept();
                    scanner = new Scanner(socket.getInputStream());
                    printWriter = new PrintWriter(socket.getOutputStream(),true);
                    Thread ChatThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(true){
                                String ClientMessage = scanner.nextLine();
                                ChatArea.append("Client: " + ClientMessage + "\n");
                            }
                        }
                    });
                    ChatThread.start();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {}

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        SendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ServerMessage = ChatBox.getText();
                ChatArea.append("Server: " + ServerMessage + "\n");
                printWriter.println(ServerMessage);
                ChatBox.setText("");
            }
        });
    }


    public static void main(String args[]) {
        ChatServer chatServer = new ChatServer();
    }
}
