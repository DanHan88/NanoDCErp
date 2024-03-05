package com.nanoDc.erp.service;

import java.util.ArrayList;
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
import com.nanoDc.erp.vo.HardwareProductVO;
import com.nanoDc.erp.vo.HardwareRewardSharingDetailVO;
import com.nanoDc.erp.vo.LoginVO;
import com.nanoDc.erp.vo.MainIndexMapper;
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
	 public List<HardwareInvestmentVO> selectInvestmentListForUser(UserInfoVO userInfoVO){
	    	return investmentMapper.selectInvestmentListForUser(userInfoVO);
	    }
	 
	 public List<HardwareRewardSharingDetailVO> selectRewardSharingDetailListByUser(UserInfoVO userInfoVO){
         return rewardSharingMapper.selectRewardSharingDetailListByUser(userInfoVO);
      }
	 
	 public String addNewTransaction(TransactionVO transcationVO) {
	       this.transactionMapper.addNewTransaction(transcationVO);
	       return "success";
	    }
	 public List<TransactionVO> selectTransactionsByUser(UserInfoVO userInfoVO) {
	       
	       return this.transactionMapper.selectTransactionsByUser(userInfoVO);
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
		 int hw_product_id= loginVO.getUserInfoVO().getHw_product_id();
		 UserInfoVO updatedUserInfoVO = userInfoMapper.selectDetailUserInfoByUserId(loginVO.getUserInfoVO().getUser_id());
		 updatedUserInfoVO.setHw_product_id(hw_product_id);
		 loginVO.setUserInfoVO(updatedUserInfoVO);
		 session.setAttribute("loginVO", loginVO);
		 return session;
	 }
	 
	 public MainIndexMapper userAppMainInfoBuilder(HttpServletRequest request,Integer hw_product_id) {
		 	HttpSession session =  userVOsessionUpdate(request);
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	       
	        MainIndexMapper mainIndexMapper = new MainIndexMapper();
	        if(hw_product_id==null) {
	        	hw_product_id=loginVO.getUserInfoVO().getHw_product_id();
	        }
	        if(hw_product_id==0) {
	        	hw_product_id=investmentMapper.selectUserDefaultProductId(loginVO.getUserInfoVO().getUser_id());
	        }
	        if(hw_product_id==null) {
	        	hw_product_id=0;
	        	mainIndexMapper.setError("No Investment");
	        }else {
	        	mainIndexMapper.setError("");
	        }
	        loginVO.getUserInfoVO().setHw_product_id(hw_product_id);
	        session.setAttribute("user", (Object)loginVO);
	        UserInfoVO investDetailForHw = userInfoMapper.selectInvestDetailInfoByUserIdAndProductId(loginVO.getUserInfoVO());
	        
	        List<HardwareProductVO> hardwareProductList =  productMapper.getProductListByUserId(loginVO.getUserInfoVO().getUser_id());
	        List<List<HardwareProductVO>> dividedList = new ArrayList<>();
	        HardwareProductVO selectedhardwareProduct = new HardwareProductVO();
	        
	        
	        for (int i = 0; i < hardwareProductList.size(); i += 3) {
	            dividedList.add(hardwareProductList.subList(i, Math.min(i + 3, hardwareProductList.size())));
	        }
	        for (int i = 0; i < hardwareProductList.size(); i ++) {
	            if(hw_product_id==hardwareProductList.get(i).getHw_product_id()) {
	            	selectedhardwareProduct = hardwareProductList.get(i);
	            }
	        }
	       
	       
	        int progress_int = (int)(investDetailForHw.getTotal_investment_fil()/ selectedhardwareProduct.getTotal_budget_fil()*10)+1;
	        if(progress_int>11) progress_int= 11;
	        if(progress_int<2) progress_int=2;
	        String progress_src = "/assets/img/filmountain/hw_progress/"+progress_int+".png";
	        String main_bg_src = "/assets/img/filmountain/nanodc_racks/"+hw_product_id+".png";
	        
	        
	       
	        mainIndexMapper.setDividedList(dividedList);
	        mainIndexMapper.setInvestDetailForHw(investDetailForHw);
	        mainIndexMapper.setLoginVO(loginVO);
	        mainIndexMapper.setMain_bg_src(main_bg_src);
	        mainIndexMapper.setProgress_src(progress_src);
	        mainIndexMapper.setSelectedhardwareProduct(selectedhardwareProduct); 
	        return mainIndexMapper;    
	 }
	 
	 public String updateProfile(UserInfoVO userInfoVO, HttpServletRequest request) {
		 	userInfoMapper.update_Password(userInfoVO);
	    	return "success";
	    }

	 
	
	 
	 
}
