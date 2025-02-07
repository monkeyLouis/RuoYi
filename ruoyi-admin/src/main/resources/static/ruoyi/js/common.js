/**
 * 通用方法封裝處理
 * Copyright (c) 2019 ruoyi 
 */

var startLayDate;
var endLayDate;
var isScrollToTop = parent.isScrollToTop;

$(function() {
	
    // 回到頂部綁定
    if ($.fn.toTop !== undefined) {
        $('#scroll-up').toTop();
    }
	
    // select2複選框事件綁定
    if ($.fn.select2 !== undefined) {
        $.fn.select2.defaults.set( "theme", "bootstrap" );
        $("select.form-control:not(.noselect2)").each(function () {
            $(this).select2().on("change", function () {
                $(this).valid();
            })
        })
    }
	
    // iCheck單選框及複選框事件綁定
    if ($.fn.iCheck !== undefined) {
        $(".check-box:not(.noicheck),.radio-box:not(.noicheck)").each(function() {
            $(this).iCheck({
                checkboxClass: 'icheckbox-blue',
                radioClass: 'iradio-blue',
            })
        })
    }
	
    // 取消回車自動提交表單
    $(document).on("keypress", ":input:not(textarea):not([type=submit])", function(event) {
        if (event.keyCode == 13) {
            event.preventDefault();
        }
    });
	 
    // laydate 時間控制項綁定
    if ($(".select-time").length > 0 && $('#startTime').length > 0 && $('#endTime').length > 0) {
       layui.use('laydate', function() {
            var laydate = layui.laydate;
            startLayDate = laydate.render({
                elem: '#startTime',
                max: $('#endTime').val(),
                theme: 'molv',
                type: $('#startTime').attr("data-type") || 'date',
                trigger: 'click',
                done: function(value, date) {
                    // 結束時間大於開始時間
                    if (value !== '') {
                        endLayDate.config.min.year = date.year;
                        endLayDate.config.min.month = date.month - 1;
                        endLayDate.config.min.date = date.date;
                    } else {
                        endLayDate.config.min.year = '';
                        endLayDate.config.min.month = '';
                        endLayDate.config.min.date = '';
                    }
                    $('#endTime').trigger('click');
                }
            });
            endLayDate = laydate.render({
                elem: '#endTime',
                min: $('#startTime').val(),
                theme: 'molv',
                type: $('#endTime').attr("data-type") || 'date',
                trigger: 'click',
                done: function(value, date) {
                    // 開始時間小於結束時間
                    if (value !== '') {
                        startLayDate.config.max.year = date.year;
                        startLayDate.config.max.month = date.month - 1;
                        startLayDate.config.max.date = date.date;
                    } else {
                        startLayDate.config.max.year = '2099';
                        startLayDate.config.max.month = '12';
                        startLayDate.config.max.date = '31';
                    }
                }
            });
        });
    }
	
    // laydate time-input 時間控制項綁定
    if ($(".time-input").length > 0) {
        layui.use('laydate', function () {
            var com = layui.laydate;
            $(".time-input").each(function (index, item) {
                var time = $(item);
                // 控制控制項外觀
                var type = time.attr("data-type") || 'date';
                // 控制回顯格式
                var format = time.attr("data-format") || 'yyyy-MM-dd';
                // 控制日期控制項按鈕
                var buttons = time.attr("data-btn") || 'clear|now|confirm', newBtnArr = [];
                // 日期控制項選擇完成後回調處理
                var callback = time.attr("data-callback") || {};
                if (buttons) {
                    if (buttons.indexOf("|") > 0) {
                        var btnArr = buttons.split("|"), btnLen = btnArr.length;
                        for (var j = 0; j < btnLen; j++) {
                            if ("clear" === btnArr[j] || "now" === btnArr[j] || "confirm" === btnArr[j]) {
                                newBtnArr.push(btnArr[j]);
                            }
                        }
                    } else {
                        if ("clear" === buttons || "now" === buttons || "confirm" === buttons) {
                            newBtnArr.push(buttons);
                        }
                    }
                } else {
                    newBtnArr = ['clear', 'now', 'confirm'];
                }
                com.render({
                    elem: item,
                    theme: 'molv',
                    trigger: 'click',
                    type: type,
                    format: format,
                    btns: newBtnArr,
                    done: function (value, data) {
                        if (typeof window[callback] != 'undefined'
                            && window[callback] instanceof Function) {
                            window[callback](value, data);
                        }
                    }
                });
            });
        });
    }
	
    // tree 關鍵字搜索綁定
    if ($("#keyword").length > 0) {
        $("#keyword").bind("focus", function focusKey(e) {
            if ($("#keyword").hasClass("empty")) {
                $("#keyword").removeClass("empty");
            }
        }).bind("blur", function blurKey(e) {
            if ($("#keyword").val() === "") {
                $("#keyword").addClass("empty");
            }
            $.tree.searchNode(e);
        }).bind("input propertychange", $.tree.searchNode);
    }
	
    // tree表格樹 展開/摺疊
    var expandFlag;
    $("#expandAllBtn").click(function() {
        var dataExpand = $.common.isEmpty(table.options.expandAll) ? true : table.options.expandAll;
        expandFlag = $.common.isEmpty(expandFlag) ? dataExpand : expandFlag;
        if (!expandFlag) {
            $.bttTable.bootstrapTreeTable('expandAll');
        } else {
            $.bttTable.bootstrapTreeTable('collapseAll');
        }
        expandFlag = expandFlag ? false: true;
    })
	
    // 按下ESC按鈕關閉彈層
    $('body', document).on('keyup', function(e) {
        if (e.which === 27) {
            $.modal.closeAll();
        }
    });
});

