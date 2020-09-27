package com.whoami.entity;

import java.util.List;

public class PageBean<T> {

    //展示的数据
    private List<T> list;

    //当前页数
    private int currentPage;

    //页容量（每页显示的数据数量）
    private int pageSize;

    //总条数
    private long totalCount;

    //总页数
    private int totalPage;

    public PageBean(List<T> list, int currentPage, int pageSize, long totalCount) {
        this.list = list;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return (int) Math.ceil(totalCount*1.0/pageSize

        );
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
