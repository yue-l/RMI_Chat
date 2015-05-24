/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatrmi.client;

import chatrmi.interfaces.ServerInterface;
import chatrmi.rmiserver.RemoteClient;

public class Node {

    public RemoteClient client;
    public ServerInterface server;
    public static String USER_NAME;
    public static String SERVER_HOST;
}
