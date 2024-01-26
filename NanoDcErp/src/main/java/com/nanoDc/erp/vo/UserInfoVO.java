/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.springframework.stereotype.Repository
 */
package com.nanoDc.erp.vo;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Repository;

@Repository(value="userInfoVO")
public class UserInfoVO {
	private int user_id;
	private String wallet_addr;
	private String phone_number;
	private String user_status;
    private Date user_reg_date;
    private String user_investment_date;
    private int purchase_size;
    private String user_node;
    private float fil_amount;
    private String user_name;
    private String user_email;
    private int investment_count;
    private String profile_picture_url;
    private String password;
    private String original_password;
	private double available_balance;
	private double total_balance;
	private long total_tb;
	private String level;
	
	
    
	public long getTotal_tb() {
		return total_tb;
	}
	public void setTotal_tb(long total_tb) {
		this.total_tb = total_tb;
	}
	public String getOriginal_password() {
		return original_password;
	}
	public void setOriginal_password(String original_password) {
		this.original_password = original_password;
	}
	public double getAvailable_balance() {
		return Math.floor(available_balance * 100) / 100;
	}
	public void setAvailable_balance(double available_balance) {
		this.available_balance = available_balance;
	}
	public double getTotal_balance() {
		return total_balance;
	}
	public void setTotal_balance(double total_balance) {
		this.total_balance = total_balance;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProfile_picture_url() {
		return profile_picture_url;
	}
	public void setProfile_picture_url(String profile_picture_url) {
		this.profile_picture_url = profile_picture_url;
	}
	public int getInvestment_count() {
		return investment_count;
	}
	public void setInvestment_count(int investment_count) {
		this.investment_count = investment_count;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getWallet_addr() {
		return wallet_addr;
	}
	public void setWallet_addr(String wallet_addr) {
		this.wallet_addr = wallet_addr;
	}

	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getUser_status() {
		return user_status;
	}
	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}
	public Date getUser_reg_date() {
		return user_reg_date;
	}
	public void setUser_reg_date(Date user_reg_date) {
		this.user_reg_date = user_reg_date;
	}
	public String getUser_investment_date() {
		return user_investment_date;
	}
	public void setUser_investment_date(String user_investment_date) {
		this.user_investment_date = user_investment_date;
	}
	public int getPurchase_size() {
		return purchase_size;
	}
	public void setPurchase_size(int purchase_size) {
		this.purchase_size = purchase_size;
	}
	public String getUser_node() {
		return user_node;
	}
	public void setUser_node(String user_node) {
		this.user_node = user_node;
	}
	public float getFil_amount() {
		return fil_amount;
	}
	public void setFil_amount(float fil_amount) {
		this.fil_amount = fil_amount;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
}

