import Decryption.MessageDecryption;
import Encryption.EncryptAesKeyWithRSAandDigSig;
import Encryption.EncryptDesKeyWithRSAandDS;
import Encryption.EncryptFile;
import Encryption.MessageEncryption;
import MessageType.AesKeyWithDigitalSignature;
import MessageType.DesKeyWithDigSig;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPairGenerator;                     // for key pair generation
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.security.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
class ClientHandler extends Thread  
{ 
    final DataInputStream dis; 
    final DataOutputStream dos; 
    final Socket s; 
      
  
    // Constructor 
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)  
    { 
        this.s = s; 
        this.dis = dis; 
        this.dos = dos; 
    } 
    ServerCode sk = new ServerCode();
    @Override
    public void run()  
    { 
        String received; 
        String toreturn; 
        while (true)  
        { 
            try { 
                       
                //generate public and private key (RSA)
            KeyPairGenerator keyGenRSA = KeyPairGenerator.getInstance("RSA");  // KeyPair Generatiom
            keyGenRSA.initialize(1024);               // KeyPair of 1024 bits
            KeyPair keyRSA = keyGenRSA.generateKeyPair();   // generate pair
            PrivateKey keyRSAPrivate = keyRSA.getPrivate();  // get private key
            PublicKey keyRSAPublic = keyRSA.getPublic();   // get public key
          
            sk.msg_area.setText(sk.msg_area.getText() + "\n\nI know My Public Key : " + keyRSAPublic + "\n\nI know My Private Key : " + keyRSAPrivate + "\n\n");
            //sending publicKey(RSA) to Client 
            sk.output = new ObjectOutputStream(sk.socket.getOutputStream());
             sk.output.writeObject(keyRSAPublic);
            
            PublicKey publicKeyOfClient = null;
            
            //receive publicKey of Client(RSA) 
            sk.input = new ObjectInputStream(sk.socket.getInputStream());
            try {
                publicKeyOfClient = (PublicKey)sk.input.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            sk.msg_area.setText(sk.msg_area.getText() + "\n\nI Know Public Key Of Client : " + publicKeyOfClient + "\n\n");
            
            //Generate AES Key
            KeyGenerator keyGenAES = KeyGenerator.getInstance("AES");
            keyGenAES.init(128);    //In AES cipher block size is 128 bits
            sk.AesKey = keyGenAES.generateKey();
            
            sk.msg_area.setText(sk.msg_area.getText().trim() + "\nAES Key : " + sk.AesKey + "\n\n\n");
            
            EncryptAesKeyWithRSAandDigSig encryptedAesKeyWithRSAandDS;
            AesKeyWithDigitalSignature encryptedAESKeyObject = null;
            
            long tim = 0;
            //Encrypt AES Key using RSA
            try {
                //encrypt AES Key with RSA and Digital Signature
                encryptedAesKeyWithRSAandDS = new EncryptAesKeyWithRSAandDigSig(sk.AesKey.getEncoded(), publicKeyOfClient,keyRSAPrivate);
                tim = encryptedAesKeyWithRSAandDS.getTime();
                encryptedAESKeyObject = new AesKeyWithDigitalSignature(encryptedAesKeyWithRSAandDS.getCipherKeyAES(),encryptedAesKeyWithRSAandDS.getSignature());
                
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SignatureException ex) {
                Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
            }

            sk.msg_area.setText(sk.msg_area.getText().trim() + "\nAES After Encryption " + encryptedAESKeyObject + "\n\nTime taken in Encryption(AES) with RSA :" + tim);
            
            //Sending AES EncryptedCipherText and Respective DigitalSignature
             sk.output .write(encryptedAESKeyObject.getCipherKeyAES());
             sk.output .write(encryptedAESKeyObject.getSignature());
            
            System.out.println("error here");
            
             String  msg_from_Client = "";
            
            //Generate DES Key
            KeyGenerator keyGenDES = KeyGenerator.getInstance("DES");
            keyGenDES.init(56);    //In DES cipher block size is 56 bits
            sk.DesKey = keyGenDES.generateKey();
            
            sk.msg_area.setText(sk.msg_area.getText().trim() + "\nDES Key : " + sk.DesKey + "\n\n\n");
            
            EncryptDesKeyWithRSAandDS encryptedDesKeyWithRSAandDS = null;
            DesKeyWithDigSig encryptedDESKeyObject = null;
            
            //Encrypt DES Key using RSA
            try {
                //encrypt AES Key with RSA and Digital Signature
                encryptedDesKeyWithRSAandDS = new EncryptDesKeyWithRSAandDS(sk.DesKey.getEncoded(), publicKeyOfClient,keyRSAPrivate);
                System.out.println("time taken : " + encryptedDesKeyWithRSAandDS.getTime());
                encryptedDESKeyObject = new DesKeyWithDigSig(encryptedDesKeyWithRSAandDS.getCipherKeyDES(),encryptedDesKeyWithRSAandDS.getSignature());
                //this.bind("cn=AesKeyWithDigitalSignature",encryptedAESKeyObject);
            } catch (NoSuchPaddingException ex) {
                Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BadPaddingException ex) {
                Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalBlockSizeException ex) {
                Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeyException ex) {
                Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SignatureException ex) {
                Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
            }
            tim = encryptedDesKeyWithRSAandDS.getTime();
            sk.msg_area.setText(sk.msg_area.getText().trim() + "\nDES After Encryp. " + encryptedDESKeyObject.getCipherKeyDES() + "  " + encryptedDESKeyObject.getSignature() + "\n\nTime in Encryption(DES) with RSA " + tim);
            
            //Sending EncryptedCipherText and Digital Signature
            sk.output.writeObject(encryptedDESKeyObject);
            sk.msg_from_client = "";
            while(!sk.msg_from_client.equals("exit"))
            {
                sk.msg_from_client = sk.din.readUTF();
                sk.msg_area.setText(sk.msg_area.getText().trim() + "\nAlice's Cipher Text : " + sk.msg_from_client + "\n");
            }
           }
            catch(Exception ex){}  
        }     
    } 
} 

