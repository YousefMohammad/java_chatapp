package App;

import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Principal;
import java.util.Scanner;
import javax.swing.*;

public class ChatClient extends JFrame{

    private Socket socket;
    private Scanner scanner;
    private PrintWriter printWriter;
    private JButton SendButton;
    private JLabel Title;
    private JTextArea ChatArea;
    private JTextField ChatBox;
    private JScrollBar scrollBar1;
    private JPanel ChatAppPanel;

    public ChatClient()
    {
        setContentPane(ChatAppPanel);
        setTitle("ChatApp");
        setSize(450,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
                try {
                    socket = new Socket("localhost",9999);
                    scanner = new Scanner(socket.getInputStream());
                    printWriter = new PrintWriter(socket.getOutputStream(),true);
                    Thread myThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while(true){
                                String ServerMessage = scanner.nextLine();
                                ChatArea.append("Server: "+ ServerMessage + "\n");
                            }
                        }
                    });
                    myThread.start();
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
                String ClientMessage = ChatBox.getText();
                ChatArea.append("Client: " + ClientMessage + "\n");
                printWriter.println(ClientMessage);
                ChatBox.setText("");
            }
        });
    }



    public static void main(String args[])
    {
        ChatClient chatClient = new ChatClient();
    }


}
