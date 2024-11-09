package com.authentication.printer.common.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.authentication.printer.server.InvalidLoginException;
import com.authentication.printer.server.helpers.Token;

public interface IPrinter extends  Remote {
    public Token login(String username, String password) throws RemoteException,InvalidLoginException;
    public String ping(Token token) throws RemoteException;
    public String print(String filename, String printer, Token token) throws RemoteException;
    public String queue(String printer, Token token) throws RemoteException;
    public void topQueue(String printer, int job, Token token) throws RemoteException;
    public void start(Token token) throws RemoteException;
    public void stop(Token token) throws RemoteException;
    public void restart(Token token) throws RemoteException;
    public String status(Token token) throws RemoteException;
    public String readConfig(String parameter, Token token) throws RemoteException;
    public String setConfig(String parameter, String value, Token token) throws RemoteException;

}
