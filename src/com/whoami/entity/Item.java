package com.whoami.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 对应数据库订单项
 */
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private int iid;          //订单项id
    private String oid;       //订单id
    private int pid;          //商品id
    private Product product;
    private BigDecimal icount;//订单项小计
    private int inum;         //订单项数量

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public BigDecimal getIcount() {
        return icount;
    }

    public void setIcount(BigDecimal icount) {
        this.icount = icount;
    }

    public int getInum() {
        return inum;
    }

    public void setInum(int inum) {
        this.inum = inum;
    }

    @Override
    public String toString() {
        return "Item{" +
                "iid=" + iid +
                ", oid='" + oid + '\'' +
                ", pid=" + pid +
                ", icount=" + icount +
                ", inum=" + inum +
                '}';
    }
}
