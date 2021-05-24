package com.example.fulldev.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

//@Entity
//@Data
public class Order implements Serializable {
    public Integer getId() {
        return id;
    }

    public Order() {
    }

    public Order(Integer id, String name, String messageId) {
        this.id = id;
        this.name = name;
        this.messageId = messageId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    private static final long serialVersionUID = 2705377050832296801L;
    @Id
    private Integer id;

    private String name;

    private String messageId;

}
