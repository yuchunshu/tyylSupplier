package cn.com.chaochuang.mobile.util;

import cn.com.chaochuang.mobile.bean.AppResponseInfo;
import cn.com.chaochuang.mobile.domain.SysMobileDevice;
import cn.com.chaochuang.mobile.util.ase.AesHelper;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Random;

/**
 * 2017-9-18
 *
 * @author Shicx
 *
 * 对称加密算法
 */
public class AesTool {

    public static final String EN_UTF8 = "UTF-8";
    /**
     * 注意: 这里的password(秘钥必须是16位的)
     */
    private static final String PUBLIC_KEY = "PznhCA26zpkEqLmz";

    /**
     * 加密
     */
    public static String encode(String content) {
        if(content==null||"".equals(content)){
            return "";
        }
        try {
            return new String(AesHelper.encrypt(content, AesTool.PUBLIC_KEY, AesHelper.MODE_TYPE));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 加密
     * @param content
     * @param key 密钥必须为16位
     * @return
     */
    public static String encode(String content,String key) {
        if(content==null||"".equals(content)){
            return "";
        }
        try {
            return new String(AesHelper.encrypt(content, key, AesHelper.MODE_TYPE));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 解密
     */
    public static String decode(String content,String key) {
        if(content==null||"".equals(content)){
            return "";
        }
        try {
            return AesHelper.decrypt(content, key, AesHelper.MODE_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String decode(String content) {
        if(content==null||"".equals(content)){
            return "";
        }
        try {
            return AesHelper.decrypt(content, AesTool.PUBLIC_KEY, AesHelper.MODE_TYPE);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * md5String
     *
     * @param s
     * @return
     */
    public final static String md5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //测试用例
    public static void main(String[] args) {
        String content = "account=swadmin&code=6594&password=1&uid=&ukey=&token=";
        String pStr = encode(content);
        System.out.println("加密前：" + content);
        System.out.println("加密后:" + pStr);

        String postStr = decode(pStr);
        System.out.println("解密后：" + postStr);
        System.out.println("md5：" + md5(postStr));
        System.out.println("16 size：" + get16Uuid());
    }

    final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z' };
    /**
     * 随机ID生成器，由数字、小写字母和大写字母组成
     *
     * @param size
     * @return
     */
    public static String uuidBySize(int size) {
        Random random = new Random();
        char[] cs = new char[size];
        for (int i = 0; i < cs.length; i++) {
            cs[i] = digits[random.nextInt(digits.length)];
        }
        return new String(cs);
    }


    /**
     * 获取16位uuid
     *
     * @return
     */
    public static String get16Uuid() {
        return uuidBySize(16);
    }

    /**
     * 返回uid和ukey
     *
     * @param mobileDevice
     * @return
     */
    public static Map<String, String> getTokenConfig(SysMobileDevice mobileDevice, Boolean aesFlag) {
        Map<String, String> configMap = Maps.newLinkedHashMap();
        if (mobileDevice != null && mobileDevice.getUser() != null) {
            try {
                String uid = mobileDevice.getUser().getId() + "";
                String ukey = mobileDevice.getUkey();
                //TODO uid暂时不加密
                //uid=encode(uid);
                ukey=encode(ukey);
                configMap.put("uid", uid);
                configMap.put("ukey", ukey);
            } catch (Exception e) {
                e.printStackTrace();
                return configMap;
            }
        }
        return configMap;
    }

    /**
     * 对返回的数据处理
     *
     * @param responseInfo
     * @param aesFlag 是否加密返回
     * @return
     */
    public static void responseData(AppResponseInfo responseInfo, Boolean aesFlag) {
        if (aesFlag!=null&&aesFlag) {
            if (responseInfo != null && responseInfo.getData() != null) {
                //对data中的内容加密返回
                String jsonData = encode(JSONObject.toJSONString(responseInfo.getData()));
                responseInfo.setData(jsonData);
                responseInfo.setEncodeFlag(true);
            }
        }
    }

    /**
     * 对返回的数据处理
     *@param aesFlag 是否加密返回
     * @return
     */
    public static AppResponseInfo responseData(String success, String message, Object data, Boolean aesFlag) {
        AppResponseInfo responseInfo = new AppResponseInfo(success,message,data);
        if (aesFlag!=null&&aesFlag) {
            if (responseInfo.getData() != null) {
                //对data中的内容加密返回
                String jsonData = encode(JSONObject.toJSONString(responseInfo.getData()));
                responseInfo.setData(jsonData);
            }
        }
        responseInfo.setEncodeFlag(aesFlag!=null&&aesFlag);
        return responseInfo;
    }

    /**
     * 对返回的数据处理
     * @param data
     * @param aesFlag 是否加密返回
     * @return
     */
    public static AppResponseInfo responseData(Object data, Boolean aesFlag) {
        AppResponseInfo responseInfo = new AppResponseInfo(data);
        if (aesFlag!=null&&aesFlag) {
            if (responseInfo.getData() != null) {
                //对data中的内容加密返回
                String jsonData = encode(JSONObject.toJSONString(responseInfo.getData()));
                responseInfo.setData(jsonData);
            }
        }
        responseInfo.setEncodeFlag(aesFlag!=null&&aesFlag);
        return responseInfo;
    }

}
