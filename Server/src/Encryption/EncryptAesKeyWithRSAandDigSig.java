package Encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class EncryptAesKeyWithRSAandDigSig {
    
    private byte[] cipherKeyAES;          // for cipher form of AES key
    private byte[] signature;
    private long time; 

    public EncryptAesKeyWithRSAandDigSig(byte[] plainText, PublicKey publicKey, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, SignatureException {
        // get an RSA cipher object
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // algo,mode,padding

        // encrypt the plaintext using the public key
        System.out.println( "\nStart encryption" );
        final long startTime = System.nanoTime();
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);   // initilize Encryption mode
        byte[] cipherText = cipher.doFinal(plainText);
        System.out.println( "Finish encryption" );
        System.out.println( new String(cipherText, StandardCharsets.UTF_8) );

        // Signature is created using MD5withRSA
        Signature sig = Signature.getInstance("MD5WithRSA");    //Creates the Signature object.
        sig.initSign(privateKey);                         //Initializes the Signature object with Private key
        sig.update(cipherText);                                  // Calculates the signature with a plaintext string.
        byte[] signature = sig.sign();
        System.out.println( "\nSignature:"  + signature );
        System.out.println( new String(signature, StandardCharsets.UTF_8) );
        this.signature = signature;
        this.cipherKeyAES = cipherText;
        final long duration = System.nanoTime() - startTime;
        System.out.println("Finish encryption");
    }

    public long getTime()
    {
        return this.time;
    }
    
    public byte[] getCipherKeyAES() {
        return this.cipherKeyAES;
    }

    public byte[] getSignature() {
        return this.signature;
    }
}
