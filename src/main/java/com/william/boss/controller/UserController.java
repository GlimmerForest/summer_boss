package com.william.boss.controller;

import com.william.boss.dto.UserDTO;
import com.william.boss.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @author john
 */
@Controller
@RequestMapping("/user")
@Slf4j
@Api(tags = "demo")
public class UserController {
    @Resource
    private IUserService userService;

    @GetMapping("/list")
    @ApiOperation("user list")
    private ModelAndView getAllUsers(Model model) {
        model.addAttribute("userList", userService.getAllUsers());
        return new ModelAndView("user/index","userModel",model);
    }

    @GetMapping("/form")
    @ApiOperation("user form")
    private ModelAndView form(Model model, Integer id) {
        UserDTO userInfo = null;
        if (id != null) {
            userInfo = userService.detail(id);
        }

        model.addAttribute("user",userInfo == null ? new UserDTO() : userInfo);
        return new ModelAndView("user/addOrEdit","userModel",model);
    }

    @PostMapping
    @ApiOperation("user add/edit")
    private ModelAndView addOrEdit(@ModelAttribute UserDTO userInfo) {
        userService.addOrEdit(userInfo);
        return new ModelAndView("redirect:/user/list");
    }

    @GetMapping("/delete")
    @ApiOperation("user delete")
    private ModelAndView delete(Integer id) {
        userService.delete(id);
        return new ModelAndView("redirect:/user/list");
    }
}
