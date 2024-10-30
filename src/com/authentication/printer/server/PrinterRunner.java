package com.authentication.printer.server;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import interfaces.IPrinter;


public class PrinterRunner extends UnicastRemoteObject implements IPrinter{
    public PrinterRunner() throws RemoteException {
        super();
    }

    

    private String name;



    
    public String ping() throws RemoteException {
        return "pong";
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'ping'");
    }
}