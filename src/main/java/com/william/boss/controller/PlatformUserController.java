package com.william.boss.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.william.boss.orm.model.PlatformUser;
import com.william.boss.orm.model.SysUserRole;
import com.william.boss.service.IPlatformUserService;
import com.william.boss.service.ISysUserRoleService;
import com.william.boss.vo.ResponseResult;
import com.william.boss.vo.SimplePage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 平台用户，用于登录系统 前端控制器
 * </p>
 *
 * @author john.wang
 * @since 2021-07-15
 */
@RestController
@RequestMapping("/boss/user")
@Api(tags = "用户管理")
public class PlatformUserController {

    @Resource
    private IPlatformUserService userService;
    @Resource
    private ISysUserRoleService userRoleService;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @ApiOperation("根据id查询用户")
    @GetMapping("/{id}")
    public ResponseResult<PlatformUser> getUser(@PathVariable Integer id) {
        return ResponseResult.success(this.userService.getById(id));
    }

    @ApiOperation("根据角色查询用户")
    @GetMapping
    public ResponseResult<List<PlatformUser>> getUserByRole(@PathVariable Integer roleId) {

        List<SysUserRole> sysUserRole = this.userRoleService.list(new QueryWrapper<SysUserRole>()
                .select(SysUserRole.USER_ID).eq(SysUserRole.ROLE_ID, roleId));
        List<Integer> userIds = sysUserRole.stream().map(SysUserRole::getUserId).collect(Collectors.toList());
        return ResponseResult.success(this.userService.list(new QueryWrapper<PlatformUser>().in(PlatformUser.ID, userIds)));
    }

    @ApiOperation("编辑用户")
    @PutMapping
    public ResponseResult<Boolean> editUser(@RequestBody PlatformUser user) {
        return ResponseResult.success(this.userService.updateById(user));
    }

    @ApiOperation("修改密码")
    @PutMapping("/{id}/passwd")
    public ResponseResult<Boolean> editPasswd(@PathVariable Integer id, @RequestParam String passwd) {
        passwd = passwordEncoder.encode(passwd);
        return ResponseResult.success(this.userService.updateById(new PlatformUser().setId(id).setPassword(passwd)));
    }

    @ApiOperation("添加用户")
    @PostMapping
    public ResponseResult<Boolean> addUser(@RequestBody PlatformUser user) {
        String pwd = passwordEncoder.encode(user.getPassword());
        return ResponseResult.success(this.userService.save(user.setPassword(pwd)));
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    public ResponseResult<Boolean> delUser(@PathVariable Integer id) {
        return ResponseResult.success(this.userService.removeById(id));
    }

    @ApiOperation("分配用户角色")
    @PostMapping("/roles")
    public ResponseResult<Boolean> addUserRoles(@RequestBody List<SysUserRole> userRoles) {
        return ResponseResult.success(this.userRoleService.saveBatch(userRoles));
    }

    @ApiOperation("编辑用户角色")
    @PutMapping("/roles")
    @Transactional
    public ResponseResult<Boolean> editUserRoles(@RequestBody List<SysUserRole> userRoles) {
        if (userRoles.size() == 0) {
            return ResponseResult.success(Boolean.TRUE);
        }
        this.userRoleService.remove(new QueryWrapper<SysUserRole>()
                .eq(SysUserRole.USER_ID, userRoles.get(0).getUserId()));
        return ResponseResult.success(this.userRoleService.saveBatch(userRoles));
    }

    @ApiOperation("分页查询用户")
    @GetMapping("/list")
    public ResponseResult<SimplePage<PlatformUser>> list(@ApiParam("角色名称") @RequestParam(required = false) String name) {

        IPage<PlatformUser> page = this.userService.page(new Page<>(),
                new QueryWrapper<PlatformUser>().like(name != null, PlatformUser.NICKNAME, name));

        return ResponseResult.success(page.getTotal(), page.getRecords());
    }

}

