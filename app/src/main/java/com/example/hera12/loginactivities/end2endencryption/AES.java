package com.example.hera12.loginactivities.end2endencryption;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AES {

    SecretKey secretKey;
    private byte[] IV;
    private String keyString;
    private String IVString;

    public void init(){
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            int KEY_SIZE = 128;
            generator.init(KEY_SIZE);
            secretKey = generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            Log.e("AES Encryption", "Error initializing AES", e);
        }
    }

    public void initKeyFromString() throws Exception{
        secretKey = new SecretKeySpec(Base64.getDecoder().decode(keyString), "AES");
        this.IV = Base64.getDecoder().decode(IVString);
    }


    public String encrypt(String message) throws Exception{
        byte[] messageInBytes = message.getBytes();
        Cipher encryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        IV = encryptionCipher.getIV();
        byte[] messageInBytesEncrypted = encryptionCipher.doFinal(messageInBytes);
        return Base64.getEncoder().encodeToString(messageInBytesEncrypted);
    }

    public String decrypt(String encryptedMessage) throws Exception{
        byte[] messageInBytes = Base64.getDecoder().decode(encryptedMessage);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, IV);
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec);
        byte[] decryptedMessage = decryptionCipher.doFinal(messageInBytes);
        return new String(decryptedMessage);

    }

    public void exportKey(){
        keyString = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        IVString = Base64.getEncoder().encodeToString(IV);
        Log.d("Key", keyString);
        Log.d("IV", IVString);
    }

    public String getIVString() {
        return IVString;
    }

    public String getKeyString() {
        return keyString;
    }

    public void setKeyString(String keyString) {
        this.keyString = keyString;
    }

    public void setIVString(String IVString) {
        this.IVString = IVString;
    }
}

