package com.william.boss.auth;

import com.william.boss.enums.ResponseCodeEnum;
import com.william.boss.service.IPlatformUserService;
import com.william.boss.vo.ResponseResult;
import com.william.boss.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author john
 */
@Component("customUserService")
@Slf4j
public class BossUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private IPlatformUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 用户信息和认证逻辑不在一个服务时处理逻辑
        ResponseResult<UserVO> response = new ResponseResult<>(ResponseCodeEnum.SUCCESS, this.userService.getUser(null, username));
        if (response.getData() == null) {
            log.info("用户{}不存在", username);
            return null;
        }
        if (ResponseCodeEnum.SUCCESS.getCode().equals(response.getCode())) {
            try {
                //转化
                UserVO user = response.getData();
                //用户信息处理
                SecurityUser securityUser = new SecurityUser();
                securityUser.setEmail(loginEntity.getData().getManagerUser().getEmail());
                securityUser.setPassword(String.valueOf(loginEntity.getData().getManagerUser().getPassword()));
                securityUser.setSign(loginEntity.getData().getManagerUser().isSign());
                //角色处理
                List<SecurityRole> roleList = new ArrayList<>();
                List<UserLoginEntity.DataBean.RoleListBean> roleListBeans = loginEntity.getData().getRoleList();
                for (UserLoginEntity.DataBean.RoleListBean roleListBean : roleListBeans) {
                    SecurityRole securityRole = new SecurityRole();
                    securityRole.setName(roleListBean.getName());
                    securityRole.setCodeName(roleListBean.getCodeName());
                    roleList.add(securityRole);
                }
                securityUser.setRoleList(roleList);
                return securityUser;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        } else {
            System.out.println(response.getCode() + ":" + response.getMsg());
            return null;
        }
    }
}
