package com.william.boss.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author john
 */
@ConfigurationProperties(prefix = "self-define-properties")
@Getter
@Setter
@Component
public class SelfDefineProperties {
    /**
     * 静态资源配置路径
     */
    private String[] resourceBasePaths;

    /**
     * 国际化配置文件路径
     */
    private String messageBasePath;
}