public class ServerCode extends javax.swing.JFrame {

    private String msg;

    public ServerCode() {
        initComponents();
    }
    
    static ServerSocket serversocket;
    static DataInputStream din;
    static DataOutputStream dout;
    static Socket socket;
    static ObjectOutputStream output;
    static ObjectInputStream input;
    static SecretKey AesKey;
    static SecretKey DesKey;
    static String msg_from_client;
    public long time;
    String msg_for_client = "";
    static String filepath;
    static File files;
    static byte[] file_con = null;
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        msg_area = new javax.swing.JTextArea();
        msg_txt = new javax.swing.JTextField();
        aes_encrypt_button = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        file_send_button = new javax.swing.JButton();
        des_encrypt_button1 = new javax.swing.JButton();
        decrypt_aes = new javax.swing.JButton();
        des_encrypt_button2 = new javax.swing.JButton();
        des_encrypt_button3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SERVER");

        msg_area.setBackground(new java.awt.Color(102, 204, 255));
        msg_area.setColumns(20);
        msg_area.setRows(5);
        jScrollPane1.setViewportView(msg_area);

        msg_txt.setBackground(new java.awt.Color(204, 204, 204));
        msg_txt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        aes_encrypt_button.setBackground(new java.awt.Color(255, 153, 0));
        aes_encrypt_button.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        aes_encrypt_button.setText("ENCRYPT");
        aes_encrypt_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aes_encrypt_buttonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setText("SERVER");

