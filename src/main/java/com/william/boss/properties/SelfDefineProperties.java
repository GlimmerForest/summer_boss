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
     * 云存储配置
     */
    private CloudConfig cloud;

    @Getter
    @Setter
    public static class CloudConfig {
        /**
         * 账号
         */
        private String accountName;
        /**
         * 密码
         */
        private String accountKey;
        /**
         * 端点
         */
        private String endPoint;
        /**
         * 协议
         */
        private String protocol;
        /**
         * 容器
         */
        private String containerName;
        /**
         * 基础路径
         */
        private String baseUrl;
        /**
         * 类型  azure:微软云
         */
        private String type;
    }
}
