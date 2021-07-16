package com.william.boss.auth;

import com.william.boss.enums.ResponseCodeEnum;
import com.william.boss.service.IPlatformUserService;
import com.william.boss.vo.ResponseResult;
import com.william.boss.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author john
 */
@Component("bossUserDetailsService")
@Slf4j
public class BossUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private IPlatformUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 用户信息和认证逻辑不在一个服务时处理逻辑
        ResponseResult<UserVO> response = ResponseResult.success(this.userService.getUser(username));
        if (response.getData() == null) {
            log.info("用户{}不存在", username);
            return null;
        }
        // 提取用户及角色信息
        UserVO user = response.getData();
        if (ResponseCodeEnum.SUCCESS.getCode().equals(response.getCode())) {
            //用户角色封装处理
            List<SimpleGrantedAuthority> authorities = new ArrayList<>(10);
            if (user.getRoles() != null) {
                user.getRoles().forEach(f -> authorities.add(new SimpleGrantedAuthority(String.valueOf(f))));
            }

            return new User(username, user.getPassword(), authorities);
        } else {
            log.info(response.getCode() + ":" + response.getMessage());
        }
        return null;
    }
}
