$(function(){
    $("[field=nodeName]").click(showFlow);//绑定在办环节字段按钮
});

//点击弹出流程图弹出框
function showFlow(id,instStatus){
	var status = 0;
	if("办结" == instStatus){
		status = 1;
	}
    $('#editWindow').window('open');
//    $('#editWindow').window('open').window('refresh','gooflow/index.html?edit=f&id='+id + '&instStatus=' + status);
//    $('#editWindow').window('refresh','gooflow/index.html?edit=f&id='+id + '&instStatus=' + status);
//    top.CC.addTab({title:'流程图',url:'gooflow/index.html?edit=f&id='+id + '&instStatus=' + status});
    $("#showGooFlow").attr("src","gooflow/index.html?edit=f&id="+id + "&instStatus=" + status).css("border","0");
    addCloseBtn();
}


//在办环节字段添加可以查看流程图的连接
function flowUrl(value,row) {
    var content = "<a href='javascript:;' onclick='showFlow(\""+row.entityId+"\",\""+row.instStatus+"\")' class='font-titlecolor'>" + value + "</a>";

    return content;
}


//添加关闭按钮
function addCloseBtn(){
    $("#editWindow").append("<div></div>");
    $("#editWindow div").load("gooflow/close.html");
    $("#showGooFlow").css("height","calc(100% - 42px)");
}