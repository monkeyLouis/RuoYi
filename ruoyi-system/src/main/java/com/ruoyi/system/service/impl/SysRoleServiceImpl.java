package com.ruoyi.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.SysRoleDept;
import com.ruoyi.system.domain.SysRoleMenu;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysRoleDeptMapper;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.mapper.SysRoleMenuMapper;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.service.ISysRoleService;

/**
 * 角色 業務層處理
 * 
 * @author ruoyi
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService
{
    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Autowired
    private SysRoleDeptMapper roleDeptMapper;

    /**
     * 根據條件分頁查詢角色數據
     * 
     * @param role 角色資訊
     * @return 角色數據集合資訊
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysRole> selectRoleList(SysRole role)
    {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 根據用戶ID查詢權限
     * 
     * @param userId 用戶ID
     * @return 權限列表
     */
    @Override
    public Set<String> selectRoleKeys(Long userId)
    {
        List<SysRole> perms = roleMapper.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根據用戶ID查詢角色
     * 
     * @param userId 用戶ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRolesByUserId(Long userId)
    {
        List<SysRole> userRoles = roleMapper.selectRolesByUserId(userId);
        List<SysRole> roles = selectRoleAll();
        for (SysRole role : roles)
        {
            for (SysRole userRole : userRoles)
            {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue())
                {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }

    /**
     * 查詢所有角色
     * 
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll()
    {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRole());
    }

    /**
     * 通過角色ID查詢角色
     * 
     * @param roleId 角色ID
     * @return 角色對象資訊
     */
    @Override
    public SysRole selectRoleById(Long roleId)
    {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * 通過角色ID刪除角色
     * 
     * @param roleId 角色ID
     * @return 結果
     */
    @Override
    @Transactional
    public boolean deleteRoleById(Long roleId)
    {
        // 刪除角色與菜單關聯
        roleMenuMapper.deleteRoleMenuByRoleId(roleId);
        // 刪除角色與部門關聯
        roleDeptMapper.deleteRoleDeptByRoleId(roleId);
        return roleMapper.deleteRoleById(roleId) > 0 ? true : false;
    }

    /**
     * 批次刪除角色資訊
     * 
     * @param ids 需要刪除的數據ID
     * @throws Exception
     */
    @Override
    @Transactional
    public int deleteRoleByIds(String ids)
    {
        Long[] roleIds = Convert.toLongArray(ids);
        for (Long roleId : roleIds)
        {
            checkRoleAllowed(new SysRole(roleId));
            checkRoleDataScope(roleId);
            SysRole role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0)
            {
                throw new ServiceException(String.format("%1$s已分配,不能刪除", role.getRoleName()));
            }
        }
        // 刪除角色與菜單關聯
        roleMenuMapper.deleteRoleMenu(roleIds);
        // 刪除角色與部門關聯
        roleDeptMapper.deleteRoleDept(roleIds);
        return roleMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 新增保存角色資訊
     * 
     * @param role 角色資訊
     * @return 結果
     */
    @Override
    @Transactional
    public int insertRole(SysRole role)
    {
        // 新增角色資訊
        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    /**
     * 修改保存角色資訊
     * 
     * @param role 角色資訊
     * @return 結果
     */
    @Override
    @Transactional
    public int updateRole(SysRole role)
    {
        // 修改角色資訊
        roleMapper.updateRole(role);
        // 刪除角色與菜單關聯
        roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }

    /**
     * 修改數據權限資訊
     * 
     * @param role 角色資訊
     * @return 結果
     */
    @Override
    @Transactional
    public int authDataScope(SysRole role)
    {
        // 修改角色資訊
        roleMapper.updateRole(role);
        // 刪除角色與部門關聯
        roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
        // 新增角色和部門資訊（數據權限）
        return insertRoleDept(role);
    }

    /**
     * 新增角色菜單資訊
     * 
     * @param role 角色對象
     */
    public int insertRoleMenu(SysRole role)
    {
        int rows = 1;
        // 新增用戶與角色管理
        List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
        for (Long menuId : role.getMenuIds())
        {
            SysRoleMenu rm = new SysRoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0)
        {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }

    /**
     * 新增角色部門資訊(數據權限)
     *
     * @param role 角色對象
     */
    public int insertRoleDept(SysRole role)
    {
        int rows = 1;
        // 新增角色與部門（數據權限）管理
        List<SysRoleDept> list = new ArrayList<SysRoleDept>();
        for (Long deptId : role.getDeptIds())
        {
            SysRoleDept rd = new SysRoleDept();
            rd.setRoleId(role.getRoleId());
            rd.setDeptId(deptId);
            list.add(rd);
        }
        if (list.size() > 0)
        {
            rows = roleDeptMapper.batchRoleDept(list);
        }
        return rows;
    }

    /**
     * 校驗角色名稱是否唯一
     * 
     * @param role 角色資訊
     * @return 結果
     */
    @Override
    public boolean checkRoleNameUnique(SysRole role)
    {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校驗角色權限是否唯一
     * 
     * @param role 角色資訊
     * @return 結果
     */
    @Override
    public boolean checkRoleKeyUnique(SysRole role)
    {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校驗角色是否允許操作
     * 
     * @param role 角色資訊
     */
    @Override
    public void checkRoleAllowed(SysRole role)
    {
        if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin())
        {
            throw new ServiceException("不允許操作超級管理員角色");
        }
    }

    /**
     * 校驗角色是否有數據權限
     * 
     * @param roleIds 角色id
     */
    @Override
    public void checkRoleDataScope(Long... roleIds)
    {
        if (!SysUser.isAdmin(ShiroUtils.getUserId()))
        {
            for (Long roleId : roleIds)
            {
                SysRole role = new SysRole();
                role.setRoleId(roleId);
                List<SysRole> roles = SpringUtils.getAopProxy(this).selectRoleList(role);
                if (StringUtils.isEmpty(roles))
                {
                    throw new ServiceException("沒有權限訪問角色數據！");
                }
            }
        }
    }

    /**
     * 透過角色ID查詢角色使用數量
     * 
     * @param roleId 角色ID
     * @return 結果
     */
    @Override
    public int countUserRoleByRoleId(Long roleId)
    {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    /**
     * 角色狀態修改
     * 
     * @param role 角色資訊
     * @return 結果
     */
    @Override
    public int changeStatus(SysRole role)
    {
        return roleMapper.updateRole(role);
    }

    /**
     * 取消授權用戶角色
     * 
     * @param userRole 用戶和角色關聯資訊
     * @return 結果
     */
    @Override
    public int deleteAuthUser(SysUserRole userRole)
    {
        return userRoleMapper.deleteUserRoleInfo(userRole);
    }

    /**
     * 批次取消授權用戶角色
     * 
     * @param roleId 角色ID
     * @param userIds 需要刪除的用戶數據ID
     * @return 結果
     */
    @Override
    public int deleteAuthUsers(Long roleId, String userIds)
    {
        return userRoleMapper.deleteUserRoleInfos(roleId, Convert.toLongArray(userIds));
    }

    /**
     * 批次選擇授權用戶角色
     * 
     * @param roleId 角色ID
     * @param userIds 需要授權的用戶數據ID
     * @return 結果
     */
    @Override
    public int insertAuthUsers(Long roleId, String userIds)
    {
        Long[] users = Convert.toLongArray(userIds);
        // 新增用戶與角色管理
        List<SysUserRole> list = new ArrayList<SysUserRole>();
        for (Long userId : users)
        {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return userRoleMapper.batchUserRole(list);
    }
}
