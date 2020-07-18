package com.example.demo.mapper;


import com.example.demo.base.BaseMapper;
import com.example.demo.model.User;

/**
 * @author SuperZhouDaLu
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户昵称查询用户主键
     *
     * @param nickname 用户昵称
     * @return 用户主键
     */
    Integer getUserIdByNickname(String nickname);

}
