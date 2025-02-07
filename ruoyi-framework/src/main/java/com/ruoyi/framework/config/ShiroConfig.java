package com.ruoyi.framework.config;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.security.CipherUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.framework.config.properties.PermitAllUrlProperties;
import com.ruoyi.framework.shiro.realm.UserRealm;
import com.ruoyi.framework.shiro.rememberMe.CustomCookieRememberMeManager;
import com.ruoyi.framework.shiro.session.OnlineSessionDAO;
import com.ruoyi.framework.shiro.session.OnlineSessionFactory;
import com.ruoyi.framework.shiro.web.CustomShiroFilterFactoryBean;
import com.ruoyi.framework.shiro.web.filter.LogoutFilter;
import com.ruoyi.framework.shiro.web.filter.captcha.CaptchaValidateFilter;
import com.ruoyi.framework.shiro.web.filter.kickout.KickoutSessionFilter;
import com.ruoyi.framework.shiro.web.filter.online.OnlineSessionFilter;
import com.ruoyi.framework.shiro.web.filter.sync.SyncOnlineSessionFilter;
import com.ruoyi.framework.shiro.web.session.OnlineWebSessionManager;
import com.ruoyi.framework.shiro.web.session.SpringSessionValidationScheduler;
import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * 權限配置載入
 * 
 * @author ruoyi
 */
@Configuration
public class ShiroConfig
{
    /**
     * Session超時時間，單位為毫秒（默認30分鐘）
     */
    @Value("${shiro.session.expireTime}")
    private int expireTime;

    /**
     * 相隔多久檢查一次session的有效性，單位毫秒，默認就是10分鐘
     */
    @Value("${shiro.session.validationInterval}")
    private int validationInterval;

    /**
     * 同一個用戶最大會話數
     */
    @Value("${shiro.session.maxSession}")
    private int maxSession;

    /**
     * 踢出之前登錄的/之後登錄的用戶，默認踢出之前登錄的用戶
     */
    @Value("${shiro.session.kickoutAfter}")
    private boolean kickoutAfter;

    /**
     * 驗證碼開關
     */
    @Value("${shiro.user.captchaEnabled}")
    private boolean captchaEnabled;

    /**
     * 驗證碼類型
     */
    @Value("${shiro.user.captchaType}")
    private String captchaType;

    /**
     * 設置Cookie的域名
     */
    @Value("${shiro.cookie.domain}")
    private String domain;

    /**
     * 設置cookie的有效訪問路徑
     */
    @Value("${shiro.cookie.path}")
    private String path;

    /**
     * 設置HttpOnly屬性
     */
    @Value("${shiro.cookie.httpOnly}")
    private boolean httpOnly;

    /**
     * 設置Cookie的過期時間，秒為單位
     */
    @Value("${shiro.cookie.maxAge}")
    private int maxAge;

    /**
     * 設置cipherKey金鑰
     */
    @Value("${shiro.cookie.cipherKey}")
    private String cipherKey;

    /**
     * 登錄地址
     */
    @Value("${shiro.user.loginUrl}")
    private String loginUrl;

    /**
     * 權限認證失敗地址
     */
    @Value("${shiro.user.unauthorizedUrl}")
    private String unauthorizedUrl;

    /**
     * 是否開啟記住我功能
     */
    @Value("${shiro.rememberMe.enabled: false}")
    private boolean rememberMe;

    /**
     * 快取管理器 使用Ehcache實現
     */
    @Bean
    public EhCacheManager getEhCacheManager()
    {
        net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getCacheManager("ruoyi");
        EhCacheManager em = new EhCacheManager();
        if (StringUtils.isNull(cacheManager))
        {
            em.setCacheManager(new net.sf.ehcache.CacheManager(getCacheManagerConfigFileInputStream()));
            return em;
        }
        else
        {
            em.setCacheManager(cacheManager);
            return em;
        }
    }

