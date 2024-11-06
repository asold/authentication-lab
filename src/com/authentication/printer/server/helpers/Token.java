package com.authentication.printer.server.helpers;

import java.io.Serializable;

public class Token implements Serializable{
    
    private String token;
    
    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
