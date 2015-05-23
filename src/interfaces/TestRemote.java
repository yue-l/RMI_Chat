package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author mk30
 */
public interface TestRemote extends Remote {

    public void isCaroline(String name) throws RemoteException;
}
