package server;

import interfaces.TestRemote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author mk30
 */
public class RemoteImple extends UnicastRemoteObject implements TestRemote {

    private static final long serialVersionUID = 1L;

    protected RemoteImple() throws RemoteException {
        super();
    }

    @Override
    public void isCaroline(String name) throws RemoteException {
        if (name.equalsIgnoreCase("caroline")) {
            System.out.println("Hi " + name);
        }else{
            System.out.println("Not hi " + name + " :(");
        }
    }

}
