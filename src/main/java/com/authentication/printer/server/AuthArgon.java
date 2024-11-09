package com.authentication.printer.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.util.Scanner;

import com.authentication.printer.common.interfaces.IAuth;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;


public class AuthArgon implements IAuth {
    private Argon2 argon2;

    public AuthArgon() {
        // Initialize Argon2 instance with recommended parameters
        this.argon2 = Argon2Factory.create();
    }

    @Override
    public boolean authenticate(String username, String password) throws RemoteException {
        String storedHash = getUserPasswordHash(username);
        if (storedHash == null) {
            return false;
        }
        // Verify if the provided password matches the stored hash
        return argon2.verify(storedHash, password.getBytes(StandardCharsets.UTF_8));
    }

    public String hashPassword(String password) {
        // Adjust the parameters according to security requirements
        int iterations = 2;
        int memory = 65536; // 64MB
        int parallelism = 1;

        // Generate a hash for the password
        return argon2.hash(iterations, memory, parallelism, password.getBytes(StandardCharsets.UTF_8));
    }

    protected String getUserPasswordHash(String username) { //to change to private
        File myObj = new File("filename.txt");
        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String[] data = line.split(":");
                if (data[0].equals(username)) {
                    return data[1];
                }
            }
        } catch (FileNotFoundException e1) {
            return null;
        }
        return null;
    }
}