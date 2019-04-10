/**
 * Created by LLM on 14-7-25.
 */

(function() {
    window.goldgrid = {};
    /** 本地临时文件路径 */
    var T_Root = "C:\\Windows\\temp\\";
    /** 远程服务器临时文件目录 */
    var T_SERVER_PATH = "/opt/upload/temp/";
    /** 已打开的本地文件 */
    var _openedLocalFileName = "";

    var EditType = {
        "只读" : "0",
        "编辑无留痕" : "1",
        "编辑痕迹无修订" : "2",
        "编辑痕迹修订" : "3"
    };
    var CommentType = {
        "无批注" : "0",
        "可批注" : "1"
    };
    var ShowType = {
        "核稿" : 0,
        "文字批注" : 1,
        "手写批注" : 2
    };
    var NOTE_ELEMENTS = new Array();
    NOTE_ELEMENTS["title"] = "";
    NOTE_ELEMENTS["receiveDate"] = "";
    NOTE_ELEMENTS["sendUnit"] = "";
    NOTE_ELEMENTS["sncode"] = "";
    NOTE_ELEMENTS["opinions1"] = "";
    NOTE_ELEMENTS["opinions2"] = "";
    NOTE_ELEMENTS["opinions3"] = "";
    NOTE_ELEMENTS["opinions4"] = "";
    NOTE_ELEMENTS["docCode"] = "";
    NOTE_ELEMENTS["dense"] = "";
    NOTE_ELEMENTS["printNum"] = "";
    NOTE_ELEMENTS["remark"] = "";
    NOTE_ELEMENTS["typist"] = "";
    NOTE_ELEMENTS["reviewer1"] = "";
    NOTE_ELEMENTS["reviewer2"] = "";
    NOTE_ELEMENTS["mainSend"] = "";
    NOTE_ELEMENTS["reportSend"] = "";
    NOTE_ELEMENTS["copySend"] = "";
    NOTE_ELEMENTS["createInfo"] = "";

    /**
     * 读取当前浏览器的版本和类型
     */
    goldgrid.uaMatch = function() {
        var userAgent = navigator.userAgent;
        var rMsie = /(msie\s|trident.*rv:)([\w.]+)/, rFirefox = /(firefox)\/([\w.]+)/, rOpera = /(opera).+version\/([\w.]+)/, rChrome = /(chrome)\/([\w.]+)/, rSafari = /version\/([\w.]+).*(safari)/;
        var ua = userAgent.toLowerCase();
        var match = rMsie.exec(ua);
        if (match != null) {
            return {
                browser : "IE",
                version : match[2] || "0"
            };
        }
        var match = rFirefox.exec(ua);
        if (match != null) {
            return {
                browser : match[1] || "",
                version : match[2] || "0"
            };
        }
        var match = rOpera.exec(ua);
        if (match != null) {
            return {
                browser : match[1] || "",
                version : match[2] || "0"
            };
        }
        var match = rChrome.exec(ua);
        if (match != null) {
            return {
                browser : match[1] || "",
                version : match[2] || "0"
            };
        }
        var match = rSafari.exec(ua);
        if (match != null) {
            return {
                browser : match[1] || "",
                version : match[2] || "0"
            };
        }
        if (match != null) {
            return {
                browser : "",
                version : "0"
            };
        }
    };
    /**
     * 判断当前浏览器是否是IE浏览器
     *
     * @returns true为IE false为非IE浏览器
     */
    goldgrid.isIEBrowser = function() {
        var browserInfo = window.goldgrid.uaMatch();
        if (browserInfo.browser.toLowerCase() == "ie") {
            return true;
        }
        return false;
    };

    /**
     * 只读文档初始化方式
     *
     * ggId：插件ID contextUri：应用系统访问路径
     */
    goldgrid.readOnlyInit = function(ggId, contextUri) {
        window.goldgrid.init(ggId, contextUri, "", "doc", EditType.只读, CommentType.无批注, ShowType.文字批注);
    };
    /**
     * 一般编辑初始化方式
     *
     * ggId：插件ID
     * contextUri：应用系统访问路径
     */
    goldgrid.defaultInit = function(ggId, contextUri) {
        window.goldgrid.init(ggId, contextUri, "", "doc", EditType.编辑无留痕, CommentType.可批注, ShowType.文字批注);
    };
    /**
     * 一般编辑初始化方式
     *
     * ggId：插件ID contextUri：应用系统访问路径
     */
    goldgrid.NormalInit = function(ggId, contextUri, userName, editType, commentType, showType) {
        window.goldgrid.init(ggId, contextUri, userName, "doc",
            (editType == "" ? EditType.编辑无留痕 : editType),
            (commentType == "" ? CommentType.可批注 : commentType),
            (showType == "" ? ShowType.文字批注 : showType));
    };

    /**
     * 领导编辑初始化方式
     *
     * ggId：插件ID contextUri：应用系统访问路径
     */
    goldgrid.LeaderInit = function(ggId, contextUri) {
        window.goldgrid.init(ggId, contextUri, "", "doc", EditType.编辑痕迹修订, CommentType.可批注, ShowType.文字批注);
    };

    /**
     * 完整初始化方式
     *
     * ggId：插件ID contextUri：应用系统访问路径 userName：操作用户名，痕迹保留需要 fileType：文档类型 .doc .xls .wps editType：编辑类型 commentType：批注类型
     * showType：文档显示方式
     */
    goldgrid.init = function(ggId, contextUri, userName, fileType, editType, commentType, showType) {
        var WebOffice = document.getElementById(ggId);
        if (WebOffice == undefined || WebOffice == null) {
            return "无效的公文插件";
        }
        // 先关闭指定编号插件
        window.goldgrid.UnLoad(ggId);
        try {
            WebOffice.WebUrl = contextUri + "/OfficeServer.jsp"; // WebUrl:系统服务器路径，与服务器文件交互操作，如保存、打开文档，重要文件
            WebOffice.RecordID = ""; // RecordID:本文档记录编号
            WebOffice.Template = ""; // Template:模板编号
            WebOffice.FileName = ""; // FileName:文档名称
            WebOffice.FileType = "." + fileType; // FileType:文档类型 .doc .xls .wps
            WebOffice.UserName = userName; // UserName:操作用户名，痕迹保留需要
            WebOffice.EditType = editType + "," + commentType; // EditType:编辑类型 方式一、方式二 <参考技术文档>
            // 第一位可以为0,1,2,3 其中:0不可编辑;1可以编辑,无痕迹;2可以编辑,有痕迹,不能修订;3可以编辑,有痕迹,能修订；
            // 第二位可以为0,1 其中:0不可批注,1可以批注。可以参考iWebOffice2009的EditType属性，详细参考技术白皮书
            WebOffice.MaxFileSize = 4 * 1024; // 最大的文档大小控制，默认是8M，现在设置成4M。
            WebOffice.Language = "CH"; // Language:多语言支持显示选择 CH 简体 TW繁体 EN英文
            WebOffice.PenColor = "#FF0000"; // PenColor:默认批注颜色
            WebOffice.PenWidth = "1"; // PenWidth:默认批注笔宽
            WebOffice.Print = "1"; // Print:默认是否可以打印:1可以打印批注,0不可以打印批注
            WebOffice.ShowToolBar = "1"; // ShowToolBar:是否显示工具栏:1显示,0不显示

            // 以下为自定义工具栏按钮↓ 参数一:Index按钮编号,参数二:Caption按钮显示内容,参数三:Icon图标名称
            // WebOffice.AppendTools("18", "打开正文", 21);
            // 以上为自定义工具栏按钮↑
            WebOffice.ShowMenu = "0"; // 控制整体菜单显示:1显示,0不显示
            WebOffice.ShowWindow = "0";// 保存打开文档时是否显示进度条:1显示,0不显示
            // 展示进度条的位置：屏幕居中
            WebOffice.ProgressXY(0, 0);
            WebOffice.DisableMenu("宏(&M);选项(&O)..."); // 禁止某个（些）菜单项
            // WebSetRibbonUIXML(); //控制OFFICE2007的选项卡显示
            // WebOffice.WebOpen(); // 打开该文档 交互OfficeServer 调出文档OPTION="LOADFILE" 调出模板OPTION="LOADTEMPLATE" <参考技术文档>
            WebOffice.ShowType = showType; // 文档显示方式 1:表示文字批注 2:表示手写批注 0:表示文档核稿
            return "true";
        } catch (e) {
            return "插件初始化错误！";
        }
    };

    /**
     * 关闭插件 ggId：插件ID
     */
    goldgrid.UnLoad = function(ggId) {
        var WebOffice = document.getElementById(ggId);
        try {
            if (!WebOffice.WebClose()) {
                return false;
            }
            return true;
        } catch (e) {
            return false;
        }
    };

    /**
     * 获取临时文件名 ggId：插件ID
     */
    goldgrid.GetTempFile = function(ggId, fileType) {
        var WebOffice = document.getElementById(ggId);
        if (fileType) {
            return T_Root + new Date().getTime() + fileType;// WebOffice.FileType;
        }
        return T_Root + new Date().getTime() + ".pgf"; // 包含批注的类型
    };
    /**
     * 获取服务器临时文件名 ggId：插件ID
     */
    goldgrid.GetServerTempFile = function(ggId) {
        var WebOffice = document.getElementById(ggId);
        return T_SERVER_PATH + new Date().getTime() + WebOffice.FileType;
    };

    /**
     * 下载服务器文件（serverFileName）至客户端系统目录（C盘）下
     *
     * ggId：插件ID serverFileName：服务器端文件,为绝对路径名
     *
     */
    goldgrid.WebGetFile = function(ggId, serverFileName) {
        var result = window.goldgrid.CheckUsable(ggId);
        if (!result) {
            return result;
        }

        if (serverFileName == "") {
            return "无效文件名";
        }
        var WebOffice = document.getElementById(ggId);
        // 创建临时文件名
        var localFileName = goldgrid.GetTempFile(ggId), mResult = true;
        try {
            // 交互OfficeServer的OPTION="GETFILE" 参数1表示本地路径 参数2表示服务器文件名称
            mResult = WebOffice.WebGetFile(localFileName, serverFileName);
            // 若文件已经下载到本地 则立即打开
            if (mResult) {
                WebOffice.WebOpenLocalFile(localFileName);
                _openedLocalFileName = localFileName; // 记住文件名，以便后面删除 by yuandl 20161215
            } else {
                //return "文件不存在或文件已损坏！";
            }
        } catch (e) {
            return "打开文件出错！" + e;
        }
        return "true";
    };

    /**
     * 将本地临时文件上传至服务器指定目录下
     *
     * ggId：插件ID serverFileName：服务器端文件,为绝对路径名 closeFlag:是否关闭插件
     *
     */
    goldgrid.WebPutFile = function(ggId, serverFileName, closeFlag) {
        var result = window.goldgrid.CheckUsable(ggId);
        if (!result) {
            return result;
        }

        var WebOffice = document.getElementById(ggId);
        // 创建临时文件名
        var localFileName = goldgrid.GetTempFile(ggId);
        var localDocFileName = goldgrid.GetTempFile(ggId, ".doc");// 存两份，解决发送部门函件时打不开正文的情况
        // 先保存本地文件
        WebOffice.WebSaveLocalFile(localFileName);
        WebOffice.WebSaveLocalFile(localDocFileName);
        // 删除原来打开的本地文件 by yuandl 20161215
        if (_openedLocalFileName != "") {
            WebOffice.WebDelFile(_openedLocalFileName,"");
            _openedLocalFileName = "";
        }
        // 提交文件保存至服务器 交互OfficeServer的OPTION="PUTFILE"
        WebOffice.WebPutFile(localDocFileName, serverFileName);
        WebOffice.WebPutFile(localFileName, serverFileName + ".pgf");
        // 提交后删除本地文件 by yuandl 20161130
        WebOffice.WebDelFile(localDocFileName,"");
        WebOffice.WebDelFile(localFileName,"");

        if (closeFlag) {
            window.goldgrid.UnLoad(ggId);
        }
    };

    /**
     * 将本地临时文件上传至服务器指定目录下 只存doc
     *
     * ggId：插件ID serverFileName：服务器端文件,为绝对路径名 closeFlag:是否关闭插件
     *
     */
    goldgrid.WebPutDocFile = function(ggId, serverFileName, closeFlag) {
        var result = window.goldgrid.CheckUsable(ggId);
        if (!result) {
            return result;
        }

        var WebOffice = document.getElementById(ggId);
        // 创建临时文件名
        var localFileName = goldgrid.GetTempFile(ggId, ".doc");
        // 先保存本地文件
        WebOffice.WebSaveLocalFile(localFileName);
        // 删除原来打开的本地文件 by yuandl 20161215
        if (_openedLocalFileName != "") {
            WebOffice.WebDelFile(_openedLocalFileName,"");
            _openedLocalFileName = "";
        }
        // 提交文件保存至服务器 交互OfficeServer的OPTION="PUTFILE"
        WebOffice.WebPutFile(localFileName, serverFileName);
        // 提交后删除本地文件 by yuandl 20161130
        WebOffice.WebDelFile(localFileName,"");
        if (closeFlag) {
            window.goldgrid.UnLoad(ggId);
        }
    };

    /**
     * 套红头文件 tempFileName:模板文件（绝对路径）
     */
    goldgrid.WebUseTemplate = function(ggId, tempFileName, nodeData) {
        var result = window.goldgrid.CheckUsable(ggId);
        if (!result) {
            return result;
        }

        var WebOffice = document.getElementById(ggId);
        if (!window.goldgrid.WebAcceptAllRevisions(ggId)) { // 清除正文痕迹的目的是为了避免痕迹状态下出现内容异常问题。
            return false;
        }
        // 创建临时文件名
        var localFileName = goldgrid.GetTempFile(ggId), serverFileName = window.goldgrid.GetServerTempFile(ggId);
        // 先保存本地文件
        WebOffice.WebSaveLocalFile(localFileName);
        // 再将文件存至服务器指定目录
        window.goldgrid.WebPutFile(ggId, serverFileName, false);
        // 设置套红头的参数
        WebOffice.WebSetMsgByName("COMMAND", "INSERTFILE"); // 设置变量COMMAND="INSERTFILE"，在WebLoadTemplate()时，一起提交到OfficeServer中
        WebOffice.Template = tempFileName; // 全局变量Template赋值，此示例读取服务器目录中模板，如读取数据库中模板，Template值为数据库中的模板编号，则上句代码不需要，如Template="1050560363767"，模板名称为“Word公文模板”，注：模板中有要标签Content，区分大小写，可以自行修改
        WebOffice.EditType = "1"; // 控制为不保留痕迹的状态
        if (WebOffice.WebLoadTemplate()) { // 交互OfficeServer的OPTION="LOADTEMPLATE"
            window.goldgrid.WebPutFile(ggId);

            WebOffice.WebSetMsgByName("SOURCEFILE", serverFileName);
            WebOffice.WebSetMsgByName("FILETYPE", WebOffice.FileType);

            for(key in nodeData) {
            	window.goldgrid.SetBookmarks(ggId, key, nodeData[key]);
            }
        	//window.goldgrid.SetBookmarks(ggId, "title", nodeData["title"]);
        	//window.goldgrid.SetBookmarks(ggId, "sncode", nodeData["sncode"]);

            if (WebOffice.WebInsertFile()) { // 填充公文正文 交互OfficeServer的OPTION="INSERTFILE"
                return true;
            }
            return false;
        }
        return false;
    };
    /**
     * 合并所有留痕
     *
     * @param ggId
     * @returns {Boolean}
     */
    goldgrid.WebAcceptAllRevisions = function(ggId) {
        var WebOffice = document.getElementById(ggId);
        try {
            WebOffice.WebObject.Application.ActiveDocument.AcceptAllRevisions();
            var mCount = WebOffice.WebObject.Application.ActiveDocument.Revisions.Count;
            if (mCount > 0) {
                return false;
            }
        } catch (e) {
            return true;
        }
        return true;
    };

    /**
     * 设置书签值 vbmName:标签名称，vbmValue:标签值 标签名称注意大小写
     *
     * @param ggId
     *            插件ID
     * @param vbmName
     *            标签名称
     * @param vbmValue
     *            标签值
     */
    goldgrid.SetBookmarks = function(ggId, vbmName, vbmValue) {
        var WebOffice = document.getElementById(ggId);
        WebOffice.WebSetBookmarks(vbmName, vbmValue);
    };

    /**
     * 获取书签值 vbmName:标签名称
     *
     * @param ggId
     *            插件ID
     * @param vbmName
     *            标签名称
     */
    goldgrid.GetBookmarks = function(ggId, vbmName) {
        var WebOffice = document.getElementById(ggId), vbmValue;
        vbmValue = WebOffice.WebGetBookmarks(vbmName);
        return vbmValue;
    };

    /**
     * 替换公文标签内容
     *
     * @param ggId
     *            插件ID
     * @param serverFileName
     *            正文文件名
     *
     * @param tempFileName
     *            模板文件名
     * @param noteDatas
     *            标签值
     */
    goldgrid.ReplaceBookmarks = function(ggId, serverFileName, tempFileName, noteDatas) {
        var WebOffice = document.getElementById(ggId);
        // 若serverFileName不为空则先保存本地文件至服务器
        if (serverFileName != null && serverFileName != "") {
            window.goldgrid.WebPutFile(ggId, serverFileName, false);
        }
        try {
            // 从服务器获取模板文件
            WebOffice.WebSetMsgByName("COMMAND", "INSERTFILE"); // 设置变量COMMAND="INSERTFILE"，在WebLoadTemplate()时，一起提交到OfficeServer中
            WebOffice.Template = tempFileName; // 全局变量Template赋值，此示例读取服务器目录中模板，如读取数据库中模板，Template值为数据库中的模板编号，则上句代码不需要，如Template="1050560363767"，模板名称为“Word公文模板”，注：模板中有要标签Content，区分大小写，可以自行修改
            WebOffice.EditType = "1"; // 控制为不保留痕迹的状态
            if (WebOffice.WebLoadTemplate()) { // 交互OfficeServer的OPTION="LOADTEMPLATE"
                // 遍历noteDatas，从noteDatas中获取公文笺中的标签进行替换
                for ( var p in NOTE_ELEMENTS) {
                    if (noteDatas[p] != undefined) {
                        window.goldgrid.SetBookmarks(ggId, p, noteDatas[p]);
                    } else {
                        window.goldgrid.SetBookmarks(ggId, p, "");
                    }
                }
            }
            return "true";
        } catch (e) {
            return e.message;
        }
    };
    /**
     * 公文笺转PDF内容
     *
     * @param ggId
     *            插件ID
     * @param serverFileName
     *            正文文件名
     *
     * @param tempFileName
     *            模板文件名
     * @param noteDatas
     *            标签值
     */
    goldgrid.WordToPDF = function(ggId, pdfFileName) {
        var WebOffice = document.getElementById(ggId);
        WebOffice.WebSetMsgByName("PDFFILENAME", pdfFileName);
        WebOffice.WebSavePDF();
    };
    goldgrid.WebOpenPrint = function (ggId) {
        var result = window.goldgrid.CheckUsable(ggId);
        if (!result) {
            return result;
        }
        var WebOffice = document.getElementById(ggId);
        WebOffice.WebOpenPrint();
    };
    /**
     * 检查插件是否已加载
     */
    goldgrid.CheckUsable = function (ggId) {
        var WebOffice = document.getElementById(ggId);
        if (!WebOffice.CopyRight) {
            alert("当前浏览器不支持公文插件，无法打开和保存公文正文。如需打开和保存公文正文，请使用IE浏览器（支持IE8.0，IE10.0，IE11.0版本）进行操作。");
            return false;
        }
        return true;
    };
})(window);
