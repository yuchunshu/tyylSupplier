/*
 * FileName:    DocCodeUtil.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月21日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author yuandl
 *
 */
public class DocCodeUtil {
    /**
     * 拆分收文文号
     *
     * @param docCode
     *            拆分特定的收文文号字符串参数 （如：工信委来文 [2013] 118 号）
     * @return 拆分后形成的List列表
     */
    public static final List splitDocCode(String docCode) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(docCode)) {
            return list;
        }
        String[] codes = docCode.split(" ");
        if (codes == null || codes.length < 3) {
            return list;
        }
        String code = codes[1].substring(codes[1].indexOf("[") + 1, codes[1].lastIndexOf("]"));
        list.add(codes[0]);
        list.add(code);
        list.add(codes[2]);

        return list;
    }
}
