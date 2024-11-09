package com.authentication.printer.server;

import java.rmi.RemoteException;

import java.rmi.server.UnicastRemoteObject;

import com.authentication.printer.common.interfaces.IAuth;
import com.authentication.printer.common.interfaces.IPrinter;
import com.authentication.printer.server.helpers.ILogger;
import com.authentication.printer.server.helpers.ISessionManagement;
import com.authentication.printer.server.helpers.SessionManagementImpl;
import com.authentication.printer.server.helpers.Token;


import java.util.LinkedList;
import java.util.Queue;

public class PrinterRunner extends UnicastRemoteObject implements IPrinter {
    
    private ILogger logger;
    private Queue<String> jobQueue = new LinkedList<>();
    private boolean isRunning = false;
    private String configuration;
    private IAuth authenticationService;
    private ISessionManagement sessionManagementService;

    public PrinterRunner(ILogger logger) throws RemoteException {
        super();
        this.logger = logger; 
        this.configuration = "default";
        this.authenticationService = new Auth();
        this.sessionManagementService = new SessionManagementImpl();
    }

    private String name;

    public String ping(Token token) throws RemoteException {
        if(sessionManagementService.validateSessionToken(token)){
            this.logger.logMessageToConsole("ping", "Ping requested, responding with pong");
            return "pong";
        }
        return "Invalid token";
    }

    @Override
    public String print(String filename, String printer, Token token) throws RemoteException {
        if (sessionManagementService.validateSessionToken(token)) {
            this.logger.logMessageToConsole("print", "The file " + filename + " will be printed on printer " + printer);
            this.jobQueue.add(filename);
            return "The file " + filename + " will be printed on printer " + printer;
        } else {
            return "Invalid token";
        }
    }

    @Override
    public String queue(String printer, Token token) throws RemoteException {
        if (sessionManagementService.validateSessionToken(token)) {
            this.logger.logMessageToConsole("queue", "Queue for printer " + printer + " requested");
            return this.jobQueue.toString();
        } else {
            return "Invalid token";
        }
    }

    @Override
    public void topQueue(String printer, int job, Token token) throws RemoteException {
        if (sessionManagementService.validateSessionToken(token)) {
            this.logger.logMessageToConsole("topQueue", "Moving job " + job + " to the top of the queue for printer " + printer);
            String jobToPromote = ((LinkedList<String>) this.jobQueue).remove(job);
            ((LinkedList<String>) this.jobQueue).addFirst(jobToPromote);
        } else {
            this.logger.logMessageToConsole("topQueue", "Invalid token");
        }
    }

    @Override
    public void start(Token token) throws RemoteException {
        if (sessionManagementService.validateSessionToken(token)) {
            this.isRunning = true;
            this.logger.logMessageToConsole("start", "Printer started");
        } else {
            this.logger.logMessageToConsole("start", "Invalid token");
        }
    }

    @Override
    public void stop(Token token) throws RemoteException {
        if (sessionManagementService.validateSessionToken(token)) {
            this.isRunning = false;
            this.logger.logMessageToConsole("stop", "Printer stopped");
        } else {
            this.logger.logMessageToConsole("stop", "Invalid token");
        }
    }

    @Override
    public void restart(Token token) throws RemoteException {
        if (sessionManagementService.validateSessionToken(token)) {
            this.logger.logMessageToConsole("restart", "Printer restarting");
            stop(token);
            start(token);
        } else {
            this.logger.logMessageToConsole("restart", "Invalid token");
        }
    }

    @Override
    public String status(Token token) throws RemoteException {
        if (sessionManagementService.validateSessionToken(token)) {
            String status = this.isRunning ? "Printer is running" : "Printer is stopped";
            this.logger.logMessageToConsole("status", "Status requested: " + status);
            return status;
        } else {
            return "Invalid token";
        }
    }

    @Override
    public String readConfig(String parameter, Token token) throws RemoteException {
        if (sessionManagementService.validateSessionToken(token)) {
            this.logger.logMessageToConsole("readConfig", "Reading configuration for parameter: " + parameter);
            return this.configuration;
        } else {
            return "Invalid token";
        }
    }

    @Override
    public String setConfig(String parameter, String value, Token token) throws RemoteException {
        if (sessionManagementService.validateSessionToken(token)) {
            this.configuration = value;
            this.logger.logMessageToConsole("setConfig", "Setting configuration: " + parameter + " = " + value);
            return "Configuration set: " + parameter + " = " + value;
        } else {
            return "Invalid token";
        }
    }
    @Override
    public Token login(String username, String password) throws RemoteException, InvalidLoginException {
        if (authenticationService.authenticate(username, password)){
            Token token = sessionManagementService.generateSessionToken(username);
            return new Token(token.getToken());
        }
        else {
            throw new InvalidLoginException("Invalid login attempt: username or password is incorrect.");
        }
    }
}
