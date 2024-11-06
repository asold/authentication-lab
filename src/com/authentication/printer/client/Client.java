package com.authentication.printer.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import com.authentication.printer.server.helpers.Token;

import interfaces.IPrinter;
// import interfaces.IPrinter;


public class Client {
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        IPrinter server = (IPrinter) Naming.lookup("rmi://localhost:5099/print");
        System.out.println("--- " + server.ping());
        String s = "1test";
        Scanner in = new Scanner(System.in);
        while (!s.equals("0")){
            s = in.nextLine();
            Token answer = server.login("lukas", s);
            System.out.println("--- " + answer);
        }
        in.close();
        System.out.println("--- " + server.print("secret_file.txt", "Office Printer"));
    }
}
