package com.whoami.controller;

import com.whoami.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String methodStr = req.getParameter(Constants.TAG);
        //如果method没有获取到值，就跳转到首页（标识符异常处理）
        if(methodStr == null && methodStr.equals("")){
            methodStr = Constants.INDEX;
        }
        //1.获取类的class对象
        Class clazz = this.getClass();
        //2.获取方法
        /**
         * 参数1：方法名
         * 参数2...：方法参数的类型
         */
        try {
            Method method = clazz.getMethod(methodStr, HttpServletRequest.class, HttpServletResponse.class);
            //3.执行方法
            /**
             * 参数1：要执行的方法对象
             * 参数2..：执行方法传入句的参数
             * 返回值：执行方法的返回值，如果方法为void，返回值为null
             */
             Object result = method.invoke(this,req,resp);

             //集中处理返回值（响应）
             if(result != null){
                 String str = (String)result;
                 if(str.startsWith(Constants.FORWARD)){
                     //转发
                     String path = str.substring(str.indexOf(Constants.FLAG) + 1);
                     req.getRequestDispatcher(path).forward(req,resp);
                 }else if(str.startsWith(Constants.REDIRECT)){
                     //重定向
                     String path = str.substring(str.indexOf(Constants.FLAG) + 1);
                     resp.sendRedirect(path);
                 }else {
                     resp.getWriter().println(str);
                 }
             }
        } catch (Exception e) {
            e.printStackTrace();
            //controller、service或dao有异常都会到此处！！！
            req.getSession().setAttribute("msg","程序异常!请稍后再试!!");
            resp.sendRedirect("/message.jsp");
        }
    }

    /**
     * 当method标识符没有值时我们默认给method赋index，并访问每个controller的index方法
     * 默认处理：跳转到首页
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public String index(HttpServletRequest req,HttpServletResponse resp) throws IOException, ServletException {
        return Constants.FORWARD+"/index.jsp";
    }
}
