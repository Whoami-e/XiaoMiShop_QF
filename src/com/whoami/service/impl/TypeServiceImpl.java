package com.whoami.service.impl;

import com.whoami.dao.TypeDao;
import com.whoami.dao.impl.TypeDaoImpl;
import com.whoami.entity.Type;
import com.whoami.service.TypeService;

import java.sql.SQLException;
import java.util.List;

public class TypeServiceImpl implements TypeService {
    //查询商品类别
    @Override
    public List<Type> findAll() throws SQLException {
        TypeDao typeDao = new TypeDaoImpl();
        List<Type> types = typeDao.selectAll();
        return types;
    }
}
