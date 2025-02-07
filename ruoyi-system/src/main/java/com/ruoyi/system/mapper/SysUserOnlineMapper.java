package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysUserOnline;

/**
 * 在線用戶 數據層
 * 
 * @author ruoyi
 */
public interface SysUserOnlineMapper
{
    /**
     * 通過會話序號查詢資訊
     * 
     * @param sessionId 會話ID
     * @return 在線用戶資訊
     */
    public SysUserOnline selectOnlineById(String sessionId);

    /**
     * 通過會話序號刪除資訊
     * 
     * @param sessionId 會話ID
     * @return 在線用戶資訊
     */
    public int deleteOnlineById(String sessionId);

    /**
     * 保存會話資訊
     * 
     * @param online 會話資訊
     * @return 結果
     */
    public int saveOnline(SysUserOnline online);

    /**
     * 查詢會話集合
     * 
     * @param userOnline 會話參數
     * @return 會話集合
     */
    public List<SysUserOnline> selectUserOnlineList(SysUserOnline userOnline);

    /**
     * 查詢過期會話集合
     * 
     * @param lastAccessTime 過期時間
     * @return 會話集合
     */
    public List<SysUserOnline> selectOnlineByExpired(String lastAccessTime);
}
