package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.authentication.printer.server.helpers.Token;

public interface IPrinter extends Remote{
    public Token login(String username, String password) throws RemoteException;
    public String ping() throws RemoteException;
    public String print(String filename, String printer) throws RemoteException;
    public String queue(String printer) throws RemoteException;
    public void topQueue(String printer, int job) throws RemoteException;
    public void start() throws RemoteException;
    public void stop() throws RemoteException;
    public void restart() throws RemoteException;
    public String status() throws RemoteException;
    public String readConfig(String parameter) throws RemoteException;
    public String setConfig(String parameter, String value) throws RemoteException;

}
