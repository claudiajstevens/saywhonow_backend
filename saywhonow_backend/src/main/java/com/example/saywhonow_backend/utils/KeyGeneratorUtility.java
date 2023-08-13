package com.example.saywhonow_backend.utils;

import java.nio.channels.IllegalSelectorException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class KeyGeneratorUtility {

    public static KeyPair generateRsaKey(){
        
        KeyPair keyPair;

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);    
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            // TODO: handle exception
            throw new IllegalSelectorException();
        }

        return keyPair;
    }
}
