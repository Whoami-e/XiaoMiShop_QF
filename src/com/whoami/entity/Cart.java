package com.whoami.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 对应数据库购物车表
 */
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    private int cid;            //购物车id
    private int uid;            //用户id
    private int pid;            //商品id
    private Product product;
    private BigDecimal ccount;  //购物车小计
    private int cnum = 0;           //购物车商品数量

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public BigDecimal getCcount() {
        BigDecimal pprice = product.getPprice();
        BigDecimal bigDecimal = new BigDecimal(cnum);
        return pprice.multiply(bigDecimal);
    }

    public void setCcount(BigDecimal ccount) {
        this.ccount = ccount;
    }

    public int getCnum() {
        return cnum;
    }

    public void setCnum(int cnum) {
        this.cnum = cnum;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", pid=" + pid +
                ", ccount=" + ccount +
                ", cnum=" + cnum +
                '}';
    }
}
