package com.william.boss.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.william.boss.orm.model.SysMenu;
import com.william.boss.orm.model.SysRoleMenu;
import com.william.boss.service.ISysMenuService;
import com.william.boss.service.ISysRoleMenuService;
import com.william.boss.vo.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/boss/menu")
@Api(tags = "权限配置模块")
public class SysMenuController {

    @Resource
    private ISysMenuService menuService;
    @Resource
    private ISysRoleMenuService roleMenuService;

    @ApiOperation("根据id查询权限配置")
    @GetMapping("/{id}")
    public ResponseResult<SysMenu> getMenu(@PathVariable Integer id) {
        return ResponseResult.success(this.menuService.getById(id));
    }

    @ApiOperation("根据角色查询权限配置")
    @GetMapping
    public ResponseResult<List<SysMenu>> getMenuByRole(Integer roleId) {
        List<SysRoleMenu> sysRoleMenus = this.roleMenuService.list(new QueryWrapper<SysRoleMenu>()
                .select(SysRoleMenu.MENU_ID).eq(SysRoleMenu.ROLE_ID, roleId));
        List<Integer> menuIds = sysRoleMenus.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
        return ResponseResult.success(this.menuService.list(new QueryWrapper<SysMenu>().in(SysMenu.ID, menuIds)));
    }

    @ApiOperation("编辑权限配置")
    @PutMapping
    public ResponseResult<Boolean> editMenu(@RequestBody SysMenu menu) {
        return ResponseResult.success(this.menuService.updateById(menu));
    }

    @ApiOperation("添加权限配置")
    @PostMapping
    public ResponseResult<Boolean> addMenu(@RequestBody SysMenu menu) {
        return ResponseResult.success(this.menuService.save(menu));
    }

    @ApiOperation("删除权限配置")
    @DeleteMapping("/{id}")
    public ResponseResult<Boolean> delMenu(@PathVariable Integer id) {
        return ResponseResult.success(this.menuService.removeById(id));
    }

    @ApiOperation("权限树")
    @GetMapping("/list")
    public ResponseResult<List<SysMenu>> list(@ApiParam("权限名称") @RequestParam(required = false) String name) {

        List<SysMenu> sysMenus = this.menuService.list(new QueryWrapper<SysMenu>().like(name != null, SysMenu.NAME, name));

        return ResponseResult.success(sysMenus);
    }

}
