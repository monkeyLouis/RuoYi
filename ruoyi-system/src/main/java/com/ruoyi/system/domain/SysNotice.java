package com.ruoyi.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.xss.Xss;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知公告表 sys_notice
 * 
 * @author ruoyi
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class SysNotice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公告ID */
    private Long noticeId;

    /** 公告標題 */
    private String noticeTitle;

    /** 公告類型（1通知 2公告） */
    private String noticeType;

    /** 公告內容 */
    private String noticeContent;

    /** 公告狀態（0正常 1關閉） */
    private String status;

    @Xss(message = "公告標題不能包含腳本字元")
    @NotBlank(message = "公告標題不能為空")
    @Size(min = 0, max = 50, message = "公告標題不能超過50個字元")
    public String getNoticeTitle()
    {
        return noticeTitle;
    }

}
