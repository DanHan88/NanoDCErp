package com.nanoDc.erp.vo;

import java.util.Date;

public class TokenPaidDetailVO {

	private double token_paid_detail_id;
	private int user_id;
	private double token_paid_id;
	private float paid_fil;
	private int investment_category_index;
	
	private String user_email;
	private String user_name;
	private String investment_category_name;
	private String status;
	private Date paid_date;
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getPaid_date() {
		return paid_date;
	}
	public void setPaid_date(Date paid_date) {
		this.paid_date = paid_date;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getInvestment_category_name() {
		return investment_category_name;
	}
	public void setInvestment_category_name(String investment_category_name) {
		this.investment_category_name = investment_category_name;
	}
	public double getToken_paid_detail_id() {
		return token_paid_detail_id;
	}
	public void setToken_paid_detail_id(double token_paid_detail_id) {
		this.token_paid_detail_id = token_paid_detail_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public double getToken_paid_id() {
		return token_paid_id;
	}
	public void setToken_paid_id(double token_paid_id) {
		this.token_paid_id = token_paid_id;
	}
	public float getPaid_fil() {
		return paid_fil;
	}
	public void setPaid_fil(float paid_fil) {
		this.paid_fil = paid_fil;
	}
	public int getInvestment_category_index() {
		return investment_category_index;
	}
	public void setInvestment_category_index(int investment_category_index) {
		this.investment_category_index = investment_category_index;
	}
}
