/*
 * FileName:    ProcessUtil.java
 * Description:
 * Company:     
 * Copyright:    (c) 2015
 * History:     2015年5月25日 (LLM) 1.0 Create
 */

package cn.com.chaochuang.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author LLM
 *
 */
public abstract class ProcessUtil {

    /**
     * 判断当前操作系统是否为win操作系统
     *
     * @return true表示为window操作系统
     */
    public static boolean isWinOs() {
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name").toLowerCase();
        return os.startsWith("win");
    }

    /**
     * 判断指定进程是否运行
     *
     * @param processName
     *            指定进程名称
     * @return true表示进程运行中
     */
    public static boolean isProcessRun(String processName) {
        if (Tools.isEmptyString(processName)) {
            return false;
        }
        processName = processName.toLowerCase();
        String winCmd = "cmd.exe /C wmic process get name", linuxCmd = "top -b -n 1";
        BufferedReader in = null;
        InputStream is = null;
        Process process = null;
        Runtime rt = Runtime.getRuntime();
        try {
            process = rt.exec(ProcessUtil.isWinOs() ? winCmd : linuxCmd);
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String str = null;
            // 若当前操作系统为window系列则直接进行判断进程
            if (ProcessUtil.isWinOs()) {
                while ((str = in.readLine()) != null) {
                    // 找到指定的进程
                    if (str.toLowerCase().indexOf(processName) != -1) {
                        return true;
                    }
                }
            } else {
                // 若当前操作系统为linux则使用top命令获取正在运行的进程，再对进程进行分解
                List<String> processList = new ArrayList();
                while ((str = in.readLine()) != null) {
                    if (!Tools.isEmptyString(str) && str.indexOf(" R ") == -1) {// 只分析正在运行的进程，top进程本身除外 &&
                        processList.add(str.toLowerCase());
                    }
                }
                boolean startFlag = false;
                int processNamePos = 12;
                // 分析当前的进程列表
                if (Tools.isNotEmptyList(processList)) {
                    for (String line : processList) {
                        if (line.indexOf("command") > 0) {
                            startFlag = true;
                            processNamePos = findLinuxProcess(line, "command");
                        } else if (startFlag && processName.equals(findLinuxdProcess(line, processNamePos))) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            return false;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception ex) {
            }
        }
        return false;
    }

    /**
     * 分解进程字符串，并获取指定位置的字符
     *
     * @param source
     * @param processPos
     * @return
     */
    private static String findLinuxdProcess(String source, int processPos) {
        String result = "";

        String[] items = source.split(" ");
        List<String> process = new ArrayList();
        for (String item : items) {
            if (!Tools.isEmptyString(item)) {
                process.add(item);
            }
        }
        if (processPos >= 0 && processPos < process.size()) {
            return process.get(processPos);
        }
        return result;
    }

    /**
     * 分解进程字符串，获取指定标识所在位置
     *
     * @param source
     * @param processName
     * @return
     */
    private static Integer findLinuxProcess(String source, String processName) {
        Integer result = null;

        String[] items = source.split(" ");
        List<String> process = new ArrayList();
        for (String item : items) {
            if (!Tools.isEmptyString(item)) {
                process.add(item);
            }
        }
        if (!Tools.isEmptyString(processName)) {
            for (int i = 0; i < process.size(); i++) {
                if (processName.toLowerCase().equals(process.get(i))) {
                    return i;
                }
            }
        }
        return result;
    }
}
