/**
 * 首頁方法封裝處理
 * Copyright (c) 2019 ruoyi
 */
var isMobile = false;
var sidebarHeight = isMobile ? '100%' : '96%';

$(function() {
    // MetsiMenu
    $('#side-menu').metisMenu();

    // 固定選單欄
    $('.sidebar-collapse').slimScroll({
        height: sidebarHeight,
        railOpacity: 0.9,
        alwaysVisible: false
    });

    // 菜單切換
    $('.navbar-minimalize').click(function() {
    	if (isMobile) {
    	    $("body").toggleClass("canvas-menu");
    	} else {
    	    $("body").toggleClass("mini-navbar");
    	}
        SmoothlyMenu();
    });

    $('#side-menu>li').click(function() {
    	if ($('body').hasClass('canvas-menu mini-navbar')) {
            NavToggle();
        }

    });
    $('#side-menu>li li a:not(:has(span))').click(function() {
        if ($(window).width() < 769) {
            NavToggle();
        }
    });

    $('.nav-close').click(NavToggle);

    //ios瀏覽器相容性處理
    if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
        $('#content-main').css('overflow-y', 'auto');
    }

});

$(window).bind("load resize", function() {
    isMobile = $.common.isMobile() || $(window).width() < 769;
    if (isMobile) {
        $('body').addClass('canvas-menu');
        $("body").removeClass("mini-navbar");
        $("nav .logo").addClass("hide");
        $(".slimScrollDiv").css({ "overflow": "hidden" });
        $('.navbar-static-side').fadeOut();
    } else {
    	if ($('body').hasClass('canvas-menu')) {
    	    $('body').addClass('fixed-sidebar');
    	    $('body').removeClass('canvas-menu');
    	    $("body").removeClass("mini-navbar");
    	    $("nav .logo").removeClass("hide");
    	    $(".slimScrollDiv").css({ "overflow": "visible" });
    	    $('.navbar-static-side').fadeIn();
    	}
    }
});

function openToCurrentTab(obj) {
    if (isScrollToTop) {
        $(obj).show().siblings('.RuoYi_iframe').hide();
    } else {
        $(obj).css({"visibility": "visible", "position": "static"}).siblings('.RuoYi_iframe').css({"visibility": "hidden", "position": "absolute", "left": "0", "top": "0"});
    }
}

function syncMenuTab(dataId) {
    if (isLinkage) {
        var $dataObj = $('a[href$="' + decodeURI(dataId) + '"]');
        if ($dataObj.attr("class") != null && !$dataObj.hasClass("noactive")) {
            $('.tab-pane li').removeClass("active");
            $('.nav ul').removeClass("in");
            $dataObj.parents("ul").addClass("in")
            $dataObj.parents("li").addClass("active").siblings().removeClass("active").find('li').removeClass("active");
            $dataObj.parents("ul").css('height', 'auto').height();
            $(".nav ul li, .nav li").removeClass("selected");
            $dataObj.parent("li").addClass("selected");
            setIframeUrl(dataId);
            
            // 頂部選單同步處理
            var tabStr = $dataObj.parents(".tab-pane").attr("id");
            if ($.common.isNotEmpty(tabStr)) {
                var sepIndex = tabStr.lastIndexOf('_');
                var menuId = tabStr.substring(sepIndex + 1, tabStr.length);
                $("#tab_" + menuId + " a").click();
            }
        }
    }
}

function NavToggle() {
    $('.navbar-minimalize').trigger('click');
}

function fixedSidebar() {
    $('#side-menu').hide();
    $("nav .logo").addClass("hide");
    setTimeout(function() {
        $('#side-menu').fadeIn(500);
    }, 100);
}

// 設置錨點
function setIframeUrl(href) {
	if ($.common.equals("history", mode)) {
	    storage.set('publicPath', href);
	} else {
	    var nowUrl = window.location.href;
	    var newUrl = nowUrl.substring(0, nowUrl.indexOf("#"));
	    window.location.href = newUrl + "#" + href;
	}
}

