$
        .extend(
                $.fn.validatebox.defaults.rules,
                {
                    CHS : {
                        validator : function(value, param) {
                            return /^[\u0391-\uFFE5]+$/.test(value);
                        },
                        message : '请输入汉字'
                    },
                    english : {// 验证英语
                        validator : function(value) {
                            return /^[A-Za-z]+$/i.test(value);
                        },
                        message : '请输入英文'
                    },
                    ip : {// 验证IP地址
                        validator : function(value) {
                            return /\d+\.\d+\.\d+\.\d+/.test(value);
                        },
                        message : 'IP地址格式不正确'
                    },
                    ZIP : {
                        validator : function(value, param) {
                            return /^[0-9]\d{5}$/.test(value);
                        },
                        message : '邮政编码不存在'
                    },
                    QQ : {
                        validator : function(value, param) {
                            return /^[1-9]\d{4,10}$/.test(value);
                        },
                        message : 'QQ号码不正确'
                    },
                    mobile : {
                        validator : function(value, param) {
                            return /^(?:13\d|15\d|18\d)-?\d{5}(\d{3}|\*{3})$/.test(value);
                        },
                        message : '手机号码不正确'
                    },
                    tel : {
                        validator : function(value, param) {
                            return /^(\d{3}-|\d{4}-)?(\d{8}|\d{7})?(-\d{1,6})?$/.test(value);
                        },
                        message : '电话号码不正确'
                    },
                    mobileAndTel : {
                        validator : function(value, param) {
                            return /(^([0\+]\d{2,3})\d{3,4}\-\d{3,8}$)|(^([0\+]\d{2,3})\d{3,4}\d{3,8}$)|(^([0\+]\d{2,3}){0,1}13\d{9}$)|(^\d{3,4}\d{3,8}$)|(^\d{3,4}\-\d{3,8}$)/
                                    .test(value);
                        },
                        message : '请正确输入电话号码'
                    },
                    number : {
                        validator : function(value, param) {
                            return /^[0-9]+.?[0-9]*$/.test(value);
                        },
                        message : '请输入数字'
                    },
                    money : {
                        validator : function(value, param) {
                            return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);
                        },
                        message : '请输入正确的金额'

                    },
                    mone : {
                        validator : function(value, param) {
                            return (/^(([1-9]\d*)|\d)(\.\d{1,2})?$/).test(value);
                        },
                        message : '请输入整数或小数'

                    },
                    integer : {
                        validator : function(value, param) {
                            return /^[+]?[1-9]\d*$/.test(value);
                        },
                        message : '请输入最小为1的整数'
                    },
                    integ : {
                        validator : function(value, param) {
                            return /^[+]?[0-9]\d*$/.test(value);
                        },
                        message : '请输入整数'
                    },
                    range : {
                        validator : function(value, param) {
                            if (/^[1-9]\d*$/.test(value)) {
                                return value >= param[0] && value <= param[1]
                            } else {
                                return false;
                            }
                        },
                        message : '输入的数字在{0}到{1}之间'
                    },
                    minLength : {
                        validator : function(value, param) {
                            return value.length >= param[0]
                        },
                        message : '至少输入{0}个字'
                    },
                    maxLength : {
                        validator : function(value, param) {
                            return value.length <= param[0]
                        },
                        message : '最多{0}个字'
                    },
                    // select即选择框的验证
                    selectValid : {
                        validator : function(value, param) {
                            if (value.trim() == param[0].trim()) {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        message : '请选择'
                    },
                    idCode : {
                        validator : function(value, param) {
                            return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value);
                        },
                        message : '请输入正确的身份证号'
                    },
                    loginName : {
                        validator : function(value, param) {
                            return /^[\u0391-\uFFE5\w]+$/.test(value);
                        },
                        message : '登录名称只允许汉字、英文字母、数字及下划线。'
                    },
                    equalTo : {
                        validator : function(value, param) {
                            return value == $(param[0]).val();
                        },
                        message : '两次输入的字符不一至'
                    },
                    englishOrNum : {// 只能输入英文和数字
                        validator : function(value) {
                            return /^[a-zA-Z0-9_ ]{1,}$/.test(value);
                        },
                        message : '请输入英文、数字、下划线或者空格'
                    },
                    xiaoshu : {
                        validator : function(value) {
                            return /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/.test(value);
                        },
                        message : '最多保留两位小数！'
                    },
                    ddPrice : {
                        validator : function(value, param) {
                            if (/^[1-9]\d*$/.test(value)) {
                                return value >= param[0] && value <= param[1];
                            } else {
                                return false;
                            }
                        },
                        message : '请输入1到100之间正整数'
                    },
                    jretailUpperLimit : {
                        validator : function(value, param) {
                            if (/^[0-9]+([.]{1}[0-9]{1,2})?$/.test(value)) {
                                return parseFloat(value) > parseFloat(param[0])
                                        && parseFloat(value) <= parseFloat(param[1]);
                            } else {
                                return false;
                            }
                        },
                        message : '请输入0到100之间的最多俩位小数的数字'
                    },
                    rateCheck : {
                        validator : function(value, param) {
                            if (/^[0-9]+([.]{1}[0-9]{1,2})?$/.test(value)) {
                                return parseFloat(value) > parseFloat(param[0])
                                        && parseFloat(value) <= parseFloat(param[1]);
                            } else {
                                return false;
                            }
                        },
                        message : '请输入0到1000之间的最多俩位小数的数字'
                    },
                    notEmpty : {
                        validator : function(value, param) {
                            if ($.trim(value) == "") {
                                return false;
                            } else {
                                return true;
                            }
                        },
                        message : '该项为必填项'
                    },
                    equaldDate: {  
                        validator: function (value, param) {  
                            var start = $(param[0]).datetimebox('getValue');
                            if ($.trim(start) == "") {
                                return true;
                            }else{
                            	return value > start;
                            }
                        },  
                        message: '结束时间应大于开始时间!'
                    }
                });

