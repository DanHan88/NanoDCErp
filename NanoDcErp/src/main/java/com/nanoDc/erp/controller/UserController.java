package com.nanoDc.erp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.nanoDc.erp.service.UserService;
import com.nanoDc.erp.vo.HardwareInvestmentVO;
import com.nanoDc.erp.vo.HardwareRewardSharingDetailVO;
import com.nanoDc.erp.vo.TransactionVO;
import com.nanoDc.erp.vo.UserInfoVO;
import com.nanoDc.erp.vo.WalletVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	 @Autowired
	    private UserService userService;
	 //로그인
	 @GetMapping(value={"/login"})
	    public ModelAndView login(HttpServletRequest request) {
	        ModelAndView mav = new ModelAndView();
	        mav.setViewName("views/user/login/sign-in");
	        return mav;
	    }

	//데시보드
	@GetMapping(value={"/dashboard"})
    public ModelAndView dashboard(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/user/dashboard");
        return mav;
    }
	//유저상품
	@GetMapping(value={"/product"})
    public ModelAndView product(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/user/product");
        return mav;
    }
	//유저 송금
	@GetMapping(value={"/cash"})
    public ModelAndView transaction(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/user/transaction");
        return mav;
    }
	
	//유저투자
	@GetMapping(value={"/investment"})
    public ModelAndView investment(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        List<HardwareInvestmentVO> investmentList = this.userService.getInvestmentListByUser(1);
        mav.addObject("investmentList", investmentList);
        mav.setViewName("views/user/userApp_investment");
        return mav;
    }
	//유저 배분
	@GetMapping(value={"/reward"})
    public ModelAndView reward(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        List<HardwareRewardSharingDetailVO> rewardDetailList = userService.selectRewardSharingDetailListByUser(1);
        
        Date lastRewardDate = rewardDetailList.get(0).getHardwareRewardSharingVO().getRegdate();
        Date firstRewardDate = rewardDetailList.get(rewardDetailList.size()-1).getHardwareRewardSharingVO().getRegdate();
        
        long  interval = (lastRewardDate.getTime() - firstRewardDate.getTime())/30;
        List<Double> dataList = new ArrayList<Double>();
        for(long i= firstRewardDate.getTime();i<lastRewardDate.getTime();i += interval) {
        	double data=0;
	        for(int j = rewardDetailList.size()-1;j>=0;j--) {
	        	if(rewardDetailList.get(j).getHardwareRewardSharingVO().getRegdate().getTime()<=i) {
	        		data += rewardDetailList.get(j).getReward_fil();
	        	}else {
	        		dataList.add(data);
	        		break;
	        	}
	        }
	        if(i + interval >= lastRewardDate.getTime()) {
	        	dataList.add(data+rewardDetailList.get(rewardDetailList.size()-1).getReward_fil());
	        }
        }
        mav.addObject("dataList",dataList);
        mav.addObject("rewardDetailList", rewardDetailList);
        mav.setViewName("views/user/reward");
        return mav;
    }
	//유저엡 메인페이지
	 @GetMapping(value={"/index"})
	    public ModelAndView test(HttpServletRequest request,Integer init_page) {

		    String CURRENCY_PAIR = "fil_krw";
	        String apiUrl = "https://api.korbit.co.kr/v1/ticker?currency_pair=" + CURRENCY_PAIR;
	        String last = "";
	        long timestamp=0;
	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.set("accept", "application/json");
	        HttpEntity<String> entity = new HttpEntity<>(headers);
	        ResponseEntity<String> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
	        if (responseEntity.getStatusCode().is2xxSuccessful()) {
	            String responseData = responseEntity.getBody();
	            
	            JSONObject jsonObject = new JSONObject(responseData);
	            timestamp = jsonObject.getLong("timestamp");
	            last = jsonObject.getString("last");
	        } else {
	            System.err.println("Error: " + responseEntity.getStatusCode());
	        }
		 
		 
	        ModelAndView mav = new ModelAndView();
	        mav.addObject("last",last);
	        mav.setViewName("views/user/userApp_index");
	        return mav;
	    }
	 
	 /* 송금신청 */
	 @ResponseBody
	 @PostMapping(value={"/addNewTransactionPayout"})
	 public String addNewTransactionPayout(@RequestBody TransactionVO transactionVO, HttpServletRequest request) {
	    return userService.addNewTransaction(transactionVO);
	    }   
	 /* 유저지갑추가 */
	 @ResponseBody
	 @PostMapping(value={"/addNewWallet"})
	 public String addNewWallet(@RequestBody WalletVO walletVO, HttpServletRequest request) {
	    return userService.addNewWallet(walletVO);
	    }   
	
}
