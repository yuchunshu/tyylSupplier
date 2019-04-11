/*
 * FileName:    OfficeServer.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年3月15日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.goldgrid.service;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

/**
 * @author LJX
 *
 */
@Service
@Transactional
public class OfficeServer {

    private iMsgServer2015 MsgObj = new iMsgServer2015();
    String                 mOption;
    String                 mUserName;
    String                 mFileName;

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (request.getMethod().equalsIgnoreCase("POST")) {// 判断请求方式
                MsgObj.setSendType("JSON");
                MsgObj.Load(request); // 解析请求
                mOption = MsgObj.GetMsgByName("OPTION");// 请求参数
                mUserName = MsgObj.GetMsgByName("USERNAME"); // 取得系统用户
                System.out.println(mOption);
                if (mOption.equalsIgnoreCase("LOADFILE")) {
                    mFileName = MsgObj.GetMsgByName("FILENAME");// 取得文档名称
                    MsgObj.MsgTextClear();// 清除文本信息
                    if (MsgObj.MsgFileLoad(mFileName)) {
                        System.out.println("文档已经加载");
                    }
                } else if (mOption.equalsIgnoreCase("SAVEFILE")) {
                    System.out.println("文档上传中");
                    mFileName = MsgObj.GetMsgByName("FILENAME");// 取得文档名称
                    MsgObj.MsgTextClear();// 清除文本信息
                    if (MsgObj.MsgFileSave(mFileName)) {
                        System.out.println("文档已经保存成功");
                    }
                } else if (mOption.equalsIgnoreCase("SAVEPDF")) {
                    System.out.println("文档转PDF");
                    mFileName = MsgObj.GetMsgByName("FILENAME");// 取得文档名称
                    MsgObj.MsgTextClear();// 清除文本信息
                    if (MsgObj.MsgFileSave(mFileName)) {
                        System.out.println("文档已经转换成功");
                    }

                }
                System.out.println("SendPackage");
                MsgObj.Send(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
