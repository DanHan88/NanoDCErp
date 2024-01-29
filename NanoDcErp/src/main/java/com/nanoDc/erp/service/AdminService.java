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
    private PasswordEncoder pwEncoder;
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
	 
	 /*새로운 유저 추가*/
	 public String addNewUser(UserInfoVO userInfoVO, HttpServletRequest request) {
	    /*	UserInfoVO overlappingUserInfoVO = investmentMapper.verifyUserInfoVO(userInfoVO);
	    	if(overlappingUserInfoVO!=null) {
	    		return "error1";
	    	} */
	    	userInfoVO.setProfile_picture_url("/profile/default_profile.jpg");	
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
	 /* 유저 투자 리스트 가져오기 */
	 public List<HardwareInvestmentVO> selectInvestmentListForUser(int user_id){
	    	return investmentMapper.selectInvestmentListForUser(user_id);
	    }
	 
	 /* 상품 업데이트 */
	 public String updateProduct(HardwareProductVO hardwareProductVO, HttpServletRequest request) {
		 	productMapper.updateProduct(hardwareProductVO);
	    	return "success";
	    }
	
	 
}
