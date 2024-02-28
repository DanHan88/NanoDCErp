package com.nanoDc.erp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nanoDc.erp.mapper.HardwareInvestmentMapper;
import com.nanoDc.erp.mapper.HardwareProductMapper;
import com.nanoDc.erp.mapper.UserInfoMapper;
import com.nanoDc.erp.service.UserService;
import com.nanoDc.erp.vo.HardwareInvestmentVO;
import com.nanoDc.erp.vo.HardwareProductVO;
import com.nanoDc.erp.vo.HardwareRewardSharingDetailVO;
import com.nanoDc.erp.vo.LoginVO;
import com.nanoDc.erp.vo.TransactionVO;
import com.nanoDc.erp.vo.UserInfoVO;
import com.nanoDc.erp.vo.WalletVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	 @Autowired
	    private UserService userService;
	 @Autowired
	    private UserInfoMapper userInfoMapper;
	 @Autowired
	 	private HardwareInvestmentMapper hardwareInvestmentMapper;
	 @Autowired
	 	private HardwareProductMapper hardwareProductMapper;
	 @Autowired
	    private PasswordEncoder pwEncoder;
	 
	 /*로그인 페이지*/ 
		@GetMapping(value={"/login"})
		  public ModelAndView login(@ModelAttribute("loginError") String loginError,
		    		@ModelAttribute LoginVO loginVO,
		            HttpServletRequest request) {
		        ModelAndView mav = new ModelAndView();
		        HttpSession session = request.getSession();
		        if(userService.checkSession(request)==true) {
		        	mav.setViewName("redirect:/user/index");
		            return mav;
		        }
		            mav.setViewName("views/user/user_Login");
		            mav.addObject("loginError", loginError);
		            return mav;
		}
		/*로그 아웃 */
		@GetMapping(value={"/logout"})
	    public String logout(HttpServletRequest request, HttpServletResponse response) {
	        HttpSession session = request.getSession();
	        session.invalidate();
	        
	       /* Cookie[] cookies = request.getCookies();
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if ("nanodc_userApp".equals(cookie.getName())) {
	                    cookie.setMaxAge(0);
	                    response.addCookie(cookie);
	                    break;
	                }
	            }
	        }*/
	        return "redirect:/user/login";
	    }
		 
		 /*로그인 조건부*/
		 @PostMapping(value={"/login.do"})
		    private String doLogin(LoginVO loginVO, BindingResult result, RedirectAttributes redirect, HttpServletRequest request, HttpServletResponse response) throws Exception {
		        LoginVO lvo = new LoginVO();
		        UserInfoVO userInfoVO = new UserInfoVO();     
		        userInfoVO.setUser_email(loginVO.getId());
		        userInfoVO = userInfoMapper.verifyUserInfoVO(userInfoVO);
		        if (userInfoVO == null) {
		        	redirect.addFlashAttribute("loginError", "아이디를 확인해주세요");
		            return "redirect:/user/login";
		        }
		        if ("inactive".equals(userInfoVO.getUser_status())) {
		        	redirect.addFlashAttribute("loginError", "유효하지 않은 아이디입니다.");
		            return "redirect:/user/login";
		        }/*
		        if (Integer.parseInt(userInfoVO.getLevel()) == 10) {
		        	redirect.addFlashAttribute("loginError", "관리자용 아이디 입니다.");
		            return "redirect:/user/login";
		        }*/
		        
		        HttpSession session = request.getSession();
		        String rawPw = "";
		        String encodePw = "";
		        if (userInfoVO != null) {
		        	lvo.setId(userInfoVO.getUser_name());
		        	userInfoVO=userInfoMapper.selectDetailUserInfoByUserId(userInfoVO.getUser_id());
		            lvo.setUserInfoVO(userInfoVO);
		            lvo.setLevel(userInfoVO.getLevel());
		        	rawPw = loginVO.getPassword();
		            String value = pwEncoder.encode(rawPw);
		            if (this.pwEncoder.matches((CharSequence)rawPw, encodePw = userInfoMapper.getUserPassword(userInfoVO.getUser_id()))) {
		                lvo.setPassword("");
		                 Cookie rememberMeCookie = new Cookie("nanodc_userApp", String.valueOf(userInfoVO.getUser_id()));
		                 rememberMeCookie.setMaxAge(7 * 24 * 60 * 60); // 30 days
		                 response.addCookie(rememberMeCookie);
		                
		                 
		                session.setAttribute("user", (Object)lvo);
		                return "redirect:/user/index";
		            }
		        }
		        
		        redirect.addFlashAttribute("loginError", "비밀번호를 확인해주세요");
		        return "redirect:/user/login";
		    }

	//데시보드
	@GetMapping(value={"/dashboard"})
    public ModelAndView dashboard(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if(!userService.checkSession(request)) {
        	mav.setViewName("redirect:/user/login");
            return mav;
        }
        LoginVO loginVO = (LoginVO)session.getAttribute("user");
        mav.setViewName("views/user/dashboard");
        return mav;
    }
	//유저상품
	@GetMapping(value={"/product"})
    public ModelAndView product(HttpServletRequest request) {
		
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if(!userService.checkSession(request)) {
        	mav.setViewName("redirect:/user/login");
            return mav;
        }
        LoginVO loginVO = (LoginVO)session.getAttribute("user");
        mav.addObject("loginVO", loginVO);
        mav.setViewName("views/user/product");
        return mav;
    }
	//유저 송금
	@GetMapping(value={"/cash"})
    public ModelAndView transaction(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = userService.userVOsessionUpdate(request);
        if(!userService.checkSession(request)) {
        	mav.setViewName("redirect:/user/login");
            return mav;
        }
        LoginVO loginVO = (LoginVO)session.getAttribute("user");
        List<WalletVO> walletList = this.userService.getWalletListByUser(loginVO.getUserInfoVO().getUser_id());
        List<TransactionVO> transactionList = this.userService.selectTransactionsByUser(loginVO.getUserInfoVO().getUser_id());
        mav.addObject("transactionList", transactionList);
        mav.addObject("walletList", walletList);
        mav.addObject("loginVO", loginVO);
        mav.setViewName("views/user/transaction");
        return mav;
    }
	
	//유저투자
	@GetMapping(value={"/investment"})
    public ModelAndView investment(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if(!userService.checkSession(request)) {
        	mav.setViewName("redirect:/user/login");
            return mav;
        }
        LoginVO loginVO = (LoginVO)session.getAttribute("user");
        List<HardwareInvestmentVO> investmentList = this.userService.getInvestmentListByUser(loginVO.getUserInfoVO().getUser_id());
        mav.addObject("investmentList", investmentList);
        mav.addObject("loginVO", loginVO);
        mav.setViewName("views/user/userApp_investment");
        return mav;
    }
	//유저 배분
	@GetMapping(value={"/reward"})
    public ModelAndView reward(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if(!userService.checkSession(request)) {
        	mav.setViewName("redirect:/user/login");
            return mav;
        }
        LoginVO loginVO = (LoginVO)session.getAttribute("user");
        List<HardwareRewardSharingDetailVO> rewardDetailList = userService.selectRewardSharingDetailListByUser(loginVO.getUserInfoVO().getUser_id());
        
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
        userService.userVOsessionUpdate(request);
        mav.addObject("dataList",dataList);
        mav.addObject("rewardDetailList", rewardDetailList);
        mav.addObject("loginVO", loginVO);
        mav.setViewName("views/user/reward");
        return mav;
    }
	//유저엡 메인페이지
	 @GetMapping(value={"/index"})
	    public ModelAndView index(HttpServletRequest request,@RequestParam(required = false) Integer hw_product_id) {
		 	
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
	           
	        if(!userService.checkSession(request)) {
	        	mav.setViewName("redirect:/user/login");
	            return mav;
	        }
	        HttpSession session =  userService.userVOsessionUpdate(request);
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        
	        if(hw_product_id==null) hw_product_id=hardwareInvestmentMapper.selectUserDefaultProductId(loginVO.getUserInfoVO().getUser_id());
	        loginVO.getUserInfoVO().setHw_product_id(hw_product_id);

	        UserInfoVO investDetailForHw = userInfoMapper.selectInvestDetailInfoByUserIdAndProductId(loginVO.getUserInfoVO());
	        
	        List<HardwareProductVO> hardwareProductList =  hardwareProductMapper.getProductListByUserId(loginVO.getUserInfoVO().getUser_id());
	        List<List<HardwareProductVO>> dividedList = new ArrayList<>();
	        HardwareProductVO selectedhardwareProduct = new HardwareProductVO();
	        
	        
	        for (int i = 0; i < hardwareProductList.size(); i += 2) {
	            dividedList.add(hardwareProductList.subList(i, Math.min(i + 2, hardwareProductList.size())));
	        }
	        for (int i = 0; i < hardwareProductList.size(); i ++) {
	            if(hw_product_id==hardwareProductList.get(i).getHw_product_id()) {
	            	selectedhardwareProduct = hardwareProductList.get(i);
	            }
	        }
	       
	       
	        int progress_int = (int)(investDetailForHw.getTotal_investment_fil()/ selectedhardwareProduct.getTotal_budget_fil()*10)+1;
	        if(progress_int>11) progress_int= 11;
	        if(progress_int<1) progress_int=1;
	        String progress_src = "/assets/img/filmountain/hw_progress/"+progress_int+".png";
	        mav.addObject("progress_src",progress_src);
	        mav.addObject("dividedList",dividedList);
	        mav.addObject("investDetailForHw",investDetailForHw);
	        mav.addObject("last",last);
	        mav.addObject("loginVO", loginVO);
	        mav.setViewName("views/user/userApp_index");
	        return mav;
	    }
	 
	 /* 송금신청 */
	 @ResponseBody
	 @PostMapping(value={"/addNewTransaction"})
	 public String addNewTransaction(@RequestBody TransactionVO transactionVO, HttpServletRequest request) {
	    return userService.addNewTransaction(transactionVO);
	    }   
	 /* 유저지갑추가 */
	 @ResponseBody
	 @PostMapping(value={"/addNewWallet"})
	 public String addNewWallet(@RequestBody WalletVO walletVO, HttpServletRequest request) {
	    return userService.addNewWallet(walletVO);
	    }   
	 /* 유저지갑삭제 */
	 @ResponseBody
	 @PostMapping(value={"/deleteWalletByWalletId"})
	 public String deleteWalletByWalletId(@RequestBody WalletVO walletVO, HttpServletRequest request) {
	    return userService.deleteWalletByWalletId(walletVO.getWallet_id());
	    }  
	 
	
	 
}
