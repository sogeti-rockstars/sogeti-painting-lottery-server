package com.sogetirockstars.sogetipaintinglotteryserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class AssociationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 64)
    private String field; // Acts as key

    @Type(type = "text")
    private String data;

    public AssociationInfo(String field, String data) {
        this.field = field;
        this.data = data;
    }

    public AssociationInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AssociationInfo [data=" + data + ", field=" + field + ", id=" + id + "]";
    }
}
