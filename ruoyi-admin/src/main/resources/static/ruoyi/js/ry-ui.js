/**
 * 通用js方法封裝處理
 * Copyright (c) 2019 ruoyi
 */

// 當前table相關資訊
var table = {
    config: {},
    // 當前實例配置
    options: {},
    // 設置實例配置
    set: function(id) {
        if ($.common.getLength(table.config) > 1 && $.common.isNotEmpty(event)) {
            var tableId = $.common.isEmpty(id) ? $(event.currentTarget).parents(".bootstrap-table").find("table.table").attr("id") || $(event.currentTarget).parents(".bootstrap-tree-table").find("table.table").attr("id") : id;
            if ($.common.isNotEmpty(tableId)) {
                table.options = table.get(tableId);
            }
        }
    },
    // 獲取實例配置
    get: function(id) {
        return table.config[id];
    },
    // 記住選擇實例組
    rememberSelecteds: {},
    // 記住選擇ID組
    rememberSelectedIds: {}
};

(function ($) {
    $.extend({
        _tree: {},
        bttTable: {},
        // 表格封裝處理
        table: {
            // 初始化表格參數
            init: function(options) {
                var defaults = {
                    id: "bootstrap-table",
                    type: 0, // 0 代表bootstrapTable 1代表bootstrapTreeTable
                    method: 'post',
                    height: undefined,
                    sidePagination: "server",
                    undefinedText: '-',
                    sortName: undefined,
                    sortOrder: "asc",
                    pagination: true,
                    paginationLoop: false,
                    pageSize: 10,
                    pageNumber: 1,
                    pageList: [10, 25, 50, 100],
                    toolbar: "toolbar",
                    loadingFontSize: 13,
                    striped: false,
                    escape: true,
                    firstLoad: true,
                    showFooter: false,
                    search: false,
                    showSearch: true,
                    showPageGo: false,
                    showRefresh: true,
                    showColumns: true,
                    showToggle: true,
                    showExport: false,
                    showPrint: false,
                    exportDataType: 'all',
                    exportTypes: ['csv', 'txt', 'doc', 'excel'],
                    clickToSelect: false,
                    singleSelect: false,
                    mobileResponsive: true,
                    maintainSelected: false,
                    rememberSelected: false,
                    fixedColumns: false,
                    fixedNumber: 0,
                    fixedRightNumber: 0,
                    queryParams: $.table.queryParams,
                    rowStyle: undefined
                };
                var options = $.extend(defaults, options);
                table.options = options;
                table.config[options.id] = options;
                $.table.initEvent();
                $('#' + options.id).bootstrapTable({
                    id: options.id,
                    url: options.url,                                   // 請求後台的URL（*）
                    contentType: "application/x-www-form-urlencoded",   // 編碼類型
                    method: options.method,                             // 請求方式（*）
                    cache: false,                                       // 是否使用快取
                    height: options.height,                             // 表格的高度
                    striped: options.striped,                           // 是否顯示行間隔色
                    undefinedText: options.undefinedText,               // 數據值為空時顯示的內容
                    sortable: true,                                     // 是否啟用排序
                    sortStable: true,                                   // 設置為 true 將獲得穩定的排序
                    sortName: options.sortName,                         // 排序列名稱
                    sortOrder: options.sortOrder,                       // 排序方式  asc 或者 desc
                    pagination: options.pagination,                     // 是否顯示分頁（*）
                    paginationLoop: options.paginationLoop,             // 是否啟用分頁條無限循環的功能
                    pageNumber: 1,                                      // 初始化載入第一頁，默認第一頁
                    pageSize: options.pageSize,                         // 每頁的紀錄行數（*） 
                    pageList: options.pageList,                         // 可供選擇的每頁的行數（*）
                    firstLoad: options.firstLoad,                       // 是否首次請求載入數據，對於數據較大可以配置false
                    escape: options.escape,                             // 轉義HTML字串
                    showFooter: options.showFooter,                     // 是否顯示錶尾
                    iconSize: 'outline',                                // 圖示大小：undefined預設的按鈕尺寸 xs超小按鈕sm小按鈕lg大按鈕
                    toolbar: '#' + options.toolbar,                     // 指定工作欄
                    virtualScroll: options.virtualScroll,               // 是否啟動虛擬滾動（大量數據純展示時使用)
                    loadingFontSize: options.loadingFontSize,           // 自訂載入文本的字體大小
                    sidePagination: options.sidePagination,             // server啟用服務端分頁client用戶端分頁
                    search: options.search,                             // 是否顯示搜索框功能
                    searchText: options.searchText,                     // 搜索框初始顯示的內容，預設為空
                    showSearch: options.showSearch,                     // 是否顯示檢索資訊
                    showPageGo: options.showPageGo,                     // 是否顯示跳轉頁
                    showRefresh: options.showRefresh,                   // 是否顯示刷新按鈕
                    showColumns: options.showColumns,                   // 是否顯示隱藏某列下拉框
                    showToggle: options.showToggle,                     // 是否顯示詳細視圖和列表視圖的切換按鈕
                    showExport: options.showExport,                     // 是否支持導出文件
                    showPrint: options.showPrint,                       // 是否支持列印頁面
                    showHeader: options.showHeader,                     // 是否顯示表頭
                    showFullscreen: options.showFullscreen,             // 是否顯示全螢幕按鈕
                    uniqueId: options.uniqueId,                         // 唯一的標識符
                    clickToSelect: options.clickToSelect,               // 是否啟用點擊選中行
                    singleSelect: options.singleSelect,                 // 是否單選checkbox
                    mobileResponsive: options.mobileResponsive,         // 是否支持行動端適配
                    cardView: options.cardView,                         // 是否啟用顯示卡片視圖
                    detailView: options.detailView,                     // 是否啟用顯示細節視圖
                    onCheck: options.onCheck,                           // 當選擇此行時觸發
                    onUncheck: options.onUncheck,                       // 當取消此行時觸發
                    onCheckAll: options.onCheckAll,                     // 當全選行時觸發
                    onUncheckAll: options.onUncheckAll,                 // 當取消全選行時觸發
                    onClickRow: options.onClickRow,                     // 點擊某行觸發的事件
                    onDblClickRow: options.onDblClickRow,               // 雙擊某行觸發的事件
                    onClickCell: options.onClickCell,                   // 單擊某格觸發的事件
                    onDblClickCell: options.onDblClickCell,             // 雙擊某格觸發的事件
                    onEditableSave: options.onEditableSave,             // 行內編輯保存的事件
                    onExpandRow: options.onExpandRow,                   // 點擊詳細視圖的事件
                    onPostBody: options.onPostBody,                     // 渲染完成後執行的事件
                    maintainSelected: options.maintainSelected,         // 前端翻頁時保留所選行
                    rememberSelected: options.rememberSelected,         // 啟用翻頁記住前面的選擇
                    fixedColumns: options.fixedColumns,                 // 是否啟用凍結列（左側）
                    fixedNumber: options.fixedNumber,                   // 列凍結的個數（左側）
                    fixedRightNumber: options.fixedRightNumber,         // 列凍結的個數（右側）
                    onReorderRow: options.onReorderRow,                 // 當拖拽結束後處理函數
                    queryParams: options.queryParams,                   // 傳遞參數（*）
                    rowStyle: options.rowStyle,                         // 通過自訂函數設置行樣式
                    footerStyle: options.footerStyle,                   // 通過自訂函數設置頁尾樣式
                    headerStyle: options.headerStyle,                   // 通過自訂函數設置標題樣式
                    columns: options.columns,                           // 顯示列資訊（*）
                    data: options.data,                                 // 被載入的數據
                    responseHandler: $.table.responseHandler,           // 在載入伺服器發送來的數據之前處理函數
                    onLoadSuccess: $.table.onLoadSuccess,               // 當所有數據被載入時觸發處理函數
                    exportOptions: options.exportOptions,               // 前端導出忽略列索引
                    exportDataType: options.exportDataType,             // 導出方式（默認all：導出所有數據；basic：導出當前頁的數據；selected：導出選中的數據）
                    exportTypes: options.exportTypes,                   // 導出文件類型 （json、xml、png、csv、txt、sql、doc、excel、xlsx、powerpoint、pdf）
                    printPageBuilder: options.printPageBuilder,         // 自訂列印頁面模板
                    detailFormatter: options.detailFormatter,           // 在行下面展示其他數據列表
                });
            },
            // 獲取實例ID，如存在多個返回#id1,#id2 delimeter分隔符號
            getOptionsIds: function(separator) {
                var _separator = $.common.isEmpty(separator) ? "," : separator;
                var optionsIds = "";  
                $.each(table.config, function(key, value){
                    optionsIds += "#" + key + _separator;
                });
                return optionsIds.substring(0, optionsIds.length - 1);
            },
            // 查詢條件
            queryParams: function(params) {
                table.set();
                var curParams = {
                    // 傳遞參數查詢參數
                    pageSize:       params.limit,
                    pageNum:        params.offset / params.limit + 1,
                    searchValue:    params.search,
                    orderByColumn:  params.sort,
                    isAsc:          params.order
                };
                var currentId = $.common.isEmpty(table.options.formId) ? $('form').attr('id') : table.options.formId;
                return $.extend(curParams, $.common.formToJSON(currentId)); 
            },
            // 請求獲取數據後處理回調函數
            responseHandler: function(res) {
                if (typeof table.get(this.id).responseHandler == "function") {
                    table.get(this.id).responseHandler(res);
                }
                var thisOptions = table.config[this.id];
                if (res.code == web_status.SUCCESS) {
                    if ($.common.isNotEmpty(thisOptions.sidePagination) && thisOptions.sidePagination == 'client') {
                        return res.rows;
                    } else {
                        if ($.common.isNotEmpty(thisOptions.rememberSelected) && thisOptions.rememberSelected) {
                            var column = $.common.isEmpty(thisOptions.uniqueId) ? thisOptions.columns[1].field : thisOptions.uniqueId;
                            $.each(res.rows, function(i, row) {
                                row.state = $.inArray(row[column], table.rememberSelectedIds[thisOptions.id]) !== -1;
                            })
                        }
                        return { rows: res.rows, total: res.total };
                    }
                } else {
                    $.modal.alertWarning(res.msg);
                    return { rows: [], total: 0 };
                }
            },
            // 初始化事件
            initEvent: function() {
                // 實例ID資訊
                var optionsIds = $.table.getOptionsIds();
                // 監聽事件處理
                $(optionsIds).on(TABLE_EVENTS, function () {
                    table.set($(this).attr("id"));
                });
                // 在表格體渲染完成，並在 DOM 中可見後觸發（事件）
                $(optionsIds).on("post-body.bs.table", function (e, args) {
                    // 浮動提示框特效
                    $(".table [data-toggle='tooltip']").tooltip();
                    // 氣泡彈出框特效
                    $('.table [data-toggle="popover"]').popover();
                });
                // 選中、取消、全部選中、全部取消（事件）
                $(optionsIds).on("check.bs.table check-all.bs.table uncheck.bs.table uncheck-all.bs.table", function (e, rowsAfter, rowsBefore) {
                    // 複選框分頁保留保存選中數組
                    var rows = $.common.equals("uncheck-all", e.type) ? rowsBefore : rowsAfter;
                    var rowIds = $.table.affectedRowIds(rows);
                    if ($.common.isNotEmpty(table.options.rememberSelected) && table.options.rememberSelected) {
                        func = $.inArray(e.type, ['check', 'check-all']) > -1 ? 'union' : 'difference';
                        var selectedIds = table.rememberSelectedIds[table.options.id];
                        if ($.common.isNotEmpty(selectedIds)) {
                            table.rememberSelectedIds[table.options.id] = _[func](selectedIds, rowIds);
                        } else {
                            table.rememberSelectedIds[table.options.id] = _[func]([], rowIds);
                        }
                        var selectedRows = table.rememberSelecteds[table.options.id];
                        if ($.common.isNotEmpty(selectedRows)) {
                            table.rememberSelecteds[table.options.id] = _[func](selectedRows, rows);
                        } else {
                            table.rememberSelecteds[table.options.id] = _[func]([], rows);
                        }
                    }
                });
                // 載入成功、選中、取消、全部選中、全部取消（事件）
                $(optionsIds).on("check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table load-success.bs.table", function () {
                    var toolbar = table.options.toolbar;
                    var uniqueId = table.options.uniqueId;
                    // 工具欄按鈕控制
                    var rows = $.common.isEmpty(uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns(uniqueId);
                    // 非多個禁用
                    $('#' + toolbar + ' .multiple').toggleClass('disabled', !rows.length);
                    // 非單個禁用
                    $('#' + toolbar + ' .single').toggleClass('disabled', rows.length!=1);
                });
                // 圖片預覽事件
                $(optionsIds).off("click").on("click", '.img-circle', function() {
                    var src = $(this).attr('src');
                    var target = $(this).data('target');
                    if ($.common.equals("self", target)) {
                        var height = $(this).data('height');
                        var width = $(this).data('width');
                        top.layer.open({
                            title: false,
                            type: 1,
                            closeBtn: true,
                            shadeClose: true,
                            content: "<img src='" + src + "' height='" + height + "' width='" + width + "'/>"
                        });
                    } else if ($.common.equals("blank", target)) {
                        window.open(src);
                    }
                });
                // 單擊tooltip事件
                $(optionsIds).on("click", '.tooltip-show', function() {
                    var target = $(this).data('target');
                    var input = $(this).prev();
                    if ($.common.equals("copy", target)) {
                        input.select();
                        document.execCommand("copy");
                    } else if ($.common.equals("open", target)) {
                        top.layer.alert(input.val(), {
                            title: "資訊內容",
                            shadeClose: true,
                            btn: ['確認'],
                            btnclass: ['btn btn-primary'],
                        });
                    }
                });
            },
            // 當所有數據被載入時觸發
            onLoadSuccess: function(data) {
                if (typeof table.options.onLoadSuccess == "function") {
                    table.options.onLoadSuccess(data);
                }
            },
            // 表格銷毀
            destroy: function (tableId) {
                var currentId = $.common.isEmpty(tableId) ? table.options.id : tableId;
                $("#" + currentId).bootstrapTable('destroy');
                delete table.rememberSelectedIds[currentId];
                delete table.rememberSelecteds[currentId];
            },
            // 序號生成
            serialNumber: function (index, tableId) {
                var currentId = $.common.isEmpty(tableId) ? table.options.id : tableId;
                var tableParams = $("#" + currentId).bootstrapTable('getOptions');
                var pageSize = $.common.isNotEmpty(tableParams.pageSize) ? tableParams.pageSize: table.options.pageSize;
                var pageNumber = $.common.isNotEmpty(tableParams.pageNumber) ? tableParams.pageNumber: table.options.pageNumber;
                if (table.options.sidePagination == 'client') {
                    return index + 1;
                }
                return pageSize * (pageNumber - 1) + index + 1;
            },
            // 列超出指定長度浮動提示 target（copy單擊複製文本 open彈出視窗打開文本）
            tooltip: function (value, length, target) {
                var _length = $.common.isEmpty(length) ? 20 : length;
                var _text = "";
                var _value = $.common.nullToStr(value);
                var _target = $.common.isEmpty(target) ? 'copy' : target;
                if (_value.length > _length) {
                    _text = _value.substr(0, _length) + "...";
                    _value = _value.replace(/\'/g,"&apos;");
                    _value = _value.replace(/\"/g,"&quot;");
                    var actions = [];
                    actions.push($.common.sprintf('<input style="opacity: 0;position: absolute;z-index:-1" type="text" value="%s"/>', _value));
                    actions.push($.common.sprintf('<a href="###" class="tooltip-show" data-toggle="tooltip" data-target="%s" title="%s">%s</a>', _target, _value, _text));
                    return actions.join('');
                } else {
                    _text = _value;
                    return _text;
                }
            },
            // 下拉按鈕切換
            dropdownToggle: function (value) {
                var actions = [];
                actions.push('<div class="btn-group">');
                actions.push('<button type="button" class="btn btn-xs dropdown-toggle" data-toggle="dropdown" aria-expanded="false">');
                actions.push('<i class="fa fa-cog"></i>&nbsp;<span class="fa fa-chevron-down"></span></button>');
                actions.push('<ul class="dropdown-menu">');
                actions.push(value.replace(/<a/g,"<li><a").replace(/<\/a>/g,"</a></li>"));
                actions.push('</ul>');
                actions.push('</div>');
                return actions.join('');
            },
            // 圖片預覽
            imageView: function (value, height, width, target) {
                if ($.common.isEmpty(width)) {
                    width = 'auto';
                }
                if ($.common.isEmpty(height)) {
                    height = 'auto';
                }
                // blank or self
                var _target = $.common.isEmpty(target) ? 'self' : target;
                if ($.common.isNotEmpty(value)) {
                    return $.common.sprintf("<img class='img-circle img-xs' data-height='%s' data-width='%s' data-target='%s' src='%s'/>", height, width, _target, value);
                } else {
                    return $.common.nullToStr(value);
                }
            },
            // 搜索-默認第一個form
            search: function(formId, tableId, pageNumber, pageSize) {
                table.set(tableId);
                table.options.formId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                var params = $.common.isEmpty(tableId) ? $("#" + table.options.id).bootstrapTable('getOptions') : $("#" + tableId).bootstrapTable('getOptions');
                if ($.common.isNotEmpty(pageNumber)) {
                    params.pageNumber = pageNumber;
                }
                if ($.common.isNotEmpty(pageSize)) {
                    params.pageSize = pageSize;
                }
                if ($.common.isNotEmpty(tableId)) {
                    $("#" + tableId).bootstrapTable('refresh', params);
                } else{
                    $("#" + table.options.id).bootstrapTable('refresh', params);
                }
            },
            // 導出數據
            exportExcel: function(formId) {
                table.set();
                $.modal.confirm("確定導出所有" + table.options.modalName + "嗎？", function() {
                    var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                    var params = $("#" + table.options.id).bootstrapTable('getOptions');
                    var dataParam = $("#" + currentId).serializeArray();
                    dataParam.push({ "name": "orderByColumn", "value": params.sortName });
                    dataParam.push({ "name": "isAsc", "value": params.sortOrder });
                    $.modal.loading("正在導出數據，請稍候...");
                    $.post(table.options.exportUrl, dataParam, function(result) {
                        if (result.code == web_status.SUCCESS) {
                            window.location.href = ctx + "common/download?fileName=" + encodeURI(result.msg) + "&delete=" + true;
                        } else if (result.code == web_status.WARNING) {
                            $.modal.alertWarning(result.msg)
                        } else {
                            $.modal.alertError(result.msg);
                        }
                        $.modal.closeLoading();
                    });
                });
            },
            // 下載模板
            importTemplate: function() {
                $.get(activeWindow().table.options.importTemplateUrl, function(result) {
                    if (result.code == web_status.SUCCESS) {
                        window.location.href = ctx + "common/download?fileName=" + encodeURI(result.msg) + "&delete=" + true;
                    } else if (result.code == web_status.WARNING) {
                        $.modal.alertWarning(result.msg)
                    } else {
                        $.modal.alertError(result.msg);
                    }
                });
            },
            // 導入數據
            importExcel: function(formId, width, height) {
                table.set();
                var currentId = $.common.isEmpty(formId) ? 'importTpl' : formId;
                var _width = $.common.isEmpty(width) ? "400" : width;
                var _height = $.common.isEmpty(height) ? "230" : height;
                top.layer.open({
                    type: 1,
                    area: [_width + 'px', _height + 'px'],
                    fix: false,
                    //不固定
                    maxmin: true,
                    shade: 0.3,
                    title: '導入' + table.options.modalName + '數據',
                    content: $('#' + currentId).html(),
                    btn: ['<i class="fa fa-check"></i> 導入', '<i class="fa fa-remove"></i> 取消'],
                    // 彈層外區域關閉
                    shadeClose: true,
                    btn1: function(index, layero){
                        var file = layero.find('#file').val();
                        if (file == '' || (!$.common.endWith(file, '.xls') && !$.common.endWith(file, '.xlsx'))) {
                            $.modal.msgWarning("請選擇後綴為 “xls”或“xlsx”的文件。");
                            return false;
                        }
                        var index = top.layer.load(2, {shade: false});
                        $.modal.disable();
                        var formData = new FormData(layero.find('form')[0]);
                        $.ajax({
                            url: table.options.importUrl,
                            data: formData,
                            cache: false,
                            contentType: false,
                            processData: false,
                            type: 'POST',
                            success: function (result) {
                                if (result.code == web_status.SUCCESS) {
                                	$.modal.close(index);
                                    $.modal.closeAll();
                                    $.modal.alertSuccess(result.msg);
                                    $.table.refresh();
                                } else if (result.code == web_status.WARNING) {
                                	$.modal.close(index);
                                    $.modal.enable();
                                    $.modal.alertWarning(result.msg)
                                } else {
                                    $.modal.close(index);
                                    $.modal.enable();
                                    $.modal.alertError(result.msg);
                                }
                            },
                            complete: function () {
                            	layero.find('#file').val('');
                            }
                        });
                    }
                });
            },
            // 刷新表格
            refresh: function(tableId, pageNumber, pageSize, url) {
                var currentId = $.common.isEmpty(tableId) ? table.options.id : tableId;
                var params = $("#" + currentId).bootstrapTable('getOptions');
                if ($.common.isEmpty(pageNumber)) {
                    pageNumber = params.pageNumber;
                }
                if ($.common.isEmpty(pageSize)) {
                    pageSize = params.pageSize;
                }
                if ($.common.isEmpty(url)) {
                    url = $.common.isEmpty(url) ? params.url : url;
                }
                $("#" + currentId).bootstrapTable('refresh', {
                    silent: true,
                    url: url,
                    pageNumber: pageNumber,
                    pageSize: pageSize
                });
            },
            // 刷新options配置
            refreshOptions: function(options, tableId) {
                var currentId = $.common.isEmpty(tableId) ? table.options.id : tableId;
                $("#" + currentId).bootstrapTable('refreshOptions', options);
            },
            // 查詢表格指定列值 deDuplication（ true去重、false不去重）
            selectColumns: function(column, deDuplication) {
                var distinct = $.common.isEmpty(deDuplication) ? true : deDuplication;
                var rows = $.map($("#" + table.options.id).bootstrapTable('getSelections'), function (row) {
                    return $.common.getItemField(row, column);
                });
                if ($.common.isNotEmpty(table.options.rememberSelected) && table.options.rememberSelected) {
                    var selectedRows = table.rememberSelecteds[table.options.id];
                    if ($.common.isNotEmpty(selectedRows)) {
                        rows = $.map(table.rememberSelecteds[table.options.id], function (row) {
                            return $.common.getItemField(row, column);
                        });
                    }
                }
                return distinct ? $.common.uniqueFn(rows) : rows;
            },
            // 獲取當前頁選中或者取消的行ID
            affectedRowIds: function(rows) {
                var column = $.common.isEmpty(table.options.uniqueId) ? table.options.columns[1].field : table.options.uniqueId;
                var rowIds;
                if ($.isArray(rows)) {
                    rowIds = $.map(rows, function(row) {
                        return $.common.getItemField(row, column);
                    });
                } else {
                    rowIds = [rows[column]];
                }
                return rowIds;
            },
            // 查詢表格首列值deDuplication（ true去重、false不去重）
            selectFirstColumns: function(deDuplication) {
                var distinct = $.common.isEmpty(deDuplication) ? true : deDuplication;
                var rows = $.map($("#" + table.options.id).bootstrapTable('getSelections'), function (row) {
                    return $.common.getItemField(row, table.options.columns[1].field);
                });
                if ($.common.isNotEmpty(table.options.rememberSelected) && table.options.rememberSelected) {
                    var selectedRows = table.rememberSelecteds[table.options.id];
                    if ($.common.isNotEmpty(selectedRows)) {
                        rows = $.map(selectedRows, function (row) {
                            return $.common.getItemField(row, table.options.columns[1].field);
                        });
                    }
                }
                return distinct ? $.common.uniqueFn(rows) : rows;
            },
            // 回顯數據字典
            selectDictLabel: function(datas, value) {
                if ($.common.isEmpty(datas) || $.common.isEmpty(value)) {
                    return '';
                }
                var actions = [];
                $.each(datas, function(index, dict) {
                    if (dict.dictValue == ('' + value)) {
                        var listClass = $.common.equals("default", dict.listClass) || $.common.isEmpty(dict.listClass) ? "" : "badge badge-" + dict.listClass;
                        var cssClass = $.common.isNotEmpty(dict.cssClass) ? dict.cssClass : listClass;
                        actions.push($.common.sprintf("<span class='%s'>%s</span>", cssClass, dict.dictLabel));
                        return false;
                    }
                });
                if (actions.length === 0) {
                    actions.push($.common.sprintf("<span>%s</span>", value))
                }
                return actions.join('');
            },
            // 回顯數據字典（字串數組）
            selectDictLabels: function(datas, value, separator) {
                if ($.common.isEmpty(datas) || $.common.isEmpty(value)) {
                    return '';
                }
                var currentSeparator = $.common.isEmpty(separator) ? "," : separator;
                var actions = [];
                $.each(value.split(currentSeparator), function(i, val) {
                    var match = false
                    $.each(datas, function(index, dict) {
                        if (dict.dictValue == ('' + val)) {
                            var listClass = $.common.equals("default", dict.listClass) || $.common.isEmpty(dict.listClass) ? "" : "badge badge-" + dict.listClass;
                            actions.push($.common.sprintf("<span class='%s'>%s</span>", listClass, dict.dictLabel));
                            match = true
                            return false;
                        }
                    });
                    if (!match) {
                        actions.push($.common.sprintf("<span> %s </span>", val));
                    }
                });
                return actions.join('');
            },
            // 顯示表格指定列
            showColumn: function(column, tableId) {
                var currentId = $.common.isEmpty(tableId) ? table.options.id : tableId;
                $("#" + currentId).bootstrapTable('showColumn', column);
            },
            // 隱藏表格指定列
            hideColumn: function(column, tableId) {
                var currentId = $.common.isEmpty(tableId) ? table.options.id : tableId;
                $("#" + currentId).bootstrapTable('hideColumn', column);
            },
            // 顯示所有表格列
            showAllColumns: function(tableId) {
                var currentId = $.common.isEmpty(tableId) ? table.options.id : tableId;
                $("#" + currentId).bootstrapTable('showAllColumns');
            },
            // 隱藏所有表格列
            hideAllColumns: function(tableId) {
                var currentId = $.common.isEmpty(tableId) ? table.options.id : tableId;
                $("#" + currentId).bootstrapTable('hideAllColumns');
            }
        },
        // 表格樹封裝處理
        treeTable: {
            // 初始化表格
            init: function(options) {
                var defaults = {
                    id: "bootstrap-tree-table",
                    type: 1, // 0 代表bootstrapTable 1代表bootstrapTreeTable
                    height: 0,
                    rootIdValue: 0,
                    ajaxParams: {},
                    toolbar: "toolbar",
                    striped: false,
                    pagination: false,
                    pageSize: 10,
                    pageList: [10, 25, 50],
                    expandColumn: 1,
                    showSearch: true,
                    showRefresh: true,
                    showColumns: true,
                    expandAll: true,
                    expandFirst: true
                };
                var options = $.extend(defaults, options);
                table.options = options;
                table.config[options.id] = options;
                $.table.initEvent();
                $.bttTable = $('#' + options.id).bootstrapTreeTable({
                    code: options.code,                                 // 用於設置父子關係
                    parentCode: options.parentCode,                     // 用於設置父子關係
                    type: 'post',                                       // 請求方式（*）
                    url: options.url,                                   // 請求後台的URL（*）
                    data: options.data,                                 // 無url時用於渲染的數據
                    ajaxParams: options.ajaxParams,                     // 請求數據的ajax的data屬性
                    rootIdValue: options.rootIdValue,                   // 設置指定根節點id值
                    height: options.height,                             // 表格樹的高度
                    pagination: options.pagination,                     // 是否顯示分頁
                    dataUrl: options.dataUrl,                           // 載入子節點非同步請求數據url
                    pageSize: options.pageSize,                         // 每頁的紀錄行數
                    pageList: options.pageList,                         // 可供選擇的每頁的行數
                    expandColumn: options.expandColumn,                 // 在哪一列上面顯示展開按鈕
                    striped: options.striped,                           // 是否顯示行間隔色
                    bordered: options.bordered,                         // 是否顯示邊框
                    toolbar: '#' + options.toolbar,                     // 指定工作欄
                    showSearch: options.showSearch,                     // 是否顯示檢索資訊
                    showRefresh: options.showRefresh,                   // 是否顯示刷新按鈕
                    showColumns: options.showColumns,                   // 是否顯示隱藏某列下拉框
                    expandAll: options.expandAll,                       // 是否全部展開
                    expandFirst: options.expandFirst,                   // 是否默認第一級展開--expandAll為false時生效
                    columns: options.columns,                           // 顯示列資訊（*）
                    onClickRow: options.onClickRow,                     // 單擊某行事件
                    responseHandler: $.treeTable.responseHandler,       // 在載入伺服器發送來的數據之前處理函數
                    onLoadSuccess: $.treeTable.onLoadSuccess            // 當所有數據被載入時觸發處理函數
                });
            },
            // 條件查詢
            search: function(formId) {
                var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                var params = $.common.formToJSON(currentId);
                $.bttTable.bootstrapTreeTable('refresh', $.extend(params, table.options.ajaxParams));
            },
            // 刷新
            refresh: function() {
                $.bttTable.bootstrapTreeTable('refresh');
            },
            // 查詢表格樹指定列值deDuplication（ true去重、false不去重）
            selectColumns: function(column, deDuplication) {
                var distinct = $.common.isEmpty(deDuplication) ? true : deDuplication;
                var rows = $.map($.bttTable.bootstrapTreeTable('getSelections'), function (row) {
                    return $.common.getItemField(row, column);
                });
                return distinct ? $.common.uniqueFn(rows) : rows;
            },
            // 請求獲取數據後處理回調函數，校驗異常狀態提醒
            responseHandler: function(res) {
                if (typeof table.options.responseHandler == "function") {
                    table.options.responseHandler(res);
                }
                if (res.code != undefined && res.code != web_status.SUCCESS) {
                    $.modal.alertWarning(res.msg);
                    return [];
                } else {
                    return res;
                }
            },
            // 當所有數據被載入時觸發
            onLoadSuccess: function(data) {
                if (typeof table.options.onLoadSuccess == "function") {
                    table.options.onLoadSuccess(data);
                }
                $(".table [data-toggle='tooltip']").tooltip();
            },
        },
        // 表單封裝處理
        form: {
            // 表單重設
            reset: function(formId, tableId, pageNumber, pageSize) {
                table.set(tableId);
                formId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                $("#" + formId)[0].reset();
                var tableId = $.common.isEmpty(tableId) ? table.options.id : tableId;
                if (table.options.type == table_type.bootstrapTable) {
                    var params = $("#" + tableId).bootstrapTable('getOptions');
                    params.pageNumber = 1;
                    if ($.common.isNotEmpty(pageSize)) {
                        params.pageSize = pageSize;
                    }
                    $("#" + tableId).bootstrapTable('refresh', params);
                } else if (table.options.type == table_type.bootstrapTreeTable) {
                    $("#" + tableId).bootstrapTreeTable('refresh', table.options.ajaxParams);
                }
                resetDate();
            },
            // 獲取選中複選框項
            selectCheckeds: function(name) {
                var checkeds = "";
                $('input:checkbox[name="' + name + '"]:checked').each(function(i) {
                    if (0 == i) {
                        checkeds = $(this).val();
                    } else {
                        checkeds += ("," + $(this).val());
                    }
                });
                return checkeds;
            },
            // 獲取選中下拉框項
            selectSelects: function(name) {
                var selects = "";
                $('#' + name + ' option:selected').each(function (i) {
                    if (0 == i) {
                        selects = $(this).val();
                    } else {
                        selects += ("," + $(this).val());
                    }
                });
                return selects;
            }
        },
        // 彈出層封裝處理
        modal: {
            // 顯示圖示
            icon: function(type) {
                var icon = "";
                if (type == modal_status.WARNING) {
                    icon = 0;
                } else if (type == modal_status.SUCCESS) {
                    icon = 1;
                } else if (type == modal_status.FAIL) {
                    icon = 2;
                } else {
                    icon = 3;
                }
                return icon;
            },
            // 消息提示
            msg: function(content, type) {
                if (type != undefined) {
                	top.layer.msg(content, { icon: $.modal.icon(type), time: 1000, shift: 5 });
                } else {
                	top.layer.msg(content);
                }
            },
            // 錯誤消息
            msgError: function(content) {
                $.modal.msg(content, modal_status.FAIL);
            },
            // 成功消息
            msgSuccess: function(content) {
                $.modal.msg(content, modal_status.SUCCESS);
            },
            // 警告消息
            msgWarning: function(content) {
                $.modal.msg(content, modal_status.WARNING);
            },
            // 彈出提示
            alert: function(content, type) {
                top.layer.alert(content, {
                    icon: $.modal.icon(type),
                    title: "系統提示",
                    btn: ['確認'],
                    btnclass: ['btn btn-primary'],
                });
            },
            // 錯誤提示
            alertError: function(content) {
                $.modal.alert(content, modal_status.FAIL);
            },
            // 成功提示
            alertSuccess: function(content) {
                $.modal.alert(content, modal_status.SUCCESS);
            },
            // 警告提示
            alertWarning: function(content) {
                $.modal.alert(content, modal_status.WARNING);
            },
            // 消息提示，重新載入頁面
            msgReload: function(msg, type) {
                top.layer.msg(msg, {
                    icon: $.modal.icon(type),
                    time: 500,
                    shade: [0.1, '#8F8F8F']
                },
                function() {
                    $.modal.reload();
                });
            },
            // 消息提示成功並刷新父窗體
            msgSuccessReload: function(msg) {
            	$.modal.msgReload(msg, modal_status.SUCCESS);
            },
            // 獲取iframe頁的DOM
            getChildFrame: function (index) {
                if ($.common.isEmpty(index)) {
                    var index = parent.layer.getFrameIndex(window.name);
                    return parent.layer.getChildFrame('body', index);
                } else {
                    return top.layer.getChildFrame('body', index);
                }
            },
            // 關閉窗體
            close: function (index) {
                if ($.common.isEmpty(index)) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                } else {
                    top.layer.close(index);
                }
            },
            // 關閉全部窗體
            closeAll: function () {
                top.layer.closeAll();
            },
            // 確認窗體
            confirm: function (content, callBack) {
                top.layer.confirm(content, {
                    icon: 3,
                    title: "系統提示",
                    btn: ['確認', '取消']
                }, function (index) {
                    $.modal.close(index);
                    callBack(true);
                });
            },
            // 彈出層指定寬度
            open: function (title, url, width, height, callback) {
                // 如果是行動端，就使用自適應大小彈出視窗
                if ($.common.isMobile()) {
                    width = 'auto';
                    height = 'auto';
                }
                if ($.common.isEmpty(title)) {
                    title = false;
                }
                if ($.common.isEmpty(url)) {
                    url = "/404.html";
                }
                if ($.common.isEmpty(width)) {
                    width = 800;
                }
                if ($.common.isEmpty(height)) {
                    height = ($(window).height() - 50);
                }
                if ($.common.isEmpty(callback)) {
                    callback = function(index, layero) {
                        var iframeWin = layero.find('iframe')[0];
                        iframeWin.contentWindow.submitHandler(index, layero);
                    }
                }
                top.layer.open({
                    type: 2,
                    area: [width + 'px', height + 'px'],
                    fix: false,
                    //不固定
                    maxmin: true,
                    shade: 0.3,
                    title: title,
                    content: url,
                    btn: ['確定', '關閉'],
                    // 彈層外區域關閉
                    shadeClose: true,
                    yes: callback,
                    cancel: function(index) {
                        return true;
                    },
                    success: function () {
                        $(':focus').blur();
                    }
                });
            },
            // 彈出層指定參數選項
            openOptions: function (options) {
                var _url = $.common.isEmpty(options.url) ? "/404.html" : options.url; 
                var _title = $.common.isEmpty(options.title) ? "系統窗口" : options.title; 
                var _width = $.common.isEmpty(options.width) ? "800" : options.width; 
                var _height = $.common.isEmpty(options.height) ? ($(window).height() - 50) : options.height;
                var _btn = ['<i class="fa fa-check"></i> 確認', '<i class="fa fa-close"></i> 關閉'];
                // 如果是行動端，就使用自適應大小彈出視窗
                if ($.common.isMobile()) {
                    _width = 'auto';
                    _height = 'auto';
                }
                if ($.common.isEmpty(options.yes)) {
                    options.yes = function(index, layero) {
                        options.callBack(index, layero);
                    }
                }
                var btnCallback = {};
                if (options.btn instanceof Array){
                    for (var i = 1, len = options.btn.length; i < len; i++) {
                        var btn = options["btn" + (i + 1)];
                        if (btn) {
                            btnCallback["btn" + (i + 1)] = btn;
                        }
                    }
                }
                var index = top.layer.open($.extend({
                    id: options.id,       // 唯一id
                    anim: options.anim,   // 彈出動畫 0-6
                    type: 2,
                    maxmin: $.common.isEmpty(options.maxmin) ? true : options.maxmin,
                    shade: 0.3,
                    title: _title,
                    fix: false,
                    area: [_width + 'px', _height + 'px'],
                    content: _url,
                    closeBtn: $.common.isEmpty(options.closeBtn) ? 1 : options.closeBtn,
                    shadeClose: $.common.isEmpty(options.shadeClose) ? true : options.shadeClose,
                    skin: options.skin,
                    // options.btn設置為0表示不顯示按鈕
                    btn: $.common.isEmpty(options.btn) ? _btn : options.btn,
                    yes: options.yes,
                    cancel: function () {
                        return true;
                    },
                    success: function () {
                        $(':focus').blur();
                    }
                }, btnCallback));
                if ($.common.isNotEmpty(options.full) && options.full === true) {
                    top.layer.full(index);
                }
            },
            // 彈出層全螢幕
            openFull: function (title, url, width, height) {
                // 如果是行動端，就使用自適應大小彈出視窗
                if ($.common.isMobile()) {
                    width = 'auto';
                    height = 'auto';
                }
                if ($.common.isEmpty(title)) {
                    title = false;
                }
                if ($.common.isEmpty(url)) {
                    url = "/404.html";
                }
                if ($.common.isEmpty(width)) {
                    width = 800;
                }
                if ($.common.isEmpty(height)) {
                    height = ($(window).height() - 50);
                }
                var index = top.layer.open({
                    type: 2,
                    area: [width + 'px', height + 'px'],
                    fix: false,
                    //不固定
                    maxmin: true,
                    shade: 0.3,
                    title: title,
                    content: url,
                    btn: ['確定', '關閉'],
                    // 彈層外區域關閉
                    shadeClose: true,
                    yes: function(index, layero) {
                        var iframeWin = layero.find('iframe')[0];
                        iframeWin.contentWindow.submitHandler(index, layero);
                    },
                    cancel: function(index) {
                        return true;
                    },
                    success: function () {
                        $(':focus').blur();
                    }
                });
                top.layer.full(index);
            },
            // 選卡頁方式打開
            openTab: function (title, url, isRefresh) {
                createMenuItem(url, title, isRefresh);
            },
            // 選卡頁同一頁簽打開
            parentTab: function (title, url) {
                var dataId = window.frameElement.getAttribute('data-id');
                createMenuItem(url, title);
                closeItem(dataId);
            },
            // 右側彈出窗口打開
            popupRight: function(title, url){
                var width = 150;
                if (top.location !== self.location) {
                    if ($(top.window).outerWidth() < 400) {
                        width = 50;
                    }
                }
                top.layer.open({
                    type: 2,
                    offset: 'r',
                    anim: 'slideLeft',
                    move: false,
                    title: title,
                    shade: 0.3,
                    shadeClose: true,
                    area: [($(window).outerWidth() - width) + 'px', '100%'],
                    content: url
                });
            },
            // 關閉選項卡
            closeTab: function (dataId) {
                closeItem(dataId);
            },
            // 禁用按鈕
            disable: function() {
                var doc = window.top == window.parent ? top.window.document : window.parent.document;
                $("a[class*=layui-layer-btn]", doc).addClass("layer-disabled");
            },
            // 啟用按鈕
            enable: function() {
                var doc = window.top == window.parent ? top.window.document : window.parent.document;
                $("a[class*=layui-layer-btn]", doc).removeClass("layer-disabled");
            },
            // 打開遮罩層
            loading: function (message) {
                $.blockUI({ message: '<div class="loaderbox"><div class="loading-activity"></div> ' + message + '</div>' });
            },
            // 關閉遮罩層
            closeLoading: function () {
                setTimeout(function(){
                    $.unblockUI();
                }, 50);
            },
            // 重新載入
            reload: function () {
                parent.location.reload();
            }
        },
        // 操作封裝處理
        operate: {
            // 提交數據
            submit: function(url, type, dataType, data, callback) {
                var config = {
                    url: url,
                    type: type,
                    dataType: dataType,
                    data: data,
                    beforeSend: function () {
                        $.modal.loading("正在處理中，請稍候...");
                    },
                    success: function(result) {
                        if (typeof callback == "function") {
                            callback(result);
                        }
                        $.operate.ajaxSuccess(result);
                    }
                };
                $.ajax(config)
            },
            // post請求傳輸
            post: function(url, data, callback) {
                $.operate.submit(url, "post", "json", data, callback);
            },
            // get請求傳輸
            get: function(url, callback) {
                $.operate.submit(url, "get", "json", "", callback);
            },
            // 詳細資訊
            detail: function(id, width, height) {
                table.set();
                var _url = $.operate.detailUrl(id);
                var options = {
                    title: table.options.modalName + "詳細",
                    width: width,
                    height: height,
                    url: _url,
                    btn: 0,
                    yes: function (index, layero) {
                        $.modal.close(index);
                    }
                };
                $.modal.openOptions(options);
            },
            // 詳細資訊，以tab頁展現
            detailTab: function(id) {
                table.set();
                $.modal.openTab("詳細" + table.options.modalName, $.operate.detailUrl(id));
            },
            // 詳細訪問地址
            detailUrl: function(id) {
                var url = "/404.html";
                if ($.common.isNotEmpty(id)) {
                    url = table.options.detailUrl.replace("{id}", id);
                } else {
                    var id = $.common.isEmpty(table.options.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns(table.options.uniqueId);
                    if (id.length == 0) {
                        $.modal.alertWarning("請至少選擇一條紀錄");
                        return;
                    }
                    url = table.options.detailUrl.replace("{id}", id);
                }
                return url;
            },
            // 刪除資訊
            remove: function(id) {
                table.set();
                $.modal.confirm("確定刪除該條" + table.options.modalName + "資訊嗎？", function() {
                    var url = $.common.isEmpty(id) ? table.options.removeUrl : table.options.removeUrl.replace("{id}", id);
                    if (table.options.type == table_type.bootstrapTreeTable) {
                        $.operate.get(url);
                    } else {
                        var data = { "ids": id };
                        $.operate.submit(url, "post", "json", data);
                    }
                });
            },
            // 批次刪除資訊
            removeAll: function() {
                table.set();
                var rows = $.common.isEmpty(table.options.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns(table.options.uniqueId);
                if (rows.length == 0) {
                    $.modal.alertWarning("請至少選擇一條紀錄");
                    return;
                }
                $.modal.confirm("確認要刪除選中的" + rows.length + "條數據嗎?", function() {
                    var url = table.options.removeUrl;
                    var data = { "ids": rows.join() };
                    $.operate.submit(url, "post", "json", data);
                });
            },
            // 清空資訊
            clean: function() {
                table.set();
                $.modal.confirm("確定清空所有" + table.options.modalName + "嗎？", function() {
                    var url = table.options.cleanUrl;
                    $.operate.submit(url, "post", "json", "");
                });
            },
            // 添加資訊
            add: function(id) {
                table.set();
                $.modal.open("添加" + table.options.modalName, $.operate.addUrl(id));
            },
            // 添加資訊，以tab頁展現
            addTab: function (id) {
                table.set();
                $.modal.openTab("添加" + table.options.modalName, $.operate.addUrl(id));
            },
            // 添加資訊 全螢幕
            addFull: function(id) {
                table.set();
                $.modal.openFull("添加" + table.options.modalName, $.operate.addUrl(id));
            },
            // 添加訪問地址
            addUrl: function(id) {
                var url = $.common.isEmpty(id) ? table.options.createUrl.replace("{id}", "") : table.options.createUrl.replace("{id}", id);
                return url;
            },
            // 修改資訊
            edit: function(id) {
                table.set();
                if ($.common.isEmpty(id) && table.options.type == table_type.bootstrapTreeTable) {
                    var row = $("#" + table.options.id).bootstrapTreeTable('getSelections')[0];
                    if ($.common.isEmpty(row)) {
                        $.modal.alertWarning("請至少選擇一條紀錄");
                        return;
                    }
                    var url = table.options.updateUrl.replace("{id}", row[table.options.uniqueId]);
                    $.modal.open("修改" + table.options.modalName, url);
                } else {
                    $.modal.open("修改" + table.options.modalName, $.operate.editUrl(id));
                }
            },
            // 修改資訊，以tab頁展現
            editTab: function(id) {
                table.set();
                $.modal.openTab("修改" + table.options.modalName, $.operate.editUrl(id));
            },
            // 修改資訊 全螢幕
            editFull: function(id) {
                table.set();
                var url = "/404.html";
                if ($.common.isNotEmpty(id)) {
                    url = table.options.updateUrl.replace("{id}", id);
                } else {
                    if (table.options.type == table_type.bootstrapTreeTable) {
                        var row = $("#" + table.options.id).bootstrapTreeTable('getSelections')[0];
                        if ($.common.isEmpty(row)) {
                            $.modal.alertWarning("請至少選擇一條紀錄");
                            return;
                        }
                        url = table.options.updateUrl.replace("{id}", row[table.options.uniqueId]);
                    } else {
                        var row = $.common.isEmpty(table.options.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns(table.options.uniqueId);
                        url = table.options.updateUrl.replace("{id}", row);
                    }
                }
                $.modal.openFull("修改" + table.options.modalName, url);
            },
            // 修改訪問地址
            editUrl: function(id) {
                var url = "/404.html";
                if ($.common.isNotEmpty(id)) {
                    url = table.options.updateUrl.replace("{id}", id);
                } else {
                    var id = $.common.isEmpty(table.options.uniqueId) ? $.table.selectFirstColumns() : $.table.selectColumns(table.options.uniqueId);
                    if (id.length == 0) {
                        $.modal.alertWarning("請至少選擇一條紀錄");
                        return;
                    }
                    url = table.options.updateUrl.replace("{id}", id);
                }
                return url;
            },
            // 右側彈出詳情
            view: function(id){
                table.set();
                var url = table.options.viewUrl.replace("{id}", id);
                $.modal.popupRight(table.options.modalName + "資訊詳情", url);
            },
            // 保存資訊 刷新表格
            save: function(url, data, callback) {
                var config = {
                    url: url,
                    type: "post",
                    dataType: "json",
                    data: data,
                    beforeSend: function () {
                        $.modal.loading("正在處理中，請稍候...");
                        $.modal.disable();
                    },
                    success: function(result) {
                        if (typeof callback == "function") {
                            callback(result);
                        }
                        $.operate.successCallback(result);
                    }
                };
                $.ajax(config)
            },
            // 保存資訊 彈出結果提示框
            saveModal: function(url, data, callback) {
                var config = {
                    url: url,
                    type: "post",
                    dataType: "json",
                    data: data,
                    beforeSend: function () {
                        $.modal.loading("正在處理中，請稍候...");
                    },
                    success: function(result) {
                        if (typeof callback == "function") {
                            callback(result);
                        }
                        if (result.code == web_status.SUCCESS) {
                            $.modal.alertSuccess(result.msg)
                        } else if (result.code == web_status.WARNING) {
                            $.modal.alertWarning(result.msg)
                        } else {
                            $.modal.alertError(result.msg);
                        }
                        $.modal.closeLoading();
                    }
                };
                $.ajax(config)
            },
            // 保存選項卡資訊
            saveTab: function(url, data, callback) {
                var config = {
                    url: url,
                    type: "post",
                    dataType: "json",
                    data: data,
                    beforeSend: function () {
                        $.modal.loading("正在處理中，請稍候...");
                    },
                    success: function(result) {
                        if (typeof callback == "function") {
                            callback(result);
                        }
                        $.operate.successTabCallback(result);
                    }
                };
                $.ajax(config)
            },
            // 保存結果彈出msg刷新table表格
            ajaxSuccess: function (result) {
                if (result.code == web_status.SUCCESS && table.options.type == table_type.bootstrapTable) {
                    $.modal.msgSuccess(result.msg);
                    $.table.refresh();
                } else if (result.code == web_status.SUCCESS && table.options.type == table_type.bootstrapTreeTable) {
                    $.modal.msgSuccess(result.msg);
                    $.treeTable.refresh();
                } else if (result.code == web_status.SUCCESS && $.common.isEmpty(table.options.type)) {
                    $.modal.msgSuccess(result.msg)
                }  else if (result.code == web_status.WARNING) {
                    $.modal.alertWarning(result.msg)
                }  else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
            },
            // 保存結果重新載入頁面
            saveReload: function (result) {
                if (result.code == web_status.SUCCESS) {
                    $.modal.msgSuccessReload(result.msg);
                } else if (result.code == web_status.WARNING) {
                    $.modal.alertWarning(result.msg)
                }  else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
            },
            // 成功回調執行事件（父窗體靜默更新）
            successCallback: function(result) {
                if (result.code == web_status.SUCCESS) {
                    var parent = activeWindow();
                    if ($.common.isEmpty(parent.table)) {
                    	$.modal.msgSuccessReload(result.msg);
                    } else if (parent.table.options.type == table_type.bootstrapTable) {
                        $.modal.close();
                        parent.$.modal.msgSuccess(result.msg);
                        parent.$.table.refresh();
                    } else if (parent.table.options.type == table_type.bootstrapTreeTable) {
                        $.modal.close();
                        parent.$.modal.msgSuccess(result.msg);
                        parent.$.treeTable.refresh();
                    }
                } else if (result.code == web_status.WARNING) {
                    $.modal.alertWarning(result.msg)
                }  else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
                $.modal.enable();
            },
            // 選項卡成功回調執行事件（父窗體靜默更新）
            successTabCallback: function(result) {
                if (result.code == web_status.SUCCESS) {
                    var topWindow = $(window.parent.document);
                    var currentId = $('.page-tabs-content', topWindow).find('.active').attr('data-panel');
                    var topWindow = $('.RuoYi_iframe[data-id="' + currentId + '"]', topWindow)[0];
                    if ($.common.isNotEmpty(topWindow) && $.common.isNotEmpty(currentId)) {
                    	var $contentWindow = topWindow.contentWindow;
                    	$contentWindow.$.modal.msgSuccess(result.msg);
                        $contentWindow.$(".layui-layer-padding").removeAttr("style");
                        if ($contentWindow.table.options.type == table_type.bootstrapTable) {
                            $contentWindow.$.table.refresh();
                        } else if ($contentWindow.table.options.type == table_type.bootstrapTreeTable) {
                            $contentWindow.$.treeTable.refresh();
                        }
                    } else {
                        $.modal.msgSuccess(result.msg);
                    }
                    $.modal.close();
                    $.modal.closeTab();
                } else if (result.code == web_status.WARNING) {
                    $.modal.alertWarning(result.msg)
                } else {
                    $.modal.alertError(result.msg);
                }
                $.modal.closeLoading();
            }
        },
        // 校驗封裝處理
        validate: {
            // 表單驗證
            form: function (formId) {
                var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                return $("#" + currentId).validate().form();
            },
            // 重設表單驗證（清除提示資訊）
            reset: function (formId) {
                var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
                return $("#" + currentId).validate().resetForm();
            }
        },
        // 樹插件封裝處理
        tree: {
            _option: {},
            _lastValue: {},
            // 初始化樹結構
            init: function(options) {
                var defaults = {
                    id: "tree",                    // 屬性ID
                    expandLevel: 0,                // 展開等級節點
                    view: {
                        selectedMulti: false,      // 設置是否允許同時選中多個節點
                        nameIsHTML: true           // 設置 name 屬性是否支持 HTML 腳本
                    },
                    check: {
                        enable: false,             // 置 zTree 的節點上是否顯示 checkbox / radio
                        nocheckInherit: true,      // 設置子節點是否自動繼承
                        chkboxType: { "Y": "ps", "N": "ps" } // 父子節點的關聯關係
                    },
                    data: {
                        key: {
                            title: "title"         // 節點數據保存節點提示資訊的屬性名稱
                        },
                        simpleData: {
                            enable: true           // true / false 分別表示 使用 / 不使用 簡單數據模式
                        }
                    },
                };
                var options = $.extend(defaults, options);
                $.tree._option = options;
                // 樹結構初始化載入
                var setting = {
                    callback: {
                        onClick: options.onClick,                      // 用於捕獲節點被點擊的事件回調函數
                        onCheck: options.onCheck,                      // 用於捕獲 checkbox / radio 被勾選 或 取消勾選的事件回調函數
                        onDblClick: options.onDblClick                 // 用於捕獲滑鼠雙擊之後的事件回調函數
                    },
                    check: options.check,
                    view: options.view,
                    data: options.data
                };
                $.get(options.url, function(data) {
                    var treeId = $("#treeId").val();
                    tree = $.fn.zTree.init($("#" + options.id), setting, data);
                    $._tree = tree;
                    for (var i = 0; i < options.expandLevel; i++) {
                        var nodes = tree.getNodesByParam("level", i);
                        for (var j = 0; j < nodes.length; j++) {
                            tree.expandNode(nodes[j], true, false, false);
                        }
                    }
                    var node = tree.getNodesByParam("id", treeId, null)[0];
                    $.tree.selectByIdName(treeId, node);
                    // 回調tree方法
                    if (typeof(options.callBack) === "function"){
                        options.callBack(tree);
                    }
                });
            },
            // 搜索節點
            searchNode: function() {
                // 取得輸入的關鍵字的值
                var value = $.common.trim($("#keyword").val());
                if ($.tree._lastValue == value) {
                    return;
                }
                // 保存最後一次搜索名稱
                $.tree._lastValue = value;
                var nodes = $._tree.getNodes();
                // 如果要查空字串，就退出不查了。
                if (value == "") {
                    $.tree.showAllNode(nodes);
                    return;
                }
                $.tree.hideAllNode(nodes);
                // 根據搜索值模糊匹配
                $.tree.updateNodes($._tree.getNodesByParamFuzzy("name", value));
            },
            // 根據Id和Name選中指定節點
            selectByIdName: function(treeId, node) {
                if ($.common.isNotEmpty(treeId) && node && treeId == node.id) {
                    $._tree.selectNode(node, true);
                }
            },
            // 顯示所有節點
            showAllNode: function(nodes) {
                nodes = $._tree.transformToArray(nodes);
                for (var i = nodes.length - 1; i >= 0; i--) {
                    if (nodes[i].getParentNode() != null) {
                        $._tree.expandNode(nodes[i], true, false, false, false);
                    } else {
                        $._tree.expandNode(nodes[i], true, true, false, false);
                    }
                    $._tree.showNode(nodes[i]);
                    $.tree.showAllNode(nodes[i].children);
                }
            },
            // 隱藏所有節點
            hideAllNode: function(nodes) {
                var nodes = $._tree.transformToArray(nodes);
                for (var i = nodes.length - 1; i >= 0; i--) {
                    $._tree.hideNode(nodes[i]);
                }
            },
            // 顯示所有父節點
            showParent: function(treeNode) {
                var parentNode;
                while ((parentNode = treeNode.getParentNode()) != null) {
                    $._tree.showNode(parentNode);
                    $._tree.expandNode(parentNode, true, false, false);
                    treeNode = parentNode;
                }
            },
            // 顯示所有孩子節點
            showChildren: function(treeNode) {
                if (treeNode.isParent) {
                    for (var idx in treeNode.children) {
                        var node = treeNode.children[idx];
                        $._tree.showNode(node);
                        $.tree.showChildren(node);
                    }
                }
            },
            // 更新節點狀態
            updateNodes: function(nodeList) {
                $._tree.showNodes(nodeList);
                for (var i = 0, l = nodeList.length; i < l; i++) {
                    var treeNode = nodeList[i];
                    $.tree.showChildren(treeNode);
                    $.tree.showParent(treeNode)
                }
            },
            // 獲取當前被勾選集合
            getCheckedNodes: function(column) {
                var _column = $.common.isEmpty(column) ? "id" : column;
                var nodes = $._tree.getCheckedNodes(true);
                return $.map(nodes, function (row) {
                    return row[_column];
                }).join();
            },
            // 不允許根父節點選擇
            notAllowParents: function(_tree) {
                var nodes = _tree.getSelectedNodes();
                if (nodes.length == 0){
                    $.modal.msgError("請選擇節點後提交");
                    return false;
                }
                for (var i = 0; i < nodes.length; i++) {
                    if (nodes[i].level == 0) {
                        $.modal.msgError("不能選擇根節點（" + nodes[i].name + "）");
                        return false;
                    }
                    if (nodes[i].isParent) {
                        $.modal.msgError("不能選擇父節點（" + nodes[i].name + "）");
                        return false;
                    }
                }
                return true;
            },
            // 不允許最後層級節點選擇
            notAllowLastLevel: function(_tree) {
                var nodes = _tree.getSelectedNodes();
                for (var i = 0; i < nodes.length; i++) {
                    if (!nodes[i].isParent) {
                        $.modal.msgError("不能選擇最後層級節點（" + nodes[i].name + "）");
                        return false;
                    }
                }
                return true;
            },
            // 隱藏/顯示搜索欄
            toggleSearch: function() {
                $('#search').slideToggle(200);
                $('#btnShow').toggle();
                $('#btnHide').toggle();
                $('#keyword').focus();
            },
            // 摺疊
            collapse: function() {
                $._tree.expandAll(false);
            },
            // 展開
            expand: function() {
                $._tree.expandAll(true);
            }
        },
        // 通用方法封裝處理
        common: {
            // 判斷字串是否為空
            isEmpty: function (value) {
                if (value == null || this.trim(value) == "" || value == undefined || value == "undefined") {
                    return true;
                }
                return false;
            },
            // 判斷一個字串是否為非空串
            isNotEmpty: function (value) {
                return !$.common.isEmpty(value);
            },
            // 如果值是空，則返回指定默認字串，否則返回字串本身
            nullToDefault: function (value, defaultValue) {
                return $.common.isEmpty(value) ? defaultValue : value;
            },
            // 空對象轉字串
            nullToStr: function(value) {
                if ($.common.isEmpty(value)) {
                    return "-";
                }
                return value;
            },
            // 是否顯示數據 為空預設為顯示
            visible: function (value) {
                if ($.common.isEmpty(value) || value == true) {
                    return true;
                }
                return false;
            },
            // 空格截取
            trim: function (value) {
                if (value == null) {
                    return "";
                }
                return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
            },
            // 比較兩個字串（大小寫敏感）
            equals: function (str, that) {
                return str == that;
            },
            // 比較兩個字串（大小寫不敏感）
            equalsIgnoreCase: function (str, that) {
                return String(str).toUpperCase() === String(that).toUpperCase();
            },
            // 將字串按指定字元分割
            split: function (str, sep, maxLen) {
                if ($.common.isEmpty(str)) {
                    return null;
                }
                var value = String(str).split(sep);
                return maxLen ? value.slice(0, maxLen - 1) : value;
            },
            // 字串格式化(%s )
            sprintf: function (str) {
                var args = arguments, flag = true, i = 1;
                str = str.replace(/%s/g, function () {
                    var arg = args[i++];
                    if (typeof arg === 'undefined') {
                        flag = false;
                        return '';
                    }
                    return arg == null ? '' : arg;
                });
                return flag ? str : '';
            },
            // 日期格式化 時間戳  -> yyyy-MM-dd HH-mm-ss
            dateFormat: function(date, format) {
                var that = this;
                if (that.isEmpty(date)) return "";
                if (!date) return;
                if (!format) format = "yyyy-MM-dd";
                switch (typeof date) {
                case "string":
                    date = new Date(date.replace(/-/g, "/"));
                    break;
                case "number":
                    date = new Date(date);
                    break;
                }
                if (!date instanceof Date) return;
                var dict = {
                    "yyyy": date.getFullYear(),
                    "M": date.getMonth() + 1,
                    "d": date.getDate(),
                    "H": date.getHours(),
                    "m": date.getMinutes(),
                    "s": date.getSeconds(),
                    "MM": ("" + (date.getMonth() + 101)).substr(1),
                    "dd": ("" + (date.getDate() + 100)).substr(1),
                    "HH": ("" + (date.getHours() + 100)).substr(1),
                    "mm": ("" + (date.getMinutes() + 100)).substr(1),
                    "ss": ("" + (date.getSeconds() + 100)).substr(1)
                };
                return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g,
                function() {
                    return dict[arguments[0]];
                });
            },
            // 獲取節點數據，支持多層級訪問
            getItemField: function (item, field) {
                var value = item;
                if (typeof field !== 'string' || item.hasOwnProperty(field)) {
                    return item[field];
                }
                var props = field.split('.');
                for (var p in props) {
                    value = value && value[props[p]];
                }
                return value;
            },
            // 指定隨機數返回
            random: function (min, max) {
                return Math.floor((Math.random() * max) + min);
            },
            // 判斷字串是否是以start開頭
            startWith: function(value, start) {
                var reg = new RegExp("^" + start);
                return reg.test(value)
            },
            // 判斷字串是否是以end結尾
            endWith: function(value, end) {
                var reg = new RegExp(end + "$");
                return reg.test(value)
            },
            // 數組去重
            uniqueFn: function(array) {
                var result = [];
                var hashObj = {};
                for (var i = 0; i < array.length; i++) {
                    if (!hashObj[array[i]]) {
                        hashObj[array[i]] = true;
                        result.push(array[i]);
                    }
                }
                return result;
            },
            // 數組中的所有元素放入一個字串
            join: function(array, separator) {
                if ($.common.isEmpty(array)) {
                    return null;
                }
                return array.join(separator);
            },
            // 獲取form下所有的欄位並轉換為json對象
            formToJSON: function(formId) {
                var json = {};
                $.each($("#" + formId).serializeArray(), function(i, field) {
                    if (json[field.name]) {
                        json[field.name] += ("," + field.value);
                    } else {
                        json[field.name] = field.value;
                    }
                });
                return json;
            },
            // 數據字典轉下拉框
            dictToSelect: function(datas, value, name) {
                var actions = [];
                actions.push($.common.sprintf("<select class='form-control' name='%s'>", name));
                $.each(datas, function(index, dict) {
                    actions.push($.common.sprintf("<option value='%s'", dict.dictValue));
                    if (dict.dictValue == ('' + value)) {
                        actions.push(' selected');
                    }
                    actions.push($.common.sprintf(">%s</option>", dict.dictLabel));
                });
                actions.push('</select>');
                return actions.join('');
            },
            // 獲取obj對象長度
            getLength: function(obj) {
                var count = 0;　　
                for (var i in obj) {
                    if (obj.hasOwnProperty(i)) {
                        count++;
                    }　　
                }
                return count;
            },
            // 判斷行動端
            isMobile: function () {
                return navigator.userAgent.match(/(Android|iPhone|SymbianOS|Windows Phone|iPad|iPod)/i);
            },
            // 數字正則表達式，只能為0-9數字
            numValid : function(text){
                var patten = new RegExp(/^[0-9]+$/);
                return patten.test(text);
            },
            // 英文正則表達式，只能為a-z和A-Z字母
            enValid : function(text){
                var patten = new RegExp(/^[a-zA-Z]+$/);
                return patten.test(text);
            },
            // 英文、數字正則表達式，必須包含（字母，數字）
            enNumValid : function(text){
                var patten = new RegExp(/^(?=.*[a-zA-Z]+)(?=.*[0-9]+)[a-zA-Z0-9]+$/);
                return patten.test(text);
            },
            // 英文、數字、特殊字元正則表達式，必須包含（字母，數字，特殊字元!@#$%^&*()-=_+）
            charValid : function(text){
                var patten = new RegExp(/^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#\$%\^&\*\(\)\-=_\+])[A-Za-z\d~!@#\$%\^&\*\(\)\-=_\+]{6,}$/);
                return patten.test(text);
            },
        }
    });
})(jQuery);

/** 表格類型 */
table_type = {
    bootstrapTable: 0,
    bootstrapTreeTable: 1
};

/** 消息狀態碼 */
web_status = {
    SUCCESS: 0,
    FAIL: 500,
    WARNING: 301
};

/** 彈出視窗狀態碼 */
modal_status = {
    SUCCESS: "success",
    FAIL: "error",
    WARNING: "warning"
};