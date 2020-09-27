package com.whoami.dao.impl;

import com.whoami.dao.TypeDao;
import com.whoami.entity.Type;
import com.whoami.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class TypeDaoImpl implements TypeDao {

    //商品类别查询
    @Override
    public List<Type> selectAll() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "select t_id as tid ,t_name as tname ,t_info as tinfo from type limit 5;";
        List<Type> list = queryRunner.query(sql, new BeanListHandler<Type>(Type.class));
        return list;
    }
}
