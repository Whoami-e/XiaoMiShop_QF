package com.whoami.entity;

import java.io.Serializable;

/**
 * 对应数据库的地址表
 */
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    private int aid; //Address_Id
    private int uid; //user_id
    private String aname;     //收件人名称
    private String aphone;    //收件人电话
    private String adetail;   //收件人地址
    private int astate;       //收件地址状态 0非默认 1默认地址


    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getAphone() {
        return aphone;
    }

    public void setAphone(String aphone) {
        this.aphone = aphone;
    }

    public String getAdetail() {
        return adetail;
    }

    public void setAdetail(String adetail) {
        this.adetail = adetail;
    }

    public int getAstate() {
        return astate;
    }

    public void setAstate(int astate) {
        this.astate = astate;
    }

    @Override
    public String toString() {
        return "Address{" +
                "aid=" + aid +
                ", uid=" + uid +
                ", aname='" + aname + '\'' +
                ", aphone='" + aphone + '\'' +
                ", adetail='" + adetail + '\'' +
                ", astate=" + astate +
                '}';
    }
}
