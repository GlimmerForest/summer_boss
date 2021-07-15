package com.william.boss.properties;

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

    /**
     * 不需要拦截的资源
     */
    private String[] ignoreSources;

    private AuthConfig authConfig;

    @Getter
    @Setter
    public static class AuthConfig {
        /**
         * 接口密码
         */
        private String apiTokenHeader;
        /**
         * 过期时间
         */
        private Integer expire;
    }
}
