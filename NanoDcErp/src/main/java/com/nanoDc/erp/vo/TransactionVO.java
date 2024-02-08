package com.nanoDc.erp.vo;

import java.util.Date;

import com.google.type.DateTime;

public class TransactionVO {
	private long transaction_id;
	private double fil_amount;
	private int user_id;
	private String status;
	private long wallet;
	private String type;
	private DateTime reg_date;
	private String message;
	public long getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(long transaction_id) {
		this.transaction_id = transaction_id;
	}
	public double getFil_amount() {
		return fil_amount;
	}
	public void setFil_amount(double fil_amount) {
		this.fil_amount = fil_amount;
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
	public long getWallet() {
		return wallet;
	}
	public void setWallet(long wallet) {
		this.wallet = wallet;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public DateTime getReg_date() {
		return reg_date;
	}
	public void setReg_date(DateTime reg_date) {
		this.reg_date = reg_date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
