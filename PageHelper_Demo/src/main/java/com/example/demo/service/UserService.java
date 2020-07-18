package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

/**
 * @author SuperZhouDaLu
 * @date 2020-07-18
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;

    public void queryDemo() {
        // 第一种方式 Example Builder 模式
        Integer builderUserId = userMapper.selectOneByExample(new Example.Builder(User.class)
                .where(WeekendSqls.<User>custom().andEqualTo(User::getNickname, "张三")).build()).getId();
        // 第二种方式 Xml 自定义 Sql 模式
        Integer customSqlUserId = userMapper.getUserIdByNickname("李四");
        System.out.println("queryDemo 执行结果: builderUserId: " + builderUserId + ", customSqlUserId: " + customSqlUserId);
    }

}
