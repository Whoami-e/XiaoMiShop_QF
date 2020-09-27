package com.whoami.controller;

import com.whoami.entity.Cart;
import com.whoami.entity.User;
import com.whoami.service.CartService;
import com.whoami.service.impl.CartServiceImpl;
import com.whoami.utils.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cart")
public class CartController extends BaseServlet{

    /**
     * 创建购物车
     * @param request
     * @param response
     * @return
     * @throws IllegalAccessException
     * @throws SQLException
     * @throws InvocationTargetException
     */
    public String create(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, SQLException, InvocationTargetException {

        //1.判断是否登录
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            session.setAttribute("msg","请先登录!!!!");
            return Constants.REDIRECT+"/login.jsp";
        }

        //2.获取商品id和用户id
        int uid = user.getUid();
        String pid = request.getParameter("pid");

        //3.调用业务逻辑创建购物车
        CartService cartService = new CartServiceImpl();
        cartService.createCart(uid,pid);


        return Constants.FORWARD+"/cartSuccess.jsp";
    }

    /**
     * 查看购物车
     * @param request
     * @param response
     * @return
     */
    public String show(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, SQLException, InvocationTargetException {

        //1.判断是否登录
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            session.setAttribute("msg","请先登录!!!!");
            return Constants.REDIRECT+"/login.jsp";
        }

        //2.获取参数
        int uid = user.getUid();

        //3.调用业务逻辑
        CartService cartService = new CartServiceImpl();
        List<Cart> list = cartService.findAll(uid);
        request.setAttribute("list",list);

        return Constants.FORWARD+"/cart.jsp";
    }

    /**
     * 删除购物车数据
     * @param request
     * @param response
     * @return
     */
    public String delete(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        //1.获取cid
        String cid = request.getParameter("cid");

        //2.调用业务逻辑进行删除
        CartService cartService = new CartServiceImpl();
        cartService.deleteCartByCid(cid);
        //3.转发到展示的处理方法中
        return Constants.FORWARD+"/cart?method=show";
    }

    /**
     * 加减购物车
     * @param request
     * @param response
     * @return
     * @throws SQLException
     */
    public String update(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        //1.获取参数
        String cid = request.getParameter("cid");
        String cnum = request.getParameter("cnum");
        String price = request.getParameter("price");

        //2.调用业务逻辑
        CartService cartService = new CartServiceImpl();
        cartService.updateCartByCid(cid,price,cnum);
        //3.转发到展示的处理方法中
        return Constants.FORWARD+"/cart?method=show";
    }

    /**
     * 清空购物车
     * @param request
     * @param response
     * @return
     * @throws SQLException
     */
    public String clear(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        //1.获取参数
        String uid = request.getParameter("uid");

        //2.调用业务逻辑
        CartService cartService = new CartServiceImpl();
        cartService.clearCartByUid(uid);

        //3.转发到展示的处理方法中
        return Constants.FORWARD+"/cart?method=show";
    }
}
