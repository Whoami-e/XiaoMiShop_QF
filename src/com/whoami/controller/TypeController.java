package com.whoami.controller;

import com.google.gson.Gson;
import com.whoami.entity.Type;
import com.whoami.service.TypeService;
import com.whoami.service.impl.TypeServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 * 商品类别
 */
@WebServlet("/type")
public class TypeController extends BaseServlet{

    public String findAll(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        TypeService typeService  = new TypeServiceImpl();
        List<Type> types = typeService.findAll();

        //转换成json数据

        //创建Gson对象
        Gson gson = new Gson();
        //转成json字符串
        String json = gson.toJson(types);


        return json;
    }
}
