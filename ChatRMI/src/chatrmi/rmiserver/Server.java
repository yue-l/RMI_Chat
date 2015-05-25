package chatrmi.rmiserver;

import chatrmi.interfaces.ClientInterface;
import chatrmi.interfaces.ServerInterface;
import chatrmi.constants.MyConstants;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 * @author caroline & yue
 */
public class Server extends UnicastRemoteObject implements ServerInterface {

    protected ArrayList<ClientInterface> clients = new ArrayList<>();

    public Server() throws RemoteException {
    }

    @Override
    public void login(ClientInterface client, String nickname) throws RemoteException {
        broadcastMessage("--> " + nickname + " is entering the chatroom", "");
        clients.add(client);
    }

    @Override
    public void broadcastMessage(String message, String nickname) throws RemoteException {
        for (int i = 0; i < clients.size(); i++) {
            ClientInterface clientInterf = clients.get(i);
            try {
                clientInterf.getMessage(nickname, message);
            } catch (RemoteException e) {
                logout(clientInterf);
                i = i - 1;
            }
        }
    }

    public void logout(ClientInterface client) {
        clients.remove(client);
    }

    @Override
    public void quit() {
        try {
            UnicastRemoteObject.unexportObject(this, true);
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) throws UnknownHostException {
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
}
