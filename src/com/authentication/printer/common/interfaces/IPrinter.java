package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPrinter extends Remote{
    
    public String ping() throws RemoteException;
}
