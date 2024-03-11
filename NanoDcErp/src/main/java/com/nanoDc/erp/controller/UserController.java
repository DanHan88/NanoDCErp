package com.nanoDc.erp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.password.PasswordEncoder;

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

import com.nanoDc.erp.mapper.FilPriceMapper;
import com.nanoDc.erp.mapper.HardwareInvestmentMapper;
import com.nanoDc.erp.mapper.HardwareProductMapper;
import com.nanoDc.erp.mapper.UserInfoMapper;
import com.nanoDc.erp.service.UserService;
import com.nanoDc.erp.vo.FilPriceVO;
import com.nanoDc.erp.vo.HardwareInvestmentVO;
import com.nanoDc.erp.vo.HardwareProductVO;
import com.nanoDc.erp.vo.HardwareRewardSharingDetailVO;
import com.nanoDc.erp.vo.LoginVO;
import com.nanoDc.erp.vo.MainIndexMapper;
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
	 	private FilPriceMapper filPriceMapper;
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
	//유저프로필편집
	@GetMapping(value={"/profileEdit"})
	 public  ModelAndView profileEdit(HttpServletRequest request) {
	    ModelAndView mav = new ModelAndView();
	    HttpSession session = request.getSession();
		    if(!userService.checkSession(request)) {
	        	mav.setViewName("redirect:/user/login");
	            return mav;
	        }
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        mav.addObject("loginVO", loginVO);
	        mav.setViewName("views/user/userApp_profileEdit");
	        return mav;
	    }
	
	//유저프로필편집
	@GetMapping(value={"/profileIconEdit"})
	 public  ModelAndView profileIconEdit(HttpServletRequest request) {
	    ModelAndView mav = new ModelAndView();
	    HttpSession session = request.getSession();
		    if(!userService.checkSession(request)) {
	        	mav.setViewName("redirect:/user/login");
	            return mav;
	        }
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        mav.addObject("loginVO", loginVO);
	        mav.setViewName("views/user/userApp_profileIconEdit");
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
        List<TransactionVO> transactionList = this.userService.selectTransactionsByUser(loginVO.getUserInfoVO());
        UserInfoVO investDetailForHw = userInfoMapper.selectInvestDetailInfoByUserIdAndProductId(loginVO.getUserInfoVO());
        mav.addObject("investDetailForHw", investDetailForHw);
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
        List<HardwareInvestmentVO> investmentList = this.userService.selectInvestmentListForUser(loginVO.getUserInfoVO());
        
        HardwareProductVO product_detail = hardwareProductMapper.getProductById(loginVO.getUserInfoVO().getHw_product_id()); 
        mav.addObject("product_detail",product_detail);
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
        List<HardwareRewardSharingDetailVO> rewardDetailList = userService.selectRewardSharingDetailListByUser(loginVO.getUserInfoVO());
        Date lastRewardDate = new Date();
        Date firstRewardDate= new Date();
        long  interval =0;
        List<Double> dataList = new ArrayList<Double>();
        if(!rewardDetailList.isEmpty()) {
        lastRewardDate = rewardDetailList.get(0).getHardwareRewardSharingVO().getRegdate();
        firstRewardDate = rewardDetailList.get(rewardDetailList.size()-1).getHardwareRewardSharingVO().getRegdate();
        interval = (lastRewardDate.getTime() - firstRewardDate.getTime())/30;
        for(long i= firstRewardDate.getTime() + interval;i<lastRewardDate.getTime()+interval;i += interval) {
        	double data=0;
	        for(int j = rewardDetailList.size()-1;j>=0;j--) {
	        	if(rewardDetailList.get(j).getHardwareRewardSharingVO().getRegdate().getTime()<=i) {
	        		data += rewardDetailList.get(j).getReward_fil();
	        		if(j==0) {
	        			dataList.add(data);
		        		break;
	        		}
	        	}else {
	        		dataList.add(data);
	        		break;
	        	}
	        }
        }

        }
        
        
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        userService.userVOsessionUpdate(request);
        mav.addObject("dataList",dataList);
        mav.addObject("firstDate",dateFormat.format(firstRewardDate));
        mav.addObject("lastDate",dateFormat.format(lastRewardDate));
        mav.addObject("dataSize",dataList.size());
        mav.addObject("rewardDetailList", rewardDetailList);
        mav.addObject("loginVO", loginVO);
        mav.setViewName("views/user/reward");
        return mav;
    }
	//FIL 가격 현황 페이지
		@GetMapping(value={"/price"})
	    public ModelAndView price(HttpServletRequest request) {
	        ModelAndView mav = new ModelAndView();
	        HttpSession session = request.getSession();
	        if(!userService.checkSession(request)) {
	        	mav.setViewName("redirect:/user/login");
	            return mav;
	        }
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        List<FilPriceVO> filPriceList = filPriceMapper.getFilPriceDataForMonth();
	        
	        Date lastDate = new Date();
	        Date firstDate= new Date();
	        long interval=0;
	        List<Integer> dataList = new ArrayList<Integer>();
	        if(!filPriceList.isEmpty()) {
		        lastDate = filPriceMapper.getLatestFilPrice().getReg_date();
		        firstDate = filPriceList.get(0).getReg_date();
		        interval = (lastDate.getTime() - firstDate.getTime())/30;
		        for(int i=0;i<filPriceList.size();i++) {
		        	dataList.add(filPriceList.get(i).getFil_last());
		        }
	        }
	        userService.userVOsessionUpdate(request);
	        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	        mav.addObject("firstDate",dateFormat.format(firstDate));
	        mav.addObject("lastDate",dateFormat.format(lastDate));
	        mav.addObject("dataList",dataList);
	        mav.addObject("dataSize",dataList.size());
	        mav.addObject("loginVO", loginVO);
	        mav.setViewName("views/user/price");
	        return mav;
	    }
		//FIL 가격 현황 페이지
				@GetMapping(value={"/status"})
			    public ModelAndView status(HttpServletRequest request) {
			        ModelAndView mav = new ModelAndView();
			        HttpSession session = request.getSession();
			        if(!userService.checkSession(request)) {
			        	mav.setViewName("redirect:/user/login");
			            return mav;
			        }
			        LoginVO loginVO = (LoginVO)session.getAttribute("user");
			        userService.userVOsessionUpdate(request);
			        mav.addObject("loginVO", loginVO);
			        mav.setViewName("views/user/status");
			        return mav;
			    }
	//유저엡 메인페이지
	 @GetMapping(value={"/index"})
	    public ModelAndView index(HttpServletRequest request,@RequestParam(required = false) Integer hw_product_id) {
		 	
	        ModelAndView mav = new ModelAndView();
	        if(!userService.checkSession(request)) {
	        	mav.setViewName("redirect:/user/login");
	            return mav;
	        }
	        MainIndexMapper mainIndexMapper = userService.userAppMainInfoBuilder(request, hw_product_id);
	        String error="";
	        if(mainIndexMapper.getError().equals("No Investment")) {
	        	error="No Investment";
	        }
	        mav.addObject("error",error);
	        mav.addObject("main_bg_src",mainIndexMapper.getMain_bg_src());
	        mav.addObject("progress_src",mainIndexMapper.getProgress_src());
	        mav.addObject("dividedList",mainIndexMapper.getDividedList());
	        mav.addObject("investDetailForHw",mainIndexMapper.getInvestDetailForHw());
	        mav.addObject("last", filPriceMapper.getLatestFilPrice().getFil_last());
	        mav.addObject("loginVO", mainIndexMapper.getLoginVO());
	        mav.setViewName("views/user/userApp_index");
	        return mav;
	    }
	 @ResponseBody
	 @PostMapping(value={"/userAppMainInfoBuilder"})
	 public MainIndexMapper userAppMainInfoBuilder(@RequestBody MainIndexMapper mainIndexMapper, HttpServletRequest request) {
		 HttpSession session = request.getSession();
	        if(!userService.checkSession(request)) {
	        	MainIndexMapper mainIndexMapper_temp = new MainIndexMapper();
	        	mainIndexMapper_temp.setError("session closed");
	        	return mainIndexMapper_temp;
	        }
	    return userService.userAppMainInfoBuilder(request,mainIndexMapper.getHw_product_id());
	    }   
	 /*유저 프로필 편집 기능 */
	 @ResponseBody
	    @PostMapping(value = { "/DOuserprofileEdit" })
	    public String DOadminPwdUpdate(@RequestBody UserInfoVO userInfoVO, HttpServletRequest request) {
		 String uvo = this.userInfoMapper.getUserPassword(userInfoVO.getUser_id());
		 if(!userService.checkSession(request)) {
	    		return "failed:session_closed";
	    	}
		   String rawPw = "";
	       String encodePw = "";
	      
	       int user_id = userInfoVO.getUser_id();   		
	       rawPw = userInfoVO.getPassword();
	        if (this.pwEncoder.matches((CharSequence)rawPw, encodePw = uvo)) {
	        	userInfoVO.setNew_password(pwEncoder.encode(userInfoVO.getNew_password()));
	        	HttpSession session = request.getSession();
	        	session.invalidate();
	            	return  userService.updateProfile(userInfoVO, request);
	           }
	            else{
	            	return "비밀번호가 일치하지 않습니다";}
	    }
	 /*유저 프로필 편집 기능 */
	 @ResponseBody
	    @PostMapping(value = { "/user_profile_icon_edit" })
	    public String user_profile_icon_edit(@RequestBody UserInfoVO userInfoVO, HttpServletRequest request) {
		 String uvo = this.userInfoMapper.getUserPassword(userInfoVO.getUser_id());
		 if(!userService.checkSession(request)) {
	    		return "failed:session_closed";
	    	}
	        return  userService.update_user_icon(userInfoVO, request);
	    } 
	 /* 송금신청 */
	 @ResponseBody
	 @PostMapping(value={"/addNewTransaction"})
	 public String addNewTransaction(@RequestBody TransactionVO transactionVO, HttpServletRequest request) {
		  HttpSession session =  userService.userVOsessionUpdate(request);
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        transactionVO.setHw_product_id(loginVO.getUserInfoVO().getHw_product_id());
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
