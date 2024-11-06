package com.authentication.printer.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

import interfaces.IAuth;

public class Auth implements IAuth{
    private MessageDigest messageDigest = null;

    public Auth(){
        try {
            this.messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }
    }
    @Override
    public boolean authenticate(String username, String password) throws RemoteException {
        //var hash = new String(test(password));
        byte[] hash_b = getHash(password);
        String hash_x = new BigInteger(1, hash_b).toString(16);
        String storedHash = getUserPasswordHash(username);
        if (storedHash == null){
            return false;
        }
        return storedHash.equals(hash_x);
    }

    private byte[] generateSalt16Byte() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        
        return salt;
    }

    private byte[] getHash(String password){
        return messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
    }

    private String getUserPasswordHash(String username){
        File myObj = new File("filename.txt");
        try (Scanner myReader = new Scanner(myObj)) {
            while (myReader.hasNextLine()) {
              String line = myReader.nextLine();
              String[] data = line.split(":");
              if (data[0].equals(username)){
                return data[1];
              }
            }
        } catch (FileNotFoundException e1) {
            return null;
        }
        return null;
    }
}