(function ($) {
    'use strict';
    $.fn.toTop = function(opt) {
        var elem = this;
        var win = (opt && opt.hasOwnProperty('win')) ? opt.win : $(window);
        var doc = (opt && opt.hasOwnProperty('doc')) ? opt.doc : $('html, body');
        var options = $.extend({
            autohide: true,
            offset: 50,
            speed: 500,
            position: true,
            right: 15,
            bottom: 5
        }, opt);
        elem.css({
            'cursor': 'pointer'
        });
        if (options.autohide) {
            elem.css('display', 'none');
        }
        if (options.position) {
            elem.css({
                'position': 'fixed',
                'right': options.right,
                'bottom': options.bottom,
            });
        }
        elem.click(function() {
            doc.animate({
                scrollTop: 0
            }, options.speed);
        });
        win.scroll(function() {
            var scrolling = win.scrollTop();
            if (options.autohide) {
                if (scrolling > options.offset) {
                    elem.fadeIn(options.speed);
                } else elem.fadeOut(options.speed);
            }
        });
    };
})(jQuery);

/** 刷新選項卡 */
var refreshItem = function(){
    var topWindow = $(window.parent.document);
    var currentId = $('.page-tabs-content', topWindow).find('.active').attr('data-id');
    var target = $('.RuoYi_iframe[data-id="' + currentId + '"]', topWindow);
    var url = target.attr('src');
    target.attr('src', url).ready();
}

/** 關閉選項卡 */
var closeItem = function(dataId){
	var topWindow = $(window.parent.document);
	if ($.common.isNotEmpty(dataId)) {
	    window.parent.$.modal.closeLoading();
	    // 根據dataId關閉指定選項卡
	    $('.menuTab[data-id="' + dataId + '"]', topWindow).remove();
	    // 移除相應tab對應的內容區
	    $('.mainContent .RuoYi_iframe[data-id="' + dataId + '"]', topWindow).remove();
	    return;
	}
	var panelUrl = window.frameElement.getAttribute('data-panel');
	$('.page-tabs-content .active i', topWindow).click();
	if ($.common.isNotEmpty(panelUrl)) {
	    $('.menuTab[data-id="' + panelUrl + '"]', topWindow).addClass('active').siblings('.menuTab').removeClass('active');
	    $('.mainContent .RuoYi_iframe', topWindow).each(function() {
	        if ($(this).data('id') == panelUrl) {
	            openToCurrentTab(this);
	            return false;
            }
        });
    }
}

