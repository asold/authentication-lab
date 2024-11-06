package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuth extends Remote {
    public boolean Authenticate(String username, String password) throws RemoteException;
    
}
