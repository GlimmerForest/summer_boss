package com.william.boss.controller;

import com.william.boss.orm.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.*;

/**
 * @author john
 */
@Slf4j
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private MessageSource messageSource;
    @Resource
    private DataSource dataSource;

    @GetMapping("/1")
    @ResponseBody
    public String echo(String content) {
        log.info(dataSource.toString());
        return content;
    }

    @RequestMapping("/2")
    public ModelAndView demo(Model model, @ModelAttribute UserInfo userInfo, Integer id, String lang) {
        // 测试遍历
        List<UserInfo> userList = new ArrayList<>(10);

        UserInfo user;
        if (userInfo != null && userInfo.getUserId() != null) {
            user = userInfo;
            if (userInfo.getUserId() == null) {userInfo.setUserId(1);}
        } else {
            user = new UserInfo().setUserId(1).setUserName("小白").setGender("男").setMobile("18817191234");
        }

        userList.add(user);

        if (id != null) {
            userList.clear();
        }
        model.addAttribute("userList", userList);
        // 静态文件跳转测试
        String staticPath = "/templates/demo/demo_static.html";
        model.addAttribute("staticPath", staticPath);

        // 其他属性
        model.addAttribute("user", user);
        model.addAttribute("id", 1);
        model.addAttribute("paddingLeft", "40em");
        model.addAttribute("paddingTop", "10px");

        // 复杂th:with 测试
        String arrString = "a,b,c";
        model.addAttribute("arrString", arrString);

        Locale locale = new Locale(lang);
        System.out.println("messageSource : " + messageSource.getMessage("greet", null ,locale));

        return new ModelAndView("demo/demo", "model", model);
    }
}
