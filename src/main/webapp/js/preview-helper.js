/**
 * 预览文件（使用金格和pdf.js控件）
 * @param ggId 金格控件id
 * @param iframeId 显示pdf的iframe id
 * @param editable 是否只读
 * @constructor
 */
function FilePreview(ggId,iframeId,editable) {

    //debug 日志调试
    var debug = true;

    function log(info, name) {
        if (debug) {
            console.log("-------" + (name || '') + "-------");
            console.log(info);
        }
    }

    //金格本地临时文件路径
    var _ggLocalRoot = "C:\\temp\\";
    this.getLocalRoot = function () {
        return _ggLocalRoot;
    };

    //预览的附件信息,以附件id为key,{附件id:{__localPath:本地存储路径,__serverPath:服务器上的存储路径,__fileType:附件类型}}
    this.attachListMap={};
    //当前预览的附件id
    this.previewFileId='';

    /**
     * 读取当前浏览器的版本和类型
     */
    function browserInfo() {
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
    }

    /**
     * 判断当前浏览器是否是IE浏览器
     *
     * @returns true为IE false为非IE浏览器
     */
    function isIEBrowser() {
        return browserInfo().browser.toLowerCase() == "ie";
    }

    //金格插件
    var webOffice = document.getElementById(ggId);
    if (webOffice&&isIEBrowser()) {
        var mkResult = webOffice.WebMkDirectory(_ggLocalRoot);
        log(mkResult, "创建本地文件");
        //不显示的按钮
        webOffice.VisibleTools("新建文件",false);
        webOffice.VisibleTools("打开文件",false);
        webOffice.VisibleTools("保存文件",false);
        webOffice.VisibleTools("文字批注",false);
        webOffice.VisibleTools("手写批注",false);
        webOffice.VisibleTools("文档清稿",false);
        webOffice.VisibleTools("重新批注",false);
        if (editable=="true") {
            var tool = webOffice.AppendTools("5", "保存文件", "4");
            log(tool, "工具栏");
        }
    }
    this.getWebOffice = function () {
        return webOffice;
    };

    //pdf.js
    var pdfIframe = document.getElementById(iframeId);

    /**
     * 检查插件是否已加载
     */
    this.checkUsable = function () {
        if (!webOffice.CopyRight) {
            $.messager.show({
                title: '提示',
                msg: '当前浏览器不支持浏览word文件。如需查看，请使用IE浏览器（支持IE8，IE10，IE11.0版本）'
            });
            return false;
        }
        return true;
    };

    /**
     * 金格打开本地文件
     * @returns {boolean}
     */
    this.ggWebOpenLocalFile = function (localFilePath,attachId) {
        this.loading();
        try {
            if (this.checkUsable()) {
                if (this.previewFileId==attachId){
                    log("文件已经打开");
                    return false;
                }
                if (localFilePath) {
                    var r = webOffice.WebOpenLocalFile(localFilePath);
                    this.previewFileId=attachId;
                    //当前预览的文件
                    var attachInfo = this.attachListMap[attachId];
                    if (attachInfo){
                        attachInfo.__localPath=localFilePath;
                    }
                    log("打开文件路径：" + r);
                    log(attachInfo,"附件信息");
                    return r;
                }
            }
        } catch (e) {
            log(e);
            return false;
        } finally {
            this.loadingEnd();
        }
        return false;
    };

    /**
     * 金格保存当前文件到服务器
     */
    this.ggWebPutCurrFile = function () {
        this.loading();
        try {
            if (this.checkUsable()) {
                if (!this.previewFileId) {
                    log("当前预览的文件id为空");
                    return false;
                }
                if (editable!="true") {
                    log("文件只读");
                    return false;
                }
                var attachInfo = this.attachListMap[this.previewFileId];
                if (!attachInfo) {
                    log("当前预览的文件为空");
                    return false;
                }
                log(attachInfo,"文件信息");
                if (webOffice.WebSaveLocalFile(attachInfo.__localPath)) {
                    var _r = webOffice.WebPutFile(attachInfo.__localPath, attachInfo.__serverPath);
                    log("文件上传：" + _r);
                    return _r;
                }
            }
        } catch (e) {
            log(e);
            return false;
        } finally {
            this.loadingEnd();
        }
        return false;
    };

    /**
     * 金格插件是否能打开文件
     * @param fileName
     */
    this.ggCanOpen = function (fileName) {
        var r = /(doc|docx|xls|xlsx|xlsm|ppt|pps|ppsx|pptx|ppsm|pptm|wps)$/.test(fileName);
        log(fileName+":"+r,"金格支持的类型");
        return r&&this.checkUsable();
    };

    /**
     * 金格插件预览文件，从服务器下载文件到本地
     * @param serverFilePath
     * @param attachId
     */
    this.ggDownloadAndOpenFile = function (serverFilePath,attachId) {
        this.loading();
        try {
            if (this.checkUsable()) {
                if (this.previewFileId==attachId){
                    log("文件已经打开");
                    return false;
                }
                if (!serverFilePath) {
                    log("文件路径为空");
                    return false;
                }
                var _temp = getTempFileName(getFileSuffix(serverFilePath));
                log(_temp, "临时文件路径");
                log(serverFilePath, "服务器文件路径");
                var result = webOffice.WebGetFile(_temp, serverFilePath);
                log(result, "文件获取");
                if (result) {
                    this.ggWebOpenLocalFile(_temp,attachId);
                    var attachInfo = this.attachListMap[attachId];
                    if (attachInfo){
                        attachInfo.__serverPath=serverFilePath;
                    }
                }
                return result;
            }
        } catch (e) {
            log(e);
            return false;
        } finally {
            this.loadingEnd()
        }
        return false;
    };

    /**
     * 删除所有预览的本地文件
     */
    this.ggDeleteLocalFile = function () {
        for (var keyId in this.attachListMap){
            var attachInfo = this.attachListMap[keyId];
            if (attachInfo&&attachInfo.__localPath){
                webOffice.WebDelFile(attachInfo.__localPath,"");
                log(attachInfo.__localPath,"删除本地文件");
            }
        }
    };

    /**
     * 预览pdf文件
     * @param attachId
     */
    this.previewPdf = function (attachId) {
        this.loading();
        try{
            if (attachId){
                if (attachId === this.previewFileId){
                    log("当前文件已经打开");
                    return false;
                }
                var pdfDownloadUri = pdfIframe.getAttribute("download-url");
                var iframeSrc = pdfIframe.getAttribute("temp-src")+encodeURIComponent(pdfDownloadUri+"?id="+attachId);
                log(iframeSrc,"iframe src");
                pdfIframe.setAttribute("src",iframeSrc);
                this.previewFileId= attachId;
            }
        }catch (e){
            log(e);
        }finally {
            this.loadingEnd()
        }
    };

    /**
     * 金格插件判断文件是否存在
     * @param filePath
     * @returns {*}
     */
    this.ggWebFileExists = function (filePath) {
        if (this.checkUsable()) {
            var r = webOffice.WebFileExists(filePath);
            log(filePath + ":" + r, "文件是否存在");
            return r;
        }
    };

    this.isLoading=false;

    this.loading = function () {
        if (!this.isLoading) {
            this.isLoading= true;
            $("body").append('<div style="position:absolute;left:0;top:0;margin:0 auto;width:100%;height:100%;;filter:alpha(opacity=80);z-index: 9999;" class="loading-cc-t"><div style="position:fixed;left:45%;top:48%;width:60px;height:70px;padding:12px 5px 10px 30px;opacity:0.8;filter:alpha(opacity=80);background:#fff url(' + loadingImg + ') no-repeat scroll 5px 10px;border:2px solid #95B8E7;color:#000;"></div></div>');
        }
    };

    this.loadingEnd = function () {
        this.isLoading=false;
        var _l = $(".loading-cc-t");
        if (_l){
            _l.each(function () {
                $(this).remove();
            });
        }
    };

    //获取临时文件名
    function getTempFileName(fileType) {
        if (fileType) {
            return _ggLocalRoot + new Date().getTime() + fileType;// WebOffice.FileType;
        }
        return _ggLocalRoot + new Date().getTime() + ".pgf"; // 包含批注的类型
    }

    //获取文件后缀
    function getFileSuffix(fileName) {
        if (fileName) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return '';
    }

    this.getFilePathInfo = function (filePath) {
        var _fn='';
        var _fp='';
        if (filePath){
            _fn = filePath.substring(filePath.lastIndexOf("/")+1);
            _fp = filePath.replace("/"+_fn,"");
        }
        log(_fp+":"+_fn,"");
        return {
            fileName:_fn,
            fileAbsPath:_fp
        };
    };

    //加载图片

    //region 加载图片
    var loadingImg = "data:image/gif;base64,R0lGODlhVQBKAPf/ANDP0LWytP7+/u3t7cC+v8bFxrq4uZaUls3MzczIy9TT1Ozs7O/u78jGyJiWl5eVl/Du77q2ucrJyt7a3eno6ammqby4u66rrbCtr+7t7tbU1eLe4bCusNbS1czLzKGeoMjEx+7q7ePi4p2anaWipJyZm9LQ0ebi5ayoq7i0t8LAwbi2uJORk5qZmvf39/Dv8NHQ0be2t6uqq6KhosnIyaqoqrm4ucHAwfz8/Lq5uoyJi+jn6NjY2Kemp9bV1oyKjLOxs4yKi56cnvT09NnY2bKwsqOho93c3dPS09XU1a+ur/Lx8q+tro2Ljfn5+be1tsC/wI2KjLu5u/Ly8tnX2PX09fr6+q6trvX19by7vL27vODf4ODg4L28vY6LjcPCwsPCw+vn6tfW1/v7+9zb3K+tr8bFxfb29tfV1sC+wOfm593c3O/r7t/e38PBw/j4+I6Mjvf29/Hw8by6vOjk59LR0uLh4quoqvv6+9va2/Py86ekpre1t+Tk5N3b3aupq/Dw8JqYmqKgotrZ2qKfoaelp768vvb19ezo6+Hg4fPz88PBwvTz9LOxsr27vcTCxOPf4pqXmbW0tdPR0sXExb28vN7d3vn4+LOys/Hw8JuZm6upqvPy8ra1tuTj5NjX2KKgoeDc3/Du8La0tuXh5La0tdXV1dfW1s/Nz9jU1/n4+c/Oz6alpo+MjsTCw7Kxsqilp97d3erp6rKvsaCcn9/d3srIyaajpeXk5ZuYmvHx8dfT1uvr69PP0tza3N/e3tnZ2djX1/Tz87u4utnV2MfGx7y6u/b19o+Mj7q3udvZ28XDxc7MzqSho8G/webl5fHv8ZuYm+Tg4+Hd4L26vOfj5v39/dzb27CtsJGPkb+9vpCOj+jn59/b3d3Z3LSys/r5+b+9v+Hg4NXU1N/c3t/c38TDxKShpM/MzszJy+Lh4ebl5quoq4+Nj6akpfv6+r25vL+7vr67vdTR0+zr6726vZWSlKOgotHN0NrW2bKwsb++v9rX2qakpouJi////yH/C05FVFNDQVBFMi4wAwEAAAAh/wtYTVAgRGF0YVhNUDw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDpBQkJGNjdBODBBRTJFNzExOTE1OUMwMjE5MTk1NjMyRiIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDo2MDhCMzYwOUUyMEUxMUU3QTM3REYyN0E4NDhBRkI2RCIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo2MDhCMzYwOEUyMEUxMUU3QTM3REYyN0E4NDhBRkI2RCIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ1M2IChXaW5kb3dzKSI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOkIxQkY2N0E4MEFFMkU3MTE5MTU5QzAyMTkxOTU2MzJGIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOkFCQkY2N0E4MEFFMkU3MTE5MTU5QzAyMTkxOTU2MzJGIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+Af/+/fz7+vn49/b19PPy8fDv7u3s6+rp6Ofm5eTj4uHg397d3Nva2djX1tXU09LR0M/OzczLysnIx8bFxMPCwcC/vr28u7q5uLe2tbSzsrGwr66trKuqqainpqWko6KhoJ+enZybmpmYl5aVlJOSkZCPjo2Mi4qJiIeGhYSDgoGAf359fHt6eXh3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"
    //endregion
}