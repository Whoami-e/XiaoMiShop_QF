package com.whoami.controller;

import com.whoami.entity.PageBean;
import com.whoami.entity.Product;
import com.whoami.service.ProductService;
import com.whoami.service.impl.ProductServiceImpl;
import com.whoami.utils.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet("/product")
public class ProductController extends BaseServlet{

    /**
     * 商品展示
     * @param request
     * @param response
     * @return
     * @throws SQLException
     */
    public String show(HttpServletRequest request, HttpServletResponse response) throws SQLException {

        //1.接收请求参数 tid 类别ID
        String tid = request.getParameter("tid");
        //获取当前页数和页容量
        int pageSize = 6; //页容量
        String currentPage = request.getParameter("currentPage");//当前页数
        int page = 1;
        if (currentPage != null) {
            page = Integer.parseInt(currentPage);
        }

        //2.调用业务逻辑得到前端需要展示的PageBean
        ProductService productService = new ProductServiceImpl();
        PageBean<Product> pageBean = productService.findPage(tid,page,pageSize);

        //3.响应

        request.setAttribute("pageBean",pageBean);
        return Constants.FORWARD+"/goodsList.jsp";
    }

    public String detail(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        //1.获取请求参数
        String pid = request.getParameter("pid");

        //2.调用业务逻辑
        ProductService productService = new ProductServiceImpl();
        Product product = productService.findProductByPid(pid);

        //3.响应
        request.setAttribute("product",product);
        return Constants.FORWARD+"/goodsDetail.jsp";
    }
}
