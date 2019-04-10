/*
 * FileName:    AttachUtils.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年5月20日 (LLM) 1.0 Create
 */

package cn.com.chaochuang.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import cn.com.chaochuang.common.comparator.FileComparator;

/**
 * @author LLM
 *
 */
public abstract class AttachUtils {
    /** 排序：按文件大小 */
    public static String       SORTBY_SIZE    = "size";
    /** 排序：按最后修改时间 */
    public static String       SORTBY_LASTMOD = "lastmod";

    private static AttachUtils me;
    private String             rootDirectory;

    public static AttachUtils getInstance() {
        return me;
    }

    /**
     * @return the rootDirectory
     */
    public String getRootDirectory() {
        return rootDirectory;
    }

    /**
     * @param rootDirectory
     *            the rootDirectory to set
     */
    public void setRootDirectory(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    /**
     * 用于产生唯一文件名的算法，在文件标识相同时做进一步区别的识别流水号
     */
    private static int                    uniqueFileSequence = 0;

    /**
     * 用于产生唯一文件名的算法，记录上一个产生的文件标识
     */
    private static Date                   preFileIdentify    = null;

    /**
     * 用于产生唯一文件名的算法，用来产生文件名字符串
     */
    private static final SimpleDateFormat FILE_NAME_FORMAT   = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    /**
     * 缓冲区大小
     */
    private static final int              BUFFER_SIZE        = 8096;                                     // 缓冲区大小

    /**
     * @return 唯一文件名
     */
    public static synchronized String getUniqueFileName() {
        Date fileIdentify = new Date();
        if (fileIdentify.equals(preFileIdentify)) {
            uniqueFileSequence++;
        } else {
            uniqueFileSequence = 0;
        }
        preFileIdentify = fileIdentify;

        StringBuffer fileName = new StringBuffer();
        fileName.append(FILE_NAME_FORMAT.format(fileIdentify));
        if (uniqueFileSequence > 0) {
            fileName.append(Integer.toString(uniqueFileSequence, 10));
        }

        return fileName.toString();
    }

    /**
     * @param prefix
     *            文件名前缀
     * @param ext
     *            文件扩展名
     * @return 唯一文件名
     */
    public synchronized static String getDocUniqueFileName(String prefix, String ext) {
        Date fileIdentify = new Date();
        if (fileIdentify.equals(preFileIdentify)) {
            uniqueFileSequence++;
        } else {
            uniqueFileSequence = 0;
        }
        preFileIdentify = fileIdentify;

        StringBuffer fileName = new StringBuffer();
        if (prefix != null) {
            fileName.append(prefix);
        }
        fileName.append(FILE_NAME_FORMAT.format(fileIdentify));
        if (uniqueFileSequence > 0) {
            fileName.append(Integer.toString(uniqueFileSequence, 10));
        }
        if (ext != null) {
            fileName.append(".").append(ext);
        }

        return fileName.toString();
    }

    /**
     * 下载附件
     *
     * @param response
     *            HttpServletResponse
     * @param fileName
     *            输出的文件名
     * @param savePath
     *            文件所在路径
     * @param isImage
     *            是否是图片格式
     * @return null 或者是 错误描述
     */
    public static String downloadAttach(HttpServletResponse response, String fileName, String savePath, boolean isImage) {
        BufferedInputStream is = null;
        OutputStream os = null;
        String result = null;
        try {
            is = new BufferedInputStream(new FileInputStream(savePath));
            response.setContentType("application/octet-stream");
            String trueName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
            if (isImage) {
                response.setHeader("Content-Disposition", "inline; filename=\"" + trueName + "\"");
            } else {
                response.setHeader("Content-Disposition", "attachment; filename=\"" + trueName + "\"");
            }
            os = response.getOutputStream();
            byte[] b = new byte[2048];
            int len = 0;
            while ((len = is.read(b)) > 0) {
                os.write(b, 0, len);
            }
        } catch (FileNotFoundException ex) {
            result = "无法在服务器找到指定的文档！";
        } catch (IOException ex) {
            // 下载过程中用户取消或其它IO异常
            result = "用户取消下载或其它IO异常" + ex.getMessage();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    result = "关闭输入流异常：" + ex.getMessage();
                }
            }
            if (os != null) {
                try {
                    os.flush();
                } catch (IOException ex) {
                    result = "清空输出缓冲异常：" + ex.getMessage();
                }
                try {
                    os.close();
                } catch (IOException ex) {
                    result = "关闭输出流异常：" + ex.getMessage();
                }
            }
        }
        return result;
    }

    /**
     * 下载文件（IE预览）
     *
     * @param config
     * @param response
     * @param downLoadFileName
     */
    public static void downLoadForPreview(HttpServletResponse response, String filePath) {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            String filename = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
            // response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "inline; filename="
                            + new String(filename.getBytes("GBK"), "ISO-8859-1"));
            bis = new BufferedInputStream(new FileInputStream(filePath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesread;
            while (-1 != (bytesread = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesread);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (bos != null)
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }

    /**
     * 将文件保存到硬盘
     *
     * @param multipartFile
     *            MultipartFile
     * @param physicalFile
     *            物理文件名
     */
    public static void saveToFile(MultipartFile multipartFile, String physicalFile) {
        try {
            File f = new File(physicalFile);
            if (!f.exists()) {
                f.getParentFile().mkdirs();
            }
            multipartFile.transferTo(f);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 拷贝文件
     *
     * @param file1
     *            源文件
     * @param file2
     *            目标文件
     * @throws Exception
     *             Exception
     */
    public static void copyFile(String file1, String file2) throws Exception {
        java.io.File fileIn = new java.io.File(file1);
        java.io.File fileOut = new java.io.File(file2);
        if (!fileOut.exists()) {
            fileOut.getParentFile().mkdirs();
            fileOut.createNewFile();
        }
        FileInputStream in1 = new FileInputStream(fileIn);
        FileOutputStream out1 = new FileOutputStream(fileOut);
        byte[] bytes = new byte[1024];
        int c;
        while ((c = in1.read(bytes)) != -1) {
            out1.write(bytes, 0, c);
        }
        in1.close();
        out1.close();
    }

    /**
     * 移走文件
     *
     * @param file1
     *            源文件
     * @param file2
     *            目标文件
     * @throws Exception
     *             Exception
     */
    public static void moveFile(String file1, String file2) throws Exception {
        java.io.File fileIn = new java.io.File(file1);
        if (!fileIn.exists()) {
            return;
        }
        copyFile(file1, file2);
        removeFile(file1);
    }

    /**
     * 移走文件
     *
     * @param sourcePath
     *            源文件目录
     * @param movePath
     *            目标文件目录
     * @param fileName
     *            文件名
     */
    public static void moveFile(String sourcePath, String movePath, String fileName) {
        try {
            File file = new File(sourcePath);
            if (!file.exists()) {
                return;
            }
            file = new File(movePath);
            if (!file.exists()) {
                createFilePath(movePath);
            }
            // 移走主xml文件(先拷贝到目的 再从源目录删除)
            copyFile(sourcePath + fileName, movePath + fileName);
            removeFile(sourcePath + fileName);
        } catch (Exception ex) {
            // 移走文件可能失败，可以后由清理程序再次移走
        }
    }

    /**
     * 移走指定目录下文件
     *
     * @param sourcePath
     *            源目录
     * @param targetPath
     *            目标目录
     */
    public static void moveFolder(String sourcePath, String targetPath) {
        try {
            File sFile = new File(sourcePath);
            if (!sFile.exists()) {
                return;
            }
            File tFile = new File(targetPath);
            if (!tFile.exists()) {
                createFilePath(targetPath);
            }
            tFile = new File(targetPath + "/" + sFile.getName());
            if (!tFile.exists()) {
                createFilePath(targetPath + "/" + sFile.getName());
            }
            Map files = new HashMap();
            for (int i = 0; i < sFile.listFiles().length; i++) {
                files.put(sFile.listFiles()[i].getPath(), sFile.listFiles()[i]);
            }
            File temp;
            // 移走主xml文件(先拷贝到目的 再从源目录删除)
            for (Iterator it = files.entrySet().iterator(); it.hasNext();) {
                Entry entry = (Entry) it.next();
                temp = (File) entry.getValue();
                // 假如是目录要递归复制文件
                if (temp.isDirectory()) {
                    moveFolder(temp.getPath(), tFile.getPath() + "/" + temp.getName());
                } else {
                    // 实体文件则进行复制和删除操作
                    copyFile(temp.getPath(), tFile.getPath() + "/" + temp.getName());
                }
                if (temp.exists()) {
                    removeFile(temp.getPath());
                }
            }
            removeFile(sFile.getPath());
        } catch (Exception ex) {
            // 移走文件可能失败，可以后由清理程序再次移走
        }
    }

    /**
     * 通过url下载文件
     *
     * @param fileUrl
     *            文件url
     * @param savePath
     *            保存地址
     * @throws Exception
     *             异常
     */
    public static void downloadFile(String fileUrl, String savePath) throws Exception {
        URL url = new URL(fileUrl);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        File fileName = new File(savePath);
        if (!fileName.exists()) {
            (fileName.getParentFile()).mkdirs();
        }
        // 获得输入流
        BufferedInputStream bis = new BufferedInputStream(httpConnection.getInputStream());
        FileOutputStream fos = new FileOutputStream(savePath); // 建立文件
        // 保存文件
        int size = 0;
        byte[] buf = new byte[BUFFER_SIZE];
        while ((size = bis.read(buf)) != -1) {
            fos.write(buf, 0, size);
        }
        fos.close();
        bis.close();
        httpConnection.disconnect();
    }

    /**
     *
     * @param oldFileName
     *            旧文件名
     * @param newFileName
     *            新文件名
     * @throws Exception
     *             Exception
     */
    public static void renameFile(String oldFileName, String newFileName) throws Exception {
        File fOld = new File(oldFileName);
        File fNew = new File(newFileName);
        fOld.renameTo(fNew);
    }

    /**
     * 删除文件
     *
     * @param fileName
     *            文件名
     * @return 删除结果
     */
    public static boolean removeFile(String fileName) {
        try {
            File file = new File(fileName);
            // 若文件存在则删除
            if (file.exists()) {
                if (!file.delete()) {
                    System.gc();
                    return file.delete();
                }
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 删除文件列表指定的文件
     *
     * @param fileNameList
     *            文件列表
     */
    public static void removeFiles(List fileNameList) {
        if (fileNameList == null || fileNameList.size() == 0) {
            return;
        }
        try {
            for (int i = 0; i < fileNameList.size(); i++) {
                AttachUtils.removeFile((String) fileNameList.get(i));
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 清空pathname目录，删除其下的所有文件和目录。
     *
     * @param pathname
     *            要清空的目录路径名
     */
    public static void cleanFolder(String pathname) {
        cleanFolder(new File(pathname));
    }

    /**
     * 清空target目录，删除其下的所有文件和目录。
     *
     * @param target
     *            要清空的目录
     */
    public static void cleanFolder(File target) {
        if (target != null && target.exists()) {
            try {
                if (target.isDirectory()) {
                    File[] subFile = target.listFiles();
                    if (subFile != null) {
                        for (int i = 0; i < subFile.length; i++) {
                            File f = subFile[i];
                            if (f.isDirectory()) {
                                cleanFolder(f);
                            }

                            if (!f.delete()) {
                                throw new RuntimeException("can not delete [" + f.getCanonicalPath()
                                                + "] is no folder!!!");
                            }
                        }
                    }
                } else {
                    throw new RuntimeException("[" + target.getCanonicalPath() + "] is no folder!!!");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 清空指定文件夹下 指定前缀或后缀的文件
     *
     * @param filePath
     *            文件路径
     * @param prefix
     *            前缀
     * @param suffix
     *            后缀
     */
    public static void cleanFolder(String filePath, String prefix, String suffix) {
        File file = new File(filePath);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File tag = files[i];
                Pattern p = Pattern.compile(prefix + "(.*)" + suffix);
                Matcher m = p.matcher(tag.getName());
                if (m.matches()) {
                    tag.delete();
                }
            }
        }
    }

    /**
     * 创建Path指定的文件夹
     *
     * @param path
     *            路径
     */
    public static void createFilePath(String path) {
        if (path != null && path.length() > 0) {
            File file = new File(path);
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    return;
                }
            }
        }
    }

    /**
     * 将指定的文件夹打包
     *
     * @param zipFile
     * @param fileList
     * @return CRC校验值
     * @throws Exception
     */
    public static String zip(String zipFile, List fileList) throws Exception {
        BufferedInputStream origin = null;
        FileOutputStream dest = null;
        CheckedOutputStream checksum = null;
        ZipOutputStream out = null;
        dest = new FileOutputStream(zipFile);
        checksum = new CheckedOutputStream(dest, new Adler32());
        out = new ZipOutputStream(checksum);
        byte data[] = new byte[BUFFER_SIZE];
        for (Iterator it = fileList.iterator(); it.hasNext();) {
            String fileName = (String) it.next();
            FileInputStream fi = new FileInputStream(fileName);
            origin = new BufferedInputStream(fi, BUFFER_SIZE);
            ZipEntry entry = new ZipEntry(fileName.substring(fileName.lastIndexOf("/") + 1));
            out.putNextEntry(entry);
            int count;
            while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                out.write(data, 0, count);
            }
        }
        closeStream(origin, out, false);
        return String.valueOf(checksum.getChecksum().getValue());
    }

    /**
     * 将指定的文件夹打包
     *
     * @param filePath
     * @throws Exception
     */
    public static String zip(String filePath) throws Exception {
        BufferedInputStream origin = null;
        if (filePath.endsWith("/") || filePath.endsWith("\\")) {
            filePath = filePath.substring(0, filePath.length() - 1);
        }
        File f = new File(filePath);
        String zipFile = filePath + ".zip";
        FileOutputStream dest = new FileOutputStream(zipFile);
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
        byte data[] = new byte[BUFFER_SIZE];
        String files[] = f.list();
        for (int i = 0; i < files.length; i++) {
            FileInputStream fi = new FileInputStream(f.getAbsolutePath() + "/" + files[i]);
            origin = new BufferedInputStream(fi, BUFFER_SIZE);
            ZipEntry entry = new ZipEntry(files[i]);
            out.putNextEntry(entry);
            int count;
            while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
                out.write(data, 0, count);
            }
            origin.close();
        }
        out.close();
        return zipFile;
    }

    /**
     * 解压缩文件
     *
     * @param zipFile
     *            zip文件输入流
     * @param zipPath
     *            zip文件解压路径
     * @return CRC校验值
     * @throws Exception
     */
    public static String unZip(InputStream zipFile, String zipPath) throws Exception {
        BufferedOutputStream dest = null;
        CheckedInputStream checksum = null;
        ZipInputStream zis = null;
        ZipEntry entry;
        File f = new File(zipPath);
        if (!f.exists()) {
            if (!f.mkdirs()) {
                return "";
            }
        }
        checksum = new CheckedInputStream(zipFile, new Adler32());
        zis = new ZipInputStream(checksum);
        while ((entry = zis.getNextEntry()) != null) {
            if (entry.isDirectory()) {
                f = new File(zipPath + f.separator + entry.getName());
                f.mkdirs();
            } else {
                int count;
                byte data[] = new byte[BUFFER_SIZE];
                FileOutputStream fos = new FileOutputStream(zipPath + f.separator + entry.getName());
                dest = new BufferedOutputStream(fos, BUFFER_SIZE);
                while ((count = zis.read(data, 0, BUFFER_SIZE)) != -1) {
                    dest.write(data, 0, count);
                }
                dest.flush();
                dest.close();
            }
        }
        zis.close();
        return String.valueOf(checksum.getChecksum().getValue());
    }

    /**
     * 解压缩文件
     *
     * @param zipFile
     *            zip文件名
     * @param zipPath
     *            zip文件解压路径
     * @throws Exception
     */
    public static String unZip(String zipFile, String zipPath) throws Exception {
        InputStream in = new BufferedInputStream(new FileInputStream(zipFile));
        return unZip(in, zipPath);
    }

    /**
     * 获取文件的后缀
     *
     * @param fileName
     *            文件名
     * @return
     */
    public static String getFileSuffix(String fileName) {
        if (Tools.isEmptyString(fileName)) {
            return "";
        }
        int idx = fileName.lastIndexOf(".");
        if (idx <= 0) {
            return "";
        }
        return fileName.substring(idx, fileName.length());
    }

    /**
     * 关闭输入输出流 不抛出异常
     *
     * @param in
     *            输入流
     * @param out
     *            输出流
     * @param isFlush
     *            是否输出缓冲
     */
    private static void closeStream(InputStream in, OutputStream out, boolean isFlush) {
        if (in != null) {
            try {
                in.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if (out != null) {
            try {
                if (isFlush) {
                    out.flush();
                }
                out.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * 关闭输入输出流 不抛出异常
     *
     * @param in
     *            输入流
     * @param out
     *            输出流
     * @param isFlush
     *            是否输出缓冲
     */
    private static void closeStream(Reader in, Writer out, boolean isFlush) {
        if (in != null) {
            try {
                in.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if (out != null) {
            try {
                if (isFlush) {
                    out.flush();
                }
                out.close();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * 获取排序的文件列表
     *
     * @param directory
     *            文件目录
     * @param filter
     *            过滤器
     * @param sortFlag
     *            排序标志
     * @return 文件列表
     */
    public static File[] getFolderFilesSort(String directory, FilenameFilter filter, String sortFlag) {
        File file = new File(directory);
        File[] files = null;
        if (filter == null) {
            files = file.listFiles();
        } else {
            files = file.listFiles(filter);
        }
        if (files == null || files.length == 0) {
            return new File[0];
        }
        if (sortFlag == null) {
            return files;
        }
        if (sortFlag.equals(AttachUtils.SORTBY_SIZE)) {
            Arrays.sort(files, new FileComparator.CompratorBySize());
        } else if (sortFlag.equals(AttachUtils.SORTBY_LASTMOD)) {
            Arrays.sort(files, new FileComparator.CompratorByLastModified());
        } else {
            Arrays.sort(files);
        }
        return files;
    }

    /**
     * 将64位编码字符串还原为文件
     *
     * @param base64
     * @param filePath
     * @throws Exception
     */
    public static File base64ToFile(String base64, String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            createFilePath(file.getParentFile().getPath());
            file.createNewFile();
        }
        FileOutputStream out = new FileOutputStream(file);
        // 对oldString进行解码
        // out.write(Base64.decode(base64.getBytes()));
        out.write(Base64.decodeBase64(base64.getBytes()));
        // 将问件内容转为byte[]
        out.close();
        out.flush();
        return file;
    }

    /**
     * 获取文件的base64编码
     *
     * @param filePath
     * @return
     */
    public static String encodeBase64File(String filePath) {
        if (Tools.isEmptyString(filePath)) {
            return "";
        }
        File file = new File(filePath);
        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()], outputs;
            inputFile.read(buffer);
            // outputs = Base64.encode(buffer);
            outputs = Base64.encodeBase64(buffer);
            return new String(outputs, "UTF-8");
        } catch (IOException ex) {
            return "";
        } finally {
            if (inputFile != null) {
                try {
                    inputFile.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    /**
     * 获取文件的MD5
     *
     * @param fileName
     *            文件名（包含文件的路径）
     * @return
     */
    public static String getFileMD5(String fileName) {
        File file = new File(fileName);
        // 校验文件是否存在
        if (!file.exists()) {
            return "";
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }
}
