package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPrinter extends Remote{
    
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
