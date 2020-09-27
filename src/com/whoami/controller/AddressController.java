package com.whoami.controller;

import com.whoami.entity.Address;
import com.whoami.entity.User;
import com.whoami.service.AddressService;
import com.whoami.service.impl.AddressServiceImpl;
import com.whoami.utils.Constants;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/address")
public class AddressController extends BaseServlet{

    /**
     * 展示收货地址
     * @param request
     * @param response
     * @return
     * @throws SQLException
     */
    public String show(HttpServletRequest request, HttpServletResponse response) throws SQLException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            session.setAttribute("msg","请先登录!!!");
            return Constants.REDIRECT+"/login.jsp";
        }

        int uid = user.getUid();

        AddressService addressService = new AddressServiceImpl();
        List<Address> addresses = addressService.findAddressByUid(uid);
        request.setAttribute("list",addresses);
        return Constants.FORWARD+"/self_info.jsp";
    }

    /**
     * 添加地址
     * @param request
     * @param response
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public String add(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, SQLException {

        //1.获取请求参数
        Map<String, String[]> map = request.getParameterMap();
        Address address = new Address();
        BeanUtils.populate(address,map);

        //2.调用业务逻辑添加地址
        AddressService addressService = new AddressServiceImpl();
        addressService.saveAddress(address);

        //3.转发到展示地址方法
        return Constants.FORWARD+"/address?method=show";
    }

    /**
     * 删除地址
     * @param request
     * @param response
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws SQLException
     */
    public String delete(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, SQLException {

        //1.获取请求参数
        String aid = request.getParameter("aid");

        //2.调用业务逻辑添加地址
        AddressService addressService = new AddressServiceImpl();
        addressService.deleteAddressByAid(aid);

        //3.转发到展示地址方法
        return Constants.FORWARD+"/address?method=show";
    }

    /**
     * 设为默认地址
     * @param request
     * @param response
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws SQLException
     */
    public String setDefault(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, SQLException {

        //1.获取请求参数
        String aid = request.getParameter("aid");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            session.setAttribute("msg","请先登录!!!");
            return Constants.REDIRECT+"/login.jsp";
        }

        //2.调用业务逻辑添加地址
        AddressService addressService = new AddressServiceImpl();
        addressService.setAddressToDefault(aid,user.getUid());

        //3.转发到展示地址方法
        return Constants.FORWARD+"/address?method=show";
    }

    /**
     * 修改地址
     * @param request
     * @param response
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws SQLException
     */
    public String update(HttpServletRequest request, HttpServletResponse response) throws InvocationTargetException, IllegalAccessException, SQLException {

        //1.获取请求参数
        Map<String, String[]> map = request.getParameterMap();
        Address address = new Address();
        BeanUtils.populate(address,map);

        //2.调用业务逻辑添加地址
        AddressService addressService = new AddressServiceImpl();
        addressService.updateAddressByAid(address);

        //3.转发到展示地址方法
        return Constants.FORWARD+"/address?method=show";
    }
}
