package com.whoami.filter;

import com.whoami.entity.User;
import com.whoami.service.UserService;
import com.whoami.service.impl.UserServiceImpl;
import com.whoami.utils.Base64Utils;
import com.whoami.utils.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter("/login.jsp") //默认情况下，过滤器只过滤正常请求和重定向
public class AutoFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            //本地存储了cookie
            String content =null;
            for (Cookie cookie : cookies) {
                //如果找到存储自动登录cookie的名字就读取存储的账号和密码
                if(cookie.getName().equals(Constants.AUTO_NAME)){
                    content = cookie.getValue();
                }
            }

            if (content != null) {
                //读取到了存储用户和密码的cookie
                content = Base64Utils.decode(content);
                /**
                 * 用 : 切割字符串
                 * 例如：wwww:eeeee
                 * 切割成wwww和eeeee两个部分
                 */
                String[] split = content.split(Constants.FLAG);
                String username = split[0];
                String password = split[1];

                UserService userService = new UserServiceImpl();
                try {
                    User user = userService.login(username, password);
                    if (user != null) {
                        //可以自动登录
                        HttpSession session = request.getSession();
                        //把用户信息存储到session之中
                        session.setAttribute("loginUser",user);
                        HttpServletResponse response = (HttpServletResponse) servletResponse;
                        response.sendRedirect(request.getContextPath()+"/index.jsp");
                    }else {
                        filterChain.doFilter(servletRequest,servletResponse);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }else {
                //没有读取到
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }else {
            //本地没有cookie，放行即可
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
