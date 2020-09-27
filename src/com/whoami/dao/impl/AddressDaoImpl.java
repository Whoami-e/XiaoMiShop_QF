package com.whoami.dao.impl;

import com.whoami.dao.AddressDao;
import com.whoami.entity.Address;
import com.whoami.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class AddressDaoImpl implements AddressDao {

    /**
     * 查询用户的收货地址
     * @param uid
     * @return
     */
    @Override
    public List<Address> seleteAddressByUid(int uid) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "select a_id as aid, u_id as uid, a_name as aname, a_phone " +
                "as aphone, a_detail as adetail, a_state as astate from " +
                "address where u_id = ? order by a_state desc;";

        List<Address> list = queryRunner.query(sql, new BeanListHandler<Address>(Address.class),uid);

        return list;
    }

    /**
     * 添加地址
     * @param address
     * @throws SQLException
     */
    @Override
    public void insertAddress(Address address) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "insert into address (u_id,a_name,a_phone,a_detail,a_state) value(?,?,?,?,?)";

        queryRunner.update(sql,address.getUid(),address.getAname(),address.getAphone(),address.getAdetail(),address.getAstate());
    }

    /**
     * 删除地址
     * @param aid
     */
    @Override
    public void deleteAddressByAid(String aid) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "delete from address where a_id = ?;";

        queryRunner.update(sql,aid);
    }

    /**
     * 设为默认地址
     * @param aid
     */
    @Override
    public void updateAddressToDefault(String aid) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "update address set a_state = 1 where a_id = ?";

        queryRunner.update(sql,aid);
    }

    /**
     * 设为普通地址
     * @param aid
     * @param uid
     */
    @Override
    public void updateAddressToCommons(String aid, int uid) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "update address set a_state = 0 where a_id != ? and u_id = ?";

        queryRunner.update(sql,aid,uid);
    }

    /**
     * 修改地址
     * @param address
     */
    @Override
    public void updateAddressByAid(Address address) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "update address set a_state = ?, a_name = ?, a_phone = ?, a_detail = ? where a_id = ? ";

        queryRunner.update(sql,address.getAstate(),address.getAname(),address.getAphone(),address.getAdetail(),address.getAid());
    }
}
