package com.william.boss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.william.boss.orm.model.PlatformUser;
import com.william.boss.vo.user.UserQueryParams;
import com.william.boss.vo.user.UserVO;

import java.util.List;

/**
 * @author john
 */
public interface IPlatformUserService extends IService<PlatformUser> {

    /**
     * 获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    UserVO getUser(String username);
}
