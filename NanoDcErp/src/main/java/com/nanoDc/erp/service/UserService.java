package com.nanoDc.erp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nanoDc.erp.mapper.HardwareInvestmentMapper;
import com.nanoDc.erp.mapper.HardwareProductMapper;
import com.nanoDc.erp.mapper.HardwareRewardSharingMapper;
import com.nanoDc.erp.mapper.UserInfoMapper;
import com.nanoDc.erp.vo.HardwareInvestmentVO;
import com.nanoDc.erp.vo.HardwareRewardSharingDetailVO;

@Service
public class UserService {
	@Autowired
    private PasswordEncoder pwEncoder;
	@Autowired
	UserInfoMapper userInfoMapper;
	@Autowired
	HardwareInvestmentMapper investmentMapper;
	@Autowired
	HardwareProductMapper productMapper;
	@Autowired
	HardwareRewardSharingMapper rewardSharingMapper;
	
	/* 유저 투자 리스트 가져오기 */
	 public List<HardwareInvestmentVO> selectInvestmentListForUser(int user_id){
	    	return investmentMapper.selectInvestmentListForUser(user_id);
	    }
	 
	 public List<HardwareRewardSharingDetailVO> selectRewardSharingDetailListByUser(int user_id){
         return rewardSharingMapper.selectRewardSharingDetailListByUser(user_id);
      }
	 
	 public List<HardwareInvestmentVO> getInvestmentListByUser(int user_id) {
	        return this.investmentMapper.selectInvestmentListForUser(user_id);
	    }
	 
	 
	 
}
