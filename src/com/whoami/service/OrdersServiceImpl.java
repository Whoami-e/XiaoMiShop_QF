package com.whoami.service;

import com.whoami.dao.CartDao;
import com.whoami.dao.OrdersDao;
import com.whoami.dao.impl.CartDaoImpl;
import com.whoami.dao.impl.OrdersDaoImpl;
import com.whoami.entity.Cart;
import com.whoami.entity.Item;
import com.whoami.entity.Orders;
import com.whoami.utils.RandomUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdersServiceImpl implements OrdersService{

    @Override
    public void createOrder(String aid, String uid, String sum) throws IllegalAccessException, SQLException, InvocationTargetException {

        //1.创建一个订单对象
        Orders orders = new Orders();
        BigDecimal bsum = new BigDecimal(sum);
        String orderId = RandomUtils.createOrderId();
        orders.setOid(orderId);
        orders.setAid(Integer.parseInt(aid));
        orders.setUid(Integer.parseInt(uid));
        orders.setOtime(new Date());
        orders.setOcount(bsum);
        orders.setOstate(1); //未支付状态

        //2.保存订单
        OrdersDao ordersDao = new OrdersDaoImpl();
        ordersDao.insertOrders(orders);

        //3.将购物车转成订单项
        CartDao cartDao = new CartDaoImpl();
        List<Cart> carts = cartDao.selectCartsByUid(Integer.parseInt(uid));

        List<Item> items = new ArrayList<>();
        for (Cart cart : carts) {
            Item item = new Item();
            item.setOid(orderId);
            item.setPid(cart.getPid());
            item.setInum(cart.getCnum());
            item.setIcount(cart.getCcount());
            items.add(item);
        }

        //4.保存订单对应的订单项
        ordersDao.insertItems(items);

        //5.清空购物车
        cartDao.deleteCartByUid(uid);
    }

    /**
     * 查询订单
     * @param uid
     * @return
     */
    @Override
    public List<Orders> findOrdersByUid(int uid) throws IllegalAccessException, SQLException, InvocationTargetException {

        OrdersDao ordersDao = new OrdersDaoImpl();

        List<Orders> list = ordersDao.selectOrdersByUid(uid);

        return list;
    }

    /**
     * 订单详情
     * @param oid
     * @return
     */
    @Override
    public Orders findOrderByOid(String oid) throws IllegalAccessException, SQLException, InvocationTargetException {

        OrdersDao ordersDao = new OrdersDaoImpl();
        //1.根据oid查询订单和订单地址信息
        //订单项和地址
        Orders orders = ordersDao.selectOrdersByOid(oid);

        //2.查询跟oid对应的订单项和商品信息
        //订单项和商品信息
        List<Item> items = ordersDao.selectItmsByOid(oid);

        //3.将订单项集合设置给订单对象
        orders.setItems(items);

        return orders;
    }

    /**
     * 修改订单状态
     * @param oid
     */
    @Override
    public void updateStateByOid(String oid) throws SQLException {

        OrdersDao ordersDao = new OrdersDaoImpl();

        ordersDao.updateStateByOid(oid);
    }
}
