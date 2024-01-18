package com.nanoDc.erp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nanoDc.erp.mapper.HardwareInvestmentMapper;
import com.nanoDc.erp.mapper.HardwareProductMapper;
import com.nanoDc.erp.mapper.HardwareRewardSharingMapper;
import com.nanoDc.erp.mapper.UserInfoMapper;
import com.nanoDc.erp.vo.HardwareInvestmentVO;
import com.nanoDc.erp.vo.HardwareProductVO;
import com.nanoDc.erp.vo.HardwareRewardSharingVO;
import com.nanoDc.erp.vo.InvestmentCategoryVO;
import com.nanoDc.erp.vo.LoginVO;
import com.nanoDc.erp.vo.UserInfoVO;

@Service
public class AdminService {
	
	@Autowired
	UserInfoMapper userInfoMapper;
	@Autowired
	HardwareInvestmentMapper investmentMapper;
	@Autowired
	HardwareProductMapper productMapper;
	@Autowired
	HardwareRewardSharingMapper rewardSharingMapper;

	 public boolean checkSession(HttpServletRequest request,boolean isAdmin) {
	    	HttpSession session = request.getSession();
	    	LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        if (session.getAttribute("user") == null || loginVO.getId() == "") {
		        return false;
		    }
	        if(isAdmin!=loginVO.isAdmin()) {
	        	return false;
	        }
		    return true;
	    }	
	
	 public List<UserInfoVO> selectUserInfoList() {
	        return this.userInfoMapper.selectUserInfoList();
	    }
	 public List<HardwareProductVO> getProductList() {
	        return this.productMapper.getProductList();
	    }
	 public List<HardwareInvestmentVO> getInvestmentList() {
	        return this.investmentMapper.getInvestmentList();
	    }
	 
	 public List<HardwareRewardSharingVO> getRewardSharingList() {
	        return this.rewardSharingMapper.getRewardSharingList();
	    }
}