// 重写校验提示位置
$.fn.textbox.defaults.tipPosition = 'top';
$.fn.validatebox.defaults.tipPosition = 'top';
$.fn.combo.defaults.tipPosition = 'top';
$.fn.combotree.defaults.tipPosition = 'top';
$.fn.combogrid.defaults.tipPosition = 'top';
$.fn.combobox.defaults.tipPosition = 'top';
$.fn.datebox.defaults.tipPosition = 'top';
$.fn.datetimebox.defaults.tipPosition = 'top';
$.fn.numberbox.defaults.tipPosition = 'top';
$.fn.datetimespinner.defaults.tipPosition = 'top';
$.fn.calendar.defaults.tipPosition = 'top';
$.fn.spinner.defaults.tipPosition = 'top';
$.fn.numberspinner.defaults.tipPosition = 'top';
$.fn.timespinner.defaults.tipPosition = 'top';

if ($.fn.tooltip) {
    $.fn.tooltip.defaults.onPosition = function() {
        $(this).tooltip('tip').css('left', $(this).offset().left);
        $(this).tooltip('arrow').css('left', 20)
    }
}

$.fn.form.methods.validate = function(jq) {
    if ($.fn.validatebox) {
        var t = $(jq[0]);
        t.find('.validatebox-text:not(:disabled)').validatebox('validate');
        var invalidbox = t.find('.validatebox-invalid');
        invalidbox.filter(':not(:disabled):first').focus().trigger('mouseenter');
        return invalidbox.length == 0;
    }
    return true;
}

