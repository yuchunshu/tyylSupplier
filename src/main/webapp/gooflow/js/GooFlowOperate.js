var myWidth = "99%";//自动宽百分比
var oldJsonData = {};//未修改的json数据

//获取根目录
function getRootPath(){
    //获取当前网址
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    return(localhostPaht+projectName);
}

//从服务器查询流程图json数据
function loadJsonData(){
    var flowId = GetQueryString("flowId");
    var entityId = GetQueryString("id");
    if(top.CC)top.CC.loading();
    $.ajax({
        url:getRootPath() + "/workflow/flow/getFlowJson.json",
        type:"post",
        data:{"flowId":flowId,"entityId":entityId},
        dataType:"json",
        success:function(result){
            if(top.CC)top.CC.loaded();
            if(result["flowJason"]){
                jsondata = JSON.parse(result["flowJason"]);
                jsondata["title"] = result["flowName"];
                oldJsonData = JSON.parse(result["flowJason"]);
            }else{
                jsondata = {"title":""};
                oldJsonData = {"title":""};
            }
            initPage();//初始化并加载页面
            myInit();
            addTitle(result.flowName,flowId);
        }
/*        error:function(){//这段代码目前作本地测试用
            if(top.CC)top.CC.loaded();
//          jsondata = {"title":"没有数据"};
            initPage();
            myInit();
            myMark(GetQueryString("id"),GetQueryString("instStatus"));
        }*/
    });
}


//加个标题
function addTitle(title,id){
    $("#propertyForm .form_title").append("<span id='proTitle'></span>");
    $("#proTitle").html(title+"（"+id+"）").css("width","60%").css("float","right");
    $("#flowId").val(id);
}


//根据可编辑与否加载不同样式
function myInit(){
    //判断是否可编辑，加载不同样式
    if(!property.haveTool){
        uneditableAutoSize();
        myMark(GetQueryString("id"),GetQueryString("instStatus"));//查看状态才标记节点和线
    }else{
        editableAutoSize();
        keyEvent();
        myCloseTab();
    }
    //重写原本的保存按钮
    demo.onBtnSaveClick = saveConfirm;
}

//回车事件
function keyEvent(){
    $("#propertyForm").keydown(function(e){
       if(e.keyCode == "13"){
           setProperty(demo.$focus);
       }
    });
}

//未保存关闭提示
function myCloseTab(){
    var pp = top.$('#tabs').tabs('getSelected');
    var tab = pp.panel('options').tab;
    $(tab).find(".tabs-close").click(function(){
        var newJsonData = JSON.stringify(demo.exportData());
        if(newJsonData != JSON.stringify(oldJsonData)){
            top.$.messager.confirm("提示","修改未保存，确认关闭？",function(b){
                if(b){
                    top.CC.closeCurTab();
                    return true;
                }
            });
            return false;
        }
    });
}

//可编辑状态下自适应宽高
function editableAutoSize(){
    $("html").css("height","100%","margin","0","overflow","hidden");
    $("head").append("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/editable.css\">");
    $("#myResult").css("clear","none");
//    $("#propertyForm").css("width",property.width);
    $("#myResult").hide();
    $("#demo").css("width","calc(100% - 215px)").css("height","100%").css("margin","0");
    $(".GooFlow_work").css("width","calc(100% - 40px").css("height","calc(100% - 25px)");

}


//非编辑状态自动宽高等
function uneditableAutoSize(){
    $(".GooFlow_work").css("width",myWidth).css("min-width",maxLengthNodeOrLine("width")+"px").css("min-height",maxLengthNodeOrLine("height")+"px").css("height","100%");
    $("#demo").css("width","100%").css("margin","0").css("height","100%");
    $(".GooFlow_work_inner").css("width",maxLengthNodeOrLine("width")+"px").css("height",maxLengthNodeOrLine("height")+"px");
    $("head").append("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/uneditable.css\">");
}


