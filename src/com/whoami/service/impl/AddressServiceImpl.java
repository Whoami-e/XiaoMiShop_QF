package com.whoami.service.impl;

import com.whoami.dao.AddressDao;
import com.whoami.dao.impl.AddressDaoImpl;
import com.whoami.entity.Address;
import com.whoami.service.AddressService;

import java.sql.SQLException;
import java.util.List;

public class AddressServiceImpl implements AddressService {

    /**
     * 查询用户的收货地址
     * @param uid
     * @return
     */
    @Override
    public List<Address> findAddressByUid(int uid) throws SQLException {

        AddressDao addressDao = new AddressDaoImpl();
        List<Address> addresses = addressDao.seleteAddressByUid(uid);

        return addresses;
    }

    /**
     * 添加地址
     * @param address
     */
    @Override
    public void saveAddress(Address address) throws SQLException {

        AddressDao addressDao = new AddressDaoImpl();
        addressDao.insertAddress(address);
    }

    /**
     * 删除地址
     * @param aid
     */
    @Override
    public void deleteAddressByAid(String aid) throws SQLException {

        AddressDao addressDao = new AddressDaoImpl();
        addressDao.deleteAddressByAid(aid);
    }

    /**
     * 设默认地址
     * @param aid
     * @param uid
     */
    @Override
    public void setAddressToDefault(String aid, int uid) throws SQLException {

        AddressDao addressDao = new AddressDaoImpl();

        //设为默认地址
        addressDao.updateAddressToDefault(aid);

        //设其它地址为普通
        addressDao.updateAddressToCommons(aid,uid);
    }

    /**
     * 修改地址
     * @param address
     */
    @Override
    public void updateAddressByAid(Address address) throws SQLException {

        AddressDao addressDao = new AddressDaoImpl();

        addressDao.updateAddressByAid(address);
    }
}