$.extend($.fn.layout.methods, {   
        /**  
         * 面板是否存在和可见  
         * @param {Object} jq  
         * @param {Object} params  
         */  
        isVisible: function(jq, params) {   
            var panels = $.data(jq[0], 'layout').panels;   
            var pp = panels[params];   
            if(!pp) {   
                return false;   
            }   
            if(pp.length) {   
                return pp.panel('panel').is(':visible');   
            } else {   
                return false;   
            }   
        },   
        /**  
         * 隐藏除某个region，center除外。  
         * @param {Object} jq  
         * @param {Object} params  
         */  
        hidden: function(jq, params) {   
            return jq.each(function() {   
                var opts = $.data(this, 'layout').options;   
                var panels = $.data(this, 'layout').panels;   
                if(!opts.regionState){   
                    opts.regionState = {};   
                }   
                var region = params;   
                function hide(dom,region,doResize){   
                    var first = region.substring(0,1);   
                    var others = region.substring(1);   
                    var expand = 'expand' + first.toUpperCase() + others;   
                    if(panels[expand]) {   
                        if($(dom).layout('isVisible', expand)) {   
                            opts.regionState[region] = 1;   
                            panels[expand].panel('close');   
                        } else if($(dom).layout('isVisible', region)) {   
                            opts.regionState[region] = 0;   
                            panels[region].panel('close');   
                        }   
                    } else {   
                        panels[region].panel('close');   
                    }   
                    if(doResize){   
                        $(dom).layout('resize');   
                    }   
                };   
                if(region.toLowerCase() == 'all'){   
                    hide(this,'east',false);   
                    hide(this,'north',false);   
                    hide(this,'west',false);   
                    hide(this,'south',true);   
                }else{   
                    hide(this,region,true);   
                }   
            });   
        },   
        /**  
         * 显示某个region，center除外。  
         * @param {Object} jq  
         * @param {Object} params  
         */  
        show: function(jq, params) {   
            return jq.each(function() {   
                var opts = $.data(this, 'layout').options;   
                var panels = $.data(this, 'layout').panels;   
                var region = params;   
      
                function show(dom,region,doResize){   
                    var first = region.substring(0,1);   
                    var others = region.substring(1);   
                    var expand = 'expand' + first.toUpperCase() + others;   
                    if(panels[expand]) {   
                        if(!$(dom).layout('isVisible', expand)) {   
                            if(!$(dom).layout('isVisible', region)) {   
                                if(opts.regionState[region] == 1) {   
                                    panels[expand].panel('open');   
                                } else {   
                                    panels[region].panel('open');   
                                }   
                            }   
                        }   
                    } else {   
                        panels[region].panel('open');   
                    }   
                    if(doResize){   
                        $(dom).layout('resize');   
                    }   
                };   
                if(region.toLowerCase() == 'all'){   
                    show(this,'east',false);   
                    show(this,'north',false);   
                    show(this,'west',false);   
                    show(this,'south',true);   
                }else{   
                    show(this,region,true);   
                }   
            });   
        }   
    });  

	/** 
	 * linkbutton方法扩展 
	 * @param {Object} jq 
	 */  
	$.extend($.fn.linkbutton.methods, {  
	    /** 
	     * 激活选项（覆盖重写） 
	     * @param {Object} jq 
	     */  
	    enable: function(jq){  
	        return jq.each(function(){  
	            var state = $.data(this, 'linkbutton');  
	            if ($(this).hasClass('l-btn-disabled')) {  
	                var itemData = state._eventsStore;  
	                //恢复超链接  
	                if (itemData.href) {  
	                    $(this).attr("href", itemData.href);  
	                }  
	                //回复点击事件  
	                if (itemData.onclicks) {  
	                    for (var j = 0; j < itemData.onclicks.length; j++) {  
	                        $(this).bind('click', itemData.onclicks[j]);  
	                    }  
	                }  
	                //设置target为null，清空存储的事件处理程序  
	                itemData.target = null;  
	                itemData.onclicks = [];  
	                $(this).removeClass('l-btn-disabled');  
	            }  
	        });  
	    },  
	    /** 
	     * 禁用选项（覆盖重写） 
	     * @param {Object} jq 
	     */  
	    disable: function(jq){  
	        return jq.each(function(){  
	            var state = $.data(this, 'linkbutton');  
	            if (!state._eventsStore)  
	                state._eventsStore = {};  
	            if (!$(this).hasClass('l-btn-disabled')) {  
	                var eventsStore = {};  
	                eventsStore.target = this;  
	                eventsStore.onclicks = [];  
	                //处理超链接  
	                var strHref = $(this).attr("href");  
	                if (strHref) {  
	                    eventsStore.href = strHref;  
	                    $(this).attr("href", "javascript:void(0)");  
	                }  
	                //处理直接耦合绑定到onclick属性上的事件  
	                var onclickStr = $(this).attr("onclick");  
	                if (onclickStr && onclickStr != "") {  
	                    eventsStore.onclicks[eventsStore.onclicks.length] = new Function(onclickStr);  
	                    $(this).attr("onclick", "");  
	                }  
	                //处理使用jquery绑定的事件  
	                var eventDatas = $(this).data("events") || $._data(this, 'events');  
	                if (eventDatas["click"]) {  
	                    var eventData = eventDatas["click"];  
	                    for (var i = 0; i < eventData.length; i++) {  
	                        if (eventData[i].namespace != "menu") {  
	                            eventsStore.onclicks[eventsStore.onclicks.length] = eventData[i]["handler"];  
	                            $(this).unbind('click', eventData[i]["handler"]);  
	                            i--;  
	                        }  
	                    }  
	                }  
	                state._eventsStore = eventsStore;  
	                $(this).addClass('l-btn-disabled');  
	            }  
	        });  
	    }  
	}); 