/*
 * FileName:    MobileTool.java
 * Description: ${DESCRIPTION}
 * Company:     南宁超创信息工程有限公司
 * Copyright:    ChaoChuang (c) 2017
 * History:     2017年10月18日 (cookie) 1.0 Create
 */
package cn.com.chaochuang.mobile.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author shicx
 */
public class MobileTool {

    /**
     * 匹配html 标签
     */
    private static final Pattern htmlTag = Pattern.compile("<[^>]+>");

    /**
     * 截取字符串，超过长度使用 ...
     *
     * @param str
     * @param length
     * @return
     */
    public static String interceptStr(String str, int length) {
        if (str != null) {
            if (str.length() <= length) {
                return str;
            } else {
                return str.substring(0, length) + "...";
            }
        }
        return null;
    }

    /**
     * 去掉字符串中的html标签
     *
     * @param str
     * @return
     */
    public static String replaceHtml(String str) {
        if (str == null) {
            return null;
        }
        return htmlTag.matcher(str).replaceAll("");
    }

    /**
     * 将zip压缩文件解压。
     * @param zipFile zip压缩文件
     * @param parentFolder 解压到的文件夹上级路径
     * @param folderName 解压到的文件夹名称
     */
    public static void unzip(File zipFile,File parentFolder,String folderName){
        File folder = new File(parentFolder, folderName);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try{
            ZipFile zip = new ZipFile(zipFile.getAbsolutePath(), Charset.forName("gbk"));
            Enumeration entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                File destinationFile = new File(folder, entry.getName());

                if (entry.isDirectory()) {
                    if(!destinationFile.exists()){
                        destinationFile.mkdirs();
                    }
                } else {
                    BufferedInputStream bis = new BufferedInputStream(zip.getInputStream(entry));

                    int b;
                    byte[] buffer = new byte[1024];


                    FileOutputStream fos = new FileOutputStream(destinationFile);
                    BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);

                    while ((b = bis.read(buffer, 0, 1024)) != -1) {
                        bos.write(buffer, 0, b);
                    }

                    bos.flush();
                    bos.close();
                    bis.close();
                }
            }
            zip.close();
            zipFile.delete();
        } catch (IOException e){
            folder.delete();
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        //System.out.println(interceptStr("1211111111111111111111111111",10));
        //System.out.println(replaceHtml("<a>1111111111</a>,dasf<img src=/>adfa"));
    }
}
