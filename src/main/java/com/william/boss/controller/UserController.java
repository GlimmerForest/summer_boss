package com.william.boss.controller;

import com.william.boss.orm.model.UserInfo;
import com.william.boss.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;

/**
 * @author john
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private IUserService userService;

    @GetMapping("/list")
    private ModelAndView getAllUsers(Model model) {
        model.addAttribute("userList", userService.getAllUsers());
        return new ModelAndView("user/index","userModel",model);

    }

    @GetMapping("/form")
    public ModelAndView createForm(Model model, Integer id){
        UserInfo userInfo = null;
        if (id != null) {
            userInfo = userService.detail(id);
        }

        model.addAttribute("user",userInfo == null ? new UserInfo() : userInfo);
        return new ModelAndView("user/addOrEdit","userModel",model);
    }

    @PostMapping
    private ModelAndView addOrEdit(@ModelAttribute UserInfo userInfo) {
        userService.addOrEdit(userInfo);
        return new ModelAndView("redirect:/user/list");
    }

    @RequestMapping("/delete")
    private ModelAndView delete(Integer id) {
        userService.delete(id);
        return new ModelAndView("redirect:/user/list");
    }
}
