package com.ruoyi.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.annotation.Excel.ColumnType;
import com.ruoyi.common.core.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 參數配置表 sys_config
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SysConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 參數主鍵 */
    @Excel(name = "參數主鍵", cellType = ColumnType.NUMERIC)
    private Long configId;

    /** 參數名稱 */
    @Excel(name = "參數名稱")
    @NotBlank(message = "參數名稱不能為空")
    @Size(min = 0, max = 100, message = "參數名稱不能超過100個字元")
    private String configName;

    /** 參數鍵名 */
    @Excel(name = "參數鍵名")
    @NotBlank(message = "參數鍵名長度不能為空")
    @Size(min = 0, max = 100, message = "參數鍵名長度不能超過100個字元")
    private String configKey;

    /** 參數鍵值 */
    @Excel(name = "參數鍵值")
    @NotBlank(message = "參數鍵值不能為空")
    @Size(min = 0, max = 500, message = "參數鍵值長度不能超過500個字元")
    private String configValue;

    /** 系統內建（Y是 N否） */
    @Excel(name = "系統內建", readConverterExp = "Y=是,N=否")
    private String configType;

}
