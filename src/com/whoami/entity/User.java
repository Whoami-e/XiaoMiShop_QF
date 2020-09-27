package com.whoami.entity;

import java.io.Serializable;

/**
 * 对应数据库用户表
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int uid;
    private String username;  //对应数据库的uname字段
    private String upassword; //密码
    private String usex;      //性别
    private String ustatus;      //用户激活状态 0未激活 1已激活
    private String code;      //邮箱激活码
    private int urole;        //0用户 1管理员
    private String email;     //对应数据库的ueamil字段


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    public String getUsex() {
        return usex;
    }

    public void setUsex(String usex) {
        this.usex = usex;
    }

    public String getUstatus() {
        return ustatus;
    }

    public void setUstatus(String ustatus) {
        this.ustatus = ustatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getUrole() {
        return urole;
    }

    public void setUrole(int urole) {
        this.urole = urole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", upassword='" + upassword + '\'' +
                ", usex='" + usex + '\'' +
                ", ustatus=" + ustatus +
                ", code='" + code + '\'' +
                ", urole=" + urole +
                ", email='" + email + '\'' +
                '}';
    }
}
