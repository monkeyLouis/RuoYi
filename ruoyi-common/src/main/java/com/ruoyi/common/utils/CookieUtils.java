package com.ruoyi.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具類
 * 
 * @author ruoyi
 */
public class CookieUtils
{
    /**
     * 設置 Cookie（生成時間為1天）
     * 
     * @param name 名稱
     * @param value 值
     */
    public static void setCookie(HttpServletResponse response, String name, String value)
    {
        setCookie(response, name, value, 60 * 60 * 24);
    }

    /**
     * 設置 Cookie
     * 
     * @param name 名稱
     * @param value 值
     * @param maxAge 生存時間（單位秒）
     * @param uri 路徑
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String path)
    {
        setCookie(response, name, value, path, 60 * 60 * 24);
    }

    /**
     * 設置 Cookie
     * 
     * @param name 名稱
     * @param value 值
     * @param maxAge 生存時間（單位秒）
     * @param uri 路徑
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge)
    {
        setCookie(response, name, value, "/", maxAge);
    }

    /**
     * 設置 Cookie
     * 
     * @param name 名稱
     * @param value 值
     * @param maxAge 生存時間（單位秒）
     * @param uri 路徑
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge)
    {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        try
        {
            cookie.setValue(URLEncoder.encode(value, "utf-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        response.addCookie(cookie);
    }

    /**
     * 獲得指定Cookie的值
     * 
     * @param name 名稱
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, String name)
    {
        return getCookie(request, null, name, false);
    }

    /**
     * 獲得指定Cookie的值，並刪除。
     * 
     * @param name 名稱
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name)
    {
        return getCookie(request, response, name, true);
    }

    /**
     * 獲得指定Cookie的值
     * 
     * @param request 請求對象
     * @param response 響應對象
     * @param name 名字
     * @param isRemove 是否移除
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name,
            boolean isRemove)
    {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
        {
            for (Cookie cookie : cookies)
            {
                if (cookie.getName().equals(name))
                {
                    try
                    {
                        value = URLDecoder.decode(cookie.getValue(), "utf-8");
                    }
                    catch (UnsupportedEncodingException e)
                    {
                        e.printStackTrace();
                    }
                    if (isRemove)
                    {
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }
            }
        }
        return value;
    }
}
