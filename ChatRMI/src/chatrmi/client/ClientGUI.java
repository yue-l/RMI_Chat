package chatrmi.client;

import chatrmi.rmiserver.RemoteClient;
import chatrmi.interfaces.ServerInterface;
import chatrmi.constants.MyConstants;
import chatrmi.rmiserver.Server;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

    public static String USER_NAME;
    public static String SERVER_HOST_IP;
    private static JTextArea LOG;

    private RemoteClient client;
    private ServerInterface server;
    private boolean isHost;
    private JTextField Message;
    private JScrollPane jScrollPaneHistory;

    public ClientGUI() {
        super();

        initGUI();
    }

    public void host() throws UnknownHostException {
        isHost = true;
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
        if (!isHost) {//for client
            regi = LocateRegistry.getRegistry(SERVER_HOST_IP, MyConstants.RMI_PORT);
            System.out.println("Host is " + SERVER_HOST_IP);
        } else {//for server
            regi = LocateRegistry.getRegistry("localhost", MyConstants.RMI_PORT);
        }
        this.server = (ServerInterface) regi.lookup(MyConstants.RMI_ID);
        client = new RemoteClient();
        server.login(client, USER_NAME);
    }

    public void stop() {

    }

    private void initGUI() {
        try {
            this.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    try {
                        if (isHost) {
                            server.quit();
                        } else {
                            client.close();
                        }
                        System.exit(0);
                    } catch (RemoteException ex) {
                        Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
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
