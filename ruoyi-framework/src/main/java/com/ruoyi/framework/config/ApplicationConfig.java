package com.ruoyi.framework.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 程序註解配置
 *
 * @author ruoyi
 */
@Configuration
// 表示通過aop框架暴露該代理對象,AopContext能夠訪問
@EnableAspectJAutoProxy(exposeProxy = true)
// 指定要掃描的Mapper類的包的路徑
@MapperScan("com.ruoyi.**.mapper")
public class ApplicationConfig
{

}
