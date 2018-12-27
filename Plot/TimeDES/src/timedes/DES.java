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

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class DES {
  public static void main(String[] argv) throws Exception {
      DES dd = new DES();
    String encrypted = dd.encrypt("Don't tell anybody!");
    String decrypted = dd.decrypt(encrypted);
    System.out.println(encrypted);
  }
     Cipher ecipher;
     Cipher dcipher;
     DES() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
     {
        SecretKey key = KeyGenerator.getInstance("DES").generateKey();
        ecipher = Cipher.getInstance("DES");
        dcipher = Cipher.getInstance("DES");
        ecipher.init(Cipher.ENCRYPT_MODE, key);
        dcipher.init(Cipher.DECRYPT_MODE, key);
     }

   public String encrypt(String str) throws Exception {
    byte[] utf8 = str.getBytes("UTF8");
    byte[] enc = ecipher.doFinal(utf8);
    return new sun.misc.BASE64Encoder().encode(enc);
  }

  public String decrypt(String str) throws Exception {
    byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
    byte[] utf8 = dcipher.doFinal(dec);
    return new String(utf8, "UTF8");
  }
}

   