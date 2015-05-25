package chatrmi.rmiserver;

import chatrmi.client.ClientGUI;
import chatrmi.interfaces.ClientInterface;
import chatrmi.interfaces.ServerInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author caroline & yue
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
