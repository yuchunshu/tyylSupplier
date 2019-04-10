package cn.com.chaochuang.doc.remotedocswap.schedule;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.com.chaochuang.doc.remotedocswap.DocSwapTools;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapLog;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteDocSwapLogStatus;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteDocSwapLogRepository;
import cn.com.chaochuang.doc.remotedocswap.service.RemoteDocSwapEnvelopeService;
@Component
@PropertySource("WEB-INF/global.spring.properties")
public class ReceiveRemoteDocSwapSchedule{

    public static String       			 SUCCESS_FILE_NAME = "success.txt";
    public static final String 			 FAILURE_FILE_NAME = "failure.txt";

    @Autowired
    private RemoteDocSwapLogRepository 	 remoteDocSwapLogRepository;

    @Autowired
    private RemoteDocSwapEnvelopeService envelopeService;

    /** 自治区公文交换 */
    @Value(value = "${remotedocswap.envelopeReceiveFiles}")
    private String 			   			 receiveEnvelopePath;

    public String getReceiveEnvelopePath() {
		return receiveEnvelopePath;
	}

//	@Scheduled(cron = "${receiveRemoteDocSwapSchedule.cron}")//每隔2分钟执行一次
	
    void processSchedule() {
        //System.out.println("----------定时扫描公文交换启动------------");

        String folderName = "";
        try {
            File[] fileList = DocSwapTools.getFolderFilesSort(this.receiveEnvelopePath, null, DocSwapTools.SORTBY_LASTMOD);
            //每次最多处理10个文件
            int max = 10;
            if (fileList != null) {
                int i = 0;
                for (File folder : fileList) {
                    boolean unRead = true;
                    if (!folder.isFile()) {
                        //只处理文件夹。
                        folderName = folder.getAbsolutePath();
                        File[] files = folder.listFiles();
                        for (File file : files) {
                            if (SUCCESS_FILE_NAME.equals(file.getName()) || FAILURE_FILE_NAME.equals(file.getName())) {
                                unRead = false;
                            }
                        }
                        if (unRead) {
                            //等5秒后再读取，防止有些文件未传输完成导致读取不到。
                            Thread.sleep(5000);
                            //修改附件文件名（不包括xml文件及子文件夹）
//                            this.runShell(folder.getPath());
                            envelopeService.saveReceiveRemoteDocSwap(folder);
                            i++;
                        }
                    }
                    if (i >= max) {
                        break;
                    }
                }
                // TODO 暂时不用
//                if(i==0){
//                    //没有新文件时检查关闸和内网程序是否正常
//                    if(!this.statisticService.checkTransferProgram()){
//                        Calendar calendar = Calendar.getInstance();
//                        if(calendar.get(Calendar.HOUR_OF_DAY)>9&&calendar.get(Calendar.HOUR_OF_DAY)<18) {
//                            this.scheduleTaskService.sendMsg("请检查光闸或内网程序是否正常。");
//                        }
//                    }
//                }
            }
        } catch (Exception ex) {
            StringWriter stringWriter= new StringWriter();
            PrintWriter printWriter= new PrintWriter(stringWriter);
            ex.printStackTrace(printWriter);
            StringBuffer errorMsg= stringWriter.getBuffer();
            RemoteDocSwapLog docSwapLog = new RemoteDocSwapLog();
            docSwapLog.setLogTime(new Date());
            docSwapLog.setLog("文件夹名称："+folderName);
            String subMsg = errorMsg.substring(0,errorMsg.length()>900?900:errorMsg.length());
            docSwapLog.setMemo(subMsg);
            //添加读取记录
            docSwapLog.setStatus(RemoteDocSwapLogStatus.失败);
            this.remoteDocSwapLogRepository.save(docSwapLog);
        }

    }


    /**
     * 运行shell（只在linux系统上运行）
     脚本语句
     cd /opt/upload/envelope/envelopereceivetemp/2016-12-30-{F318B17F-43F1-4BF7-8CA2-F2B39E46A24E}
     for file in `ls`
     do
     if test -f $file;
     then
        if test "${file##*.}" != "xml";
        then
           if test "${file##*.}" != "class";
           then
              mv $file  `uuidgen`"."${file##*.}
           fi
        fi
     fi
     done
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public void runShell(String filePath) throws Exception {
        //获取系统名称
        String osName = System.getProperties().getProperty("os.name").toLowerCase();
        //只在linux系统上运行
        if(osName.contains("linux")){
            String shStr = "cd "+filePath+"\n" +
                    "for file in `ls`\n" +
                    "do\n" +
                    " if test -f $file;\n" +
                    " then\n" +
                    "  if test \"${file##*.}\" != \"xml\";\n" +
                    "  then\n" +
                    "  if test \"${file##*.}\" != \"class\";\n" +
                    "  then\n" +
                    "  mv $file `uuidgen`\".\"${file##*.}\n" +
                    "  fi\n" +
                    "  fi\n" +
                    " fi\n" +
                    "done";
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",shStr},null,null);
            process.waitFor();
        }
    }
}
