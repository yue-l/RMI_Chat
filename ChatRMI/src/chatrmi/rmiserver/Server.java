package chatrmi.rmiserver;

import chatrmi.interfaces.ClientInterface;
import chatrmi.interfaces.ServerInterface;
import chatrmi.constants.MyConstants;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                clientInterf.getMessage(message, nickname);
            } catch (RemoteException e) {
                logout(clientInterf);
                i = i - 1;
            }
        }
    }

    public void logout(ClientInterface client) {
        clients.remove(client);
    }

    public static void main(String[] args) {
        Server server = null;
        try {
            server = new Server();
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        Registry regi = null;
        try {
            regi = LocateRegistry.createRegistry(MyConstants.RMI_PORT);
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            regi.bind(MyConstants.RMI_ID, server);
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("server is ready");
    }
}
