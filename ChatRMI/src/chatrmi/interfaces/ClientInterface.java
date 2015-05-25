package chatrmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author caroline & yue
 */
public interface ClientInterface extends Remote {

    public void getMessage(String message, String nickname) throws RemoteException;

    public void close() throws RemoteException;
}
