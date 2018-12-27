package Decryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class MessageDecryption {
    private String message;
    private long time;
    
    public MessageDecryption(String message, Key key,String type) {
        if(message == null){
            System.out.println("null");
        }
        try {
            byte[] cipherText = new byte[message.length()];       //cipher text in Byte form
            char[] carr = message.toCharArray();         // storing message into char array
            for (int i = 0; i < message.length(); i++) {
                cipherText[i] = (byte) carr[i];
            }

            // Creates the Cipher object (specifying the algorithm, mode, and padding)
            
            Cipher cipher = Cipher.getInstance(type + "/ECB/PKCS5Padding");                 // from java.crpto.Cipher ....

            // decrypt the ciphertext using the same key
            System.out.println("\nStart decryption");
            final long startTime = System.nanoTime();
            cipher.init(Cipher.DECRYPT_MODE, key);                   // initialize the cipher as DECRYPT_MODE
            byte[] newPlainText = cipher.doFinal(cipherText);        // here you get plain text
            final long duration = System.nanoTime() - startTime;
            System.out.println("Finish decryption");

      
            this.message = new String(newPlainText, StandardCharsets.UTF_8);
            System.out.println("It took " + duration + " nanosecond to decrypt the message " + this.message);
            System.out.println("Message length is " + this.message.length());
            time = duration;
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            System.out.println("mssg");
        }
    }

    public String getMessage() {
        System.out.println("print " + message);
        return message;
    }
    
    public long getTime()
    {
        return time;
    }
}
