package com.canace.novel.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.List;

/**
 * @author canace
 * @version 1.0
 * @description 跨域配置
 * @date 2023/11/25 16:48
 */

@ConfigurationProperties(prefix = "novel.cors")
@Data
public class CorsProperties {

    /**
     * 允许跨域的域名
     */
    private List<String> allowOrigins;
}
