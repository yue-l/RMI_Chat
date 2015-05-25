
import chatrmi.client.ClientGUI;
import java.awt.HeadlessException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mk30
 */
public class ChatRMI {

    public static void main(String[] args) {
        ClientGUI.USER_NAME = JOptionPane.showInputDialog("Enter your nickname");
        if (ClientGUI.USER_NAME != null && !ClientGUI.USER_NAME.equals("")) {
            try {
                ClientGUI inst = new ClientGUI();
                if (JOptionPane.showConfirmDialog(null, "Do you wish to host?",
                        "host?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    System.out.println("Yes");
                    inst.host();
                    inst.join(true);
                } else {
                    ClientGUI.SERVER_HOST = JOptionPane.showInputDialog("Enter the host of the chatserver", "localhost");
                    System.out.println("No");
                    inst.join(false);
                }
                inst.setVisible(true);
            } catch (HeadlessException | UnknownHostException | MalformedURLException | RemoteException | NotBoundException e) {
                System.exit(0);
            }
        } else {
            System.out.println("no user name entered, bye!!!!");
            System.exit(0);
        }
    }
}
