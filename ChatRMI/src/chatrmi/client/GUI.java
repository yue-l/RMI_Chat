package chatrmi.client;

import chatrmi.rmiserver.RemoteClient;
import chatrmi.interfaces.ServerInterface;
import chatrmi.constants.MyConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

public class GUI extends JFrame {

    private RemoteClient client;
    private ServerInterface server;
    private static String USER_NAME;
    private static String SERVER_HOST;
    private static JTextArea History;
    private JTextField Message;
    private JScrollPane jScrollPaneHistory;

    public static void main(String[] args) {
//        Node node = new Node();
//        node. = JOptionPane.showInputDialog("Enter the host of the chatserver", "localhost");
//        USER_NAME = JOptionPane.showInputDialog("Enter your nickname");
        SERVER_HOST = JOptionPane.showInputDialog("Enter the host of the chatserver", "localhost");
        USER_NAME = JOptionPane.showInputDialog("Enter your nickname");
        if (SERVER_HOST != null && USER_NAME != null && !USER_NAME.equals("")) {
            try {
                GUI inst = new GUI();
                inst.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    public GUI() throws MalformedURLException, RemoteException, NotBoundException {
        super();
        Registry regi = LocateRegistry.getRegistry("localhost", MyConstants.RMI_PORT);
        this.server = (ServerInterface) regi.lookup(MyConstants.RMI_ID);
        client = new RemoteClient();
        server.login(client, USER_NAME);
        initGUI();
    }

    private void initGUI() {
        try {
            jScrollPaneHistory = new JScrollPane();
            getContentPane().add(jScrollPaneHistory);
            jScrollPaneHistory.setBounds(7, 7, 378, 203);
            jScrollPaneHistory.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            History = new JTextArea();
            History.setLineWrap(true);
            History.setEditable(false);
            jScrollPaneHistory.setViewportView(History);
            Message = new JTextField();
            getContentPane().add(Message);
            Message.setBounds(7, 217, 378, 42);
            Message.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent evt) {
                    MessageKeyPressed(evt);
                }
            });
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            getContentPane().setLayout(null);
            this.setTitle("MyRMIChat - " + USER_NAME);
            this.setResizable(false);
            pack();
            setSize(400, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateChatMessage(String name, String msg) {
        if (!name.equals("")) {
            History.append(name + ": " + msg + "\n");
        } else {
            History.append(msg + "\n");
        }
    }

    private void MessageKeyPressed(KeyEvent evt) {
        if (!Message.getText().equals("") && evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                server.broadcastMessage(Message.getText(), USER_NAME);
            } catch (RemoteException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            Message.setText("");
        }
    }
}
