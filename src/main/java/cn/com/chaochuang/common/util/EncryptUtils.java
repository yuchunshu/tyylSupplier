package cn.com.chaochuang.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.util.Base64;

/**
 * 加密工具
 * 
 * @author yuandl 2017-01-17
 *
 */
public class EncryptUtils {

    public static final String ALGORITHM_DES           = "DES";
    public static final String ALGORITHM_TripleDES_CBC = "TripleDES/CBC/PKCS5Padding";

    // 密钥是16位长度的byte[]进行Base64转换后得到的字符串
    public static String       key                     = "^Cn+TxDY>Z6w32987Du=v=dK";

    /**
     * 测试方法
     * 
     * @param args
     */
    public static void main(String[] args) {
        if (args != null && args.length >= 2) {
            int mode = args[0].equals("encrypt") ? 1 : args[0].equals("decrypt") ? 2 : 0;// encrypt加密,decrypt解密
            if (mode != 1 && mode != 2) {
                System.err.println("[错误]无法识别操作。正确操作类别：encrypt | decrypt");
                return;
            }

            String algorithm = args[1];
            if (!EncryptUtils.ALGORITHM_DES.equals(algorithm)
                            && !EncryptUtils.ALGORITHM_TripleDES_CBC.equals(algorithm)) {
                System.err.println("[错误]无法识别加密算法。正确算法类别：DES | TripleDES/CBC/PKCS5Padding");
                System.exit(0);
            }

            String filepath = args[2];
            if (StringUtils.isBlank(filepath)) {
                System.err.println("[错误]无法识别待处理文件。");
                System.exit(0);
            }

            File sourceFile = new File(filepath);
            File destFile = new File(filepath + "_dest");

            if (!sourceFile.exists()) {
                System.err.println("[错误]打开文件失败。");
                System.exit(0);
            }

            InputStream is = null;
            OutputStream os = null;
            CipherInputStream cis = null;

            try {
                is = new FileInputStream(sourceFile);
                os = new FileOutputStream(destFile);
                if (mode == 1) {// 加密文件
                    cis = EncryptUtils.encryptInputStream(algorithm, is);
                    IOUtils.copy(cis, os);
                } else if (mode == 2) {// 解密文件
                    cis = EncryptUtils.decryptInputStream(algorithm, is);
                    IOUtils.copy(cis, os);
                }
                System.out.println("[成功]处理成功！");
            } catch (Exception e) {
                System.err.println("[错误]" + e.getMessage());
            } finally {
                IOUtils.closeQuietly(cis);
                IOUtils.closeQuietly(is);
                IOUtils.closeQuietly(os);
            }
        }
        System.exit(0);
    }

    /**
     * 加密inputstream
     * 
     * @param algorithm
     * @param is
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public static CipherInputStream encryptInputStream(String algorithm, InputStream is)
                    throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        SecretKey secretKey = EncryptUtils.generateKey(key);
        Cipher cipher = Cipher.getInstance(StringUtils.isNotBlank(algorithm) ? algorithm : EncryptUtils.ALGORITHM_DES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        CipherInputStream cis = new CipherInputStream(is, cipher);
        return cis;
    }

    /**
     * 加密outputstream
     * 
     * @param algorithm
     * @param os
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public static CipherOutputStream encryptOutputStream(String algorithm, OutputStream os)
                    throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        SecretKey secretKey = EncryptUtils.generateKey(key);
        Cipher cipher = Cipher.getInstance(StringUtils.isNotBlank(algorithm) ? algorithm : EncryptUtils.ALGORITHM_DES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        CipherOutputStream cos = new CipherOutputStream(os, cipher);
        return cos;
    }

    /**
     * 解密inputstream
     * 
     * @param algorithm
     * @param is
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public static CipherInputStream decryptInputStream(String algorithm, InputStream is)
                    throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        SecretKey secretKey = EncryptUtils.generateKey(key);
        Cipher cipher = Cipher.getInstance(StringUtils.isNotBlank(algorithm) ? algorithm : EncryptUtils.ALGORITHM_DES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        CipherInputStream cis = new CipherInputStream(is, cipher);
        return cis;
    }

    /**
     * 解密outputstream
     * 
     * @param algorithm
     * @param os
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     */
    public static CipherOutputStream decryptOutputStream(String algorithm, OutputStream os)
                    throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        SecretKey secretKey = EncryptUtils.generateKey(key);
        Cipher cipher = Cipher.getInstance(StringUtils.isNotBlank(algorithm) ? algorithm : EncryptUtils.ALGORITHM_DES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        CipherOutputStream cos = new CipherOutputStream(os, cipher);
        return cos;
    }

    public static SecretKey generateKey(String key) throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance("DES");
        generator.init(new SecureRandom(key.getBytes()));
        SecretKey secretKey = generator.generateKey();
        return secretKey;
    }

    public static void getKeyIV(String encryptKey, byte[] key, byte[] iv) {
        // 密钥Base64解密
        byte[] buf = null;
        buf = Base64.decodeFast(encryptKey);
        // 前8位为key
        int i;
        for (i = 0; i < key.length; i++) {
            key[i] = buf[i];
        }
        // 后8位为iv向量
        for (i = 0; i < iv.length; i++) {
            iv[i] = buf[i + 8];
        }
    }
}
