package com.authentication.printer.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.authentication.printer.Test;


public class Client {
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        Test server = (Test) Naming.lookup("rmi://localhost:5099/print");
        System.out.println("--- " + server.ping());
    }
}
