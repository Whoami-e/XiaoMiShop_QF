package com.whoami.dao;

import com.whoami.entity.Type;

import java.sql.SQLException;
import java.util.List;

public interface TypeDao {

    List<Type> selectAll() throws SQLException;
}
