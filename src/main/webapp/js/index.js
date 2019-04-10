function CCApp() {
    if (typeof CCApp.instance === "object") {
        return CCApp.instance;
    }
    CCApp.instance = this;
    this.CURRENT_MENUS_TITLE = "";
    //this.init = fnInit;
    this.indexUrl = "";
    this.addTab = fnAddTab;// 添加标签页:opt{url}
    this.closeTab = fnCloseTab;// 不做处理
    this.closeCurTab = fnCloseCurTab;// 显示菜单页
    this.goMenusPage = fnGoMenusPage;// 显示菜单页
    this.goIndex = fnGoIndex;// 显示首页
    this.goWorkPage = fnGoWorkPage;//显示工作区
    this.messager = fnMmessager;// 弹窗消息

    this.CURRENT_MENUS_TITLE="";

    var tabs = $("#tabs");
    var index = "首页";
    var workArea = "工作区域";

    function fnGoWorkPage(){
        tabs.tabs('select', workArea);
    }

    function fnMmessager(opt){
        top.CC.messager(options);
    }

    /**
    function fnInit() {
        $("#west .module ul li").on("click", function() {
            var options = eval("(" + $(this).attr("m-options") + ")");
            if (options) {
                $(this).parent().find("li").removeClass("default");
                $(this).addClass("default");
                CURRENT_MENUS_TITLE = options.title;
                if (!tabs.tabs('exists', options.title)) {
                    tabs.tabs('add', {
                        title : options.title,
                        href : 'desktop/menus?powerId=' + options.id
                    })
                    tabs.tabs('select', options.title);
                } else {
                    tabs.tabs('select', options.title);
                }
            }
        });

        $(".indexMenus .nnrd-main ul li").on("click", function() {

            var options = eval("(" + $(this).attr("m-options") + ")");

            if (options) {
                var src = options.url ? options.url.substring(1) : options.url;
                fnAddTab({
                    url : src
                });

            }

        });

    }
    */

    function fnAddTab(opt) {
        if (opt.url) {
            if (!tabs.tabs('exists', workArea)) {
                tabs.tabs('add', {
                    title : workArea,
                    content : '<iframe scrolling="auto" frameborder="0"  src="' + opt.url
                            + '" class="cc-frame"></iframe>'
                })
            } else {
                var tab = tabs.tabs('getTab', workArea);
                tabs.tabs('select',workArea);
                tabs.tabs('update', {
                    tab : tab,
                    options : {
                        title :workArea,
                        content : '<iframe scrolling="auto" frameborder="0" src="' + opt.url
                                + '" class="cc-frame"></iframe>'
                    }
                });
            }
        }
    }

    function fnGoIndex() {
        var url = this.indexUrl;
        if (!tabs.tabs('exists', index)) {
            tabs.tabs('add', {
                title : index,
                content : '<iframe scrolling="auto" frameborder="0"  src="' + url
                        + '" class="cc-frame"></iframe>'
            })
        } else {
            var tab = tabs.tabs('getTab', index);
            tabs.tabs('select',index);
            tabs.tabs('update', {
                tab : tab,
                options : {
                    title :index,
                    content : '<iframe scrolling="auto" frameborder="0" src="' + url
                            + '" class="cc-frame"></iframe>'
                }
            });
        }
    }


    function fnCloseTab(opt) {

    }

    function fnGoMenusPage() {
        tabs.tabs('select', CURRENT_MENUS_TITLE);
    }

    function fnCloseCurTab() {
        fnGoMenusPage();
    }

}