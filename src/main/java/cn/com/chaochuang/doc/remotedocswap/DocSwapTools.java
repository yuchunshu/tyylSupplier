package cn.com.chaochuang.doc.remotedocswap;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



public class DocSwapTools {
	
	/** 排序：按文件大小 */
    public static String SORTBY_SIZE = "size";
	/** 排序：按最后修改时间 */
    public static String SORTBY_LASTMOD = "lastmod";
    
    private static final int BUFFER_SIZE = 8096; //缓冲区大小

    /** 按 yyyy-MM-dd HH:mm:ss 格式化时间. */
    public static final SimpleDateFormat DATE_TIME_FORMAT       = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /** 按 yyyy-MM-dd HH:mm 格式化时间. */
    public static final SimpleDateFormat DATE_MINUTE_FORMAT     = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    /** 按 yyyy-MM-dd 格式化日期. */
    public static final SimpleDateFormat DATE_FORMAT            = new SimpleDateFormat("yyyy-MM-dd");
    /** 按 yyyy/MM/dd 格式化日期. */
    public static final SimpleDateFormat DATE_FORMAT2           = new SimpleDateFormat("yyyy/MM/dd");
    /** 按 yyyy年MM月dd日 格式化日期. */
    public static final SimpleDateFormat DATE_FORMAT3           = new SimpleDateFormat("yyyy年MM月dd日");
    /** 按 yyyyMMdd 格式化日期. */
    public static final SimpleDateFormat DATE_FORMAT4           = new SimpleDateFormat("yyyyMMdd");
    /** 按 yyyy年M月d日 格式化日期. */
    public static final SimpleDateFormat DATE_FORMAT5           = new SimpleDateFormat("yyyy年M月d日");
    /** 按 yyyy-MM-dd'T'HH:mm:ss.SSSZ 格式化日期. */
    public static final SimpleDateFormat DATE_FORMAT6           = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    /** 数值格式化 */
    public static final DecimalFormat    DECIMAL_FORMAT         = new DecimalFormat("#0.######");
    /** 金额格式化 */
    public static final DecimalFormat    MONEY_FORMAT           = new DecimalFormat("#0.##");

    /** 页面操作成功提示信息 */
    public static String                 MESSAGE_NAME           = "message";
    /** 页面操作失败提示信息 */
    public static String                 ERROR_MESSAGE_NAME     = "errMsg";

    /** BigDecimal的0 */
    public static final BigDecimal       BDEC_ZERO              = new BigDecimal(0);
    /** 在 session 中标识当前用户. */
    public static final String           USER_KEY               = "currentUser";

    /** 标识提示信息的时间. */
    public static final String           OPERATE_TIME_KEY       = "opTime";
    /** 标识提示信息. */
    public static final String           OPERATE_MESSAGE_KEY    = "opMessage";

    /** 指示执行新增操作. */
    public static final String           OPERATE_INSERT         = "insert";
    /** 指示执行修改操作. */
    public static final String           OPERATE_UPDATE         = "update";

    /** 在 session 中标识缓存的查询条件. */
    private static final String          CONDITION_KEY          = "queryCondition";
    /** 在 session 中缓存的查询条件数量. */
    private static final int             CACHE_CONDITION_SIZE   = 10;

    /** 每页显示数据量. */
    public static final int              PAGE_SIZE              = 15;

    /** 系统数据中主菜单的引用关键字 */
    public static final String           MAIN_MENU_DATATYPE_KEY = "Menus";
    /** 短信发送标志键 */
    public static final String           SEND_SMS_FLAG_KEY      = "ifSend";
    /** 短信发送标志值 */
    public static final String           SEND_SMS_FLAG_VALUE    = "1";
    /** 短信内容键 */
    public static final String           SEND_SMS_CONTENT_KEY   = "smsMessage";

    // /** 无效的记录标志，如已删除. */
    // public static final String IS_VALID_FALSE = "0";
    // /** 有效的记录标志. */
    // public static final String IS_VALID_TRUE = "1";

    /** 是 */
    public static final String           YES                    = "1";
    /** 否 */
    public static final String           NO                     = "2";

    /** 在 session 中标识当前用户登录密码经过校验 */
    public static final String           CHECK_PASSWORD         = "Checked";
    /**
     * 移走文件
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @throws Exception Exception
     */
    public static void moveFile(String sourceFile, String targetFile) throws IOException {
        File fileIn = new File(sourceFile);
        if (!fileIn.exists()) {
            return;
        }
        copyFile(sourceFile, targetFile);
        removeFile(sourceFile);
    }
    
//	 /**
//     * 根据字典名称和字典内容查找键值
//     * @param dicName 字典名称
//     * @param value 内容
//     * @return 键值
//     */
//    public static Object getKeyFormMap(String dicName, Object value) {
//        Map dicData = (Map) DictionarysInVelocityToolbox.getDictionary(dicName);
//        if (dicData != null && !dicData.isEmpty()) {
//            for (Iterator iter = dicData.entrySet().iterator(); iter.hasNext();) {
//                Map.Entry element = (Map.Entry) iter.next();
//                if (element.getValue().equals(value)) {
//                    return element.getKey();
//                }
//            }
//        }
//        return null;
//    }
    
