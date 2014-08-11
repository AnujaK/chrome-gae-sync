package com.bootsimply.sync.entity;

import java.util.Date;

public class ServiceResponse {

    private Long _id;
    private Date _createdAt;
    private String _createdBy;
    private Date _upatedAt;
    private String _updatedBy;
    private String _status;
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

	public String get_createdBy() {
		return _createdBy;
	}

	public void set_createdBy(String _createdBy) {
		this._createdBy = _createdBy;
	}

	public Date get_upatedAt() {
		return _upatedAt;
	}

	public void set_upatedAt(Date _upatedAt) {
		this._upatedAt = _upatedAt;
	}

	public String get_updatedBy() {
		return _updatedBy;
	}

	public void set_updatedBy(String _updatedBy) {
		this._updatedBy = _updatedBy;
	}

	public String get_status() {
		return _status;
	}

	public void set_status(String _status) {
		this._status = _status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
