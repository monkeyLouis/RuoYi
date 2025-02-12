package com.ruoyi.common.utils.security;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ruoyi.common.constant.PermissionConstants;
import com.ruoyi.common.utils.MessageUtils;

/**
 * permission 工具類
 * 
 * @author ruoyi
 */
public class PermissionUtils
{
    private static final Logger log = LoggerFactory.getLogger(PermissionUtils.class);

    /**
     * 查看數據的權限
     */
    public static final String VIEW_PERMISSION = "no.view.permission";

    /**
     * 創建數據的權限
     */
    public static final String CREATE_PERMISSION = "no.create.permission";

    /**
     * 修改數據的權限
     */
    public static final String UPDATE_PERMISSION = "no.update.permission";

    /**
     * 刪除數據的權限
     */
    public static final String DELETE_PERMISSION = "no.delete.permission";

    /**
     * 導出數據的權限
     */
    public static final String EXPORT_PERMISSION = "no.export.permission";

    /**
     * 其他數據的權限
     */
    public static final String PERMISSION = "no.permission";

    /**
     * 權限錯誤消息提醒
     * 
     * @param permissionsStr 錯誤資訊
     * @return 提示資訊
     */
    public static String getMsg(String permissionsStr)
    {
        String permission = StringUtils.substringBetween(permissionsStr, "[", "]");
        String msg = MessageUtils.message(PERMISSION, permission);
        if (StringUtils.endsWithIgnoreCase(permission, PermissionConstants.ADD_PERMISSION))
        {
            msg = MessageUtils.message(CREATE_PERMISSION, permission);
        }
        else if (StringUtils.endsWithIgnoreCase(permission, PermissionConstants.EDIT_PERMISSION))
        {
            msg = MessageUtils.message(UPDATE_PERMISSION, permission);
        }
        else if (StringUtils.endsWithIgnoreCase(permission, PermissionConstants.REMOVE_PERMISSION))
        {
            msg = MessageUtils.message(DELETE_PERMISSION, permission);
        }
        else if (StringUtils.endsWithIgnoreCase(permission, PermissionConstants.EXPORT_PERMISSION))
        {
            msg = MessageUtils.message(EXPORT_PERMISSION, permission);
        }
        else if (StringUtils.endsWithAny(permission,
                new String[] { PermissionConstants.VIEW_PERMISSION, PermissionConstants.LIST_PERMISSION }))
        {
            msg = MessageUtils.message(VIEW_PERMISSION, permission);
        }
        return msg;
    }

    /**
     * 返回用戶屬性值
     *
     * @param property 屬性名稱
     * @return 用戶屬性值
     */
    public static Object getPrincipalProperty(String property)
    {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null)
        {
            Object principal = subject.getPrincipal();
            try
            {
                BeanInfo bi = Introspector.getBeanInfo(principal.getClass());
                for (PropertyDescriptor pd : bi.getPropertyDescriptors())
                {
                    if (pd.getName().equals(property) == true)
                    {
                        return pd.getReadMethod().invoke(principal, (Object[]) null);
                    }
                }
            }
            catch (Exception e)
            {
                log.error("Error reading property [{}] from principal of type [{}]", property,
                        principal.getClass().getName());
            }
        }
        return null;
    }
}
