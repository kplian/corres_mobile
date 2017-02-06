package com.pxpcorres.faviofigueroa.pxpcorres.pxpservice_android;

import android.util.Log;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.engines.RijndaelEngine;
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.spongycastle.crypto.paddings.ZeroBytePadding;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.encoders.Hex;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 * Created by favio figueroa on 14-Jan-16.
 */
public class PxpEncryption {


    public boolean md5_encryp;

    public String PXPEncryptRijndael256(String usuario,String pwd) throws DataLengthException, IllegalStateException, InvalidCipherTextException {

        // just some constants
        boolean ENCRYPT = true;
        boolean DECRYPT = false;

        String md5 = (md5_encryp)? md5(pwd):pwd;
        String cadena_para_encriptar = uniqid("pxp", false);
        cadena_para_encriptar +="$$"+usuario;


        // the key is either in binary in PHP or a string (dynamic isn't it?), lets assume ASCII
        byte[] givenKey = md5.getBytes(Charset.forName("ASCII"));

        // determine the key size dynamically, somebody thought this was a good idea...
        // NOTE: PHP will emit a warning if the key size is larger, but will simply use the
        // largest key size otherwise
        final int keysize;

            keysize = 256;


        // create a 256 bit key by adding zero bytes to the decoded key
        byte[] keyData = new byte[keysize / Byte.SIZE];
        System.arraycopy(givenKey, 0, keyData, 0, Math.min(givenKey.length, keyData.length));
        KeyParameter key = new KeyParameter(keyData);

        // create a Rijndael cipher with 256 bit block size, this is not AES
        BlockCipher rijndael = new RijndaelEngine(256);

        // use a padding method that only works on data that cannot end with zero valued bytes
        ZeroBytePadding c = new ZeroBytePadding();

        // use ECB mode encryption, which should never be used
        PaddedBufferedBlockCipher pbbc = new PaddedBufferedBlockCipher(rijndael, c);

        // initialize the cipher using the key (no need for an IV, this is ECB)
        pbbc.init(ENCRYPT, key);

        // create a plain text byte array
        byte[] plaintext = cadena_para_encriptar.getBytes(Charset.forName("UTF8"));

        // create a buffer for the ciphertext
        byte[] ciphertext = new byte[pbbc.getOutputSize(plaintext.length)];

        int offset = 0;
        offset += pbbc.processBytes(plaintext, 0, plaintext.length, ciphertext, offset);
        offset += pbbc.doFinal(ciphertext, offset);


        // show the ciphertext
        System.out.println(new String(Hex.encode(ciphertext), Charset.forName("ASCII")));
        Log.d("rijndael 256", String.valueOf(Hex.encode(ciphertext)));

        String result = new String(org.spongycastle.util.encoders.Base64.encode(ciphertext));
        return result;


        /*
        // reverse the encryption
        pbbc.init(DECRYPT, key);
        byte[] decrypted = new byte[pbbc.getOutputSize(ciphertext.length)];
        offset = 0;
        offset += pbbc.processBytes(ciphertext, 0, ciphertext.length, decrypted, offset);
        offset += pbbc.doFinal(decrypted, offset);

        // this will probably print out correctly, but it isn't actually correct
        System.out.println(new String(decrypted, Charset.forName("UTF8")));

        // check out the zero's at the end
        System.out.println(new String(Hex.encode(decrypted), Charset.forName("UTF8")));

        // add it yourself before printing the string
        System.out.println(new String(decrypted, Charset.forName("UTF8")).replaceAll("\\x00+$", ""));

        */


    }


    public String md5(String s) {
        if (s != null)
        {
            try { // Create MD5 Hash

                MessageDigest digest = MessageDigest .getInstance("MD5");
                digest.update(s.getBytes());
                byte messageDigest[] = digest.digest();

                // Create Hex String
                StringBuffer hexString = new StringBuffer();
                for (int i = 0; i < messageDigest.length; i++) {
                    String h = Integer.toHexString(0xFF & messageDigest[i]);
                    while (h.length() < 2)
                        h = "0" + h;
                    hexString.append(h);
                }
                return hexString.toString();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return "";
        }
        return "";
    }

    public String uniqid(String prefix,boolean more_entropy){
        long time = System.currentTimeMillis();


        /*Double uniqidSeed = Math.floor(Math.random() * 0x75bcd15);
        Log.d("uniqidSeed", String.valueOf(uniqidSeed));
        uniqidSeed++;
        Log.d("uniqidSeedSum", String.valueOf(uniqidSeed));*/

        //String uniqidSeed = String.format("%fd%05f", Math.floor(time), (time - Math.floor(time)) * 0x75bcd15);
        //uniqid = uniqid.substring(0, 13);
        String uniqid = "";
        if(!more_entropy)
        {
            uniqid = String.format("%s%08x%05x", prefix, time/1000, time);
        }else
        {

            SecureRandom sec = new SecureRandom();
            byte[] sbuf = sec.generateSeed(8);
            ByteBuffer bb = ByteBuffer.wrap(sbuf);

            uniqid = String.format("%s%08x%05x", prefix, time/1000, time);
            uniqid += "." + String.format("%.8s", ""+bb.getLong()*-1);
        }


        return uniqid ;
    }

}