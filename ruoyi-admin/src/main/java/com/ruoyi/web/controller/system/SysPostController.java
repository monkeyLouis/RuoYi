package com.ruoyi.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.ISysPostService;

/**
 * 崗位資訊操作處理
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/post")
public class SysPostController extends BaseController
{
    private String prefix = "system/post";

    @Autowired
    private ISysPostService postService;

    @RequiresPermissions("system:post:view")
    @GetMapping()
    public String operlog()
    {
        return prefix + "/post";
    }

    @RequiresPermissions("system:post:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysPost post)
    {
        startPage();
        List<SysPost> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    @Log(title = "崗位管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:post:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysPost post)
    {
        List<SysPost> list = postService.selectPostList(post);
        ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
        return util.exportExcel(list, "崗位數據");
    }

    @RequiresPermissions("system:post:remove")
    @Log(title = "崗位管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(postService.deletePostByIds(ids));
    }

    /**
     * 新增崗位
     */
    @RequiresPermissions("system:post:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存崗位
     */
    @RequiresPermissions("system:post:add")
    @Log(title = "崗位管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysPost post)
    {
        if (!postService.checkPostNameUnique(post))
        {
            return error("新增崗位'" + post.getPostName() + "'失敗，崗位名稱已存在");
        }
        else if (!postService.checkPostCodeUnique(post))
        {
            return error("新增崗位'" + post.getPostName() + "'失敗，崗位編碼已存在");
        }
        post.setCreateBy(getLoginName());
        return toAjax(postService.insertPost(post));
    }

    /**
     * 修改崗位
     */
    @RequiresPermissions("system:post:edit")
    @GetMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, ModelMap mmap)
    {
        mmap.put("post", postService.selectPostById(postId));
        return prefix + "/edit";
    }

    /**
     * 修改保存崗位
     */
    @RequiresPermissions("system:post:edit")
    @Log(title = "崗位管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysPost post)
    {
        if (!postService.checkPostNameUnique(post))
        {
            return error("修改崗位'" + post.getPostName() + "'失敗，崗位名稱已存在");
        }
        else if (!postService.checkPostCodeUnique(post))
        {
            return error("修改崗位'" + post.getPostName() + "'失敗，崗位編碼已存在");
        }
        post.setUpdateBy(getLoginName());
        return toAjax(postService.updatePost(post));
    }

    /**
     * 校驗崗位名稱
     */
    @PostMapping("/checkPostNameUnique")
    @ResponseBody
    public boolean checkPostNameUnique(SysPost post)
    {
        return postService.checkPostNameUnique(post);
    }

    /**
     * 校驗崗位編碼
     */
    @PostMapping("/checkPostCodeUnique")
    @ResponseBody
    public boolean checkPostCodeUnique(SysPost post)
    {
        return postService.checkPostCodeUnique(post);
    }
}
