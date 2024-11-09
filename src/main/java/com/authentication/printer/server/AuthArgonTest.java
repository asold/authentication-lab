package com.authentication.printer.server;

import java.rmi.RemoteException;

public class AuthArgonTest {
    public static void main(String[] args) throws RemoteException {
        System.out.println("Test started...");

        // Test data
        String username = "testUser";
        String password = "securePassword";

        // Create an instance of the testable AuthArgon class
        AuthArgon auth = new AuthArgonTestable(username, password);

        // Test case 1: Correct username and password
        boolean result1 = auth.authenticate(username, password);
        if (result1) {
            System.out.println("Test Case 1 Passed: Correct username and password.");
        } else {
            System.out.println("Test Case 1 Failed: Correct username and password.");
        }

        // Test case 2: Correct username, incorrect password
        boolean result2 = auth.authenticate(username, "wrongPassword");
        if (!result2) {
            System.out.println("Test Case 2 Passed: Correct username, incorrect password.");
        } else {
            System.out.println("Test Case 2 Failed: Correct username, incorrect password.");
        }

        // Test case 3: Incorrect username
        boolean result3 = auth.authenticate("wrongUser", password);
        if (!result3) {
            System.out.println("Test Case 3 Passed: Incorrect username.");
        } else {
            System.out.println("Test Case 3 Failed: Incorrect username.");
        }

        // Test case 4: Empty username and password
        boolean result4 = auth.authenticate("", "");
        if (!result4) {
            System.out.println("Test Case 4 Passed: Empty username and password.");
        } else {
            System.out.println("Test Case 4 Failed: Empty username and password.");
        }

        System.out.println("Test completed.");
    }

    // Inner class to override the getUserPasswordHash method for testing
    static class AuthArgonTestable extends AuthArgon {
        private String testUsername;
        private String testHashedPassword;

        public AuthArgonTestable(String testUsername, String testPassword) {
            super();
            this.testUsername = testUsername;
            // Hash the test password using the existing method
            this.testHashedPassword = this.hashPassword(testPassword);
        }

        @Override
        protected String getUserPasswordHash(String username) {
            if (this.testUsername.equals(username)) {
                return this.testHashedPassword;
            } else {
                return null;
            }
        }
    }
}