        file_send_button.setBackground(new java.awt.Color(255, 153, 0));
        file_send_button.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        file_send_button.setText("SELECT FILE");
        file_send_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                file_send_buttonActionPerformed(evt);
            }
        });

        des_encrypt_button1.setBackground(new java.awt.Color(255, 153, 0));
        des_encrypt_button1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        des_encrypt_button1.setText("SEND MESSAGE");
        des_encrypt_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                des_encrypt_button1ActionPerformed(evt);
            }
        });

        decrypt_aes.setBackground(new java.awt.Color(255, 153, 0));
        decrypt_aes.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        decrypt_aes.setText("DECRYPT");
        decrypt_aes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decrypt_aesActionPerformed(evt);
            }
        });

        des_encrypt_button2.setBackground(new java.awt.Color(255, 153, 0));
        des_encrypt_button2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        des_encrypt_button2.setText("SEND FILE");
        des_encrypt_button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                des_encrypt_button2ActionPerformed(evt);
            }
        });

        des_encrypt_button3.setBackground(new java.awt.Color(255, 153, 0));
        des_encrypt_button3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        des_encrypt_button3.setText("ENCRYPT FILE");
        des_encrypt_button3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                des_encrypt_button3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(decrypt_aes, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(aes_encrypt_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(des_encrypt_button3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(file_send_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(des_encrypt_button1, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                    .addComponent(des_encrypt_button2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(msg_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(msg_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aes_encrypt_button, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(file_send_button, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(des_encrypt_button1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(decrypt_aes, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(des_encrypt_button3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(des_encrypt_button2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //encrypt with AES
    private void aes_encrypt_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aes_encrypt_buttonActionPerformed
        // TODO add your handling code here:
        
        msg_for_client = msg_txt.getText().trim();
        msg_area.setText(msg_area.getText() + "\n \t \t You : " + msg_for_client + "\n");
        try {
            MessageEncryption mssg = new MessageEncryption(msg_for_client,AesKey,"AES");
            msg_for_client = mssg.getMessage();
            time = mssg.getTime();
            msg_area.setText(msg_area.getText() + "\n \t \t EncryptedWithAES : " + msg_for_client + "\n\nTime : " + time);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_aes_encrypt_buttonActionPerformed

    //Sending Encrypted Message
    private void des_encrypt_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_des_encrypt_button1ActionPerformed
        try {
            // TODO add your handling code here:
            dout.writeUTF(msg_for_client);
        } catch (IOException ex) {
            Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
        }
            msg_txt.setText("");
    }//GEN-LAST:event_des_encrypt_button1ActionPerformed

    private void decrypt_aesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decrypt_aesActionPerformed
        // TODO add your handling code here:
        MessageDecryption mssg = new MessageDecryption(msg_from_client,AesKey,"AES");
        msg_from_client = mssg.getMessage();
        long tim = mssg.getTime();
        msg_area.setText(msg_area.getText() + "\n\nDecrypted Message(AES) : " + msg_from_client + "\nTime : " + tim + "\n");
    }//GEN-LAST:event_decrypt_aesActionPerformed

    private void file_send_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_file_send_buttonActionPerformed
        JFileChooser file = new JFileChooser();
        int returnvalue = file.showOpenDialog(file);
        filepath = null;
        if(returnvalue == JFileChooser.APPROVE_OPTION){
            filepath = file.getSelectedFile().getAbsolutePath();
        }else{
            System.exit(1);
        }
        
        files = new File(filepath);
        file_con = new byte[(int)files.length()];   // sending file in byte 
        try {
            BufferedReader br = new BufferedReader(new FileReader(files));
            msg_for_client = "";
            String st = "";
            while((st = br.readLine()) != null){
                msg_for_client += (st + "/");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_file_send_buttonActionPerformed

    private void des_encrypt_button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_des_encrypt_button2ActionPerformed

        try {
            dout.writeUTF("file/" + msg_for_client);
        } catch (IOException ex) {
            Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_des_encrypt_button2ActionPerformed
    
    private void des_encrypt_button3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_des_encrypt_button3ActionPerformed
        try {
            MessageEncryption msgc = new MessageEncryption(msg_for_client,AesKey,"AES");
            msg_for_client = msgc.getMessage();
            System.out.println("File in " + msg_for_client);
            long tim = msgc.getTime();
            msg_area.setText(msg_area.getText() + "\nTime : " + tim + "\n");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_des_encrypt_button3ActionPerformed
    
    public static void main(String args[]) throws IOException, NoSuchAlgorithmException {

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
                java.util.logging.Logger.getLogger(ServerCode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(ServerCode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(ServerCode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(ServerCode.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
            
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new ServerCode().setVisible(true);
                }
            });
            
            
            serversocket = new ServerSocket(6666);
            
    while(true) 
    {        
        try 
        {
            socket = serversocket.accept();
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            
            // create a new thread object 
            Thread t = new ClientHandler(socket, din, dout);
            t.start();
            
            
           }
             catch (Exception ex) {
            Logger.getLogger(ServerCode.class.getName()).log(Level.SEVERE, null, ex);
            }
      }
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton aes_encrypt_button;
    private javax.swing.JButton decrypt_aes;
    private javax.swing.JButton des_encrypt_button1;
    private javax.swing.JButton des_encrypt_button2;
    private javax.swing.JButton des_encrypt_button3;
    private javax.swing.JButton file_send_button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextArea msg_area;
    private javax.swing.JTextField msg_txt;
    // End of variables declaration//GEN-END:variables
}