/**
 * Bootstrap Table Chinese translation
 * Author: Zhixin Wen<wenzhixin2010@gmail.com>
 */
$.fn.bootstrapTable.locales['zh-CN'] = {
  formatShowSearch: function formatShowSearch() {
    return '隱藏/顯示搜索';
  },
  formatPageGo: function formatPageGo() {
    return '跳轉';
  },
  formatCopyRows: function formatCopyRows() {
    return '複製行';
  },
  formatPrint: function formatPrint() {
    return '列印';
  },
  formatLoadingMessage: function formatLoadingMessage() {
    return '正在努力地載入數據中，請稍候';
  },
  formatRecordsPerPage: function formatRecordsPerPage(pageNumber) {
    return "每頁顯示 ".concat(pageNumber, " 條紀錄");
  },
  formatShowingRows: function formatShowingRows(pageFrom, pageTo, totalRows, totalNotFiltered) {
    if (totalNotFiltered !== undefined && totalNotFiltered > 0 && totalNotFiltered > totalRows) {
      return "顯示第 ".concat(pageFrom, " 到第 ").concat(pageTo, " 條紀錄，總共 ").concat(totalRows, " 條紀錄（從 ").concat(totalNotFiltered, " 總記錄中過濾）");
    }
    return "顯示第 ".concat(pageFrom, " 到第 ").concat(pageTo, " 條紀錄，總共 ").concat(totalRows, " 條紀錄");
  },
  formatSRPaginationPreText: function formatSRPaginationPreText() {
    return '上一頁';
  },
  formatSRPaginationPageText: function formatSRPaginationPageText(page) {
    return "第".concat(page, "頁");
  },
  formatSRPaginationNextText: function formatSRPaginationNextText() {
    return '下一頁';
  },
  formatDetailPagination: function formatDetailPagination(totalRows) {
    return "總共 ".concat(totalRows, " 條紀錄");
  },
  formatClearSearch: function formatClearSearch() {
    return '清空過濾';
  },
  formatSearch: function formatSearch() {
    return '搜索';
  },
  formatNoMatches: function formatNoMatches() {
    return '沒有找到匹配的紀錄';
  },
  formatPaginationSwitch: function formatPaginationSwitch() {
    return '隱藏/顯示分頁';
  },
  formatPaginationSwitchDown: function formatPaginationSwitchDown() {
    return '顯示分頁';
  },
  formatPaginationSwitchUp: function formatPaginationSwitchUp() {
    return '隱藏分頁';
  },
  formatRefresh: function formatRefresh() {
    return '刷新';
  },
  formatToggle: function formatToggle() {
    return '切換';
  },
  formatToggleOn: function formatToggleOn() {
    return '顯示卡片視圖';
  },
  formatToggleOff: function formatToggleOff() {
    return '隱藏卡片視圖';
  },
  formatColumns: function formatColumns() {
    return '列';
  },
  formatColumnsToggleAll: function formatColumnsToggleAll() {
    return '切換所有';
  },
  formatFullscreen: function formatFullscreen() {
    return '全螢幕';
  },
  formatAllRows: function formatAllRows() {
    return '所有';
  },
  formatAutoRefresh: function formatAutoRefresh() {
    return '自動刷新';
  },
  formatExport: function formatExport() {
    return '導出數據';
  },
  formatJumpTo: function formatJumpTo() {
    return '跳轉';
  },
  formatAdvancedSearch: function formatAdvancedSearch() {
    return '高級搜索';
  },
  formatAdvancedCloseButton: function formatAdvancedCloseButton() {
    return '關閉';
  },
  formatFilterControlSwitch: function formatFilterControlSwitch() {
    return '隱藏/顯示過濾控制';
  },
  formatFilterControlSwitchHide: function formatFilterControlSwitchHide() {
    return '隱藏過濾控制';
  },
  formatFilterControlSwitchShow: function formatFilterControlSwitchShow() {
    return '顯示過濾控制';
  }
};
$.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);