//最右边节点（或line）的距离最左边的距离,或最下边距离最上边
function maxLengthNodeOrLine(widthOrHeight){
    var dir,len,more,length,n;
    if(widthOrHeight == "width"){
        dir = "left";
        len = "width";
    }
    if(widthOrHeight == "height"){
        dir = "top";
        len = "height";
    }
    length = 0;
    more = 50;//边界留出多余空间
    //找出距离最远的节点
    for(i in demo.$nodeData){
        n = demo.$nodeData[i][dir] + demo.$nodeData[i][len];
        if(n >= length){
            length = n;
        }
    }
    //找出最远的line
    for(j in demo.$lineData){
        if(dir == "left" && demo.$lineData[j]["type"] == "lr"){
            n = demo.$lineData[j]["M"];
        }
        if(dir == "top" && demo.$lineData[j]["type"] == "tb"){
            n = demo.$lineData[j]["M"];
        }
        if(n >= length){
            length = n;
        }
    }
    return length+more;
}


//确认保存流程图json数据
function saveConfirm(){
    (!top.$.messager.confirm("提示","确认保存流程图",function(b){
        if(!b)return;
        saveJsonData();
    }));
}


//保存流程图json数据
function saveJsonData(){
    var id,flowName,flowJason,flowStatus;
    id = GetQueryString("flowId");
    flowJason = JSON.stringify(demo.exportData());
    flowName = jsondata["title"];
//    if(!confirm("确认保存流程图JSON数据")) return;
    $.ajax({
        type:"post",
        url:getRootPath() + "/workflow/flow/updateFlowJason.json",
        data:{
            "id":id,
            "flowName":flowName,
            "flowJason":flowJason,
            "flowStatus":flowStatus
        },
        dataType:"json",
        success:function(result){
            if(result){
                top.CC.messager({
                    title:'提示',
                    msg:'保存成功！'
                });
                oldJsonData = JSON.parse(flowJason);
            }else{
                top.CC.messager({
                    title:'提示',
                    msg:'保存失败，稍后重试'
                });
            }
        },
        error:function(result){}
    });
}


//标记节点和线
function myMark(entityId,status){
    if(!entityId) return;
    var from = new Array();
    var to = new Array();
    var nodeArr = new Array();
    var nodeIndex = 0;
    var index = 0;
    $.ajax({
        type:"post",
        url:getRootPath() + "/workflow/event/selectNodeInstIds.json",
        data:{"entityId":entityId},
        dataType:"json",
        success:function(result){
            //标记开始节点
            for(x in demo.$nodeData){
                if(demo.$nodeData[x].type == "start round"){
                    nodeArr[nodeIndex++] = x;
                }
            }

            var firstS = result[0].charAt(0);
            //非本流程的节点暂时不要（可能是子流程节点）
            for(i = 0;i<result.length;i++) {
                if (result[i].charAt(0) != firstS) {
                    continue;
                }
                //阅知节点不要
                if(result[i] == "N8888"){
                	continue;
                }
                nodeArr[nodeIndex++] = result[i];
            }

            //添加结束节点
            for(x in demo.$nodeData){
                if(status == 1 && demo.$nodeData[x].type == "end round"){
                    nodeArr[nodeIndex++] = x;
                }
            }

            //循环标记节点
            for(l = 0;l<nodeArr.length;l++){
                demo.markItem(nodeArr[l],"node",true);

                if(l == nodeArr.length - 1){//最后一个节点改变背景颜色
                    $("#" + nodeArr[l]).css("background-color","rgba(255,165,0,0.5)");
                }
                if(l > 0){//把当前节点和前一个连接它的节点之间的线的to、from属性放入数组，之后遍历标记
                    to[index] = nodeArr[l];
                    from[index] = nodeArr[l-1];
                    index++;
                }
            }

            //循环标记线，按流程顺序标记两节点间的线
            for(lineId in demo.$lineData){
                for(j = 0;j<index;j++){
                    if(demo.$lineData[lineId].from == from[j] && demo.$lineData[lineId].to == to[j]){
                        //重建线再标记，不然会有遮挡问题
                        demo.$draw.removeChild(demo.$lineDom[lineId]);
                        demo.addLineDom(lineId,demo.$lineData[lineId]);
                        demo.markItem(lineId,"line",true);
                    }
                }
            }
            lineAndTextCursor();
        }
    });
}


//获取地址栏指定参数
//name:参数名
function GetQueryString(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}


//判断编辑状态,edit==f 为不可编辑(默认为可编辑)
function checkEdit(){
    var edit = GetQueryString("edit");
    if(edit == "f"){
        property.haveGroup = false;
        property.haveHead = false;
        property.haveTool = false;
        $("#propertyForm").hide();
        $("#myResult").hide();
    }
}


