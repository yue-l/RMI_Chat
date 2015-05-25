package chatrmi.client;

import chatrmi.rmiserver.RemoteClient;
import chatrmi.interfaces.ServerInterface;
import chatrmi.constants.MyConstants;
import chatrmi.rmiserver.Server;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.Inet4Address;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

public class ClientGUI extends JFrame {

    private RemoteClient client;
    private ServerInterface server;
    private static String USER_NAME;
    private static String SERVER_HOST;
    private static JTextArea LOG;
    private JTextField Message;
    private JScrollPane jScrollPaneHistory;

    public static void main(String[] args) {
        USER_NAME = JOptionPane.showInputDialog("Enter your nickname");
        if (USER_NAME != null && !USER_NAME.equals("")) {
            try {
                ClientGUI inst = new ClientGUI();
                if (JOptionPane.showConfirmDialog(null, "Do you wish to host?",
                        "host?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.out.println("Yes");
                    inst.host();
                    inst.join(true);
                } else {
                    SERVER_HOST = JOptionPane.showInputDialog("Enter the host of the chatserver", "localhost");
                    System.out.println("No");
                    inst.join(false);
                }
                inst.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    public ClientGUI() {
        super();
        initGUI();
    }

    public void host() throws UnknownHostException {
        Server server = null;
        Registry regi = null;
        try {
            server = new Server();
            regi = LocateRegistry.createRegistry(MyConstants.RMI_PORT);
            regi.bind(MyConstants.RMI_ID, server);
            System.out.println("server is ready at: " + Inet4Address.getLocalHost().getHostAddress());
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void join(boolean isHost) throws MalformedURLException, RemoteException, NotBoundException {
        Registry regi = null;
        if (!isHost) {
            regi = LocateRegistry.getRegistry(SERVER_HOST, MyConstants.RMI_PORT);
        } else {
            regi = LocateRegistry.getRegistry("localhost", MyConstants.RMI_PORT);
        }
        this.server = (ServerInterface) regi.lookup(MyConstants.RMI_ID);
        client = new RemoteClient();
        server.login(client, USER_NAME);
    }

    private void initGUI() {
        try {
            jScrollPaneHistory = new JScrollPane();
            getContentPane().add(jScrollPaneHistory);
            jScrollPaneHistory.setBounds(7, 7, 378, 203);
            jScrollPaneHistory.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            LOG = new JTextArea();
            LOG.setLineWrap(true);
            LOG.setEditable(false);
            jScrollPaneHistory.setViewportView(LOG);
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
            LOG.append(name + ": " + msg + "\n");
        } else {
            LOG.append(msg + "\n");
        }
    }

    private void MessageKeyPressed(KeyEvent evt) {
        if (!Message.getText().equals("") && evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                server.broadcastMessage(Message.getText(), USER_NAME);
            } catch (RemoteException ex) {
                Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            Message.setText("");
        }
    }
}
