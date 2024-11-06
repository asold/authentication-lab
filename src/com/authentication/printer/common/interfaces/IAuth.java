package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.authentication.printer.server.helpers.Token;

public interface IAuth extends Remote {
    public boolean authenticate(String username, String password) throws RemoteException;
}
