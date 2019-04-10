/*
 * FileName:    FileTools.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月15日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.tool;

import java.io.File;

/**
 * @author LJX
 *
 */
public class FileTools {
    /**
     * 判断文件夹是否为空；
     *
     * @param path
     * @return
     */
    public static boolean isEmptyFile(String path) {
        File file = new File(path);
        if (file.exists() && file.list().length > 0) {
            return false;
        } else {
            return true;
        }
    }
}
