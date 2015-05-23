package client;

import constants.MyConstants;
import interfaces.TestRemote;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author mk30
 */
public class TestClient {
    
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry regi = LocateRegistry.getRegistry("localhost", MyConstants.RMI_PORT);
        TestRemote remote = (TestRemote) regi.lookup(MyConstants.RMI_ID);
        remote.isCaroline("Caroline");
        remote.isCaroline("Yue");
    }
}