    /**
     * 移走指定目录下文件
     *
     * @param sourcePath 源目录
     * @param targetPath 目标目录
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
            Map<String,File> files = new HashMap<String,File>();
            for (int i = 0; i < sFile.listFiles().length; i++) {
                files.put(sFile.listFiles()[i].getPath(), sFile.listFiles()[i]);
            }
            File temp;
            //移走主xml文件(先拷贝到目的 再从源目录删除)
            for (Entry<String, File> entry: files.entrySet()) {
                temp = entry.getValue();
                //假如是目录要递归复制文件
                if (temp.isDirectory()) {
                    moveFolder(temp.getPath(), tFile.getPath() + "/" + temp.getName());
                } else {
                    //实体文件则进行复制和删除操作
                    copyFile(temp.getPath(), tFile.getPath() + "/" + temp.getName());
                }
                if (temp.exists()) {
                    removeFile(temp.getPath());
                }
            }
            removeFile(sFile.getPath());
        } catch (Exception ex) {
            //移走文件可能失败，可以后由清理程序再次移走
        }
    }
    
    /**
     * 删除文件
     * @param fileName 文件名
     * @return 删除结果
     */
    public static boolean removeFile(String fileName) {
        try {
            File file = new File(fileName);
            //若文件存在则删除
            if (file.exists()) {
                System.gc();
                return file.delete();
            }
            return false;
        } catch (Exception ex) {
           return false;
        }
    }
    
