package com.nanoDc.erp.vo;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.google.type.DateTime;

@Repository(value="WalletVO")
public class WalletVO {
	private long wallet_id;
	private String address;
	private int user_id;
	private String status;
	private Date reg_date;
	public long getWallet_id() {
		return wallet_id;
	}
	public void setWallet_id(long wallet_id) {
		this.wallet_id = wallet_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getReg_date() {
		return reg_date;
	}
	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}
	
	
	
	
}
