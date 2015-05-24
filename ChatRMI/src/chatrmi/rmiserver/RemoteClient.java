package chatrmi.rmiserver;

import chatrmi.client.GUI;
import chatrmi.interfaces.ClientInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteClient extends UnicastRemoteObject implements ClientInterface {

    public RemoteClient() throws RemoteException {
    }

    @Override
    public void getMessage(String message, String nickname) throws RemoteException {
        GUI.updateChatMessage(message, nickname);
    }
}