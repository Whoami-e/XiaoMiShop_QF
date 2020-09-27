package com.whoami.dao.impl;

import com.whoami.dao.CartDao;
import com.whoami.entity.Cart;
import com.whoami.entity.Product;
import com.whoami.utils.C3P0Utils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartDaoImpl implements CartDao {

    /**
     * 判断购物车是否存在
     * @param uid
     * @param pid
     * @return
     */
    @Override
    public Cart hasCart(int uid, String pid) throws SQLException, InvocationTargetException, IllegalAccessException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "select p.p_name as pname,p.p_id as pid,p.t_id as tid," +
                "p.p_time as ptime,p.p_image as pimage,p_state as pstate," +
                "p.p_info as pinfo, p.p_price as pprice,c.c_id as cid,c.u_id as uid,c.c_count as ccount," +
                "c.c_num as cnum from product p join cart c on p.p_id = c.p_id where" +
                " c.u_id = ? and c.p_id = ?;";

        Map<String, Object> query = queryRunner.query(sql, new MapHandler(), uid, pid);

        if (query == null) {
            return null;
        }

        Cart cart = new Cart();
        Product product = new Product();

        BeanUtils.populate(cart,query);
        BeanUtils.populate(product,query);

        cart.setProduct(product);

        return cart;
    }

    /**
     * 如果购物车存在，去修改数据
     * @param cart
     */
    @Override
    public void updateCart(Cart cart) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "update cart set c_num = ? , c_count = ? where c_id = ?";

        queryRunner.update(sql,cart.getCnum(),cart.getCcount(),cart.getCid());
    }

    /**
     * 购物车不存在，即可创建购物车
     * @param cart
     */
    @Override
    public void insertCart(Cart cart) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "insert into cart (u_id,p_id,c_num,c_count) value(?,?,?,?)";

        queryRunner.update(sql,cart.getUid(),cart.getProduct().getPid(),cart.getCnum(),cart.getCcount());
    }

    /**
     * 根据UID查询购物车
     * @param uid 查询条件
     * @return
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
    public List<Cart> selectCartsByUid(int uid) throws SQLException, InvocationTargetException, IllegalAccessException {

        //注意 查询cart时需要关联到product表

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "select p.p_name as pname,p.p_id as pid,p.t_id as tid," +
                "p.p_time as ptime,p.p_image as pimage,p_state as pstate," +
                "p.p_info as pinfo, p.p_price as pprice,c.c_id as cid,c.u_id as uid,c.c_count as ccount," +
                "c.c_num as cnum from product p join cart c on p.p_id = c.p_id where" +
                " c.u_id = ?;";

        List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(), uid);

        if(list == null){
            return null;
        }

        List<Cart> carts = new ArrayList<>();

        for (Map<String, Object> map : list) {
            Cart cart = new Cart();
            Product product = new Product();

            BeanUtils.populate(cart,map);
            BeanUtils.populate(product,map);

            cart.setProduct(product);
            carts.add(cart);
        }

        return carts;
    }

    /**
     * 删除购物车数据
     * @param cid 删除的条件
     */
    @Override
    public void deleteCartByCid(String cid) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "delete from cart where c_id = ?;";

        queryRunner.update(sql,cid);
    }

    /**
     * 加减购物车
     * @param count
     * @param cnumbig
     * @param cid
     */
    @Override
    public void updateCartByCid(BigDecimal count, BigDecimal cnumbig, String cid) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "update cart set c_count = ? , c_num = ? where c_id = ?;";

        queryRunner.update(sql,count,cnumbig,cid);
    }

    /**
     * 清空购物车
     * @param uid
     */
    @Override
    public void deleteCartByUid(String uid) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "delete from cart where u_id = ?;";

        queryRunner.update(sql,uid);
    }
}
