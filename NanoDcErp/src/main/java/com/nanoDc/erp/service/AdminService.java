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
import com.nanoDc.erp.vo.HardwareInvestmentVO;
import com.nanoDc.erp.vo.HardwareProductVO;
import com.nanoDc.erp.vo.HardwareRewardSharingDetailVO;
import com.nanoDc.erp.vo.HardwareRewardSharingVO;
import com.nanoDc.erp.vo.InvestmentCategoryVO;
import com.nanoDc.erp.vo.LoginVO;
import com.nanoDc.erp.vo.TransactionVO;
import com.nanoDc.erp.vo.UserInfoVO;

@Service
public class AdminService {
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

	 public boolean checkSession(HttpServletRequest request) {
	    	HttpSession session = request.getSession();
	    	LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        if (session.getAttribute("user") == null || loginVO.getId() == "") {
		        return false;
		    }
	        if(loginVO.getLevel()!=null && Integer.parseInt(loginVO.getLevel()) < 10) {
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
	 public UserInfoVO selectDetailUserInfoByUserId(int user_id) {
	    	return userInfoMapper.selectDetailUserInfoByUserId(user_id);
	    }
	 public List<TransactionVO> getTransactionList() {
	        return this.transactionMapper.getTransactionList();
	    }
	 
	 /*새로운 유저 추가*/
	 public String addNewUser(UserInfoVO userInfoVO, HttpServletRequest request) {
	    /*	UserInfoVO overlappingUserInfoVO = investmentMapper.verifyUserInfoVO(userInfoVO);
	    	if(overlappingUserInfoVO!=null) {
	    		return "error1";
	    	} */
	    	userInfoVO.setProfile_picture_url("/img/default_profile.jpg");	
	    	userInfoMapper.addNewUser(userInfoVO);
	    	
	    	int userId = userInfoMapper.get_last_useriId();
	    	userInfoVO.setUser_id(userId);
	    	String value = pwEncoder.encode(userInfoVO.getPassword());
	    	userInfoVO.setPassword(value);
	    	userInfoMapper.addNewUser_pwd(userInfoVO);
	    	//UserInfoVO newUserInfoVO = investmentMapper.verifyUserInfoVO(userInfoVO);
	    	//memoVO.setUser_id(newUserInfoVO.getUser_id());
	    	//memoVO.setMemo(newUserInfoVO.memoCreate("유저등록"));
	    	//regMemo(memoVO, request);
	    	return "success";
	    }
	 /* 유저 정보 수정 */
	 
	 public String updateUser(UserInfoVO userInfoVO, HttpServletRequest request) {
		 	userInfoMapper.updateUser(userInfoVO);
	    	return "success";
	    }
	 
	 /* 유저 비밀번호 초기화 */
	 public String userPwReset(UserInfoVO userInfoVO, HttpServletRequest request) {
		 	userInfoMapper.userPwReset(userInfoVO);
		 	String value = pwEncoder.encode(userInfoVO.getPassword());
	    	userInfoVO.setPassword(value);
	    	userInfoMapper.userPwReset(userInfoVO);
	    	return "success";
	    }
	 
	 public String updatePwd(UserInfoVO userInfoVO, HttpServletRequest request) {
		 	userInfoMapper.update_Password(userInfoVO);
	    	return "success";
	    }
	 
	 /* 유저 투자 리스트 가져오기 */
	 public List<HardwareInvestmentVO> selectInvestmentListForUser(UserInfoVO userInfoVO){
	    	return investmentMapper.selectInvestmentListForUser(userInfoVO);
	    }
	 /* 유저 투자 등록 */
	 public String addNewInvestment(HardwareInvestmentVO hardwareInvestmentVO, HttpServletRequest request) {
		 	investmentMapper.addNewinvestmentUser(hardwareInvestmentVO);
	    	return "success";
	    }
	 /* 유저 투자 수정 */
	 public String updateinvestmentUser(HardwareInvestmentVO hardwareInvestmentVO, HttpServletRequest request) {
		 	investmentMapper.updateinvestmentUser(hardwareInvestmentVO);
	    	return "success";
	    }
	 
	 /* 상품 업데이트 */
	 public String updateProduct(HardwareProductVO hardwareProductVO, HttpServletRequest request) {
		 	productMapper.updateProduct(hardwareProductVO);
	    	return "success";
	    }
	
	 /*상품 등록*/
	 public String addProduct(HardwareProductVO hardwareProductVO, HttpServletRequest request) {
		 	productMapper.addProduct(hardwareProductVO);
	    	return "success";
	    }
	 /*배분 상세내역 조회*/
	 public List<HardwareRewardSharingDetailVO> selectRewardSharingDetailListById(int reward_sharing_id){
         return rewardSharingMapper.selectRewardSharingDetailListById(reward_sharing_id);
      }
	 /*송금 신청 승인*/
	 public String approveFundRequest(TransactionVO transactionVO) {
			
			
		    
		 	transactionMapper.updateStatus(transactionVO);
		    return "success";  
		}
	 /*송금 신청 거절*/
	 public String declineFundRequest(TransactionVO transactionVO) {
			// TODO Auto-generated method stub
		 transactionMapper.updateStatus(transactionVO);
		    // 조회된 행이 존재하고 상태가 '신청'인 경우에만 업데이트
			return "success";	
		}
	 
}
