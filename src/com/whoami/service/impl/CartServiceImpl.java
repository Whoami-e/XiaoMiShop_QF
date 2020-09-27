package com.whoami.service.impl;

import com.whoami.dao.CartDao;
import com.whoami.dao.ProductDao;
import com.whoami.dao.impl.CartDaoImpl;
import com.whoami.dao.impl.ProductDaoImpl;
import com.whoami.entity.Cart;
import com.whoami.entity.Product;
import com.whoami.service.CartService;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class CartServiceImpl implements CartService {

    /**
     * 创建购物车
     * @param uid
     * @param pid
     */
    @Override
    public void createCart(int uid, String pid) throws SQLException, InvocationTargetException, IllegalAccessException {
        //1.判断是否购物车已经存在了
        CartDao cartDao = new CartDaoImpl();
        //查询购物车是否存在
        Cart cart = cartDao.hasCart(uid,pid);

        if (cart != null) {
            //2.存在的话修改小计和数量
            cart.setCnum(cart.getCnum()+1);
            cartDao.updateCart(cart);
        }else {
            //3.不存在，添加即可
            //根据商品id查询商品
            ProductDao productDao = new ProductDaoImpl();
            Product product = productDao.selectProductByPid(pid);

            cart = new Cart();
            cart.setCnum(1);
            cart.setPid(Integer.parseInt(pid));
            cart.setProduct(product);
            cart.setUid(uid);

            cartDao.insertCart(cart);
        }


    }

    /**
     * 查看购物车
     * @param uid 查询的条件
     * @return
     */
    @Override
    public List<Cart> findAll(int uid) throws IllegalAccessException, SQLException, InvocationTargetException {

        CartDao cartDao = new CartDaoImpl();

        List<Cart> carts = cartDao.selectCartsByUid(uid);

        return carts;
    }

    /**
     * 删除购物车数据
     * @param cid 删除的条件
     */
    @Override
    public void deleteCartByCid(String cid) throws SQLException {

        CartDao cartDao = new CartDaoImpl();

        cartDao.deleteCartByCid(cid);
    }

    /**
     * 加减购物车操作
     * @param cid
     * @param price
     * @param cnum
     */
    @Override
    public void updateCartByCid(String cid, String price, String cnum) throws SQLException {

        BigDecimal cnumbig = new BigDecimal(cnum);
        BigDecimal pricebig = new BigDecimal(price);

        BigDecimal count = pricebig.multiply(cnumbig);

        CartDao cartDao = new CartDaoImpl();

        cartDao.updateCartByCid(count,cnumbig,cid);

    }

    /**
     * 清空购物车
     * @param uid
     */
    @Override
    public void clearCartByUid(String uid) throws SQLException {

        CartDao cartDao = new CartDaoImpl();

        cartDao.deleteCartByUid(uid);
    }
}
