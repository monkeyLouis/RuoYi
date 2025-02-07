package com.ruoyi.web.controller.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 模態窗口
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/demo/modal")
public class DemoDialogController
{
    private String prefix = "demo/modal";

    /**
     * 模態窗口
     */
    @GetMapping("/dialog")
    public String dialog()
    {
        return prefix + "/dialog";
    }

    /**
     * 彈層組件
     */
    @GetMapping("/layer")
    public String layer()
    {
        return prefix + "/layer";
    }

    /**
     * 表單
     */
    @GetMapping("/form")
    public String form()
    {
        return prefix + "/form";
    }

    /**
     * 表格
     */
    @GetMapping("/table")
    public String table()
    {
        return prefix + "/table";
    }

    /**
     * 表格check
     */
    @GetMapping("/check")
    public String check()
    {
        return prefix + "/table/check";
    }

    /**
     * 表格radio
     */
    @GetMapping("/radio")
    public String radio()
    {
        return prefix + "/table/radio";
    }

    /**
     * 表格回傳父窗體
     */
    @GetMapping("/parent")
    public String parent()
    {
        return prefix + "/table/parent";
    }

    /**
     * 多層窗口frame1
     */
    @GetMapping("/frame1")
    public String frame1()
    {
        return prefix + "/table/frame1";
    }

    /**
     * 多層窗口frame2
     */
    @GetMapping("/frame2")
    public String frame2()
    {
        return prefix + "/table/frame2";
    }
}