/** 創建選項卡 */
function createMenuItem(dataUrl, menuName, isRefresh) {
    var panelUrl = window.frameElement.getAttribute('data-id'),
    dataIndex = $.common.random(1, 100),
    flag = true;
    if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;
    var topWindow = $(window.parent.document);
    // 選項卡菜單已存在
    $('.menuTab', topWindow).each(function() {
        if ($(this).data('id') == dataUrl) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.menuTab').removeClass('active');
                scrollToTab(this);
                $('.page-tabs-content').animate({ marginLeft: ""}, "fast");
                // 顯示tab對應的內容區
                $('.mainContent .RuoYi_iframe', topWindow).each(function() {
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
        var str = '<a href="javascript:;" class="active menuTab noactive" data-id="' + dataUrl + '" data-panel="' + panelUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
        $('.menuTab', topWindow).removeClass('active');

        // 添加選項卡對應的iframe
        var str1 = '<iframe class="RuoYi_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" data-panel="' + panelUrl + '" seamless></iframe>';
        if (isScrollToTop) {
            $('.mainContent', topWindow).find('iframe.RuoYi_iframe').hide();
        } else {
            $('.mainContent', topWindow).find('iframe.RuoYi_iframe').css({"visibility": "hidden", "position": "absolute", "left": "0", "top": "0"});
        }
        $('.mainContent', topWindow).append(str1);
        
        window.parent.$.modal.loading("數據載入中，請稍候...");
        $('.mainContent iframe:visible', topWindow).on('load', function() {
            window.parent.$.modal.closeLoading();
        });

        // 添加選項卡
        $('.menuTabs .page-tabs-content', topWindow).append(str);
        scrollToTab($('.menuTab.active', topWindow));
    }
    return false;
}

// 刷新iframe
function refreshTab() {
	var topWindow = $(window.parent.document);
	var currentId = $('.page-tabs-content', topWindow).find('.active').attr('data-id');
	var target = $('.RuoYi_iframe[data-id="' + currentId + '"]', topWindow);
    var url = target.attr('src');
	target.attr('src', url).ready();
}

// 滾動到指定選項卡
function scrollToTab(element) {
    var topWindow = $(window.parent.document);
    var marginLeftVal = calSumWidth($(element).prevAll()),
    marginRightVal = calSumWidth($(element).nextAll());
    // 可視區域非tab寬度
    var tabOuterWidth = calSumWidth($(".content-tabs", topWindow).children().not(".menuTabs"));
    //可視區域tab寬度
    var visibleWidth = $(".content-tabs", topWindow).outerWidth(true) - tabOuterWidth;
    //實際滾動寬度
    var scrollVal = 0;
    if ($(".page-tabs-content", topWindow).outerWidth() < visibleWidth) {
        scrollVal = 0;
    } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
        if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
            scrollVal = marginLeftVal;
            var tabElement = element;
            while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content", topWindow).outerWidth() - visibleWidth)) {
                scrollVal -= $(tabElement).prev().outerWidth();
                tabElement = $(tabElement).prev();
            }
        }
    } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
        scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
    }
    $('.page-tabs-content', topWindow).animate({ marginLeft: 0 - scrollVal + 'px' }, "fast");
}

// 計算元素集合的總寬度
function calSumWidth(elements) {
    var width = 0;
    $(elements).each(function() {
        width += $(this).outerWidth(true);
    });
    return width;
}

// 返回當前啟用的Tab頁面關聯的iframe的Windows對象
function activeWindow() {
	var topWindow = $(window.parent.document);
	var currentId = $('.page-tabs-content', topWindow).find('.active').attr('data-id');
	if (!currentId) {
		return window.parent;
	}
    return $('.RuoYi_iframe[data-id="' + currentId + '"]', topWindow)[0].contentWindow;
}

