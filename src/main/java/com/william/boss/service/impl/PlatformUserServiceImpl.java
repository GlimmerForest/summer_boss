package com.william.boss.service.impl;

import com.william.boss.orm.dao.PlatformUserMapper;
import com.william.boss.orm.model.PlatformUser;
import com.william.boss.service.IPlatformUserService;
import com.william.boss.utils.BeanUtil;
import com.william.boss.utils.JsonUtil;
import com.william.boss.vo.user.UserQueryParams;
import com.william.boss.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author john
 */
@Service
@Slf4j
public class PlatformUserServiceImpl implements IPlatformUserService {

    @Resource
    private PlatformUserMapper userMapper;


    @Override
    public void save(UserVO userVO) {
        PlatformUser user = new PlatformUser();
        BeanUtil.copyProperties(userVO, user, null, true);

        Object context = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(context, null, null);

        if (context instanceof UserDetails) {
//            (UserDetails)context;
        }

//        AuthenticationProvider


    }

    @Override
    public void modify(UserVO userVO) {

    }

    @Override
    public UserVO getUser(Integer id, String username) {
        return null;
    }

    @Override
    public List<UserVO> getUsers(UserQueryParams params) {
        return null;
    }

    @Override
    public Integer getUsersCount(UserQueryParams params) {
        return null;
    }
}
