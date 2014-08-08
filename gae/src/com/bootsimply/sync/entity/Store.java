package com.bootsimply.sync.entity;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Store {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long _id;

    @Persistent
    private String _name;

    @Persistent
    private Date _createdAt;

    @Persistent
    private Date _upatedAt;

    @Persistent
    private String data;

    public Long get_id() {
	return _id;
    }

    public void set_id(Long _id) {
	this._id = _id;
    }

    public Date get_createdAt() {
	return _createdAt;
    }

    public void set_createdAt(Date _createdAt) {
	this._createdAt = _createdAt;
    }

    public Date get_upatedAt() {
	return _upatedAt;
    }

    public void set_upatedAt(Date _upatedAt) {
	this._upatedAt = _upatedAt;
    }

    public String getData() {
	return data;
    }

    public void setData(String data) {
	this.data = data;
    }

    public String get_name() {
	return _name;
    }

    public void set_name(String _name) {
	this._name = _name;
    }

}
