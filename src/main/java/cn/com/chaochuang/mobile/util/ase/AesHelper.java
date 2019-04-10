package cn.com.chaochuang.mobile.util.ase;

import cn.com.chaochuang.mobile.util.AesTool;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesHelper {

  /**
   * 默认偏移(16位)
   */
  private static String ivParameter = "ql5itsgsgyetrv7d";

  private static String WAYS = "AES";
  private static boolean isPwd = false;
  /**详见AesType,为了与ios一致，使用CBC方式*/
  public static int MODE_TYPE = 1;

  private static String val = "0";

  /**
   * 加密模式
   * @param type
   * @return
   */
  public static String selectMod(int type) {
    String modeCode = "PKCS5Padding";
    String MODE;
    switch (type) {
      case 0:
        isPwd = false;
        MODE = WAYS + "/" + AesType.ECB.key() + "/" + modeCode;
        break;
      case 1:
        isPwd = true;
        MODE = WAYS + "/" + AesType.CBC.key() + "/" + modeCode;
        break;
      case 2:
        isPwd = true;
        MODE = WAYS + "/" + AesType.CFB.key() + "/" + modeCode;
        break;
      case 3:
        isPwd = true;
        MODE = WAYS + "/" + AesType.OFB.key() + "/" + modeCode;
        break;
      default:
        //默认 CBC
        isPwd = true;
        MODE = WAYS + "/" + AesType.CBC.key() + "/" + modeCode;
    }

    return MODE;

  }

  public static byte[] encrypt(String sSrc, String sKey, int type) throws Exception {
    sKey = toMakekey(sKey, 16, val);
    Cipher cipher = Cipher.getInstance(selectMod(type));
    byte[] raw = sKey.getBytes();
    SecretKeySpec skeySpec = new SecretKeySpec(raw, WAYS);

    // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
    IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
    if (!isPwd) {
      // ECB 不用密码
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
    } else {
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
    }

    byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
    // 此处使用BASE64做转码。
    return Base64.encodeBase64(encrypted);
  }

  public static String decrypt(String sSrc, String sKey, int type) throws Exception {
    sKey = toMakekey(sKey, 16, val);
    try {
      byte[] raw = sKey.getBytes("ASCII");
      SecretKeySpec skeySpec = new SecretKeySpec(raw, WAYS);
      Cipher cipher = Cipher.getInstance(selectMod(type));
      IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
      // ECB 不用密码
      if (!isPwd) {
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
      } else {
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
      }
      // 先用base64解密
      byte[] encrypted1 = Base64.decodeBase64(sSrc.getBytes());
      byte[] original = cipher.doFinal(encrypted1);
      return new String(original, AesTool.EN_UTF8);
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  public static String toMakekey(String str, int strLength, String val) {

    int strLen = str.length();
    if (strLen < strLength) {
      StringBuilder strBuilder = new StringBuilder(str);
      while (strLen < strLength) {
        strBuilder.append(val);
        strLen = strBuilder.length();
      }
      str = strBuilder.toString();
    }
    return str;
  }


}
