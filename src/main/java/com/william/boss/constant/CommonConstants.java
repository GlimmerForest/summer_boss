package com.william.boss.constant;

/**
 * @author john
 */
public interface CommonConstants {
    String JWT_KEY_USER = "currentUser";

    // 请求头用户信息
    String CURRENT_USER_NAME = "currentUserName";
    String CURRENT_USER_ID = "currentUserId";
    /**
     * 同一个请求在不同服务之间传递,方便检索
     */
    String TRACE_ID = "traceId";
    String TENANT = "tenant";

    // 数据记录时间和人
    String CREATE_TIME = "createTime";
    String UPDATE_TIME = "updateTime";
    String CREATOR = "creator";
    String UPDATER = "updater";
    String CREATOR_ID = "creatorId";
    String UPDATER_ID = "updaterId";
    // 泛指成功
    String SUCCESS = "0";

    // 测试配置
    String TOKEN = "token123";
}
