package com.jobo.myrxmvpbaseproject.entity;

import java.util.List;

/**
 * Created by JoBo on 2018/6/1.
 */

public class RecyclerBean {
    private String id;
    private String isOpen;
    private String message;
    private List<RecyclerBean> chiles;

    public RecyclerBean(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<RecyclerBean> getChiles() {
        return chiles;
    }

    public void setChiles(List<RecyclerBean> chiles) {
        this.chiles = chiles;
    }


}
