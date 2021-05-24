package com.example.fulldev.bo;

public class PageCounter {
    private Integer page;
    private Integer count;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public PageCounter(Integer page, Integer count) {
        this.page = page;
        this.count = count;
    }
}
