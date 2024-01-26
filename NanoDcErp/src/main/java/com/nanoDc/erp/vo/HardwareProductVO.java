package com.nanoDc.erp.vo;

import java.util.Date;

public class HardwareProductVO {
	private int hw_product_id;
	private String hw_product_name;
	private String hw_product_status;
	private Date recruitment_start_date;
	private Date preparation_start_date;
	private Date service_start_date;
	private Date service_end_date;
	private double total_budget_fil;
	private String city;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getHw_product_id() {
		return hw_product_id;
	}
	public void setHw_product_id(int hw_product_id) {
		this.hw_product_id = hw_product_id;
	}
	public String getHw_product_name() {
		return hw_product_name;
	}
	public void setHw_product_name(String hw_product_name) {
		this.hw_product_name = hw_product_name;
	}
	public String getHw_product_status() {
		return hw_product_status;
	}
	public void setHw_product_status(String hw_product_status) {
		this.hw_product_status = hw_product_status;
	}
	public Date getRecruitment_start_date() {
		return recruitment_start_date;
	}
	public void setRecruitment_start_date(Date recruitment_start_date) {
		this.recruitment_start_date = recruitment_start_date;
	}
	public Date getPreparation_start_date() {
		return preparation_start_date;
	}
	public void setPreparation_start_date(Date preparation_start_date) {
		this.preparation_start_date = preparation_start_date;
	}
	public Date getService_start_date() {
		return service_start_date;
	}
	public void setService_start_date(Date service_start_date) {
		this.service_start_date = service_start_date;
	}
	public Date getService_end_date() {
		return service_end_date;
	}
	public void setService_end_date(Date service_end_date) {
		this.service_end_date = service_end_date;
	}
	public double getTotal_budget_fil() {
		return total_budget_fil;
	}
	public void setTotal_budget_fil(double total_budget_fil) {
		this.total_budget_fil = total_budget_fil;
	}
}
