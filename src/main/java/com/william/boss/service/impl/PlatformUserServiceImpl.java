package com.william.boss.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.william.boss.orm.dao.PlatformUserMapper;
import com.william.boss.orm.dao.SysUserRoleMapper;
import com.william.boss.orm.model.PlatformUser;
import com.william.boss.orm.model.SysUserRole;
import com.william.boss.service.IPlatformUserService;
import com.william.boss.utils.BeanUtil;
import com.william.boss.vo.user.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author john
 */
@Service
@Slf4j
public class PlatformUserServiceImpl extends ServiceImpl<PlatformUserMapper, PlatformUser> implements IPlatformUserService {

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Override
    public UserVO getUser(String username) {
        PlatformUser user = this.getOne(new QueryWrapper<PlatformUser>().eq(PlatformUser.USERNAME, username));
        if (ObjectUtils.isEmpty(user) || user.getId() == null) {
            return null;
        }

        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO, null);

        List<SysUserRole> roles = this.userRoleMapper.selectList(new QueryWrapper<SysUserRole>()
                .select(SysUserRole.ROLE_ID).eq(SysUserRole.USER_ID, user.getId()));

        List<Integer> roleIds = roles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        userVO.setRoles(roleIds);

        return userVO;
    }
}
