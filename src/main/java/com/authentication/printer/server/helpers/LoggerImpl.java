package com.authentication.printer.server.helpers;

public class LoggerImpl implements ILogger{

    @Override
    public void logMessageToConsole(String invokedMethod, String message) {
        System.out.println("[" + invokedMethod + "] " + message);
    }
    
}
