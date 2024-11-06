package com.authentication.printer.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.authentication.printer.server.helpers.ILogger;
import com.authentication.printer.server.helpers.LoggerImpl;

public class ServerStarter {
    public static void main(String[] args) throws RemoteException {
        ILogger logger = new LoggerImpl();
        Registry registry = LocateRegistry.createRegistry(5099);
        registry.rebind("print", new PrinterRunner(logger));
    }
}
