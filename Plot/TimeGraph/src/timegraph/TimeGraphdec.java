/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timegraph;
/**
 *
 * @author AkM
 */
import static aes.AES.decrypt;
import static aes.AES.encrypt;
import static aes.AES.sk;
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


public class TimeGraphdec {
    static String e;
    public static void main(String[] args) throws FileNotFoundException, IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
       
        BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\AkM\\Desktop\\10mb"));
        String line;
        long start = System.currentTimeMillis();
        while ((line = in.readLine()) != null) {
            e=encrypt(line,sk);
        }
        long end = System.currentTimeMillis();
        System.out.println("Encryption time= "+(end-start));
    }
}
