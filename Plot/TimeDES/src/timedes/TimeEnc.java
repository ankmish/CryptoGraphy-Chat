/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timedes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author AkM
 */
public class TimeEnc {
     static String e;
    public static void main(String[] args) throws FileNotFoundException, IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, Exception {
       
        BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\AkM\\Desktop\\256"));
        String line;
        DES dd = new DES();
        long start = System.currentTimeMillis();
        while ((line = in.readLine()) != null) {
            e=dd.encrypt(line);
        }
        long end = System.currentTimeMillis();
        System.out.println("Encryption time= "+(end-start));
    
}
}