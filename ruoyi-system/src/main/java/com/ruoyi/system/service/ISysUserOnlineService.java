package com.ruoyi.system.service;

import java.util.Date;
import java.util.List;
import com.ruoyi.system.domain.SysUserOnline;

/**
 * 在線用戶 服務層
 * 
 * @author ruoyi
 */
public interface ISysUserOnlineService
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
    public void deleteOnlineById(String sessionId);

    /**
     * 通過會話序號刪除資訊
     * 
     * @param sessions 會話ID集合
     * @return 在線用戶資訊
     */
    public void batchDeleteOnline(List<String> sessions);

    /**
     * 保存會話資訊
     * 
     * @param online 會話資訊
     */
    public void saveOnline(SysUserOnline online);

    /**
     * 查詢會話集合
     * 
     * @param userOnline 分頁參數
     * @return 會話集合
     */
    public List<SysUserOnline> selectUserOnlineList(SysUserOnline userOnline);

    /**
     * 強退用戶
     * 
     * @param sessionId 會話ID
     */
    public void forceLogout(String sessionId);

    /**
     * 清理用戶快取
     * 
     * @param loginName 登錄名稱
     * @param sessionId 會話ID
     */
    public void removeUserCache(String loginName, String sessionId);

    /**
     * 查詢會話集合
     * 
     * @param expiredDate 有效期
     * @return 會話集合
     */
    public List<SysUserOnline> selectOnlineByExpired(Date expiredDate);
}
