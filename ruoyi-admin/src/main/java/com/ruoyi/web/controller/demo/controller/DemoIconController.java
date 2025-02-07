package com.ruoyi.web.controller.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 圖示相關
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/demo/icon")
public class DemoIconController
{
    private String prefix = "demo/icon";

    /**
     * FontAwesome圖示
     */
    @GetMapping("/fontawesome")
    public String fontAwesome()
    {
        return prefix + "/fontawesome";
    }

    /**
     * Glyphicons圖示
     */
    @GetMapping("/glyphicons")
    public String glyphicons()
    {
        return prefix + "/glyphicons";
    }
}
