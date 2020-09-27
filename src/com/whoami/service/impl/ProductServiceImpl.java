package com.whoami.service.impl;

import com.whoami.dao.ProductDao;
import com.whoami.dao.impl.ProductDaoImpl;
import com.whoami.entity.PageBean;
import com.whoami.entity.Product;
import com.whoami.service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    @Override
    public PageBean<Product> findPage(String tid, int page, int pageSize) throws SQLException {

        ProductDao productDao = new ProductDaoImpl();

        long count = productDao.selectCountByTid(tid);

        List<Product> list = productDao.selectProductByPag(page,pageSize,tid);


        return new PageBean<Product>(list,page,pageSize,count);
    }

    /**
     * 根据商品id查询商品详情
     * @param pid
     * @return
     */
    @Override
    public Product findProductByPid(String pid) throws SQLException {

        ProductDao productDao = new ProductDaoImpl();

        Product product = productDao.selectProductByPid(pid);

        return product;
    }
}
