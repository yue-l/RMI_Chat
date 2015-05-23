/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import constants.MyConstants;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author mk30
 */
public class RemoteServer {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

        RemoteImple impl;
        impl = new RemoteImple();
        Registry regi = LocateRegistry.createRegistry(MyConstants.RMI_PORT);
        regi.bind(MyConstants.RMI_ID, impl);

        System.out.println("Service Started");
    }
}
