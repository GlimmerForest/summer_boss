package com.william.boss.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.william.boss.orm.model.SysRole;
import com.william.boss.orm.model.SysRoleMenu;
import com.william.boss.orm.model.SysUserRole;
import com.william.boss.service.ISysRoleMenuService;
import com.william.boss.service.ISysRoleService;
import com.william.boss.service.ISysUserRoleService;
import com.william.boss.vo.ResponseResult;
import com.william.boss.vo.SimplePage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/boss/role")
public class SysRoleController {

    @Resource
    private ISysRoleService roleService;
    @Resource
    private ISysRoleMenuService roleMenuService;
    @Resource
    private ISysUserRoleService userRoleService;

    @ApiOperation("根据id查询角色")
    @GetMapping("/{id}")
    public ResponseResult<SysRole> getRole(@PathVariable Integer id) {
        return ResponseResult.success(this.roleService.getById(id));
    }

    @ApiOperation("根据用户查询角色")
    @GetMapping
    public ResponseResult<List<SysRole>> getRoleByUser(@PathVariable Integer userId) {

        List<SysUserRole> sysUserRole = this.userRoleService.list(new QueryWrapper<SysUserRole>()
                .select(SysRoleMenu.MENU_ID).eq(SysUserRole.USER_ID, userId));
        List<Integer> roleIds = sysUserRole.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        return ResponseResult.success(this.roleService.list(new QueryWrapper<SysRole>().in(SysRole.ID, roleIds)));
    }

    @ApiOperation("编辑角色")
    @PutMapping
    public ResponseResult<Boolean> editRole(@RequestBody SysRole role) {
        return ResponseResult.success(this.roleService.updateById(role));
    }

    @ApiOperation("添加角色")
    @PostMapping
    public ResponseResult<Boolean> addRole(@RequestBody SysRole role) {
        return ResponseResult.success(this.roleService.save(role));
    }

    @ApiOperation("删除角色")
    @DeleteMapping("/{id}")
    public ResponseResult<Boolean> delRole(@PathVariable Integer id) {
        return ResponseResult.success(this.roleService.removeById(id));
    }

    @ApiOperation("分配角色权限")
    @PostMapping("/distributeAuthority")
    public ResponseResult<Boolean> distributeAuthority(@RequestBody List<SysRoleMenu> roleMenus) {
        return ResponseResult.success(this.roleMenuService.saveBatch(roleMenus));
    }

    @ApiOperation("编辑角色权限")
    @PutMapping("editAuthority")
    @Transactional
    public ResponseResult<Boolean> editAuthority(@RequestBody List<SysRoleMenu> roleMenus) {
        if (roleMenus.size() == 0) {
            return ResponseResult.success(Boolean.TRUE);
        }
        this.roleMenuService.remove(new QueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu.ROLE_ID, roleMenus.get(0).getRoleId()));
        return ResponseResult.success(this.roleMenuService.saveBatch(roleMenus));
    }

    @ApiOperation("分页查询角色")
    @GetMapping("/list")
    public ResponseResult<SimplePage<SysRole>> list(@ApiParam("角色名称") @RequestParam(required = false) String name) {

        IPage<SysRole> page = this.roleService.page(new Page<>(),
                new QueryWrapper<SysRole>().like(name != null, SysRole.NAME, name));

        return ResponseResult.success(page.getTotal(), page.getRecords());
    }

}