function openToCurrentTab(obj) {
    if (isScrollToTop) {
        $(obj).show().siblings('.RuoYi_iframe').hide();
    } else {
        $(obj).css({"visibility": "visible", "position": "static"}).siblings('.RuoYi_iframe').css({"visibility": "hidden", "position": "absolute", "left": "0", "top": "0"});
    }
}

/** 密碼規則範圍驗證 */
function checkpwd(chrtype, password) {
    if (chrtype == 1) {
        if (!$.common.numValid(password)) {
            $.modal.alertWarning("密碼只能為0-9數字");
            return false;
        }
    } else if (chrtype == 2) {
        if (!$.common.enValid(password)) {
            $.modal.alertWarning("密碼只能為a-z和A-Z字母");
            return false;
        }
    } else if (chrtype == 3) {
        if (!$.common.enNumValid(password)) {
            $.modal.alertWarning("密碼必須包含字母以及數字");
            return false;
        }
    } else if (chrtype == 4) {
        if (!$.common.charValid(password)) {
            $.modal.alertWarning("密碼必須包含字母、數字、以及特殊符號<font color='red'>~!@#$%^&*()-=_+</font>");
            return false;
        }
    }
    return true;
}

/** 開始時間/時分秒 */
function beginOfTime(date) {
    if ($.common.isNotEmpty(date)) {
        return $.common.sprintf("%s 00:00:00", date);
    }
}

/** 結束時間/時分秒 */
function endOfTime(date) {
    if ($.common.isNotEmpty(date)) {
        return $.common.sprintf("%s 23:59:59", date);
    }
}

/** 重設日期/年月日 */
function resetDate() {
	if ($.common.isNotEmpty(startLayDate) && $.common.isNotEmpty(endLayDate)) {
	    endLayDate.config.min.year = '';
	    endLayDate.config.min.month = '';
	    endLayDate.config.min.date = '';
	    startLayDate.config.max.year = '2099';
	    startLayDate.config.max.month = '12';
	    startLayDate.config.max.date = '31';
	}
}

// 日誌列印封裝處理
var log = {
    log: function(msg) {
        console.log(msg);
    },
    info: function(msg) {
        console.info(msg);
    },
    warn: function(msg) {
        console.warn(msg);
    },
    error: function(msg) {
        console.error(msg);
    }
};

// 本地快取處理
var storage = {
    set: function(key, value) {
        window.localStorage.setItem(key, value);
    },
    get: function(key) {
        return window.localStorage.getItem(key);
    },
    remove: function(key) {
        window.localStorage.removeItem(key);
    },
    clear: function() {
        window.localStorage.clear();
    }
};

