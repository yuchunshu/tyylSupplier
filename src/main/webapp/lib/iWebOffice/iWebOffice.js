

	//显示控件，弹出框取消时触发
	function showWebOffice(obj){
		if($("#" + obj).length>0){//如果控件存在，才隐藏控件
    		$("#" + obj).attr("width","100%");
    	}
	}
	
	//隐藏控件，弹出框打开时触发
	function hideWebOffice(obj){
		if($("#" + obj).length>0){//如果控件存在，才隐藏控件
    		$("#" + obj).attr("width",0);
    	}
	}