    /**
     * 返回設定檔流 避免ehcache設定檔一直被占用，無法完全銷毀項目重新部署
     */
    protected InputStream getCacheManagerConfigFileInputStream()
    {
        String configFile = "classpath:ehcache/ehcache-shiro.xml";
        InputStream inputStream = null;
        try
        {
            inputStream = ResourceUtils.getInputStreamForPath(configFile);
            byte[] b = IOUtils.toByteArray(inputStream);
            InputStream in = new ByteArrayInputStream(b);
            return in;
        }
        catch (IOException e)
        {
            throw new ConfigurationException(
                    "Unable to obtain input stream for cacheManagerConfigFile [" + configFile + "]", e);
        }
        finally
        {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 自訂Realm
     */
    @Bean
    public UserRealm userRealm(EhCacheManager cacheManager)
    {
        UserRealm userRealm = new UserRealm();
        userRealm.setAuthorizationCacheName(Constants.SYS_AUTH_CACHE);
        userRealm.setCacheManager(cacheManager);
        return userRealm;
    }

    /**
     * 自訂sessionDAO會話
     */
    @Bean
    public OnlineSessionDAO sessionDAO()
    {
        OnlineSessionDAO sessionDAO = new OnlineSessionDAO();
        return sessionDAO;
    }

    /**
     * 自訂sessionFactory會話
     */
    @Bean
    public OnlineSessionFactory sessionFactory()
    {
        OnlineSessionFactory sessionFactory = new OnlineSessionFactory();
        return sessionFactory;
    }

    /**
     * 會話管理器
     */
    @Bean
    public OnlineWebSessionManager sessionManager()
    {
        OnlineWebSessionManager manager = new OnlineWebSessionManager();
        // 加入快取管理器
        manager.setCacheManager(getEhCacheManager());
        // 刪除過期的session
        manager.setDeleteInvalidSessions(true);
        // 設置全局session超時時間
        manager.setGlobalSessionTimeout(expireTime * 60 * 1000);
        // 去掉 JSESSIONID
        manager.setSessionIdUrlRewritingEnabled(false);
        // 定義要使用的無效的Session定時調度器
        manager.setSessionValidationScheduler(SpringUtils.getBean(SpringSessionValidationScheduler.class));
        // 是否定時檢查session
        manager.setSessionValidationSchedulerEnabled(true);
        // 自訂SessionDao
        manager.setSessionDAO(sessionDAO());
        // 自訂sessionFactory
        manager.setSessionFactory(sessionFactory());
        return manager;
    }

    /**
     * 安全管理器
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm)
    {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 設置realm.
        securityManager.setRealm(userRealm);
        // 記住我
        securityManager.setRememberMeManager(rememberMe ? rememberMeManager() : null);
        // 注入快取管理器;
        securityManager.setCacheManager(getEhCacheManager());
        // session管理器
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 退出過濾器
     */
    public LogoutFilter logoutFilter()
    {
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setLoginUrl(loginUrl);
        return logoutFilter;
    }

    /**
     * Shiro過濾器配置
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager)
    {
        CustomShiroFilterFactoryBean shiroFilterFactoryBean = new CustomShiroFilterFactoryBean();
        // Shiro的核心安全介面,這個屬性是必須的
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 身份認證失敗，則跳轉到登入頁面的配置
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 權限認證失敗，則跳轉到指定頁面
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        // Shiro連接約束配置，即過濾鏈的定義
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 對靜態資源設置匿名訪問
        filterChainDefinitionMap.put("/favicon.ico**", "anon");
        filterChainDefinitionMap.put("/ruoyi.png**", "anon");
        filterChainDefinitionMap.put("/html/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/docs/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/ajax/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/ruoyi/**", "anon");
        filterChainDefinitionMap.put("/captcha/captchaImage**", "anon");
        // 匿名訪問不鑒權註解列表
        List<String> permitAllUrl = SpringUtils.getBean(PermitAllUrlProperties.class).getUrls();
        if (StringUtils.isNotEmpty(permitAllUrl))
        {
            permitAllUrl.forEach(url -> filterChainDefinitionMap.put(url, "anon"));
        }
        // 退出 logout地址，shiro去清除session
        filterChainDefinitionMap.put("/logout", "logout");
        // 不需要攔截的訪問
        filterChainDefinitionMap.put("/login", "anon,captchaValidate");
        // 註冊相關
        filterChainDefinitionMap.put("/register", "anon,captchaValidate");
        // 系統權限列表
        // filterChainDefinitionMap.putAll(SpringUtils.getBean(IMenuService.class).selectPermsAll());

        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        filters.put("onlineSession", onlineSessionFilter());
        filters.put("syncOnlineSession", syncOnlineSessionFilter());
        filters.put("captchaValidate", captchaValidateFilter());
        filters.put("kickout", kickoutSessionFilter());
        // 註銷成功，則跳轉到指定頁面
        filters.put("logout", logoutFilter());
        shiroFilterFactoryBean.setFilters(filters);

        // 所有請求需要認證
        filterChainDefinitionMap.put("/**", "user,kickout,onlineSession,syncOnlineSession");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 自訂在線用戶處理過濾器
     */
    public OnlineSessionFilter onlineSessionFilter()
    {
        OnlineSessionFilter onlineSessionFilter = new OnlineSessionFilter();
        onlineSessionFilter.setLoginUrl(loginUrl);
        onlineSessionFilter.setOnlineSessionDAO(sessionDAO());
        return onlineSessionFilter;
    }

    /**
     * 自訂在線用戶同步過濾器
     */
    public SyncOnlineSessionFilter syncOnlineSessionFilter()
    {
        SyncOnlineSessionFilter syncOnlineSessionFilter = new SyncOnlineSessionFilter();
        syncOnlineSessionFilter.setOnlineSessionDAO(sessionDAO());
        return syncOnlineSessionFilter;
    }

    /**
     * 自訂驗證碼過濾器
     */
    public CaptchaValidateFilter captchaValidateFilter()
    {
        CaptchaValidateFilter captchaValidateFilter = new CaptchaValidateFilter();
        captchaValidateFilter.setCaptchaEnabled(captchaEnabled);
        captchaValidateFilter.setCaptchaType(captchaType);
        return captchaValidateFilter;
    }

    /**
     * cookie 屬性設置
     */
    public SimpleCookie rememberMeCookie()
    {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        cookie.setDomain(domain);
        cookie.setPath(path);
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge * 24 * 60 * 60);
        return cookie;
    }

    /**
     * 記住我
     */
    public CustomCookieRememberMeManager rememberMeManager()
    {
        CustomCookieRememberMeManager cookieRememberMeManager = new CustomCookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        if (StringUtils.isNotEmpty(cipherKey))
        {
            cookieRememberMeManager.setCipherKey(Base64.decode(cipherKey));
        }
        else
        {
            cookieRememberMeManager.setCipherKey(CipherUtils.generateNewKey(128, "AES").getEncoded());
        }
        return cookieRememberMeManager;
    }

    /**
     * 同一個用戶多設備登錄限制
     */
    public KickoutSessionFilter kickoutSessionFilter()
    {
        KickoutSessionFilter kickoutSessionFilter = new KickoutSessionFilter();
        kickoutSessionFilter.setCacheManager(getEhCacheManager());
        kickoutSessionFilter.setSessionManager(sessionManager());
        // 同一個用戶最大的會話數，默認-1無限制；比如2的意思是同一個用戶允許最多同時兩個人登錄
        kickoutSessionFilter.setMaxSession(maxSession);
        // 是否踢出後來登錄的，預設是false；即後者登錄的用戶踢出前者登錄的用戶；踢出順序
        kickoutSessionFilter.setKickoutAfter(kickoutAfter);
        // 被踢出後重定向到的地址；
        kickoutSessionFilter.setKickoutUrl("/login?kickout=1");
        return kickoutSessionFilter;
    }

    /**
     * thymeleaf模板引擎和shiro框架的整合
     */
    @Bean
    public ShiroDialect shiroDialect()
    {
        return new ShiroDialect();
    }

    /**
     * 開啟Shiro註解通知器
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            @Qualifier("securityManager") SecurityManager securityManager)
    {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