    /** 拷贝文件
     * @param file1 源文件
     * @param file2 目标文件
     * @throws Exception Exception
     */
    /**
     * 拷贝文件
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @throws Exception Exception
     */
    public static void copyFile(String sourceFile, String targetFile) throws IOException {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            File fileIn = new File(sourceFile);
            File fileOut = new File(targetFile);
            if (!fileOut.exists()) {
                fileOut.getParentFile().mkdirs();
                fileOut.createNewFile();
            }
            in = new FileInputStream(fileIn);
            out = new FileOutputStream(fileOut);
            byte[] bytes = new byte[1024];
            int c;
            while ((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    
    /**
     * 获取文件的后缀
     *
     * @param fileName 文件名
     * @return
     */
    public static String getFileSuffix(String fileName) {
        if (fileName==null||fileName.trim()=="") {
            return "";
        }
        int idx = fileName.lastIndexOf(".");
        if (idx <= 0) {
            return "";
        }
        return fileName.substring(idx, fileName.length());
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
     * 判断list是否为非空列表（null和零长度的字符串都是空）
     * @param list 源列表
     * @return true：非空列表 false：空列表
     */
    public static boolean isNotEmptyList(List list) {
        boolean result = true;

        if (list != null && !list.isEmpty()) {
            return result;
        }
        return false;
    }

    /**
     * 判断map是否为非空Map（null和零长度的字符串都是空）
     * @param map 源map
     * @return true：非空map false：空map
     */
    public static boolean isNotEmptyMap(Map map) {
        boolean result = true;

        if (map != null && !map.isEmpty()) {
            return result;
        }
        return false;
    }
    
//    /**
//     * 将64位编码字符串还原为文件
//     *
//     * @param base64
//     * @param filePath
//     * @throws Exception
//     */
//    public static File base64ToFile(String base64, String filePath) throws Exception{
//        File file = new File(filePath);
//        if (!file.exists()) {
//            createFilePath(file.getParentFile().getPath());
//            file.createNewFile();
//        }
//        FileOutputStream out = new FileOutputStream(file);
//        //对oldString进行解码
//        out.write(Base64.decode(base64.getBytes()));
//        //将问件内容转为byte[]
//        out.close();
//        out.flush();
//        return file;
//    }

//    /**
//     * 获取文件的base64编码
//     *
//     * @param filePath
//     * @return
//     */
//    public static String encodeBase64File2(String filePath) {
//        File file = new File(filePath);
//        if(!file.exists()){
//        	return "";
//        }
//        FileInputStream inputFile = null;
//        try {
//            inputFile = new FileInputStream(file);
//            byte[] buffer = new byte[10240], outputs;
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            while(inputFile.read(buffer)!=-1){
//            	outputs = Base64.encode(buffer);
//            	out.write(outputs,0,outputs.length);
//            }
//            return new String(out.toByteArray(), "UTF-8");
//        } catch (IOException ex) {
//            return "";
//        } finally {
//            if (inputFile != null) {
//                try {
//                    inputFile.close();
//                } catch (IOException ex) {
//                }
//            }
//        }
//    }
    
//    /**
//     * 获取文件的base64编码
//     *
//     * @param filePath
//     * @return
//     */
//    public static String encodeBase64File(String filePath) {
//        File file = new File(filePath);
//        FileInputStream inputFile = null;
//        try {
//            inputFile = new FileInputStream(file);
//            byte[] buffer = new byte[(int) file.length()], outputs;
//            inputFile.read(buffer);
//            outputs = Base64.encode(buffer);
//            return new String(outputs, "UTF-8");
//        } catch (IOException ex) {
//            return "";
//        } finally {
//            if (inputFile != null) {
//                try {
//                    inputFile.close();
//                } catch (IOException ex) {
//                }
//            }
//        }
//    }
    
    /**
     * 字符串转成日期（yyyy-mm-dd hh:mm:ss）
     * @param dates 日期字符串
     * @return 日期
     */
    public static Date parseDateTime(String dates) {
        synchronized (DATE_TIME_FORMAT) {
            try {
                return DATE_TIME_FORMAT.parse(dates);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * 字符串转成日期（yyyy-mm-dd）
     * @param dates 日期字符串
     * @return 日期
     */
    public static Date parseDate(String dates) {
        synchronized (DATE_FORMAT) {
            try {
                return DATE_FORMAT.parse(dates);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
    /**
     * 获取文件的ND5码
     *
     * @param file 要获取MD5码的文件
     * @return
     * @throws IOException
     */
    @SuppressWarnings("resource")
	public static String getFileMd5Code(File file) throws IOException {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (java.security.NoSuchAlgorithmException ex) {
            throw new AssertionError(ex);
        }
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
                file.length());
        md.update(byteBuffer);
        return bufferToHex(md.digest());
    }
    
    /**
     * 算出字节数组的MD5码
     *
     * @param bytes
     * @return
     */
    private static String bufferToHex(byte bytes[]) {
        int m = 0, n = bytes.length;
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    /**
     * @param bt
     * @param stringbuffer
     */
    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
    
    //安全删除文件夹
    public static void deleteRecursively(File root) {
		if (root != null && root.exists()) {
			if (root.isDirectory()) {
				//获取子文件
				File[] children = root.listFiles();
				if (children != null) {
					for (File child : children) {
						//删除每个子文件
						deleteRecursively(child);
					}
				}
			}
			//删除没有子文件的文件
			root.delete();
		}
	}
    
    /**
     * 获取排序的文件列表
     *
     * @param directory 文件目录
     * @param filter 过滤器
     * @param sortFlag 排序标志
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
        if (sortFlag.equals(SORTBY_SIZE)) {
            Arrays.sort(files, new FileComparator.CompratorBySize<File>());
        } else if (sortFlag.equals(SORTBY_LASTMOD)) {
            Arrays.sort(files, new FileComparator.CompratorByLastModified<File>());
        } else {
            Arrays.sort(files);
        }
        return files;
    }
    
    /**
     * 获取类名（去掉包前缀）
     *
     * @param className 原类名
     * @return 类名
     */
    public static String getClassName(String className) {
        String[] paths = className.split("\\.");
        String tmps = className;
        if (paths.length > 1) {
            tmps = paths[paths.length - 1];
        }
        return tmps;
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
     * 根据部门id从部门字典中获取部门名称
     * @param depId
     * @return
     */
//    public static String getDepartmentName(String depId) {
//    	Map<String,String> dicData = (Map<String,String>) DictionarysInVelocityToolbox.getDictionary("dicDepartment");
//    	return dicData.get(depId);
//    }

    /**
     * 根据电子公文单位id获取单位名称
     * @param swapDeptId
     * @return
     */
//    public static String getSwapDeptName(String swapDeptId) {
//        if(swapDeptId!=null&&swapDeptId.length()>=9){
//            swapDeptId = swapDeptId.substring(0,9);
//            Map<String,String> dicData = (Map<String,String>) DictionarysInVelocityToolbox.getDictionary("dicRemoteUnitDef");
//            return dicData.get(swapDeptId);
//        }
//        return null;
//    }

    /**
     * 日期相减 计算相差天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer calculateDayInterval(Date startDate,Date endDate) {
        if (endDate == null || startDate == null) {
            return 0;
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try {
            startDate=sdf.parse(sdf.format(startDate));
            endDate=sdf.parse(sdf.format(endDate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(endDate);
            long time2 = cal.getTimeInMillis();
            long between_days=(time2-time1)/(1000*3600*24);

            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            e.printStackTrace();
        }
       return 0;
    }
}
