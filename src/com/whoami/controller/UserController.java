package com.whoami.controller;

import com.whoami.entity.User;
import com.whoami.service.UserService;
import com.whoami.service.impl.UserServiceImpl;
import com.whoami.utils.Base64Utils;
import com.whoami.utils.Constants;
import com.whoami.utils.MD5Utils;
import com.whoami.utils.RandomUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

/**
 * 用于用户模块的controller
 */
@WebServlet("/user")
public class UserController extends BaseServlet{

    /**
     * 检测用户名是否存在
     */
    public String check(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        //1.获取用户名
        String username = req.getParameter("username");
        if (username == null) {
            return Constants.HAS_USER;
        }
        //2.调用业务逻辑判断用户名是否存在
        UserService userService = new UserServiceImpl();
        boolean b = userService.checkedUser(username);
        //3.响应字符串 1存在 0不存在
        if (b) {
            return Constants.HAS_USER;
        }
        return Constants.NOT_HAS_USER;
    }

    /**
     * 用户注册
     * @param req
     * @param resp
     * @return
     */
    public String register(HttpServletRequest req, HttpServletResponse resp){
        //1.获取用户信息
        Map<String, String[]> parameterMap = req.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        //2.完善用户信息
        /**
         * 已经赋值：用户名  密码  邮箱  性别
         * 没有赋值：激活状态  账号类型  激活码
         */
        user.setUstatus(Constants.NOT_USER_ACTIVE);  // 0未激活状态  1已激活
        user.setUrole(Constants.ROLE_CUSTOMER);    // 0普通客户   1管理员
        user.setCode(RandomUtils.createActive());


        /**
         * 需要处理的属性： 密码 md5加密
         */
        user.setUpassword(MD5Utils.md5(user.getUpassword()));

        //3.调用用户的业务逻辑进行注册
        UserService userService = new UserServiceImpl();
        try {
            userService.registerUser(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            req.setAttribute("registerMsg","注册失败!!!");
            return Constants.FORWARD+"/register.jsp";
        }
        //4.响应
        return Constants.FORWARD+"/registerSuccess.jsp";
    }

    /**
     * 邮箱激活
     * @param req
     * @param resp
     * @return
     */
    public String active(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        //1.获取激活码
        //已经转成Base64
        String c = req.getParameter("c");
        //Base64翻转
        String code = Base64Utils.decode(c);

        //2.调用激活的业务逻辑
        UserService userService = new UserServiceImpl();
        int i = userService.activeUser(code);

        //3.响应（激活失败--》激活码没找到  已经激活 激活成功）
        if(i == 0){
            req.setAttribute("msg","未激活成功!!!");
        }else if(i == 1){
            req.setAttribute("msg","激活成功，请登录!!!");
        }else {
            req.setAttribute("msg","已经激活成功!!!");
        }

        return Constants.FORWARD+"message.jsp";
    }

    /**
     * 1.前端提交账号、密码和验证码
     * 2.对比验证码 成功 --》 对比账号密码
     * 3.对比账号密码
     *      失败--》回到登录页面 进行提示
     *      成功--》未激活 登录页面 进行提示
     *         --》已激活  程序的首页 将用户存到session之中
     */

    public String login(HttpServletRequest request,HttpServletResponse response) throws SQLException {
        //1.获取请求参数（用户名、密码、验证码）
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String code = request.getParameter("code");
        String auto = request.getParameter("auto");
        //2.判断验证码是否正确
        HttpSession session = request.getSession();
        String codestr = (String) session.getAttribute("code");
        if(code == null || !code.equalsIgnoreCase(codestr)){
            request.setAttribute("msg","验证码有误!!!");
            return Constants.FORWARD+"/login.jsp";
        }

        //3.调用业务逻辑判断账号密码
        UserService userService = new UserServiceImpl();
        User user = userService.login(username, password);

        //4.响应
        /**
         * user==null 证明账号或密码错误
         * user != null 但是未激活
         */
        if (user == null) {
            request.setAttribute("msg","账号或密码错误!!!");
            return Constants.FORWARD+"/login.jsp";
        }

        if(user.getUstatus().equals(Constants.NOT_USER_ACTIVE)){
            request.setAttribute("msg","账号未激活!!!");
            return Constants.FORWARD+"/login.jsp";
        }
        session.setAttribute("loginUser",user);

        //判断是否勾选自动登录
        if (auto == null) {
            //没有勾选，将本地浏览器的存储的cookie清空
            Cookie cookie = new Cookie(Constants.AUTO_NAME,"");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }else {
            String content = username+Constants.FLAG+password;
            content = Base64Utils.encode(content);
            Cookie cookie = new Cookie(Constants.AUTO_NAME,content);
            cookie.setPath("/");
            cookie.setMaxAge(14*24*60*60);
            response.addCookie(cookie);
        }

        return Constants.REDIRECT+"/index.jsp";
    }

    /**
     * 注销登录
     */
    public String logOut(HttpServletRequest request,HttpServletResponse response){
        //1.清空sess中的用户数据
        HttpSession session = request.getSession();
        session.removeAttribute("loginUser");
        //2.清空和覆盖cookie存储的自动登录的信息
        Cookie cookie = new Cookie(Constants.AUTO_NAME,"");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        //3.转发到登录页面
        request.setAttribute("msg","注销登录成功!!!");
        return Constants.FORWARD+"/index.jsp";
    }
}
