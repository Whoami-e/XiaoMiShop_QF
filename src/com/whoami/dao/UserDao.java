package com.whoami.dao;

import com.whoami.entity.User;

import java.sql.SQLException;

/**
 * 负责用户模块数据库访问的接口
 */
public interface UserDao {
    /**
     * 根据用户名查询用户是否存在
     * @param username  查询的条件
     * @return 返回对应的用户数据
     */
    User selectUserByUname(String username) throws SQLException;

    /**
     * 往数据库添加用户信息
     * @param user
     * @return
     */
    int insertUser(User user) throws SQLException;

    /**
     * 根据code查找用户
     * @param code
     * @return
     */
    User selectUserByCode(String code) throws SQLException;

    /**
     * 根据用户id更新账号状态
     * @param uid
     * @return
     */
    int updateStatusByUid(int uid) throws SQLException;
}
