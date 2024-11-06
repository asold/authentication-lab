package com.authentication.printer.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import interfaces.IPrinter;

// import interfaces.IPrinter;


public class Client {
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        IPrinter server = (IPrinter) Naming.lookup("rmi://localhost:5099/print");
        System.out.println("--- " + server.ping());
        System.out.println("--- " + server.print("secret_file.txt", "Office Printer"));
    }
}
