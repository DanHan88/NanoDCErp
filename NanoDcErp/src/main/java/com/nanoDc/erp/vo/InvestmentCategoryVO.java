/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  org.springframework.stereotype.Repository
 */
package com.nanoDc.erp.vo;

import org.springframework.stereotype.Repository;

@Repository(value="investmentCategoryVO")
public class InvestmentCategoryVO {
	private String investment_category_name;
	private int investment_category_index;
	private String status;
	private String response;
	private int cateogry_fil_per_tb;
	
    public int getCateogry_fil_per_tb() {
		return cateogry_fil_per_tb;
	}

	public void setCateogry_fil_per_tb(int cateogry_fil_per_tb) {
		this.cateogry_fil_per_tb = cateogry_fil_per_tb;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInvestment_category_name() {
        return this.investment_category_name;
    }

    public void setInvestment_category_name(String investment_category_name) {
        this.investment_category_name = investment_category_name;
    }

	public int getInvestment_category_index() {
		return investment_category_index;
	}

	public void setInvestment_category_index(int investment_category_index) {
		this.investment_category_index = investment_category_index;
	}
	

}

