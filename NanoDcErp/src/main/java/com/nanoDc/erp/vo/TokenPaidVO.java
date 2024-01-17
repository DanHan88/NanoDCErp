package com.nanoDc.erp.vo;

import java.util.Date;
import java.util.List;

public class TokenPaidVO {

	private double token_paid_id;
	private Date paid_date;
	private float fil_paid_per_tb;
	private String admin_id;
	private String status;
	private Date update_date;
	private float fil_paid_ratio_change;
	private float total_paid;
	private int unique_category_count;
	private String payout_category_name;
	
	public int getUnique_category_count() {
		return unique_category_count;
	}
	public void setUnique_category_count(int unique_category_count) {
		this.unique_category_count = unique_category_count;
	}
	public String getPayout_category_name() {
		return payout_category_name;
	}
	public void setPayout_category_name(String payout_category_name) {
		this.payout_category_name = payout_category_name;
	}
	public float getFil_paid_ratio_change() {
		return fil_paid_ratio_change;
	}
	public void setFil_paid_ratio_change(float fil_paid_ratio_change) {
		this.fil_paid_ratio_change = fil_paid_ratio_change;
	}
	public float getTotal_paid() {
		return total_paid;
	}
	public void setTotal_paid(float total_paid) {
		this.total_paid = total_paid;
	}
	private List<TokenPaidDetailVO> tokenPaidDetailVOList;
	
	public List<TokenPaidDetailVO> getTokenPaidDetailVOList() {
		return tokenPaidDetailVOList;
	}
	public void setTokenPaidDetailVOList(List<TokenPaidDetailVO> tokenPaidDetailVOList) {
		this.tokenPaidDetailVOList = tokenPaidDetailVOList;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	public double getToken_paid_id() {
		return token_paid_id;
	}
	public void setToken_paid_id(double token_paid_id) {
		this.token_paid_id = token_paid_id;
	}
	public Date getPaid_date() {
		return paid_date;
	}
	public void setPaid_date(Date paid_date) {
		this.paid_date = paid_date;
	}
	public float getFil_paid_per_tb() {
		return fil_paid_per_tb;
	}
	public void setFil_paid_per_tb(float fil_paid_per_tb) {
		this.fil_paid_per_tb = fil_paid_per_tb;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
