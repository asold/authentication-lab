package com.authentication.printer.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.authentication.printer.server.helpers.ILogger;
import interfaces.IPrinter;

import java.util.LinkedList;
import java.util.Queue;

public class PrinterRunner extends UnicastRemoteObject implements IPrinter {
    
    private ILogger logger;
    private Queue<String> jobQueue = new LinkedList<>();
    private boolean isRunning = false;
    private String configuration;

    public PrinterRunner(ILogger logger) throws RemoteException {
        super();
        this.logger = logger; 
        this.configuration = "default";
    }

    private String name;

    public String ping() throws RemoteException {
        this.logger.logMessageToConsole("ping", "Ping requested, responding with pong");
        return "pong";
    }

    @Override
    public String print(String filename, String printer) throws RemoteException {
        this.logger.logMessageToConsole("print", "The file " + filename + " will be printed on printer " + printer);
        this.jobQueue.add(filename);
        return "The file " + filename + " will be printed on printer " + printer;
    }

    @Override
    public String queue(String printer) throws RemoteException {
        this.logger.logMessageToConsole("queue", "Queue for printer " + printer + " requested");
        return this.jobQueue.toString();
    }

    @Override
    public void topQueue(String printer, int job) throws RemoteException {
        this.logger.logMessageToConsole("topQueue", "Moving job " + job + " to the top of the queue for printer " + printer);
        String jobToPromote = ((LinkedList<String>) this.jobQueue).remove(job);
        ((LinkedList<String>) this.jobQueue).addFirst(jobToPromote);
    }

    @Override
    public void start() throws RemoteException {
        this.isRunning = true;
        this.logger.logMessageToConsole("start", "Printer started");
    }

    @Override
    public void stop() throws RemoteException {
        this.isRunning = false;
        this.logger.logMessageToConsole("stop", "Printer stopped");
    }

    @Override
    public void restart() throws RemoteException {
        this.logger.logMessageToConsole("restart", "Printer restarting");
        stop(); 
        start(); 
    }

    @Override
    public String status() throws RemoteException {
        String status = this.isRunning ? "Printer is running" : "Printer is stopped";
        this.logger.logMessageToConsole("status", "Status requested: " + status);
        return status;
    }

    @Override
    public String readConfig(String parameter) throws RemoteException {
        this.logger.logMessageToConsole("readConfig", "Reading configuration for parameter: " + parameter);
        return this.configuration;
    }

    @Override
    public String setConfig(String parameter, String value) throws RemoteException {
        this.configuration = value;
        this.logger.logMessageToConsole("setConfig", "Setting configuration: " + parameter + " = " + value);
        return "Configuration set: " + parameter + " = " + value;
    }
}
