package com.authentication.printer.common.interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IAuth extends Remote {
    public boolean authenticate(String username, String password) throws RemoteException;
}
