package com.whoami.dao.impl;

import com.whoami.dao.OrdersDao;
import com.whoami.entity.Address;
import com.whoami.entity.Item;
import com.whoami.entity.Orders;
import com.whoami.entity.Product;
import com.whoami.utils.C3P0Utils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrdersDaoImpl implements OrdersDao {

    @Override
    public void insertOrders(Orders orders) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "insert into orders (o_id,a_id,u_id,o_count,o_state,o_time) " +
                "value(?,?,?,?,?,?);";

        queryRunner.update(sql,orders.getOid(),orders.getAid(),orders.getUid(),
                orders.getOcount(),orders.getOstate(),orders.getOtime());
    }

    /**
     * 添加订单项
     * @param items
     */
    @Override
    public void insertItems(List<Item> items) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        Object[][] params = new Object[items.size()][];

        String sql = "insert into item (o_id,p_id,i_count,i_num) value(?,?,?,?);";

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            params[i] = new Object[]{item.getOid(),item.getPid(),item.getIcount(),item.getInum()};
        }

        queryRunner.batch(sql,params);
    }

    /**
     * 查询订单项
     * @param uid
     * @return
     */
    @Override
    public List<Orders> selectOrdersByUid(int uid) throws SQLException, InvocationTargetException, IllegalAccessException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "select o.o_id as oid,o.u_id as uid,o.a_id as aid," +
                "o.o_count as ocount,o.o_time as otime,o.o_state as ostate," +
                "a.a_name as aname,a.a_phone as aphone,a.a_detail as adetail," +
                "a.a_state as astate from address a join orders o on a.a_id = o.a_id " +
                "where o.u_id = ?;";

        List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(), uid);

        if (list == null){
            return null;
        }

        List<Orders> ordersList = new ArrayList<>();

        for (Map<String, Object> map : list) {
            Orders orders = new Orders();
            Address address = new Address();

            BeanUtils.populate(orders,map);
            BeanUtils.populate(address,map);

            orders.setAddress(address);
            ordersList.add(orders);
        }
        return ordersList;
    }

    /**
     * 查询订单
     * @param oid
     * @return
     */
    @Override
    public Orders selectOrdersByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "select o.o_id as oid,o.u_id as uid,o.a_id as aid," +
                "o.o_count as ocount,o.o_time as otime,o.o_state as ostate," +
                "a.a_name as aname,a.a_phone as aphone,a.a_detail as adetail," +
                "a.a_state as astate from address a join orders o on a.a_id = o.a_id " +
                "where o.o_id = ?;";

        Map<String, Object> map = queryRunner.query(sql, new MapHandler(), oid);

        if (map == null){
            return null;
        }

        Orders orders = new Orders();
        Address address = new Address();

        BeanUtils.populate(orders,map);
        BeanUtils.populate(address,map);

        orders.setAddress(address);

        return orders;
    }

    /**
     * 查询订单项
     * @param oid
     * @return
     */
    @Override
    public List<Item> selectItmsByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "select p.p_id as pid,p.t_id as tid,p.p_name as pname,p.p_time as ptime ," +
                "p.p_image as pimage,p.p_state as pstate ,p.p_info as pinfo , p.p_price as pprice, " +
                "i.o_id as oid, i.i_id as iid ,i.i_count as icount ,i.i_num as inum " +
                "from product p join item i on p.p_id = i.p_id where i.o_id = ? ;";

        List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(), oid);

        if (list == null) {
            return null;
        }

        List<Item> items = new ArrayList<>();

        for (Map<String, Object> map : list) {
            Item item = new Item();
            Product product = new Product();

            BeanUtils.populate(item,map);
            BeanUtils.populate(product,map);

            item.setProduct(product);

            items.add(item);
        }

        return items;
    }

    /**
     * 修改订单状态
     * @param oid
     */
    @Override
    public void updateStateByOid(String oid) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "update orders set o_state = ? where o_id = ?;";

        queryRunner.update(sql,2,oid);

    }
}
