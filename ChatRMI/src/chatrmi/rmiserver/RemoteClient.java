package chatrmi.rmiserver;

import chatrmi.client.ClientGUI;
import chatrmi.interfaces.ClientInterface;
import chatrmi.interfaces.ServerInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * The client remote object, to identify the client connection
 *
 * @author yue
 */
public class RemoteClient extends UnicastRemoteObject implements ClientInterface {

    public RemoteClient(ServerInterface server) throws RemoteException {

    }

    @Override
    public void getMessage(String message, String nickname) throws RemoteException {
        ClientGUI.updateChatMessage(message, nickname);
    }

    @Override
    public void close() throws RemoteException {
    }
}
