package com.whoami.service.impl;

import com.whoami.dao.UserDao;
import com.whoami.dao.impl.UserDaoImpl;
import com.whoami.entity.User;
import com.whoami.service.UserService;
import com.whoami.utils.Constants;
import com.whoami.utils.EmailUtils;
import com.whoami.utils.MD5Utils;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    @Override
    public boolean checkedUser(String username) throws SQLException {
        //1.创建dao访问对象
        UserDao userDao = new UserDaoImpl();
        //2.执行结果
        User user = userDao.selectUserByUname(username);
        //3.处理返回值
        /**
         * user == null false
         * user != null true
         */
        if (user != null) {
            return true;
        }
        return false;
    }

    @Override
    public int registerUser(User user) throws SQLException {
        /**
         * 执行注册操作
         */
        //1.创建dao访问对象
        UserDao userDao = new UserDaoImpl();
        //2.执行结果
        int row = userDao.insertUser(user);

        /**
         * 执行发送邮件操作
         */
        EmailUtils.sendEmail(user);
        return row;
    }

    @Override
    public int activeUser(String code) throws SQLException {
        //1.根据激活码查找用户
        UserDao userDao = new UserDaoImpl();
        User user = userDao.selectUserByCode(code);
        if (user == null) {
            //激活失败
            return 0;
        }
        //2.判断用户是否激活
        if(user.getUstatus().equals(Constants.USER_ACTIVE)){
            //已激活
            return 2;
        }
        //3.进行激活操作
        int i = userDao.updateStatusByUid(user.getUid());
        if(i>0){
            //激活成功
            return 1;
        }

        return 0;
    }

    @Override
    public User login(String username, String password) throws SQLException {
        //1.对密码进行MD5处理
        String md5password = MD5Utils.md5(password);

        //2.根据用户名查找用户
        UserDao userDao = new UserDaoImpl();
        User user = userDao.selectUserByUname(username);

        if (user != null && user.getUpassword().equals(md5password)) {
            return user;
        }
        return null;
    }
}
