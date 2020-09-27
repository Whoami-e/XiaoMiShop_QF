package com.whoami.dao;

import com.whoami.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    long selectCountByTid(String tid) throws SQLException;

    List<Product> selectProductByPag(int page, int pageSize, String tid) throws SQLException;

    Product selectProductByPid(String pid) throws SQLException;
}