// 主子表操作封裝處理
var sub = {
    editRow: function() {
    	var dataColumns = [];
		for (var columnIndex = 0; columnIndex < table.options.columns.length; columnIndex++) {
    		if (table.options.columns[columnIndex].visible != false) {
    			dataColumns.push(table.options.columns[columnIndex]);
    		}
    	}
		var params = new Array();
		var data = $("#" + table.options.id).bootstrapTable('getData');
    	var count = data.length;
    	for (var dataIndex = 0; dataIndex < count; dataIndex++) {
    	    var columns = $('#' + table.options.id + ' tr[data-index="' + dataIndex + '"] td:visible');
    	    var obj = new Object();
    	    for (var i = 0; i < columns.length; i++) {
    	        var inputValue = $(columns[i]).find('input');
    	        var selectValue = $(columns[i]).find('select');
    	        var textareaValue = $(columns[i]).find('textarea');
    	        var key = dataColumns[i].field;
    	        if ($.common.isNotEmpty(inputValue.val())) {
    	            obj[key] = inputValue.val();
    	        } else if ($.common.isNotEmpty(selectValue.val())) {
    	            obj[key] = selectValue.val();
    	        } else if ($.common.isNotEmpty(textareaValue.val())) {
    	            obj[key] = textareaValue.val();
    	        } else {
    	            if (key == "index" && $.common.isNotEmpty(data[dataIndex].index)) {
    	                obj[key] = data[dataIndex].index;
    	            } else {
    	                obj[key] = "";
    	            }
    	        }
    	    }
    	    var item = data[dataIndex];
    	    var extendObj = $.extend({}, item, obj);
    	    params.push({ index: dataIndex, row: extendObj });
    	}
    	$("#" + table.options.id).bootstrapTable("updateRow", params);
    },
    delRow: function(column) {
    	sub.editRow();
    	var subColumn = $.common.isEmpty(column) ? "index" : column;
    	var ids = $.table.selectColumns(subColumn);
        if (ids.length == 0) {
            $.modal.alertWarning("請至少選擇一條紀錄");
            return;
        }
        $("#" + table.options.id).bootstrapTable('remove', { field: subColumn, values: ids });
    },
    delRowByIndex: function(value, tableId) {
    	var currentId = $.common.isEmpty(tableId) ? table.options.id : tableId;
    	sub.editRow();
        $("#" + currentId).bootstrapTable('remove', { field: "index", values: [value] });
        sub.editRow();
    },
    addRow: function(row, tableId) {
    	var currentId = $.common.isEmpty(tableId) ? table.options.id : tableId;
    	table.set(currentId);
    	var count = $("#" + currentId).bootstrapTable('getData').length;
    	sub.editRow();
    	$("#" + currentId).bootstrapTable('insertRow', { index: count + 1, row: row });
    }
};

// 動態載入css文件
function loadCss(file, headElem) {
    var link = document.createElement('link');
    link.href = file;
    link.rel = 'stylesheet';
    link.type = 'text/css';
    if (headElem) headElem.appendChild(link);
    else document.getElementsByTagName('head')[0].appendChild(link);
}

// 動態載入js文件
function loadJs(file, headElem) {
    var script = document.createElement('script');
    script.src = file;
    script.type = 'text/javascript';
    if (headElem) headElem.appendChild(script);
    else document.getElementsByTagName('head')[0].appendChild(script);
}

// 禁止後退鍵（Backspace）
window.onload = function() {
	document.getElementsByTagName("body")[0].onkeydown = function() {
		// 獲取事件對象  
		var elem = event.relatedTarget || event.srcElement || event.target || event.currentTarget;
		// 判斷按鍵為backSpace鍵  
		if (event.keyCode == 8) {
			// 判斷是否需要阻止按下鍵盤的事件默認傳遞  
			var name = elem.nodeName;
			var className = elem.className;
			// 屏蔽特定的樣式名稱
			if (className.indexOf('note-editable') != -1)
			{
				return true;
			}
			if (name != 'INPUT' && name != 'TEXTAREA') {
				return _stopIt(event);
			}
			var type_e = elem.type.toUpperCase();
			if (name == 'INPUT' && (type_e != 'TEXT' && type_e != 'TEXTAREA' && type_e != 'PASSWORD' && type_e != 'FILE' && type_e != 'SEARCH' && type_e != 'NUMBER' && type_e != 'EMAIL' && type_e != 'URL')) {
				return _stopIt(event);
			}
			if (name == 'INPUT' && (elem.readOnly == true || elem.disabled == true)) {
				return _stopIt(event);
			}
		}
	};
};
function _stopIt(e) {
	if (e.returnValue) {
		e.returnValue = false;
	}
	if (e.preventDefault) {
		e.preventDefault();
	}
	return false;
}

/** 設置全局ajax處理 */
$.ajaxSetup({
    complete: function(XMLHttpRequest, textStatus) {
        if (textStatus == 'timeout') {
            $.modal.alertWarning("伺服器超時，請稍後再試！");
            $.modal.enable();
            $.modal.closeLoading();
        } else if (textStatus == "parsererror" || textStatus == "error") {
            $.modal.alertWarning("伺服器錯誤，請聯絡管理員！");
            $.modal.enable();
            $.modal.closeLoading();
        }
    }
});
