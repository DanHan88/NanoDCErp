package com.nanoDc.erp.vo;

import java.util.Date;

import org.springframework.stereotype.Repository;

@Repository(value="MemoVO")
public class MemoVO {

	private double id;
	private int user_id;
	private String memo;
	private Date reg_date;
	private String admin_id;
	
	public double getId() {
		return id;
	}
	public void setId(double id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
}
