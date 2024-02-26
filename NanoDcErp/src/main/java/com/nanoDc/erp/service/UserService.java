package com.nanoDc.erp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nanoDc.erp.mapper.HardwareInvestmentMapper;
import com.nanoDc.erp.mapper.HardwareProductMapper;
import com.nanoDc.erp.mapper.HardwareRewardSharingMapper;
import com.nanoDc.erp.mapper.TransactionMapper;
import com.nanoDc.erp.mapper.UserInfoMapper;
import com.nanoDc.erp.mapper.WalletMapper;
import com.nanoDc.erp.vo.HardwareInvestmentVO;
import com.nanoDc.erp.vo.HardwareRewardSharingDetailVO;
import com.nanoDc.erp.vo.LoginVO;
import com.nanoDc.erp.vo.TransactionVO;
import com.nanoDc.erp.vo.UserInfoVO;
import com.nanoDc.erp.vo.WalletVO;

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
	@Autowired
	TransactionMapper transactionMapper;
	@Autowired
	WalletMapper walletMapper;
	
	public boolean checkSession(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	LoginVO loginVO = (LoginVO)session.getAttribute("user");
        if (session.getAttribute("user") == null || loginVO.getId() == "") {
	        return false;
	    }
        if(loginVO.getLevel()==null) {
        	return false;
        }
        
	    return true;
    }	
	 public UserInfoVO selectDetailUserInfoByUserId(int user_id) {
	    	return userInfoMapper.selectDetailUserInfoByUserId(user_id);
	    }
	
	
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
	 
	 public String addNewTransaction(TransactionVO transcationVO) {
	       this.transactionMapper.addNewTransaction(transcationVO);
	       return "success";
	    }
	 public List<TransactionVO> selectTransactionsByUser(int user_id) {
	       
	       return this.transactionMapper.selectTransactionsByUser(user_id);
	    }
	 
	 
	 
	 
	 public String addNewWallet(WalletVO walletVO) {
	       this.walletMapper.addNewWallet(walletVO);
	       return "success";
	    }
	 public List<WalletVO> getWalletListByUser(int user_id){
		 return this.walletMapper.getWalletListByUser(user_id);
	 }
	 public String deleteWalletByWalletId(long wallet_id){
		 this.walletMapper.deleteWalletByWalletId(wallet_id);
		 return "success";
	 }
	 
	 public HttpSession userVOsessionUpdate( HttpServletRequest request) {
		 HttpSession session = request.getSession();
		 LoginVO loginVO = (LoginVO) session.getAttribute("user");
		 UserInfoVO updatedUserInfoVO = userInfoMapper.selectDetailUserInfoByUserId(loginVO.getUserInfoVO().getUser_id());
		 loginVO.setUserInfoVO(updatedUserInfoVO);
		 session.setAttribute("loginVO", loginVO);
		 return session;
	 }
	 
	
	 
	 
}
