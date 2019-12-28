package com.example.demo.model;

/**
 * 用户实体
 *
 * @author SuperZhouDaLu
 * @since 2019-12-28
 */
public class User {

    /**
     * 账户名称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
