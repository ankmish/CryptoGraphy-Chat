/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timedes;

/**
 *
 * @author AkM
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class TimeDES {
    static String e;
    static String d;
    public static void main(String[] args) throws FileNotFoundException, IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, Exception {
        BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\AkM\\Desktop\\256"));
        DES dd = new DES();
        String line;
        long s=0;
        while ((line = in.readLine()) != null) {
            e=dd.encrypt(line);
            long start = System.currentTimeMillis();
            d=dd.decrypt(e);
            long end = System.currentTimeMillis();
            s = s + end-start;
        }
        
        System.out.println("Decryption time= "+s);
    }
}