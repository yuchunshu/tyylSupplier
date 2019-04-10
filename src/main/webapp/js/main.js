/**
 * Created by Administrator on 15-1-10.
 */
function CCApp() {
    if (typeof CCApp.instance === "object") {
        return CCApp.instance;
    }
    CCApp.instance = this;
    this.initMenu = fnInitNavMenu;// 初始化左边菜单
    this.addTab = fnAddTab;// 添加标签页:opt{title,url,[icon]}
    this.closeTab = fnCloseTab;// 关闭Tab:title
    this.selectTab = fnSelectTab;// 选中Tab：title
    this.switchMenu = fnSwitchMenu;// 切换功能块
    this.init = fnInit;
    this.closeCurTab = fnCloseCurTab;// 关闭当前Tab
    this.refreshTab = fnRefreshTab;// 刷新Tab:title
    this.messager = fnMmessager;// 弹窗消息
    this.getCurTab = fnGetCurTab; // 获取当前页
    this.loading = fnLoading;// 添加遮罩层，参数：[msg]默认'正在努力加载...'
    this.loaded = fnLoaded;// 取消遮罩层
    this.goMenusPage = fnGoMenusPage;// 显示菜单页
    this.goIndex = fnGoIndex;// 显示首页
    this.goWorkPage = fnGoWorkPage;// 显示工作区

    this.indexUrl = "";// 首页url

    this.menus = {};

    this.CONTAIN_GOLDGRID_FLAG = false; // 当前选中页是否含有金格插件
    this.CURRENT_MENUS_URL = ""; // 当前选中的左边栏
    this.MENUS_TITLE = ""; // “导航菜单” tab标题
    var index = "首页";

    var ui = {
        navigator : $("body").layout('panel', 'west'),
        menu : $("#wnav"),
        main : $("#tabs"),
        tabMenu : $("#tabMenu"),
        tabHeader : $(".tabs-inner"),
        menuItems : []
    };


    function fnInit() {
        MENUS_TITLE = "导航菜单";
        _initTabs();
    }


    /*******************************************************************************************************************
     * 初始化导航菜单相关 *
     ******************************************************************************************************************/
    function fnInitNavMenu(menuData) {
        $("#west ul li").on("click", function() {
            var options = eval("(" + $(this).attr("m-options") + ")");
            if (options) {
                CURRENT_MENUS_URL = "desktop/menus?powerId=" + options.id;
                fnAddTab({
                    title : MENUS_TITLE,
                    url : CURRENT_MENUS_URL,
                    index : 1
                })
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

    function fnSwitchMenu(m, tar) {
        var _panels = ui.menu.accordion("panels");
        for (var i = _panels.length; i > 0; i--) {
            ui.menu.accordion("remove", 0);
        }
        $('#css3menu a').removeClass('active');
        $(tar).addClass('active');
        $('.layout-panel-west>.panel-header>.panel-title').html(m);
        _closeAllTab();
        fnInitNavMenu(window.CC.menus[m]);
    }

    function _initMenuItems(items, i) {
        var treeStr = "<ul id=\"nav-menu-easyui-tree-" + i + "\" class=\"easyui-tree\" data-options='data:"
                + JSON.stringify(items) + "'></ul>";
        return treeStr;
    }

    function _MenuItemClick(node) {
        // var _optStr = $(this).attr("cc-option");
        // var _opt = eval("(" + _optStr + ")");
        var _opt = {
            title : node.text,
            url : node.url,
            icon : node.tabIcon ? node.tabIcon : ''
        };
        fnAddTab(_opt);
    }

    /*******************************************************************************************************************
     * Tab页面相关 *
     ******************************************************************************************************************/
    function fnAddTab(opt) {
        // 增加打开tab时的遮罩 by yuandl 20160824 begin
        window._onTabLoad = function() {
            fnLoaded();
        };
        opt.onFrameLoad = '_onTabLoad';
        // end
        var _opt = _mixTabOptions(opt);
        if (ui.main.tabs("tabs").length <= 0) {
            _opt.closable = false
        }
        if (!ui.main.tabs('exists', _opt.title)) {
            fnLoading();// tab打开前增加遮罩 by yuandl 20160824
            ui.main.tabs('add', _opt);
            _initTabRightClick();
        } else {
            var _tab = ui.main.tabs('getTab', _opt.title);
            ui.main.tabs('select', _opt.title);
            fnUpdateTab(_tab, _opt);
        }

        // 自动关闭最早打开的窗口，工作区最多只保留8个窗口
        var _tabcount = ui.main.tabs('tabs').length;// 取tabs数
        if (_tabcount > 8) {
            var menuTabIndex = ui.main.tabs('getTabIndex', ui.main.tabs('getTab', MENUS_TITLE));
            // 不关闭导航菜单页
            for (var closeIndex = 1; closeIndex < _tabcount; closeIndex++) {
                if (menuTabIndex !== closeIndex) {
                    ui.main.tabs('close', closeIndex);
                    break;
                }
            }
        }

    }

    function fnUpdateTab(tab, opt) {
        /** 修改ie浏览器下input无法获得焦点的bug */
        var frame = $('iframe', tab);
        if ($.browser.msie) {
            CollectGarbage();
        }
        if (frame.length > 0) {
            frame[0].contentWindow.document.write('');
            frame[0].contentWindow.close();
            frame.src = "";
            frame.remove();

        }
        /** end */

        var _opt = _mixTabOptions(opt);
        ui.main.tabs('update', {
            tab : tab,
            options : _opt
        });
    }

    function fnCloseTab(title) {
        ui.main.tabs('close', title);
    }

    function fnCloseCurTab() {
        var tab = ui.main.tabs('getSelected');
        var index = ui.main.tabs('getTabIndex', tab);

        ui.main.tabs('close', index);
    }

    function fnGetCurTab() {
        var tab = ui.main.tabs('getSelected');
        return tab;
    }

    function fnSelectTab(title) {
        ui.main.tabs('select', title);
    }

    function _mixTabOptions(opt) {
        if (!opt.icon)
            opt.icon = "icon icon-nav";
        var content = '';
        if (opt.onFrameLoad) {
            content = '<iframe scrolling="auto" onLoad="' + opt.onFrameLoad + '();" frameborder="0"  src="' + opt.url
                    + '" class="cc-frame"></iframe>';
        } else {
            content = '<iframe scrolling="auto" frameborder="0"  src="' + opt.url + '" class="cc-frame"></iframe>';
        }
        $.extend(opt, {
            icon : '',// 去掉tab页 “点” 2016-7-18修改
            content : content,
            closable : true
        });

        return opt;
    }

    function _initTabRightClick() {
        $.extend(ui, {
            tabHeader : $(".tabs-inner")
        });
        ui.tabHeader.on('contextmenu', function(e) {
            ui.tabMenu.menu('show', {
                left : e.pageX,
                top : e.pageY
            });
            var title = $(this).text();
            ui.main.tabs('select', title);
            return false;
        });
    }

    function _initTabRightClickMenu() {
        var tabMenuUI = {
            tabupdate : _get("#mm-tabupdate"),
            tabclose : _get("#mm-tabclose"),
            tabcloseall : _get("#mm-tabcloseall"),
            tabcloseother : _get("#mm-tabcloseother"),
            tabcloseright : _get("#mm-tabcloseright"),
            tabcloseleft : _get("#mm-tabcloseleft"),
            exit : _get("#mm-exit")
        };

        function _get(id) {
            return $(id, ui.tabMenu);
        }

        // 刷新
        tabMenuUI.tabupdate.click(function() {
            var currTab = ui.main.tabs('getSelected');
            fnUpdateTab(currTab, {
                url : $(currTab.panel('options').content).attr('src')
            });
        });
        // 关闭当前
        tabMenuUI.tabclose.click(function() {
            ui.main.tabs('close', _getCurrentId());
        });
        // 全部关闭
        tabMenuUI.tabcloseall.click(function() {
            var _tabs = ui.main.tabs("tabs");
            for (var i = _tabs.length; i > -1; i--) {
                ui.main.tabs("close", i);
            }
        });
        // 关闭除当前之外的TAB
        tabMenuUI.tabcloseother.click(function() {
            var _tabs = ui.main.tabs("tabs");
            var index = _getCurrentId();
            for (var i = _tabs.length; i > -1; i--) {
                if (index < i) {
                    ui.main.tabs("close", i);
                } else if (index > i) {
                    ui.main.tabs("close", i);
                    index--;
                }
                if (i == 0) {
                    ui.main.tabs("select", 1);
                }
            }
        });
        // 关闭当前右侧的TAB
        tabMenuUI.tabcloseright.click(function() {
            return _toCloseTab(function(i) {
                return _getCurrentId() < i;
            });
        });
        // 关闭当前左侧的TAB
        tabMenuUI.tabcloseleft.click(function() {
            return _toCloseTab(function(i) {
                return _getCurrentId() > i;
            });
        });

        // 退出
        tabMenuUI.exit.click(function() {
            ui.tabMenu.menu('hide');
        });

        function _getCurrentId() {
            var tab = ui.main.tabs('getSelected');
            return ui.main.tabs('getTabIndex', tab);
        }

        function _toCloseTab(affirm) {
            var _tabs = ui.main.tabs("tabs");
            for (var i = _tabs.length; i > -1; i--) {
                if (affirm(i)) {
                    ui.main.tabs("close", i);
                }
            }
            return false;
        }
    }

    function _closeAllTab() {
        var _tabs = ui.main.tabs("tabs");
        for (var i = _tabs.length; i > -1; i--) {
            ui.main.tabs("close", i);
        }
    }


    function _initTabs() {
        // 第一页不给关闭
        ui.main.tabs({
            onBeforeClose : function(title, index) {
                if (0 === index)
                    return false;
            },
            onSelect : function(title, index) {
                var containsGoldgrid = false;
                containsGoldgrid = ($($($(this).tabs("getTab", index)).panel("body").find("iframe")).contents().find(
                        "#WebOffice2009").length > 0); // 若选中的TAB页面含有金格插件
                CCApp.instance.CONTAIN_GOLDGRID_FLAG = containsGoldgrid;
                if(index == 0){
                    fnRefreshTab(title);
                }
            }
        });
        _initTabRightClickMenu();
        _initTabRightClick();
    }


    // 刷新TAB
    function fnRefreshTab(title) {
        var tab = ui.main.tabs('getTab', title);
        if (tab) {
            ui.main.tabs('update', {
                tab : tab,
                options : {
                    title : title,
                    href : tab.panel("options").href
                }
            });
        }

    }

    // 弹窗
    function fnMmessager(options) {
        options.timeout = 3000;
        $.extend(options, {
            style : {
                right : '',
                top : document.body.scrollTop + document.documentElement.scrollTop + 100,
                bottom : ''
            }
        });
        $.messager.show(options);
    }

    function fnGoMenusPage() {
        if (CURRENT_MENUS_URL != "") {
            fnAddTab({
                title : MENUS_TITLE,
                url : CURRENT_MENUS_URL,
                index : 1
            });
        }
    }

    function fnGoWorkPage() {
        // ui.main.tabs('select', workArea);
    }

    function fnGoIndex() {
        var url = this.indexUrl;
        var tabs = ui.main;
        if (!tabs.tabs('exists', index)) {
            tabs.tabs('add', {
                title : index,
                content : '<iframe scrolling="auto" frameborder="0"  src="' + url + '" class="cc-frame"></iframe>'
            })
        } else {
            var tab = tabs.tabs('getTab', index);
            tabs.tabs('select', index);
            tabs.tabs('update', {
                tab : tab,
                options : {
                    title : index,
                    content : '<iframe scrolling="auto" frameborder="0" src="' + url + '" class="cc-frame"></iframe>'
                }
            });
        }
    }

    /*******************************************************************************************************************
     * 遮罩层
     ******************************************************************************************************************/
    function fnLoading(message) {
        var msg = message || "正在努力加载···";
        $.easyui.loading({
            msg : msg,
            locale : $('body')
        });
    }

    function fnLoaded() {
        $.easyui.loaded($('body'));
    }
}