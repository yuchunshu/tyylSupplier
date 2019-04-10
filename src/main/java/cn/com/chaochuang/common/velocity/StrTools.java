/*
 * FileName:    StrTools.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月5日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.velocity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;

import com.alibaba.fastjson.JSONArray;

/**
 * @author LJX
 *
 */
@DefaultKey("strTool")
@ValidScope(Scope.APPLICATION)
public class StrTools implements Serializable {

    private static final long serialVersionUID = -7946684310875558797L;

    public boolean isImgStr(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        String[] imgs = { ".png", ".jpg", ".gif", ".jpeg", ".tiff", ".psd", ".swf", ".svg", ".bmp" };
        String strsub = str.substring(str.lastIndexOf("."));
        if (StringUtils.isNotBlank(strsub) && Arrays.asList(imgs).contains(strsub.toLowerCase())) {
            return true;
        }
        return false;
    }

    /**
     * 将用 "," 分割的字符串转成 string数组
     *
     * @param str
     * @return
     */
    public String[] strToArr(String str) {
        if (StringUtils.isNotBlank(str)) {
            return str.split(",");
        }
        return null;
    }

    /**
     * 将json数组字符串转为list
     *
     * @param str
     * @return
     */
    public List JSONArrayToList(String str) {
        if (StringUtils.isNotBlank(str)) {
            return JSONArray.parseArray(str);
        }
        return null;
    }

    /**
     * 获取character 在 orig中的位置
     *
     * @param orig
     * @param character
     * @return
     */
    public int StrIndexOf(String orig, String character) {
        if (StringUtils.isNotBlank(orig) && character != null) {
            return orig.indexOf(character);
        }
        return -1;
    }

}
