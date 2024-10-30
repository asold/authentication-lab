package com.authentication.printer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Test extends Remote{
    
    public String ping() throws RemoteException;
}
