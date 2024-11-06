package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.authentication.printer.server.helpers.Token;

public interface IAuth extends Remote {
    public Token Authenticate(String username, String password) throws RemoteException;
    
    public boolean AuthorizeJWT(Token token) throws RemoteException; 
}
