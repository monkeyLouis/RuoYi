package com.ruoyi.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import com.ruoyi.common.utils.poi.ExcelHandlerAdapter;

/**
 * 自訂導出Excel數據註解
 * 
 * @author ruoyi
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel
{
    /**
     * 導出時在excel中排序
     */
    public int sort() default Integer.MAX_VALUE;

    /**
     * 導出到Excel中的名字.
     */
    public String name() default "";

    /**
     * 日期格式, 如: yyyy-MM-dd
     */
    public String dateFormat() default "";

    /**
     * 如果是字典類型，請設置字典的type值 (如: sys_user_sex)
     */
    public String dictType() default "";

    /**
     * 讀取內容轉表達式 (如: 0=男,1=女,2=未知)
     */
    public String readConverterExp() default "";

    /**
     * 分隔符號，讀取字串組內容
     */
    public String separator() default ",";

    /**
     * BigDecimal 精度 默認:-1(默認不開啟BigDecimal格式化)
     */
    public int scale() default -1;

    /**
     * BigDecimal 捨入規則 默認:BigDecimal.ROUND_HALF_EVEN
     */
    public int roundingMode() default BigDecimal.ROUND_HALF_EVEN;

    /**
     * 導出時在excel中每個列的高度
     */
    public double height() default 14;

    /**
     * 導出時在excel中每個列的寬度
     */
    public double width() default 16;

    /**
     * 文字後綴,如% 90 變成90%
     */
    public String suffix() default "";

    /**
     * 當值為空時,欄位的預設值
     */
    public String defaultValue() default "";

    /**
     * 提示資訊
     */
    public String prompt() default "";

    /**
     * 是否允許內容換行 
     */
    public boolean wrapText() default false;

    /**
     * 設置只能選擇不能輸入的列內容.
     */
    public String[] combo() default {};

    /**
     * 是否從字典讀數據到combo,默認不讀取,如讀取需要設置dictType註解.
     */
    public boolean comboReadDict() default false;

    /**
     * 是否需要縱向合併單元格,應對需求:含有list集合單元格)
     */
    public boolean needMerge() default false;

    /**
     * 是否導出數據,應對需求:有時我們需要導出一份模板,這是標題需要但內容需要用戶手工填寫.
     */
    public boolean isExport() default true;

    /**
     * 另一個類中的屬性名稱,支持多級獲取,以小數點隔開
     */
    public String targetAttr() default "";

    /**
     * 是否自動統計數據,在最後追加一行統計數據總和
     */
    public boolean isStatistics() default false;

    /**
     * 導出類型（0數字 1字串 2圖片）
     */
    public ColumnType cellType() default ColumnType.STRING;

    /**
     * 導出列頭背景顏色
     */
    public IndexedColors headerBackgroundColor() default IndexedColors.GREY_50_PERCENT;

    /**
     * 導出列頭字體顏色
     */
    public IndexedColors headerColor() default IndexedColors.WHITE;

    /**
     * 導出單元格背景顏色
     */
    public IndexedColors backgroundColor() default IndexedColors.WHITE;

    /**
     * 導出單元格字體顏色
     */
    public IndexedColors color() default IndexedColors.BLACK;

    /**
     * 導出欄位對齊方式
     */
    public HorizontalAlignment align() default HorizontalAlignment.CENTER;

    /**
     * 自訂數據處理器
     */
    public Class<?> handler() default ExcelHandlerAdapter.class;

    /**
     * 自訂數據處理器參數
     */
    public String[] args() default {};

    /**
     * 欄位類型（0：導出導入；1：僅導出；2：僅導入）
     */
    Type type() default Type.ALL;

    public enum Type
    {
        ALL(0), EXPORT(1), IMPORT(2);
        private final int value;

        Type(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }

    public enum ColumnType
    {
        NUMERIC(0), STRING(1), IMAGE(2), TEXT(3);
        private final int value;

        ColumnType(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }
}