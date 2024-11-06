package com.authentication.printer.server.helpers;

public interface ISessionManagement {
    public Token generateSessionToken(String username);
    public boolean validateSessionToken(Token token);
}
