import Decryption.DecryptAesKeyWithRSAandDigSig;
import Decryption.DecryptDesKeyWithRSAandDS;
import Decryption.MessageDecryption;
import Encryption.MessageEncryption;
import MessageType.AesKeyWithDigitalSignature;
import MessageType.DesKeyWithDigSig;
import java.awt.RenderingHints.Key;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javafx.application.Application;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class ClientCode extends javax.swing.JFrame {

    private static SecretKey DESKey;
    private static byte[] byy = null;
    private static InputStream in;
    private Object msg_txt;
    private long time;
    String filename = "/home/akm/Desktop/an.txt";
    public ClientCode() {
        initComponents();
    }
    
    static Socket socket;
    static DataInputStream din;
    static DataOutputStream dout;
    static ObjectOutputStream output;
    static ObjectInputStream input;
    private static SecretKey AESKey;
    static String msg_from_server;
    String msg_for_server;
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        msg_area = new javax.swing.JTextArea();
        msgText = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        decryptAes = new javax.swing.JButton();
        encryptAes = new javax.swing.JButton();
        decryptAes1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CLIENT");

        msg_area.setBackground(new java.awt.Color(204, 255, 255));
        msg_area.setColumns(20);
        msg_area.setRows(5);
        jScrollPane1.setViewportView(msg_area);

        msgText.setBackground(new java.awt.Color(204, 204, 204));
        msgText.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        sendButton.setBackground(new java.awt.Color(255, 204, 51));
        sendButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        sendButton.setText("SEND MESSAGE");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 102));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setText("CLIENT");

        decryptAes.setBackground(new java.awt.Color(255, 204, 0));
        decryptAes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        decryptAes.setText("DECRYPT");
        decryptAes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decryptAesActionPerformed(evt);
            }
        });

        encryptAes.setBackground(new java.awt.Color(255, 204, 0));
        encryptAes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        encryptAes.setText("ENCRYPT");
        encryptAes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                encryptAesActionPerformed(evt);
            }
        });

        decryptAes1.setBackground(new java.awt.Color(255, 204, 51));
        decryptAes1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        decryptAes1.setText("DECRYPT FILE");
        decryptAes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decryptAes1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(decryptAes, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(encryptAes, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(sendButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(decryptAes1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(251, 251, 251)
                .addComponent(jLabel1))
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(msgText, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(msgText, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(decryptAes, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(encryptAes, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(decryptAes1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        try {
            dout.writeUTF(msg_for_server);
        } catch (IOException ex) {
            Logger.getLogger(ClientCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        msgText.setText("");
    }//GEN-LAST:event_sendButtonActionPerformed

    private void decryptAesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decryptAesActionPerformed
        // TODO add your handling code here:
        MessageDecryption mssg = new MessageDecryption(msg_from_server,AESKey,"AES");
        msg_from_server = mssg.getMessage();
        long tim = mssg.getTime();
        msg_area.setText(msg_area.getText() + "\n\nDecrypted Message(AES) : " + msg_from_server + "\nTime : " + tim + "\n");
    }//GEN-LAST:event_decryptAesActionPerformed

    private void encryptAesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_encryptAesActionPerformed
        // TODO add your handling code here:
        msg_for_server = msgText.getText().trim();
        msg_area.setText(msg_area.getText() + "\n \t \t You : " + msg_for_server + "\n");
        try {
            MessageEncryption mssg = new MessageEncryption(msg_for_server,AESKey,"AES");
            msg_for_server = mssg.getMessage();
            time = mssg.getTime();
            msg_area.setText(msg_area.getText() + "\n \t \t EncryptedWithAES : " + msg_for_server + "\n\nTime : " + time);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(ClientCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ClientCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(ClientCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(ClientCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(ClientCode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_encryptAesActionPerformed
    
    
    private void decryptAes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decryptAes1ActionPerformed
        String message = msg_from_server.substring((msg_from_server.indexOf('/') + 1));
        System.out.println("mssg " + message);
        
        MessageDecryption mesg_dec = new MessageDecryption(message,AESKey,"AES");
        msg_from_server = mesg_dec.getMessage();
        
        System.out.println("message(FILE)" + msg_from_server + "\n");
        
        long tim = mesg_dec.getTime();
        try {
            DataOutputStream doo = new DataOutputStream(new FileOutputStream(filename));
            PrintWriter pw = new PrintWriter(new FileWriter(filename));
            char c;
            String nwln = System.getProperty("line.separator");
            for(int i=0;i<msg_from_server.length();i++){
                c = msg_from_server.charAt(i);
                String s = "" + c;
                if(s.equals("/")){
                    doo.writeBytes(nwln);
                }else{
                    doo.write(c);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        msg_area.setText(msg_area.getText() + "\nTime : " + tim + "\n");
        
    }//GEN-LAST:event_decryptAes1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws NoSuchAlgorithmException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientCode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientCode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientCode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientCode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientCode().setVisible(true);
            }
        });
        
        try {
            socket = new Socket("localhost",6666);
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            
            //get PublicKeyOfServer
            PublicKey publicKeyOfServer = null;
            input = new ObjectInputStream(socket.getInputStream());
            try {
                publicKeyOfServer = (PublicKey)input.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientCode.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            msg_area.setText(msg_area.getText().trim() + "\n\nI know Public Key Of Server : " + publicKeyOfServer.toString() + "\n\n");
            
            //Generate Public And Private Key(RSA)
            KeyPairGenerator keyGenRSA = KeyPairGenerator.getInstance("RSA");
            keyGenRSA.initialize(1024);
            KeyPair keyRSA = keyGenRSA.generateKeyPair();
            PrivateKey keyRSAPrivate = keyRSA.getPrivate();
            PublicKey keyRSAPublic = keyRSA.getPublic();
            
            msg_area.setText(msg_area.getText().trim() + "\n\nI know My Public Key : " + keyRSAPublic.toString() + "\n\nI know My Private Key : " + keyRSAPrivate.toString() + "\n");
            
            //Send PublicKey To Server
            output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(keyRSAPublic);
            
            
            //Receiving Encrypted AES KeyObject From Server
            in = socket.getInputStream();

            byte[] b = new byte[128];
            in.read(b);
            byte[] b2 = new byte[128];
            in.read(b2);
            
            
            // AES Key with Digital Signature
            AesKeyWithDigitalSignature aesKeyDigitalAndSig = new AesKeyWithDigitalSignature(b,b2);
            System.out.println("error not here" + aesKeyDigitalAndSig.getCipherKeyAES() + "\n" + aesKeyDigitalAndSig.getSignature());
            DecryptAesKeyWithRSAandDigSig decryptKeyWithRSAandDS = new DecryptAesKeyWithRSAandDigSig(aesKeyDigitalAndSig.getCipherKeyAES(),keyRSAPrivate,publicKeyOfServer,aesKeyDigitalAndSig.getSignature());
            AESKey = decryptKeyWithRSAandDS.getKey();
            
            msg_area.setText(msg_area.getText().trim() + "\n\nDecryption (AES)Time : " + decryptKeyWithRSAandDS.getTime() + "\n\nCommon AESKey : " + AESKey + "\n\n\n");
            
            
            //Receiving Encrypted DES KeyObject From Server
            DesKeyWithDigSig desKeyDigitalAndSig = null;
            try {
                desKeyDigitalAndSig = (DesKeyWithDigSig)input.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientCode.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println("error not here" + desKeyDigitalAndSig.getCipherKeyDES() + "\n" + desKeyDigitalAndSig.getSignature());
            DecryptDesKeyWithRSAandDS decryptDesKeyWithRSAandDS = new DecryptDesKeyWithRSAandDS(desKeyDigitalAndSig.getCipherKeyDES(),keyRSAPrivate,publicKeyOfServer,desKeyDigitalAndSig.getSignature());
            DESKey = decryptDesKeyWithRSAandDS.getKey();
            msg_area.setText(msg_area.getText().trim() + "\n\nDecryption (DES)Time : " + decryptDesKeyWithRSAandDS.getTime() + "\n\nCommon DESKey : " + DESKey + "\n\n\n");
           
            
            msg_from_server = "";
            while(!msg_from_server.equals("exit"))
            {
                msg_from_server = din.readUTF();
                if(msg_from_server.contains("file")){
                    msg_area.setText(msg_area.getText().trim() + "\nServer send a file : \n" + msg_from_server + "\n");
                }else
                    msg_area.setText(msg_area.getText().trim() + "\nServer's Cipher Text : " + msg_from_server + "\n");
            }
          

        } catch (IOException ex) {
            System.out.println("err hre 1");
            Logger.getLogger(ClientCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton decryptAes;
    private javax.swing.JButton decryptAes1;
    private javax.swing.JButton encryptAes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField msgText;
    public static javax.swing.JTextArea msg_area;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
}
