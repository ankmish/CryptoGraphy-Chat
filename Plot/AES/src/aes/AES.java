/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aes;
/**
 *
 * @author AkM
 */
import static aes.AES.sk;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class AES {
    //public static KeyGenerator kg;
    public static String sKey ="Ld+iUNE1/TZKobXvBQlG9g==";
    static Base64.Decoder dc = Base64.getDecoder();
    static byte[] bKey = dc.decode(sKey);
    public static SecretKey sk=new SecretKeySpec(bKey,0,bKey.length,"AES");
    
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
           
    }
    public static String encrypt(String pt,SecretKey sk) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException{
        Cipher c= Cipher.getInstance("AES");
        byte[] arr = pt.getBytes();
        c.init(Cipher.ENCRYPT_MODE,sk);
        byte[] eb = c.doFinal(arr);
        Base64.Encoder encode = Base64.getEncoder();
        String et=encode.encodeToString(eb);
        return et;
    }
    
    public static String decrypt(String et,SecretKey sk) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException{
        Cipher c= Cipher.getInstance("AES");
        Base64.Decoder decode = Base64.getDecoder();
        byte eb[]=decode.decode(et);
        c.init(Cipher.DECRYPT_MODE, sk);
        byte[] db;
        db = c.doFinal(eb);
        String dt=new String(db);
        return dt;
    }
}
