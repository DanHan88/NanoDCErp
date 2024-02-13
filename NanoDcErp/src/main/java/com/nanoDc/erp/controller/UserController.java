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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nanoDc.erp.mapper.UserInfoMapper;
import com.nanoDc.erp.service.UserService;
import com.nanoDc.erp.vo.HardwareInvestmentVO;
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
	    private PasswordEncoder pwEncoder;
	 
	 /*로그인 페이지*/ 
		@GetMapping(value={"/login"})
		  public ModelAndView login(@ModelAttribute("loginError") String loginError,
		    		@ModelAttribute LoginVO loginVO,
		            HttpServletRequest request) {
		        ModelAndView mav = new ModelAndView();
		        Cookie[] cookies = request.getCookies();
		        if (cookies != null) {
		            for (Cookie cookie : cookies) {
		                if ("userId".equals(cookie.getName())) {
		                    String storedUserId = cookie.getValue();
		                    UserInfoVO userInfoVO = userService.selectDetailUserInfoByUserId(Integer.parseInt(storedUserId));
		                    if (userInfoVO != null) {
		                    	HttpSession session = request.getSession();
		                        LoginVO lvo = new LoginVO();
		                        lvo.setId(userInfoVO.getUser_email());
		                        lvo.setUserInfoVO(userInfoVO);
		                        lvo.setPassword("");
		                        lvo.setLevel(userInfoVO.getLevel());
		                        session.setAttribute("user", (Object)lvo);
		                        mav.setViewName("redirect:/user/index");
		                        return mav;
		                    }
		                }
		            }
		            }
		        if (!userService.checkSession(request)) {
		            mav.setViewName("views/user/user_Login");
		            mav.addObject("loginError", loginError);
		            return mav;
		        }
		        mav.setViewName("redirect:/user/index");
		        return mav;
		}
		/*로그 아웃 */
		@GetMapping(value={"/logout"})
	    public String logout(HttpServletRequest request, HttpServletResponse response) {
	        HttpSession session = request.getSession();
	        session.invalidate();
	        
	        Cookie[] cookies = request.getCookies();
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if ("userId".equals(cookie.getName())) {
	                    cookie.setMaxAge(0); // Set the max age to zero to delete the cookie
	                    response.addCookie(cookie);
	                    break;
	                }
	            }
	        }
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
		        }
		        if (Integer.parseInt(userInfoVO.getLevel()) == 10) {
		        	redirect.addFlashAttribute("loginError", "관리자용 아이디 입니다.");
		            return "redirect:/user/login";
		        }
		        
		        HttpSession session = request.getSession();
		        String rawPw = "";
		        String encodePw = "";
		        if (userInfoVO != null) {
		        	lvo.setId(userInfoVO.getUser_name());
		            lvo.setUserInfoVO(userInfoVO);
		            lvo.setLevel(userInfoVO.getLevel());
		        	rawPw = loginVO.getPassword();
		            String value = pwEncoder.encode(rawPw);
		            if (this.pwEncoder.matches((CharSequence)rawPw, encodePw = userInfoMapper.getUserPassword(userInfoVO.getUser_id()))) {
		                lvo.setPassword("");
		                 Cookie rememberMeCookie = new Cookie("userId", String.valueOf(userInfoVO.getUser_id()));
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
        mav.setViewName("views/user/product");
        return mav;
    }
	//유저 송금
	@GetMapping(value={"/cash"})
    public ModelAndView transaction(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if(!userService.checkSession(request)) {
        	mav.setViewName("redirect:/user/login");
            return mav;
        }
        LoginVO loginVO = (LoginVO)session.getAttribute("user");
        List<WalletVO> walletList = this.userService.getWalletListByUser(1);
        mav.addObject("walletList", walletList);
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
        List<HardwareInvestmentVO> investmentList = this.userService.getInvestmentListByUser(1);
        mav.addObject("investmentList", investmentList);
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
	    public ModelAndView index(HttpServletRequest request,Integer init_page) {
		 	
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
	        HttpSession session = request.getSession();
	        if(!userService.checkSession(request)) {
	        	mav.setViewName("redirect:/user/login");
	            return mav;
	        }
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        mav.addObject("last",last);
	        
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
