/**
 * 基於bootstrapTreeTable/bootstrap-table-treegrid修改
 * Copyright (c) 2019 ruoyi
 */
(function($) {
    "use strict";

    $.fn.bootstrapTreeTable = function(options, param) {
        var target = $(this).data('bootstrap.tree.table');
        target = target ? target : $(this);
        // 如果是調用方法
        if (typeof options == 'string') {
            return $.fn.bootstrapTreeTable.methods[options](target, param);
        }
        // 如果是初始化組件
        options = $.extend({}, $.fn.bootstrapTreeTable.defaults, options || {});
        target.hasSelectItem = false;    // 是否有radio或checkbox
        target.data_list = null;         // 用於快取格式化後的數據-按父分組
        target.data_obj = null;          // 用於快取格式化後的數據-按id存對象
        target.hiddenColumns = [];       // 用於存放被隱藏列的field
        target.lastAjaxParams;           // 用戶最後一次請求的參數
        target.isFixWidth=false;         // 是否有固定寬度
        target.totalRows = 0;            // 記錄總數
        target.totalPages = 0;           // 總頁數
        // 初始化
        var init = function() {
            // 初始化容器
            initContainer();
            // 初始化工具欄
            initToolbar();
            // 初始化表頭
            initHeader();
            // 初始化表體
            initBody();
            // 初始化數據服務
            initServer();
            // 動態設置表頭寬度
            autoTheadWidth(true);
            // 快取target對象
            target.data('bootstrap.tree.table', target);
        }
        // 初始化容器
        var initContainer = function() {
            // 在外層包裝一下div，樣式用的bootstrap-table的
            var $main_div = $("<div class='bootstrap-tree-table'></div>");
            var $treetable = $("<div class='treetable-table'></div>");
            target.before($main_div);
            $main_div.append($treetable);
            $treetable.append(target);
            target.addClass("table");
            if (options.striped) {
                target.addClass('table-striped');
            }
            if (options.bordered) {
                target.addClass('table-bordered');
            }
            if (options.hover) {
                target.addClass('table-hover');
            }
            if (options.condensed) {
                target.addClass('table-condensed');
            }
            target.html("");
        }
        // 初始化工具欄
        var initToolbar = function() {
            var $toolbar = $("<div class='treetable-bars'></div>");
            if (options.toolbar) {
                $(options.toolbar).addClass('tool-left');
                $toolbar.append($(options.toolbar));
            }
            var $rightToolbar = $('<div class="btn-group tool-right">');
            $toolbar.append($rightToolbar);
            target.parent().before($toolbar);
            // ruoyi 是否顯示檢索資訊
            if (options.showSearch) {
                var $searchBtn = $('<button class="btn btn-default btn-outline" type="button" aria-label="search" title="搜索"><i class="glyphicon glyphicon-search"></i></button>');
                $rightToolbar.append($searchBtn);
                registerSearchBtnClickEvent($searchBtn);
            }
            // 是否顯示刷新按鈕
            if (options.showRefresh) {
                var $refreshBtn = $('<button class="btn btn-default btn-outline" type="button" aria-label="refresh" title="刷新"><i class="glyphicon glyphicon-repeat"></i></button>');
                $rightToolbar.append($refreshBtn);
                registerRefreshBtnClickEvent($refreshBtn);
            }
            // 是否顯示列選項
            if (options.showColumns) {
                var $columns_div = $('<div class="btn-group pull-right" title="列"><button type="button" aria-label="columns" class="btn btn-default btn-outline dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="glyphicon glyphicon-list"></i> <span class="caret"></span></button></div>');
                var $columns_ul = $('<ul class="dropdown-menu columns" role="menu"></ul>');
                $.each(options.columns, function(i, column) {
                    if (column.field != 'selectItem') {
                        var _li = null;
                        if(typeof column.visible == "undefined"||column.visible==true){
                            _li = $('<li role="menuitem"><label><input type="checkbox" checked="checked" data-field="'+column.field+'" value="'+column.field+'" > '+column.title+'</label></li>');
                        }else{
                            _li = $('<li role="menuitem"><label><input type="checkbox" data-field="'+column.field+'" value="'+column.field+'" > '+column.title+'</label></li>');
                            target.hiddenColumns.push(column.field);
                        }
                        $columns_ul.append(_li);
                    }
                });
                $columns_div.append($columns_ul);
                $rightToolbar.append($columns_div);
                // 註冊列選項事件
                registerColumnClickEvent();
            }else{
                $.each(options.columns, function(i, column) {
                    if (column.field != 'selectItem') {
                        if(!(typeof column.visible == "undefined"||column.visible==true)){
                            target.hiddenColumns.push(column.field);
                        }
                    }
                });
            }
        }
        // 初始化隱藏列
        var initHiddenColumns = function(){
            $.each(target.hiddenColumns, function(i, field) {
                target.find("."+field+"_cls").hide();
            });
        }
        // 初始化表頭
        var initHeader = function() {
            var $thr = $('<tr></tr>');
            $.each(options.columns, function(i, column) {
                var $th = null;
                // 判斷有沒有選擇列
                if (i == 0 && column.field == 'selectItem') {
                    target.hasSelectItem = true;
                    $th = $('<th style="width:36px"></th>');
                } else {
                    $th = $('<th style="' + ((column.width) ? ('width:' + column.width + ((column.widthUnit) ? column.widthUnit : 'px')) : '') + '" class="' + column.field + '_cls"></th>');
                    if (column.align) {
                        $th.css("text-align", column.align);
                    }
                }
                if((!target.isFixWidth)&& column.width){
                    target.isFixWidth = column.width.indexOf("px")>-1?true:false;
                }
                $th.html(column.title);
                $thr.append($th);
            });
            var $thead = $('<thead class="treetable-thead"></thead>');
            $thead.append($thr);
            target.append($thead);
        }
        // 初始化表體
        var initBody = function() {
            var $tbody = $('<tbody class="treetable-tbody"></tbody>');
            target.append($tbody);
            // 默認高度
            if (options.height) {
                $tbody.css("height", options.height);
            }
            if (options.pagination) {
                var $pagination = $('<div class="fixed-table-pagination"></div>');
                target.append($pagination);
            }
        }
        // 初始化數據服務
        var initServer = function(parms) {
            if (options.pagination) {
                if(parms == undefined || parms == null) {
                    parms = {};
                }
                parms[options.parentCode] = options.rootIdValue;
            }
            // 載入數據前先清空
            target.data_list = {};
            target.data_obj = {};
            // 設置請求分頁參數
            if (options.pagination) {
                var params = {};
                params.offset = options.pageSize * (options.pageNumber - 1);
                params.limit = options.pageSize;
                var curParams = { pageSize: params.limit, pageNum: params.offset / params.limit + 1 };
                parms = $.extend(curParams, parms);
            }
            var $tbody = target.find("tbody");
            // 添加載入loading
            var $loading = '<tr><td colspan="' + options.columns.length + '"><div style="display: block;text-align: center;">正在努力地載入數據中，請稍候……</div></td></tr>'
            $tbody.html($loading);
            if (options.url) {
                $.ajax({
                    type: options.type,
                    url: options.url,
                    data: $.extend(parms, options.ajaxParams),
                    dataType: "json",
                    success: function(data, textStatus, jqXHR) {
                    	data = calculateObjectValue(options, options.responseHandler, [data], data);
                        renderTable(data);
                        calculateObjectValue(options, options.onLoadSuccess, [data], data);
                    },
                    error: function(xhr, textStatus) {
                        var _errorMsg = '<tr><td colspan="' + options.columns.length + '"><div style="display: block;text-align: center;">' + xhr.responseText + '</div></td></tr>'
                        $tbody.html(_errorMsg);
                    }
                });
            } else {
                renderTable(options.data);
            }
        }
        // 載入完數據後渲染表格
        var renderTable = function(data) {
            var list, totalPage = 0, currPage = 0;
            if (options.pagination) {
            	list = data.rows;  // 數據
                currPage = options.pageNumber; // 當前頁
                totalPage = ~~((data.total - 1) / options.pageSize) + 1 // 總頁數
                target.totalPages  = totalPage;
                target.totalRows = data.total; // 總記錄數
            } else {
            	list = data;
            }
            data = list;
            var $tbody = target.find("tbody");
            // 先清空
            $tbody.html("");
            if (!data || data.length <= 0) {
                var _empty = '<tr><td colspan="' + options.columns.length + '"><div style="display: block;text-align: center;">沒有找到匹配的紀錄</div></td></tr>'
                $tbody.html(_empty);
                options.pageNumber = 1;
                initPagination(0, 0);
                return;
            }
            // 快取並格式化數據
            formatData(data);
            // 獲取所有根節點
            var rootNode = target.data_list["_root_"];
            // 開始繪製
            if (rootNode) {
                $.each(rootNode, function(i, item) {
                    var _child_row_id = "row_id_" + i
                    recursionNode(item, 1, _child_row_id, "row_root", item[options.code]);
                });
            }
            // 下面的操作主要是為了查詢時讓一些沒有根節點的節點顯示
            $.each(data, function(i, item) {
                if (!item.isShow) {
                    var tr = renderRow(item, false, 1, "", "", options.pagination, item[options.code]);
                    $tbody.append(tr);
                }
            });
            registerExpanderEvent();
            registerRowClickEvent();
            initHiddenColumns();
            // 動態設置表頭寬度
            autoTheadWidth();
            if (options.pagination) {
                initPagination(totalPage, currPage);
            }
            // 行動端適配
            var treetableTable = $(target).parent('.treetable-table');
            var availableHeight = treetableTable.outerWidth();
            if($.common.isMobile() || availableHeight < 769){
                var tableStyle = "width: " + availableHeight + "px;overflow: auto;position: relative;";
                treetableTable.attr('style', tableStyle);
                var w = 0;
                $.each(options.columns, function(i, column) {
                    if (i == 0 && column.field == 'selectItem') {
                        w += 36;
                    } else {
                        w += 200;
                    }
                });
                $(target).attr('style','width:' + w +'px');
            }
        }
        // 初始化分頁
        var initPagination = function (totalPage,currPage) {
            var $pagination = target.find(".fixed-table-pagination");
            $pagination.empty();
            var html = [];
            var pageFrom = (options.pageNumber - 1) * options.pageSize + 1;
            var pageTo = options.pageNumber * options.pageSize;
            if (pageTo > target.totalRows) {
                pageTo = target.totalRows;
            }
            if (pageFrom > pageTo) {
                pageFrom = pageTo;
            }
            html.push('<div class="pull-left pagination-detail">');
            html.push('<span class="pagination-info">' + formatShowingRows(pageFrom, pageTo, target.totalRows) + '</span>');
            var pageList = false;
            $.each(options.pageList, function (i, page) {
                if(target.totalRows > page){
                    pageList = true;
                }
            })
            if(pageList){
                var _page_list = [];
                _page_list.push('<span class="page-list">');
                _page_list.push('<span class="btn-group dropup">');
                _page_list.push('<button type="button" class="btn btn-default btn-outline dropdown-toggle" data-toggle="dropdown">');
                _page_list.push('<span class="page-size">' + options.pageSize + '</span>');
                _page_list.push('<span class="caret"></span>');
                _page_list.push('</button>');
                _page_list.push('<ul class="dropdown-menu" role="menu">');
                $.each(options.pageList, function (i, page) {
                    if(page == options.pageSize){
                        _page_list.push('<li class="active"><a href="javascript:void(0)">' + page + '</a></li>');
                    }
                    else if(page >= target.totalRows && i === 1){
                        _page_list.push('<li><a href="javascript:void(0)">' + page + '</a></li>');
                    }
                    else if(page <= target.totalRows){
                        _page_list.push('<li><a href="javascript:void(0)">' + page + '</a></li>');
                    }
                })
                _page_list.push('</ul>');
                _page_list.push('</span>');
                html.push(formatRecordsPerPage(_page_list.join('')))
                html.push('</span>');
            }
            html.push('</div>');

            if(totalPage > 1){
                html.push('<div class="pull-right pagination">');
                html.push('<ul class="pagination pagination-outline">');
                html.push('<li class="page-pre"><a href="javascript:void(0)">' + options.paginationPreText + '</a></li>');
                var from, to;
                if (totalPage < 5) {
                    from = 1;
                    to = totalPage;
                } else {
                    from = currPage - 2;
                    to = from + 4;
                    if (from < 1) {
                        from = 1;
                        to = 5;
                    }
                    if (to > totalPage) {
                        to = totalPage;
                        from = to - 4;
                    }
                }

                if (totalPage >= 6) {
                    if (currPage >= 3) {
                        html.push('<li class="page-first' + (1 == currPage ? ' active' : '') + '">', '<a href="javascript:void(0)">', 1, '</a>', '</li>');
                        from++;
                    }
                    if (currPage >= 4) {
                        if (currPage == 4 || totalPage == 6 || totalPage == 7) {
                            from--;
                        } else {
                            html.push('<li class="page-first-separator disabled">', '<a href="javascript:void(0)">...</a>', '</li>');
                        }
                        to--;
                    }
                }

                if (totalPage >= 7) {
                    if (currPage >= (totalPage - 2)) {
                        from--;
                    }
                }
                if (totalPage == 6) {
                    if (currPage >= (totalPage - 2)) {
                        to++;
                    }
                } else if (totalPage >= 7) {
                    if (totalPage == 7 || currPage >= (totalPage - 3)) {
                        to++;
                    }
                }

                for (var i = from; i <= to; i++) {
                    html.push('<li class="page-number' + (i == currPage ? ' active' : '') + '">', '<a href="javascript:void(0)">', i, '</a>', '</li>');
                }

                if (totalPage >= 8) {
                    if (currPage <= (totalPage - 4)) {
                        html.push('<li class="page-last-separator disabled">', '<a href="javascript:void(0)">...</a>', '</li>');
                    }
                }

                if (totalPage >= 6) {
                    if (currPage <= (totalPage - 3)) {
                        html.push('<li class="page-last' + (totalPage === currPage ? ' active' : '') + '">', '<a href="javascript:void(0)">', totalPage, '</a>', '</li>');
                    }
                }

                html.push('<li class="page-next"><a href="javascript:void(0)">' + options.paginationNextText + '</a></li>');
                html.push('</ul></div>');
            }

            $pagination.append(html.join(''));

            var $pageList = $pagination.find('.page-list a');
            var $pre = $pagination.find('.page-pre');
            var $next = $pagination.find('.page-next');
            var $number = $pagination.find('.page-number');
            var $first = $pagination.find('.page-first');
            var $last = $pagination.find('.page-last');
            $pre.off('click').on('click', $.proxy(onPagePre, this));
            $pageList.off('click').on('click', $.proxy(onPageListChange, this));
            $number.off('click').on('click', $.proxy(onPageNumber, this));
            $first.off('click').on('click', $.proxy(onPageFirst, this));
            $last.off('click').on('click', $.proxy(onPageLast, this));
            $next.off('click').on('click', $.proxy(onPageNext, this));
        }
        var onPageListChange = function(event){
            var $this = $(event.currentTarget);
            $this.parent().addClass('active').siblings().removeClass('active');
            var $pagination = target.find(".fixed-table-pagination");
            options.pageSize = $this.text().toUpperCase() === target.totalRows ? target.totalRows : + $this.text();
            
            if(target.totalRows < options.pageSize * options.pageNumber){
                options.pageNumber = 1;
            }
            $pagination.find('.page-size').text(options.pageSize);
            initServer();
        }
        var onPagePre = function(event){
            if ((options.pageNumber - 1) === 0) {
                options.pageNumber = target.totalPages;
            } else {
                options.pageNumber--;
            }
            initServer();
        }
        var onPageNumber = function(event){
            if (options.pageNumber == $(event.currentTarget).text()) {
                return;
            }
            options.pageNumber = $(event.currentTarget).text();
            initServer();
        }
        var onPageFirst = function(event){
            options.pageNumber = 1;
            initServer();
        }
        var onPageLast = function (event) {
            options.pageNumber = target.totalPages;
            initServer();
        }
        var onPageNext = function(event){
            if ((options.pageNumber + 1) > target.totalPages) {
                options.pageNumber = 1;
            } else {
                options.pageNumber++;
            }
            initServer();
        }
        // 動態設置表頭寬度
        var autoTheadWidth = function(initFlag) {
            if(options.height>0){
                var $thead = target.find("thead");
                var $tbody = target.find("tbody");
                var borderWidth = parseInt(target.css("border-left-width")) + parseInt(target.css("border-right-width"))
                
                $thead.css("width", $tbody.children(":first").width());
                if(initFlag){
                    var resizeWaiter = false;
                    $(window).resize(function() {
                        if(!resizeWaiter){
                            resizeWaiter = true;
                            setTimeout(function(){
                                if(!target.isFixWidth){
                                    $tbody.css("width", target.parent().width()-borderWidth);
                                }
                                $thead.css("width", $tbody.children(":first").width());
                                resizeWaiter = false;
                            }, 300);
                        }
                    });
                }
            }
        
        }
        // 快取並格式化數據
        var formatData = function(data) {
            var _root = options.rootIdValue ? options.rootIdValue : null;
            // 父節點屬性列表
            var parentCodes = [];
            var rootFlag = false;
            $.each(data, function(index, item) {
            	if($.inArray(item[options.parentCode], parentCodes) == -1){
            		parentCodes.push(item[options.parentCode]);
                }
            });
            $.each(data, function(index, item) {
                // 添加一個默認屬性，用來判斷當前節點有沒有被顯示
                item.isShow = false;
                // 是否分頁
                if (options.pagination) {
                    if (item.isTreeLeaf == undefined || item.isTreeLeaf == null) {
                        item.isTreeLeaf = false;
                    } else {
                        item.isTreeLeaf = (item["isTreeLeaf"] == 1 ? true: false) || ((item["isTreeLeaf"] == 'true' || item["isTreeLeaf"] == true) ? true: false);
                    }
                }
                // 頂級節點校驗判斷，相容0,'0','',null
                var _defaultRootFlag = item[options.parentCode] == '0' ||
                item[options.parentCode] == 0 ||
                item[options.parentCode] == null ||
                item[options.parentCode] == '' ||
                $.inArray(item[options.code], parentCodes) > 0 && !rootFlag;
                if (!item[options.parentCode] || (_root ? (item[options.parentCode] == options.rootIdValue) : _defaultRootFlag)) {
                	rootFlag = true;
                	if (!target.data_list["_root_"]) {
                        target.data_list["_root_"] = [];
                    }
                    if (!target.data_obj["id_" + item[options.code]]) {
                        target.data_list["_root_"].push(item);
                    }
                } else {
                    if (!target.data_list["_n_" + item[options.parentCode]]) {
                        target.data_list["_n_" + item[options.parentCode]] = [];
                    }
                    if (!target.data_obj["id_" + item[options.code]]) {
                        target.data_list["_n_" + item[options.parentCode]].push(item);
                    }
                }
                target.data_obj["id_" + item[options.code]] = item;
            });
        }
        // 遞迴獲取子節點並且設置子節點
        var recursionNode = function(parentNode, lv, row_id, p_id, k) {
            var $tbody = target.find("tbody");
            var _ls = target.data_list["_n_" + parentNode[options.code]];
            var $tr = renderRow(parentNode, _ls ? true : false, lv, row_id, p_id, options.pagination, k);
            $tbody.append($tr);
            if (_ls) {
                $.each(_ls, function(i, item) {
                    var _child_row_id = row_id + "_" + i
                    recursionNode(item, (lv + 1), _child_row_id, row_id, item[options.code])
                });
            }
        };
        // 繪製行
        var renderRow = function(item, isP, lv, row_id, p_id, _pagination, k) {
            // 標記已顯示
            item.isShow = true;
            item.row_id = row_id;
            item.p_id = p_id;
            item.lv = lv;
            var $tr = $('<tr id="' + row_id + '" data-id="' + k + '"pid="' + p_id + '"></tr>');
            var _icon = options.expanderCollapsedClass;
            if (options.expandAll) {
                $tr.css("display", "table");
                _icon = options.expanderExpandedClass;
            } else if (lv == 1) {
                $tr.css("display", "table");
                _icon = (options.expandFirst) ? options.expanderExpandedClass : options.expanderCollapsedClass;
            } else if (lv == 2) {
                if (options.expandFirst) {
                    $tr.css("display", "table");
                } else {
                    $tr.css("display", "none");
                }
                _icon = options.expanderCollapsedClass;
            } else if (_pagination) {
                if (item.isTreeLeaf) {
                    _icon = options.expanderCollapsedClass;
                }
            } else {
                $tr.css("display", "none");
                _icon = options.expanderCollapsedClass;
            }
            $.each(options.columns, function(index, column) {
                // 判斷有沒有選擇列
                if (column.field == 'selectItem') {
                    target.hasSelectItem = true;
                    var $td = $('<td style="text-align:center;width:36px"></td>');
                    if (column.radio) {
                        var _ipt = $('<input name="select_item" type="radio" value="' + item[options.code] + '"></input>');
                        $td.append(_ipt);
                    }
                    if (column.checkbox) {
                        var _ipt = $('<input name="select_item" type="checkbox" value="' + item[options.code] + '"></input>');
                        $td.append(_ipt);
                    }
                    $tr.append($td);
                } else {
                    var $td = $('<td name="' + column.field + '" class="' + column.field + '_cls"></td>');
                    if(column.width){
                        $td.css("width",column.width + (column.widthUnit ? column.widthUnit : 'px'));
                    }
                    if(column.align){
                        $td.css("text-align",column.align);
                    }
                    if(options.expandColumn == index){
                        $td.css("text-align","left");
                    }
                    if(column.valign){
                        $td.css("vertical-align",column.valign);
                    }
                    if(options.showTitle){
                        $td.addClass("ellipsis");
                    }
                    // 增加formatter渲染
                    if (column.formatter) {
                        $td.html(column.formatter.call(this, getItemField(item, column.field), item, index));
                    } else {
                        if(options.showTitle){
                            // 只在欄位沒有formatter時才添加title屬性
                            $td.attr("title",item[column.field]);
                        }
                        $td.text(getItemField(item, column.field));
                    }
                    if (options.expandColumn == index) {
                    	if (_pagination) {
                    	    if (item["isTreeLeaf"]) {
                    	        $td.prepend('<span class="treetable-expander ' + _icon + '"></span>');
                    	    } else {
                    	        $td.prepend('<span class="treetable-expander"></span>')
                    	    }
                    	} else {
	                        if (!isP) {
	                            $td.prepend('<span class="treetable-expander"></span>')
	                        } else {
	                            $td.prepend('<span class="treetable-expander ' + _icon + '"></span>');
	                        }
                    	}
                    	for (var int = 0; int < (lv - options.expandColumn); int++) {
                            $td.prepend('<span class="treetable-indent"></span>')
                        }
                    }
                    $tr.append($td);
                }
            });
            return $tr;
        }
        // 檢索資訊按鈕點擊事件
        var registerSearchBtnClickEvent = function(btn) {
            $(btn).off('click').on('click', function () {
                $(".search-collapse").slideToggle();
            });
        }
        // 註冊刷新按鈕點擊事件
        var registerRefreshBtnClickEvent = function(btn) {
            $(btn).off('click').on('click', function () {
                target.refresh();
            });
        }
        // 註冊列選項事件
        var registerColumnClickEvent = function() {
            $(".bootstrap-tree-table .treetable-bars .columns label input").off('click').on('click', function () {
                var $this = $(this);
                if($this.prop('checked')){
                    target.showColumn($(this).val());
                }else{
                    target.hideColumn($(this).val());
                }
            });
        }
        // 註冊行點擊選中事件
        var registerRowClickEvent = function() {
            target.find("tbody").find("tr").unbind();
            target.find("tbody").find("tr").click(function() {
                if (target.hasSelectItem) {
                    var _ipt = $(this).find("input[name='select_item']");
                    if (_ipt.attr("type") == "radio") {
                        _ipt.prop('checked', true);
                        target.find("tbody").find("tr").removeClass("treetable-selected");
                        $(this).addClass("treetable-selected");
                    } else if (_ipt.attr("type") == "checkbox") {
                    	if (_ipt.prop('checked')) {
                    		_ipt.prop('checked', true);
                    		target.find("tbody").find("tr").removeClass("treetable-selected");
                    		$(this).addClass("treetable-selected");
                    	} else {
                    		_ipt.prop('checked', false);
                    		target.find("tbody").find("tr").removeClass("treetable-selected");
                    	}
                    } else {
                        if (_ipt.prop('checked')) {
                            _ipt.prop('checked', false);
                            $(this).removeClass("treetable-selected");
                        } else {
                            _ipt.prop('checked', true);
                            $(this).addClass("treetable-selected");
                        }
                    }
                    var _rowData = target.data_obj["id_" + $(this).data('id')];
                    calculateObjectValue(options, options.onClickRow, [_rowData], _rowData);
                }
            });
        }
        // 註冊小圖示點擊事件--展開縮起
        var registerExpanderEvent = function() {
            target.find("tbody").find("tr").find(".treetable-expander").unbind();
            target.find("tbody").find("tr").find(".treetable-expander").click(function() {
                var _isExpanded = $(this).hasClass(options.expanderExpandedClass);
                var _isCollapsed = $(this).hasClass(options.expanderCollapsedClass);
                if (_isExpanded || _isCollapsed) {
                    var tr = $(this).parent().parent();
                    var row_id = tr.attr("id");
                    var row_pid = tr.attr("pid");
                    var _id = tr.attr("data-id");
                    var _ls = target.find("tbody").find("tr[id^='" + row_id + "_']");
                    if (!options.pagination) {
	                    if (_isExpanded) {
	                        $(this).removeClass(options.expanderExpandedClass);
	                        $(this).addClass(options.expanderCollapsedClass);
	                        if (_ls && _ls.length > 0) {
	                            $.each(_ls, function(index, item) {
	                                $(item).css("display", "none");
	                            });
	                        }
	                    } else {
	                        $(this).removeClass(options.expanderCollapsedClass);
	                        $(this).addClass(options.expanderExpandedClass);
	                        if (_ls && _ls.length > 0) {
	                            $.each(_ls, function(index, item) {
	                                var _p_icon = $("#" + $(item).attr("pid")).children().eq(options.expandColumn).find(".treetable-expander");
	                                var _p_display = $("#" + $(item).attr("pid")).css('display');
	                                if (_p_icon.hasClass(options.expanderExpandedClass) && _p_display == 'table') {
	                                    $(item).css("display", "table");
	                                }
	                            });
	                        }
	                    }
                    } else {
                        var _ls = target.find("tbody").find("tr[id^='" + row_id + "_']");
                        if (_ls && _ls.length > 0) {
                            if (_isExpanded) {
                                if (row_pid == "row_root") {
                                    $('table tr[id^="' + row_id + '_"]').css("display", "none");
                                    $('table tr[id^="' + row_id + '_"]').each(function(i,n) {
                                        var _isExpanded = $(n).find(".treetable-expander").hasClass(options.expanderExpandedClass);
                                        if (_isExpanded) {
                                            $(n).find(".treetable-expander").trigger("click");
                                        }
                                    })
                                } else {
                                    $.each(_ls, function(index, item) {
                                        $(item).css("display", "none");
                                        var _isExpanded = $(item).find(".treetable-expander").hasClass(options.expanderExpandedClass);
                                        if (_isExpanded) {
                                            $(item).find(".treetable-expander").trigger("click");
                                        }
                                     });
                            	}
                            } else {
                                if (row_pid == "row_root") {
                                    $('table tr[pid="' + row_id + '"]').css("display", "table");
                                } else {
	                                $.each(_ls, function(index, item) {
	                                    var _p_icon = $("#" + $(item).attr("pid")).children().eq(options.expandColumn).find(".treetable-expander");
                                        var _isExpanded = _p_icon.hasClass(options.expanderExpandedClass);
                                        var _isCollapsed = _p_icon.hasClass(options.expanderCollapsedClass);
	                                    if (row_id == $(item).attr("pid")) {
		                                    $(item).css("display", "table");
	                                    }
	                                });
                                }
                            }
                        } else {
                            if (options.pagination) {
                                var parms = {};
                                parms[options.parentCode] = _id;
                                if (options.dataUrl) {
                                    $.ajax({
                                        type: options.type,
                                        url: options.dataUrl,
                                        data: parms,
                                        dataType: "json",
                                        success: function(data, textStatus, jqXHR) {
                                            $("#" + row_id + "_load").remove();
                                            var list = data;
                                            data = list;
                                            target.appendData(data)
                                        },
                                        error: function(xhr, textStatus) {
                                            var _errorMsg = '<tr><td colspan="' + options.columns.length + '"><div style="display: block;text-align: center;">' + xhr.responseText + '</div></td></tr>'
                                            $("#" + row_id).after(_errorMsg);
                                        }
                                    });
                                }
                            }
                        }
                        if (_isExpanded) {
                            $(this).removeClass(options.expanderExpandedClass);
                            $(this).addClass(options.expanderCollapsedClass);
                        } else {
                            $(this).removeClass(options.expanderCollapsedClass);
                            $(this).addClass(options.expanderExpandedClass);
                        }
                    }
                }
            });
        }
        // 刷新數據
        target.refresh = function(parms) {
            if(parms){
                target.lastAjaxParams=parms;
            }
            initServer(target.lastAjaxParams);
        }
        // 添加數據刷新表格
        target.appendData = function(data) {
            data.reverse()
            // 下面的操作主要是為了查詢時讓一些沒有根節點的節點顯示
            $.each(data, function(i, item) {
                if (options.pagination) {
                    item.__nodes = (item["nodes"] == 1 ? true: false) || ((item["nodes"] == 'true' || item["nodes"] == true) ? true: false);
                }
                var _data = target.data_obj["id_" + item[options.code]];
                var _p_data = target.data_obj["id_" + item[options.parentCode]];
                var _c_list = target.data_list["_n_" + item[options.parentCode]];
                var row_id = ""; //行id
                var p_id = ""; //父行id
                var _lv = 1; //如果沒有父就是1默認顯示
                var tr; //要添加行的對象
                if (_data && _data.row_id && _data.row_id != "") {
                    row_id = _data.row_id; // 如果已經存在了，就直接引用原來的
                }
                if (_p_data) {
                    p_id = _p_data.row_id;
                    if (row_id == "") {
                        var _tmp = 0
                        if (_c_list && _c_list.length > 0) {
                            _tmp = _c_list.length;
                        }
                        row_id = _p_data.row_id + "_" + _tmp;
                    }
                    _lv = _p_data.lv + 1; //如果有父
                    // 繪製行
                    tr = renderRow(item, true, _lv, row_id, p_id, options.pagination, item[options.code]);

                    var _p_icon = $("#" + _p_data.row_id).children().eq(options.expandColumn).find(".treetable-expander");
                    var _isExpanded = _p_icon.hasClass(options.expanderExpandedClass);
                    var _isCollapsed = _p_icon.hasClass(options.expanderCollapsedClass);
                    // 父節點有沒有展開收縮按鈕
                    if (_isExpanded || _isCollapsed) {
                        // 父節點展開狀態顯示新加行
                        if (_isExpanded) {
                            tr.css("display", "table");
                        }
                    } else {
                        // 父節點沒有展開收縮按鈕則添加
                        _p_icon.addClass(options.expanderCollapsedClass);
                    }

                    if (_data) {
                        $("#" + _data.row_id).before(tr);
                        $("#" + _data.row_id).remove();
                    } else {
                        // 計算父的同級下一行
                        var _tmp_ls = _p_data.row_id.split("_");
                        var _p_next = _p_data.row_id.substring(0, _p_data.row_id.length - (_tmp_ls[_tmp_ls.length - 1] + "").length) + (parseInt(_tmp_ls[_tmp_ls.length - 1]) + 1);
                        $("#" + _p_data.row_id).after(tr);
                    }
                } else {
                    tr = renderRow(item, false, _lv, row_id, p_id, options.pagination, item[options.code]);
                    if (_data) {
                        $("#" + _data.row_id).before(tr);
                        $("#" + _data.row_id).remove();
                    } else {
                        // 畫上
                        var tbody = target.find("tbody");
                        tbody.append(tr);
                    }
                }
                item.isShow = true;
                // 快取並格式化數據
                formatData([item]);
            });
            registerExpanderEvent();
            registerRowClickEvent();
            initHiddenColumns();
        }

        // 展開/摺疊指定的行
        target.toggleRow=function(id) {
            var _rowData = target.data_obj["id_" + id];
            var $row_expander = $("#"+_rowData.row_id).find(".treetable-expander");
            $row_expander.trigger("click");
        }
        // 展開指定的行
        target.expandRow=function(id) {
            var _rowData = target.data_obj["id_" + id];
            var $row_expander = $("#"+_rowData.row_id).find(".treetable-expander");
            var _isCollapsed = $row_expander.hasClass(target.options.expanderCollapsedClass);
            if (_isCollapsed) {
                $row_expander.trigger("click");
            }
        }
        // 摺疊 指定的行
        target.collapseRow=function(id) {
            var _rowData = target.data_obj["id_" + id];
            var $row_expander = $("#"+_rowData.row_id).find(".treetable-expander");
            var _isExpanded = $row_expander.hasClass(target.options.expanderExpandedClass);
            if (_isExpanded) {
                $row_expander.trigger("click");
            }
        }
        // 展開所有的行
        target.expandAll=function() {
            target.find("tbody").find("tr").find(".treetable-expander").each(function(i,n){
                var _isCollapsed = $(n).hasClass(options.expanderCollapsedClass);
                if (_isCollapsed) {
                    $(n).trigger("click");
                }
            })
        }
        // 摺疊所有的行
        target.collapseAll=function() {
            target.find("tbody").find("tr").find(".treetable-expander").each(function(i,n){
                var _isExpanded = $(n).hasClass(options.expanderExpandedClass);
                if (_isExpanded) {
                    $(n).trigger("click");
                }
            })
        }
        // 顯示指定列
        target.showColumn=function(field,flag) {
            var _index = $.inArray(field, target.hiddenColumns);
            if (_index > -1) {
                target.hiddenColumns.splice(_index, 1);
            }
            target.find("."+field+"_cls").show();
            //是否更新列選項狀態
            if(flag&&options.showColumns){
                var $input = $(".bootstrap-tree-table .treetable-bars .columns label").find("input[value='"+field+"']")
                $input.prop("checked", 'checked');
            }
        }
        // 隱藏指定列
        target.hideColumn=function(field,flag) {
            target.hiddenColumns.push(field);
            target.find("."+field+"_cls").hide();
            //是否更新列選項狀態
            if(flag&&options.showColumns){
                var $input = $(".bootstrap-tree-table .treetable-bars .columns label").find("input[value='"+field+"']")
                $input.prop("checked", '');
            }
        }
        // ruoyi 解析數據，支持多層級訪問
        var getItemField = function (item, field) {
            var value = item;

            if (typeof field !== 'string' || item.hasOwnProperty(field)) {
                return item[field];
            }
            var props = field.split('.');
            for (var p in props) {
                value = value && value[props[p]];
            }
            return value;
        };
        // ruoyi 發起對目標(target)函數的調用
        var calculateObjectValue = function (self, name, args, defaultValue) {
            var func = name;

            if (typeof name === 'string') {
                var names = name.split('.');

                if (names.length > 1) {
                    func = window;
                    $.each(names, function (i, f) {
                        func = func[f];
                    });
                } else {
                    func = window[name];
                }
            }
            if (typeof func === 'object') {
                return func;
            }
            if (typeof func === 'function') {
                return func.apply(self, args);
            }
            if (!func && typeof name === 'string' && sprintf.apply(this, [name].concat(args))) {
                return sprintf.apply(this, [name].concat(args));
            }
            return defaultValue;
        };
        var  formatRecordsPerPage =  function (pageNumber) {
            return '每頁顯示 ' + pageNumber + ' 條紀錄';
        };
        var formatShowingRows = function (pageFrom, pageTo, totalRows) {
        	return '顯示第 ' + pageFrom + ' 到第 ' + pageTo + ' 條紀錄，總共 ' + totalRows + ' 條紀錄。';
        };
        // 初始化
        init();
        return target;
    };

    // 組件方法封裝........
    $.fn.bootstrapTreeTable.methods = {
        // 為了相容bootstrap-table的寫法，統一返回數組，這裡返回了表格顯示列的數據
        getSelections: function(target, data) {
            // 所有被選中的紀錄input
            var _ipt = target.find("tbody").find("tr").find("input[name='select_item']:checked");
            var chk_value = [];
            // 如果是radio
            if (_ipt.attr("type") == "radio") {
                var _data = target.data_obj["id_" + _ipt.val()];
                chk_value.push(_data);
            } else {
                _ipt.each(function(_i, _item) {
                    var _data = target.data_obj["id_" + $(_item).val()];
                    chk_value.push(_data);
                });
            }
            return chk_value;
        },
        // 刷新紀錄
        refresh: function(target, parms) {
            if (parms) {
                target.refresh(parms);
            } else {
                target.refresh();
            }
        },
        // 添加數據到表格
        appendData: function(target, data) {
            if (data) {
                target.appendData(data);
            }
        },
        // 展開/摺疊指定的行
        toggleRow: function(target, id) {
            target.toggleRow(id);
        },
        // 展開指定的行
        expandRow: function(target, id) {
            target.expandRow(id);
        },
        // 摺疊 指定的行
        collapseRow: function(target, id) {
            target.collapseRow(id);
        },
        // 展開所有的行
        expandAll: function(target) {
            target.expandAll();
        },
        // 摺疊所有的行
        collapseAll: function(target) {
            target.collapseAll();
        },
        // 顯示指定列
        showColumn: function(target,field) {
            target.showColumn(field,true);
        },
        // 隱藏指定列
        hideColumn: function(target,field) {
            target.hideColumn(field,true);
        }
        // 組件的其他方法也可以進行類似封裝........
    };

    $.fn.bootstrapTreeTable.defaults = {
        code: 'code',              // 選取記錄返回的值,用於設置父子關係
        parentCode: 'parentCode',  // 用於設置父子關係
        rootIdValue: 0,            // 設置根節點id值----可指定根節點，預設為null,"",0,"0"
        data: null,                // 構造table的數據集合
        type: "GET",               // 請求數據的ajax類型
        url: null,                 // 請求數據的ajax的url
        ajaxParams: {},            // 請求數據的ajax的data屬性
        expandColumn: 1,           // 在哪一列上面顯示展開按鈕
        expandAll: false,          // 是否全部展開
        expandFirst: true,         // 是否默認第一級展開--expandAll為false時生效
        striped: false,            // 是否各行漸變色
        bordered: false,           // 是否顯示邊框
        hover: true,               // 是否滑鼠懸停
        condensed: false,          // 是否緊縮表格
        columns: [],               // 列
        toolbar: null,             // 頂部工具條
        height: 0,                 // 表格高度
        pagination: false,         // 是否顯示分頁
        dataUrl: null,             // 載入子節點非同步請求數據url
        pageNumber: 1,             // 當前頁條數
        pageSize: 10,              // 每頁的紀錄行數
        onClickRow: null,          // 單擊某行事件
        pageList: [10, 25, 50],    // 可供選擇的每頁的行數
        showTitle: true,           // 是否採用title屬性顯示欄位內容（被formatter格式化的欄位不會顯示）
        showSearch: true,          // 是否顯示檢索資訊
        showColumns: true,         // 是否顯示內容列下拉框
        showRefresh: true,         // 是否顯示刷新按鈕
        paginationPreText: '&lsaquo;',
        paginationNextText: '&rsaquo;',
        expanderExpandedClass: 'glyphicon glyphicon-chevron-down',   // 展開的按鈕的圖示
        expanderCollapsedClass: 'glyphicon glyphicon-chevron-right', // 縮起的按鈕的圖示
        responseHandler: function(res) {
            return false;
        },
        onLoadSuccess: function(res) {
            return false;
        }
    };
})(jQuery);