//百分比字符转数字
function perToNum(per){
    var num = per.split("%")[0];
    return num/100;
}


//属性设置
function setProperty(id){
    var eleId,name,type,model,left,top,width,height,from,to;
    eleId = $("#ele_id").val();
    name = $("#ele_name").val();
    type = $("#ele_type").val();
    model = $("#ele_model").val();
    left = parseFloat($("#ele_left").val());
    top = parseFloat($("#ele_top").val());
    width = parseFloat($("#ele_width").val());
    height = parseFloat($("#ele_height").val());
    from = $("#ele_from").val();
    to = $("#ele_to").val();

    if(id != eleId)demo.transNewId(id,eleId,model)//改变id
    demo.$focus = eleId;
    if(model == "node"){
        demo.resizeNode(eleId,width,height);//大小
        demo.moveNode(eleId,left,top);//移动
        document.onmousemove=null;
        document.onmouseup=null;
    }
    if(model == "line"){
        var M = 0;
        if(demo.$lineData[eleId]["type"] == "lr")M = left;
        if(demo.$lineData[eleId]["type"] == "tb")M = top;
        demo.moveLinePoints(eleId,from,to);//重连接节点
        demo.setLineM(eleId,M);//重新设置折线重中点位置
    }

    demo.setName(eleId,name,model);//重命名
}


//线和文本的光标改为默认样式(其他的例如节点等，可在css文件修改)
function lineAndTextCursor(){
    for(i in demo.$lineDom){
        $(demo.$lineDom[i]).css("cursor","default");
    }
    $("#demo text").css("cursor","default");
}

//确认一键生成(编辑模式)
function confirmCreate(){
    var width = parseFloat($("#ele_width").val());
    var height = parseFloat($("#ele_height").val());
    if(!width || !height){
        top.$.messager.confirm("提示","节点初始宽高未设置，将使用默认值。确定生成？",function(b){
            if(b){
                createFlow(width,height);
            }else{
                $("#ele_width").focus();
            }
        });
    }else{
        createFlow(width,height);
    }
}

//一键生成流程图(编辑模式)
function createFlow(width,height){
    var flowId = $("#flowId").val();
    var nodeIds = "";
    for(var nodeId in demo.$nodeData){
        nodeIds += nodeId+ ",";
    }
    nodeIds = nodeIds.substr(0,nodeIds.length-1);
    var url = getRootPath() + "/workflow/flow/addFlowAutomated.json";
    $.post(url,{flowId:flowId,nodeIds:nodeIds},function(result){
        createNodeAndLine(result,width,height);
    });
}

//自动创建节点和连线(编辑模式)
function createNodeAndLine(flowList,width,height) {
    var left = 0;
    var top = 0;
    //生成节点
    for(var i = 0;i<flowList.length;i++){
        var json = {};
        json.id = flowList[i].nodeId;
        json.left = left;
        json.top = top;
        if(flowList[i].nodeName == "开始"){
            json.type = "start round";
        }else if(flowList[i].nodeName == "结束"){
            json.type = "end round";
        }else{
            json.type = "chat";
        }
        json.name = flowList[i].nodeName;
        json.width = !width ? 140 : width;
        json.height = !height ? 50 : height;
        //生成的节点左上角排版
        top += json.height + 40;
        if(top >= flowList.length/2*json.height){
            top = 0;
            left += json.width + 40;
        }
        demo.addNode(flowList[i].nodeId,json);
    }
    //连线
    for(var i = 0;i<flowList.length;i++){
        var from = "";
        var tos = "";
        if(flowList[i].nodeId){
            from = flowList[i].nodeId;
        }
        if(flowList[i].nextNodeIds){
            tos = flowList[i].nextNodeIds.split(",")
        }
        //连线下一个节点
        for(var j = 0;j<tos.length;j++){
            demo.addLine(demo.$id+"_line_"+demo.$max,{from:from,to:tos[j],name:""});
            demo.$max++;
        }
        //连线上一个节点
        if(flowList[i].prevNodeIds){
            var prevNodeIds = flowList[i].prevNodeIds.split(",");
            for(var k = 0;k<prevNodeIds.length;k++){
                demo.addLine(demo.$id+"_line_"+demo.$max,{from:prevNodeIds[k],to:flowList[i].nodeId,name:""});
                demo.$max++;
            }
        }
    }
}