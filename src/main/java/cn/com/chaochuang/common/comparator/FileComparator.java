/*
 * FileName:    FileComparator.java
 * Description:
 * Company:     
 * Copyright:    (c) 2015
 * History:     2015年5月20日 (LLM) 1.0 Create
 */

package cn.com.chaochuang.common.comparator;

import java.io.File;
import java.util.Comparator;

/**
 * @author LLM
 *
 */
public class FileComparator {
    /**
     * 按文件的最后修改时间排序 The <code>CompratorByLastModified</code>
     *
     * @author LLM
     * @version 1.0, 2009-8-28
     */
    public static class CompratorByLastModified implements Comparator {
        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(Object o1, Object o2) {
            File file1 = (File) o1;
            File file2 = (File) o2;

            long diff = file1.lastModified() - file2.lastModified();
            if (diff > 0) {
                return 1;
            } else if (diff == 0) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    /**
     * 按文件大小排序 The <code>CompratorBySize</code>
     *
     * @author LLM
     * @version 1.0, 2009-8-28
     */
    public static class CompratorBySize implements Comparator {
        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(Object o1, Object o2) {
            File file1 = (File) o1;
            File file2 = (File) o2;

            long diff = file1.length() - file2.length();
            if (diff > 0) {
                return 1;
            } else if (diff == 0) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    /**
     * 按文件名排序 The <code>CompratorByName</code>
     *
     * @author LLM
     * @version 1.0, 2011-8-5
     */
    public static class CompratorByName implements Comparator {
        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare(Object o1, Object o2) {
            File file1 = (File) o1;
            File file2 = (File) o2;

            return file1.getName().compareTo(file2.getName());
        }
    }
}
