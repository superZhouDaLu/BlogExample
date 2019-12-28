package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录接口
 *
 * @author SuperZhouDaLu
 * @since 2019-12-28
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    /**
     * 登录接口
     *
     * @param user 用户登录信息
     */
    @PostMapping
    public void login(@RequestBody User user) {
        System.out.println("登录账户：" + user.getUsername() + "-----登录密码：" + user.getPassword());
    }

}
