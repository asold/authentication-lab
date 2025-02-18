package com.authentication.printer.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import com.authentication.printer.common.interfaces.IPrinter;
import com.authentication.printer.server.InvalidLoginException;
import com.authentication.printer.server.helpers.Token;


public class Client {
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException,InvalidLoginException {
        IPrinter server = (IPrinter) Naming.lookup("rmi://localhost:5099/print");
        String s = "1test";
        Scanner in = new Scanner(System.in);
        while (!s.equals("0")){
            s = in.nextLine();
            Token answer = server.login("lukas", s);
            System.out.println("--- " + server.ping(answer));
            System.out.println("--- " + answer.getToken());
            System.out.println("--- " + server.print("secret_file.txt", "Office Printer", answer));
        }
        in.close();
    }
}