function SmoothlyMenu() {
    if (isMobile && !$('body').hasClass('canvas-menu')) {
    	$('.navbar-static-side').fadeIn();
    	fixedSidebar();
    } else if (!isMobile &&!$('body').hasClass('mini-navbar')) {
    	fixedSidebar();
    	$("nav .logo").removeClass("hide");
    } else if (isMobile && $('body').hasClass('fixed-sidebar')) {
    	$('.navbar-static-side').fadeOut();
    	fixedSidebar();
    } else if (!isMobile && $('body').hasClass('fixed-sidebar')) {
    	fixedSidebar();
    } else {
        $('#side-menu').removeAttr('style');
    }
}

/**
 * iframe處理
 */
$(function() {
    //計算元素集合的總寬度
    function calSumWidth(elements) {
        var width = 0;
        $(elements).each(function() {
            width += $(this).outerWidth(true);
        });
        return width;
    }

    // 啟用指定選項卡
    function setActiveTab(element) {
        if (!$(element).hasClass('active')) {
            var currentId = $(element).data('id');
            syncMenuTab(currentId);
            // 顯示tab對應的內容區
            $('.RuoYi_iframe').each(function() {
                if ($(this).data('id') == currentId) {
                    openToCurrentTab(this);
                }
            });
            $(element).addClass('active').siblings('.menuTab').removeClass('active');
            scrollToTab(element);
        }
    }

    //滾動到指定選項卡
    function scrollToTab(element) {
        var marginLeftVal = calSumWidth($(element).prevAll()),
        marginRightVal = calSumWidth($(element).nextAll());
        // 可視區域非tab寬度
        var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".menuTabs"));
        //可視區域tab寬度
        var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
        //實際滾動寬度
        var scrollVal = 0;
        if ($(".page-tabs-content").outerWidth() < visibleWidth) {
            scrollVal = 0;
        } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
            if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
                scrollVal = marginLeftVal;
                var tabElement = element;
                while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
                    scrollVal -= $(tabElement).prev().outerWidth();
                    tabElement = $(tabElement).prev();
                }
            }
        } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
            scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
        }
        $('.page-tabs-content').animate({ marginLeft: 0 - scrollVal + 'px' }, "fast");
    }

    //查看左側隱藏的選項卡
    function scrollTabLeft() {
        var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
        // 可視區域非tab寬度
        var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".menuTabs"));
        //可視區域tab寬度
        var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
        //實際滾動寬度
        var scrollVal = 0;
        if (($(".page-tabs-content").width()) < visibleWidth) {
            return false;
        } else {
            var tabElement = $(".menuTab:first");
            var offsetVal = 0;
            while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) { //找到離當前tab最近的元素
                offsetVal += $(tabElement).outerWidth(true);
                tabElement = $(tabElement).next();
            }
            offsetVal = 0;
            if (calSumWidth($(tabElement).prevAll()) > visibleWidth) {
                while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                    offsetVal += $(tabElement).outerWidth(true);
                    tabElement = $(tabElement).prev();
                }
                scrollVal = calSumWidth($(tabElement).prevAll());
            }
        }
        $('.page-tabs-content').animate({ marginLeft: 0 - scrollVal + 'px' }, "fast");
    }

    //查看右側隱藏的選項卡
    function scrollTabRight() {
        var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
        // 可視區域非tab寬度
        var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".menuTabs"));
        //可視區域tab寬度
        var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
        //實際滾動寬度
        var scrollVal = 0;
        if ($(".page-tabs-content").width() < visibleWidth) {
            return false;
        } else {
            var tabElement = $(".menuTab:first");
            var offsetVal = 0;
            while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) { //找到離當前tab最近的元素
                offsetVal += $(tabElement).outerWidth(true);
                tabElement = $(tabElement).next();
            }
            offsetVal = 0;
            while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                offsetVal += $(tabElement).outerWidth(true);
                tabElement = $(tabElement).next();
            }
            scrollVal = calSumWidth($(tabElement).prevAll());
            if (scrollVal > 0) {
                $('.page-tabs-content').animate({ marginLeft: 0 - scrollVal + 'px' }, "fast");
            }
        }
    }

    //透過遍歷給菜單項加上data-index屬性
    $(".menuItem").each(function(index) {
        if (!$(this).attr('data-index')) {
            $(this).attr('data-index', index);
        }
    });

    function menuItem() {
        // 獲取標識數據
        var dataUrl = $(this).attr('href'),
        dataIndex = $(this).data('index'),
        menuName = $(this).data('title') || $.trim($(this).text()),
        isRefresh = $(this).data("refresh"),
        flag = true;

        var $dataObj = $('a[href$="' + decodeURI(dataUrl) + '"]');
        if (!$dataObj.hasClass("noactive")) {
            $('.tab-pane li').removeClass("active");
            $('.nav ul').removeClass("in");
            $dataObj.parents("ul").addClass("in")
            $dataObj.parents("li").addClass("active").siblings().removeClass("active").find('li').removeClass("active");
            $dataObj.parents("ul").css('height', 'auto').height();
            $(".nav ul li, .nav li").removeClass("selected");
            $(this).parent("li").addClass("selected");
        }
        setIframeUrl(dataUrl);
        if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;

        // 選項卡菜單已存在
        $('.menuTab').each(function() {
            if ($(this).data('id') == dataUrl) {
                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').siblings('.menuTab').removeClass('active');
                    scrollToTab(this);
                    // 顯示tab對應的內容區
                    $('.mainContent .RuoYi_iframe').each(function() {
                        if ($(this).data('id') == dataUrl) {
                            openToCurrentTab(this);
                            return false;
                        }
                    });
                }
                if (isRefresh) {
                    refreshTab();
                }
                flag = false;
                return false;
            }
        });
        // 選項卡菜單不存在
        if (flag) {
            var str = '<a href="javascript:;" class="active menuTab" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
            $('.menuTab').removeClass('active');

            // 添加選項卡對應的iframe
            var str1 = '<iframe class="RuoYi_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" data-refresh="' + isRefresh + '" seamless></iframe>';
            if (isScrollToTop) {
                $('.mainContent').find('iframe.RuoYi_iframe').hide();
            } else {
                $('.mainContent').find('iframe.RuoYi_iframe').css({"visibility": "hidden", "position": "absolute", "left": "0", "top": "0"});
            }
            $('.mainContent').append(str1);
            
            $.modal.loading("數據載入中，請稍候...");

            $('.mainContent iframe:visible').on('load', function() {
            	$.modal.closeLoading();
            });

            // 添加選項卡
            $('.menuTabs .page-tabs-content').append(str);
            scrollToTab($('.menuTab.active'));
        }
        return false;
    }

    function menuBlank() {
    	// 新窗口打開外網以http://開頭，如http://ruoyi.vip
    	var dataUrl = $(this).attr('href');
    	window.open(dataUrl);
    	return false;
    }

    $('.menuItem').on('click', menuItem);

    $('.menuBlank').on('click', menuBlank);

    // 關閉選項卡菜單
    function closeTab() {
        var closeTabId = $(this).parents('.menuTab').data('id');
        var currentWidth = $(this).parents('.menuTab').width();
        var panelUrl = $(this).parents('.menuTab').data('panel');
        // 當前元素處於活動狀態
        if ($(this).parents('.menuTab').hasClass('active')) {

            // 當前元素後面有同輩元素，使後面的一個元素處於活動狀態
            if ($(this).parents('.menuTab').next('.menuTab').length) {

                var activeId = $(this).parents('.menuTab').next('.menuTab:eq(0)').data('id');
                $(this).parents('.menuTab').next('.menuTab:eq(0)').addClass('active');

                $('.mainContent .RuoYi_iframe').each(function() {
                    if ($(this).data('id') == activeId) {
                        openToCurrentTab(this);
                        return false;
                    }
                });

                var marginLeftVal = parseInt($('.page-tabs-content').css('margin-left'));
                if (marginLeftVal < 0) {
                    $('.page-tabs-content').animate({ marginLeft: (marginLeftVal + currentWidth) + 'px' }, "fast");
                }

                //  移除當前選項卡
                $(this).parents('.menuTab').remove();

                // 移除tab對應的內容區
                $('.mainContent .RuoYi_iframe').each(function() {
                    if ($(this).data('id') == closeTabId) {
                        $(this).remove();
                        return false;
                    }
                });
            }

            // 當前元素後面沒有同輩元素，使當前元素的上一個元素處於活動狀態
            if ($(this).parents('.menuTab').prev('.menuTab').length) {
                var activeId = $(this).parents('.menuTab').prev('.menuTab:last').data('id');
                $(this).parents('.menuTab').prev('.menuTab:last').addClass('active');
                $('.mainContent .RuoYi_iframe').each(function() {
                    if ($(this).data('id') == activeId) {
                        openToCurrentTab(this);
                        return false;
                    }
                });

                //  移除當前選項卡
                $(this).parents('.menuTab').remove();

                // 移除tab對應的內容區
                $('.mainContent .RuoYi_iframe').each(function() {
                    if ($(this).data('id') == closeTabId) {
                        $(this).remove();
                        return false;
                    }
                });

                if ($.common.isNotEmpty(panelUrl)) {
            		$('.menuTab[data-id="' + panelUrl + '"]').addClass('active').siblings('.menuTab').removeClass('active');
            		$('.mainContent .RuoYi_iframe').each(function() {
                        if ($(this).data('id') == panelUrl) {
                            openToCurrentTab(this);
                            return false;
                        }
                    });
            	}
            }
        }
        // 當前元素不處於活動狀態
        else {
            //  移除當前選項卡
            $(this).parents('.menuTab').remove();

            // 移除相應tab對應的內容區
            $('.mainContent .RuoYi_iframe').each(function() {
                if ($(this).data('id') == closeTabId) {
                    $(this).remove();
                    return false;
                }
            });
        }
        scrollToTab($('.menuTab.active'));
        syncMenuTab($.common.isNotEmpty(panelUrl) ? panelUrl : $('.page-tabs-content').find('.active').attr('data-id'));
        return false;
    }

    $('.menuTabs').on('click', '.menuTab i', closeTab);

    //滾動到已啟用的選項卡
    function showActiveTab() {
        scrollToTab($('.menuTab.active'));
    }
    $('.tabShowActive').on('click', showActiveTab);

    // 點擊選項卡菜單
    function activeTab() {
        if (!$(this).hasClass('active')) {
            var currentId = $(this).data('id');
            var isRefresh = false;
            syncMenuTab(currentId);
            // 顯示tab對應的內容區
            $('.mainContent .RuoYi_iframe').each(function() {
                if ($(this).data('id') == currentId) {
                    openToCurrentTab(this);
                    isRefresh = $.common.nullToDefault($(this).data('refresh'), false);
                    return false;
                }
            });
            $(this).addClass('active').siblings('.menuTab').removeClass('active');
            if (isRefresh) {
                refreshTab();
            }
            scrollToTab(this);
        }
    }

    // 點擊選項卡菜單
    $('.menuTabs').on('click', '.menuTab', activeTab);

    // 刷新iframe
    function refreshTab() {
    	var currentId = $('.page-tabs-content').find('.active').attr('data-id');
    	var target = $('.RuoYi_iframe[data-id="' + currentId + '"]');
        var url = target.attr('src');
    	target.attr('src', url).ready();
    }

    // 頁簽全螢幕
    function fullScreenTab() {
    	var currentId = $('.page-tabs-content').find('.active').attr('data-id');
    	var target = $('.RuoYi_iframe[data-id="' + currentId + '"]');
    	target.fullScreen(true);
    }

    // 關閉當前選項卡
    function tabCloseCurrent() {
    	$('.page-tabs-content').find('.active i').trigger("click");
    }

    //關閉其他選項卡
    function tabCloseOther() {
        $('.page-tabs-content').children("[data-id]").not(":first").not(".active").each(function() {
            $('.RuoYi_iframe[data-id="' + $(this).data('id') + '"]').remove();
            $(this).remove();
        });
        $('.page-tabs-content').animate({ marginLeft: '0px' }, "fast");
    }

    // 關閉全部選項卡
    function tabCloseAll() {
    	$('.page-tabs-content').children("[data-id]").not(":first").each(function() {
            $('.RuoYi_iframe[data-id="' + $(this).data('id') + '"]').remove();
            $(this).remove();
        });
        $('.page-tabs-content').children("[data-id]:first").each(function() {
            if (isScrollToTop) {
                $('.RuoYi_iframe[data-id="' + $(this).data('id') + '"]').show();
            } else {
                $('.RuoYi_iframe[data-id="' + $(this).data('id') + '"]').css({"visibility": "visible", "position": "static"});
            }
            $(this).addClass("active");
        });
        $('.page-tabs-content').css("margin-left", "0");
        syncMenuTab($('.page-tabs-content').find('.active').attr('data-id'));
    }


    // 全螢幕顯示
    $('#fullScreen').on('click', function () {
    	$(document).toggleFullScreen();
    });
    
    // 鎖定螢幕
    $('#lockScreen').on('click', function () {
    	storage.set('lockPath', $('.page-tabs-content').find('.active').attr('data-id'));
    	location.href  = ctx + "lockscreen";
    });

    // 頁簽刷新按鈕
    $('.tabReload').on('click', refreshTab);

    // 頁簽全螢幕按鈕
    $('.tabFullScreen').on('click', fullScreenTab);

    // 雙擊選項卡全螢幕顯示
    $('.menuTabs').on('dblclick', '.menuTab', activeTabMax);

    // 左移按扭
    $('.tabLeft').on('click', scrollTabLeft);

    // 右移按扭
    $('.tabRight').on('click', scrollTabRight);

    // 關閉當前
    $('.tabCloseCurrent').on('click', tabCloseCurrent);

    // 關閉其他
    $('.tabCloseOther').on('click', tabCloseOther);

    // 關閉全部
    $('.tabCloseAll').on('click', tabCloseAll);

    // tab全螢幕顯示
    $('.tabMaxCurrent').on('click', function () {
        $('.page-tabs-content').find('.active').trigger("dblclick");
    });

    // 關閉全螢幕
    $('#ax_close_max').click(function(){
    	$('#content-main').toggleClass('max');
    	$('#ax_close_max').hide();
    })

    // 雙擊選項卡全螢幕顯示
    function activeTabMax() {
        $('#content-main').toggleClass('max');
        $('#ax_close_max').show();
    }

    $(window).keydown(function(event) {
        if (event.keyCode == 27) {
            $('#content-main').removeClass('max');
            $('#ax_close_max').hide();
        }
    });

    window.onhashchange = function() {
        var hash = location.hash;
        var url = hash.substring(1, hash.length);
        $('a[href$="' + url + '"]').click();
    };

    // 右鍵菜單實現
    $.contextMenu({
        selector: ".menuTab",
        trigger: 'right',
        autoHide: true,
        items: {
            "close_current": {
                name: "關閉當前",
                icon: "fa-close",
                callback: function(key, opt) {
                    opt.$trigger.find('i').trigger("click");
                }
            },
            "close_other": {
                name: "關閉其他",
                icon: "fa-window-close-o",
                callback: function(key, opt) {
                    setActiveTab(this);
                    tabCloseOther();
                }
            },
            "close_left": {
                name: "關閉左側",
                icon: "fa-reply",
                callback: function(key, opt) {
                    setActiveTab(this);
                    this.prevAll('.menuTab').not(":last").each(function() {
                        if ($(this).hasClass('active')) {
                            setActiveTab(this);
                        }
                        $('.RuoYi_iframe[data-id="' + $(this).data('id') + '"]').remove();
                        $(this).remove();
                    });
                    $('.page-tabs-content').animate({ marginLeft: '0px' }, "fast");
                }
            },
            "close_right": {
                name: "關閉右側",
                icon: "fa-share",
                callback: function(key, opt) {
                    setActiveTab(this);
                    this.nextAll('.menuTab').each(function() {
                        $('.RuoYi_iframe[data-id="' + $(this).data('id') + '"]').remove();
                        $(this).remove();
                    });
                }
            },
            "close_all": {
                name: "全部關閉",
                icon: "fa-window-close",
                callback: function(key, opt) {
                    tabCloseAll();
                }
            },
            "step": "---------",
            "full": {
                name: "全螢幕顯示",
                icon: "fa-arrows-alt",
                callback: function(key, opt) {
                    setActiveTab(this);
                    var target = $('.RuoYi_iframe[data-id="' + this.data('id') + '"]');
                    target.fullScreen(true);
                }
            },
            "refresh": {
                name: "刷新頁面",
                icon: "fa-refresh",
                callback: function(key, opt) {
                    setActiveTab(this);
                    var target = $('.RuoYi_iframe[data-id="' + this.data('id') + '"]');
                    var url = target.attr('src');
                    $.modal.loading("數據載入中，請稍候...");
                    target.attr('src', url).on('load', function() {
                    	$.modal.closeLoading();
                    });
                }
            },
            "open": {
                name: "新窗口打開",
                icon: "fa-link",
                callback: function(key, opt) {
                    var target = $('.RuoYi_iframe[data-id="' + this.data('id') + '"]');
                    window.open(target.attr('src'));
                }
            },
        }
    });
});
