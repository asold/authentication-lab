package com.authentication.printer.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import com.authentication.printer.common.interfaces.IPrinter;
import com.authentication.printer.server.InvalidLoginException;
import com.authentication.printer.server.helpers.Token;

public class ClientTest {

    public static void main(String[] args) {
        boolean testFailed = false; // Flag to track if any test fails

        try {
            // Test with correct username and password
            if (testCorrectUsernameAndPassword()) {
                System.out.println("Test with correct username and password passed");
            } else {
                System.out.println("Test with correct username and password failed");
                testFailed = true;
            }

            // Test with incorrect username
            if (testIncorrectUsername()) {
                System.out.println("Test with incorrect username passed");
            } else {
                System.out.println("Test with incorrect username failed");
                testFailed = true;
            }

            // Test with incorrect password
            if (testIncorrectPassword()) {
                System.out.println("Test with incorrect password passed");
            } else {
                System.out.println("Test with incorrect password failed");
                testFailed = true;
            }

            // Test with incorrect username and password
            if (testIncorrectUsernameAndPassword()) {
                System.out.println("Test with incorrect username and password passed");
            } else {
                System.out.println("Test with incorrect username and password failed");
                testFailed = true;
            }

            // Throw an exception at the end if any test failed
            if (testFailed) {
                throw new IllegalStateException("One or more tests failed.");
            }

        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Test method to verify login with correct username and password.
     * 
     * @return true if login and token validation are successful, false otherwise
     */
    private static boolean testCorrectUsernameAndPassword() 
            throws MalformedURLException, RemoteException, NotBoundException {

        try {
            IPrinter server = (IPrinter) Naming.lookup("rmi://localhost:5099/print");
            String correctUsername = "lukas"; 
            String correctPassword = "password";

            Token answer = server.login(correctUsername, correctPassword);

            // Use the helper method to validate the server response and token
            return isValidResponse(server, answer);
        } catch (InvalidLoginException e) {
            System.err.println("Unexpected InvalidLoginException for correct credentials: " + e.getMessage());
            return false;
        }
    }

    /**
     * Test method to verify login with incorrect username.
     * 
     * @return true if the server correctly throws InvalidLoginException, false otherwise
     */
    private static boolean testIncorrectUsername() 
            throws MalformedURLException, RemoteException, NotBoundException {

        try {
            IPrinter server = (IPrinter) Naming.lookup("rmi://localhost:5099/print");
            String incorrectUsername = "wronguser"; 
            String correctPassword = "password";

            server.login(incorrectUsername, correctPassword);

            // If no exception is thrown, the test fails
            System.err.println("Expected InvalidLoginException for incorrect username but login succeeded.");
            return false;
        } catch (InvalidLoginException e) {
            // Expected exception for incorrect username
            System.out.println("Test with incorrect username passed: " + e.getMessage());
            return true;
        }
    }

    /**
     * Test method to verify login with incorrect password.
     * 
     * @return true if the server correctly throws InvalidLoginException, false otherwise
     */
    private static boolean testIncorrectPassword() 
            throws MalformedURLException, RemoteException, NotBoundException {

        try {
            IPrinter server = (IPrinter) Naming.lookup("rmi://localhost:5099/print");
            String correctUsername = "lukas"; 
            String incorrectPassword = "wrongpassword";

            server.login(correctUsername, incorrectPassword);

            // If no exception is thrown, the test fails
            System.err.println("Expected InvalidLoginException for incorrect password but login succeeded.");
            return false;
        } catch (InvalidLoginException e) {
            // Expected exception for incorrect password
            System.out.println("Test with incorrect password passed: " + e.getMessage());
            return true;
        }
    }

    /**
     * Test method to verify login with incorrect username and password.
     * 
     * @return true if the server correctly throws InvalidLoginException, false otherwise
     */
    private static boolean testIncorrectUsernameAndPassword() 
            throws MalformedURLException, RemoteException, NotBoundException {

        try {
            IPrinter server = (IPrinter) Naming.lookup("rmi://localhost:5099/print");
            String incorrectUsername = "wronguser"; 
            String incorrectPassword = "wrongpassword";

            server.login(incorrectUsername, incorrectPassword);

            // If no exception is thrown, the test fails
            System.err.println("Expected InvalidLoginException for incorrect username and password but login succeeded.");
            return false;
        } catch (InvalidLoginException e) {
            // Expected exception for incorrect username and password
            System.out.println("Test with incorrect username and password passed: " + e.getMessage());
            return true;
        }
    }

    /**
     * Helper method to check if the server response and token are valid.
     * 
     * @param server the IPrinter server
     * @param answer the Token received from login
     * @return true if ping returns "pong" and token is valid, false otherwise
     */
    private static boolean isValidResponse(IPrinter server, Token answer) 
            throws RemoteException {
        
        if (answer == null) return false;

        String pong = server.ping(answer);
        
        if (!"pong".equals(pong)) {
            return false;
        }

        return isValidToken(answer.getToken());
    }

    /**
     * Placeholder for token validation logic.
     * 
     * @param token the token to validate
     * @return true if token is valid, false otherwise
     */
    private static boolean isValidToken(String token) {
        if ("Invalid token".equals(token)) {
            return false;
        }
    
        if (token != null && !token.isEmpty()) {
            return true;
        }
    
        throw new IllegalStateException("This should not be possible");
    }
}
