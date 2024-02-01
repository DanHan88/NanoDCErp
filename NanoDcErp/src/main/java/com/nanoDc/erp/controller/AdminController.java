package com.nanoDc.erp.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

import com.nanoDc.erp.mapper.UserInfoMapper;
import com.nanoDc.erp.service.AdminService;
import com.nanoDc.erp.vo.HardwareInvestmentVO;
import com.nanoDc.erp.vo.HardwareProductVO;
import com.nanoDc.erp.vo.HardwareRewardSharingDetailVO;
import com.nanoDc.erp.vo.HardwareRewardSharingVO;
import com.nanoDc.erp.vo.InvestmentCategoryVO;
import com.nanoDc.erp.vo.LoginVO;
import com.nanoDc.erp.vo.UserInfoVO;

@Controller
@RequestMapping("/admin")
public class AdminController {
	 @Value("${upload.directory}")
	    private String uploadDirectory;
	 @Autowired
	 	private PasswordEncoder pwEncoder;
	 @Autowired
	    private AdminService adminService;
	 @Autowired
	    private UserInfoMapper userInfoMapper;
	
	 /*----------------------------------*/
	 /* -----------GetMapping----------*/
	 /* ---------------------------------*/
	
	@GetMapping(value={"/"})
    public ModelAndView adminMain(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/admin/userManager");
        return mav;
    }
	/*로그인 페이지*/ 
	@GetMapping(value={"/login"})
	  public ModelAndView login(@ModelAttribute("loginError") String loginError,
	    		@ModelAttribute LoginVO loginVO,
	            HttpServletRequest request) {
	        ModelAndView mav = new ModelAndView();
	        Cookie[] cookies = request.getCookies();
        mav.setViewName("views/admin/admin_Login");
        return mav;
    }
	 @PostMapping(value={"/login.do"})
	    private String doLogin(LoginVO loginVO, BindingResult result, RedirectAttributes redirect, HttpServletRequest request, HttpServletResponse response) throws Exception {
	        LoginVO lvo = new LoginVO();
	        UserInfoVO userInfoVO = new UserInfoVO();     
	        userInfoVO.setUser_email(loginVO.getId());
	        userInfoVO = userInfoMapper.verifyUserInfoVO(userInfoVO);
	        if (userInfoVO == null) {
	        	redirect.addFlashAttribute("loginError", "아이디를 확인해주세요");
	            return "redirect:/login";
	        }
	        if ("inactive".equals(userInfoVO.getUser_status()) || Integer.parseInt(userInfoVO.getLevel()) < 10) {
	        	redirect.addFlashAttribute("loginError", "유효하지 않은 아이디입니다.");
	            return "redirect:/login";
	        }
	        if (Integer.parseInt(userInfoVO.getLevel()) < 10) {
	        	redirect.addFlashAttribute("loginError", "유효한 관리자 계정이 아닙니다.");
	            return "redirect:/login";
	        }
	        
	        HttpSession session = request.getSession();
	        String rawPw = "";
	        String encodePw = "";
	        if (userInfoVO != null) {
	        	lvo.setId(userInfoVO.getUser_name());
	            lvo.setUserInfoVO(userInfoVO);
	            lvo.setAdmin(false);
	        	rawPw = loginVO.getPassword();
	            String value = pwEncoder.encode(rawPw);
	            if (this.pwEncoder.matches((CharSequence)rawPw, encodePw = userInfoMapper.getUserPassword(loginVO.getId()))) {
	                lvo.setPassword("");
	                 Cookie rememberMeCookie = new Cookie("userId", String.valueOf(userInfoVO.getUser_id()));
	                 rememberMeCookie.setMaxAge(7 * 24 * 60 * 60); // 30 days
	                 response.addCookie(rememberMeCookie);
	                session.setAttribute("user", (Object)lvo);
	                return "redirect:/userManager";
	            }
	        }
	        redirect.addFlashAttribute("loginError", "비밀번호를 확인해주세요");
	        return "redirect:/login";
	    }
	
	/*회원 관리 페이지*/ 
	 @GetMapping(value={"/userManager"})
	    public ModelAndView userManager(HttpServletRequest request,Integer init_page) {
	        ModelAndView mav = new ModelAndView(); 
	        List<UserInfoVO> userInfoList = this.adminService.selectUserInfoList();
	        List<HardwareProductVO> productList = this.adminService.getProductList(); 
	        mav.addObject("userInfoList", userInfoList);
	        mav.setViewName("views/admin/userManager");
	        mav.addObject("productList", productList);
	        return mav;
	    }
	 
	 /*송금 관리 페이지*/ 
	 @GetMapping(value={"/transactionManager"})
	    public ModelAndView transactionManager(HttpServletRequest request,Integer init_page) {
	        ModelAndView mav = new ModelAndView();
	        List<UserInfoVO> userInfoList = this.adminService.selectUserInfoList();
	        mav.addObject("userInfoList", userInfoList);
	        mav.setViewName("views/admin/transactionManager");
	        return mav;
	    }
	 /*수익 관리 페이지*/ 
	 @GetMapping(value={"/rewardManager"})
	    public ModelAndView rewardManager(HttpServletRequest request,Integer init_page) {
	        ModelAndView mav = new ModelAndView();
	        List<HardwareRewardSharingVO> rewardList = this.adminService.getRewardSharingList();
	        mav.addObject("rewardList", rewardList);
	        mav.setViewName("views/admin/rewardManager");
	        return mav;
	    }
	 
	 /*투자 배분 관리 페이지*/ 
	 @GetMapping(value={"/investmentManager"})
	    public ModelAndView investmentManager(HttpServletRequest request,Integer init_page) {
	        ModelAndView mav = new ModelAndView();        
	        List<HardwareProductVO> productList = this.adminService.getProductList();
	        List<HardwareInvestmentVO> investmentList = this.adminService.getInvestmentList();
	        mav.addObject("productList", productList);
	        mav.addObject("investmentList", investmentList);
	        mav.setViewName("views/admin/investmentManager");
	        return mav;
	    }
	 /*공지 사항 페이지*/ 
	 @GetMapping(value={"/announcementManager"})
	    public ModelAndView announcementManager(HttpServletRequest request,Integer init_page) {
	        ModelAndView mav = new ModelAndView();
	        List<UserInfoVO> userInfoList = this.adminService.selectUserInfoList();
	        mav.addObject("userInfoList", userInfoList);
	        mav.setViewName("views/admin/announcementManager");
	        return mav;
	    }
	 /*이벤트  페이지*/ 
	 @GetMapping(value={"/eventManager"})
	    public ModelAndView eventManager(HttpServletRequest request,Integer init_page) {
	        ModelAndView mav = new ModelAndView();
	        List<UserInfoVO> userInfoList = this.adminService.selectUserInfoList();
	        mav.addObject("userInfoList", userInfoList);
	        mav.setViewName("views/admin/eventManager");
	        return mav;
	    }
	 /*상품 관리 페이지*/ 
	 @GetMapping(value={"/productManager"})
	    public ModelAndView productManager(HttpServletRequest request,Integer init_page) {
	        ModelAndView mav = new ModelAndView();
	        List<HardwareProductVO> productList = this.adminService.getProductList();        
	        
	        mav.addObject("productList", productList);
	        mav.setViewName("views/admin/productManager");
	       
	        return mav;
	    }
	 /*----------------------------------*/
	 /* -----------ResponseBody----------*/
	 /* ---------------------------------*/
	 
	 /*----------------------------------*/
	 /* -----------userManager기능 ----------*/
	 /* ---------------------------------*/
	 
	 /* 유저 추가 */
	 @ResponseBody
	 @PostMapping(value={"/addNewUser"})
	 public String addNewUser(@RequestBody UserInfoVO userInfoVO, HttpServletRequest request) {
	    
	    return adminService.addNewUser(userInfoVO,request);
	    }   
	 
	 /* 유저 정보 수정 */
	 @ResponseBody
	 @PostMapping(value={ "/updateUser" })
	 public String updateUser(MultipartHttpServletRequest request) {
		   
		   String user_email = request.getParameter("user_email");
		   String user_phone = request.getParameter("phone_number");
		   String user_name = request.getParameter("user_name");
		   String user_status = request.getParameter("user_status");
		   String level = request.getParameter("level");
		   int user_id = Integer.parseInt(request.getParameter("user_id"));
		   
		   
		   
		   UserInfoVO userInfoVO = new UserInfoVO();
		   userInfoVO.setUser_email(user_email);
		   userInfoVO.setPhone_number(user_phone);
		   userInfoVO.setUser_name(user_name);
		   userInfoVO.setUser_status(user_status);
		   userInfoVO.setUser_id(user_id);
		   userInfoVO.setLevel(level);
		   
		   
		   return adminService.updateUser(userInfoVO, request);
	 }
	 
	 /* 유저 비밀번호 초기화 */
	 @ResponseBody
	    @PostMapping(value = { "/userPwReset" })
	    public String userPwReset(@RequestBody UserInfoVO userInfoVO, HttpServletRequest request) {
	        

	        return adminService.userPwReset(userInfoVO, request);
	    }
	 /* 유저 투자리스트 가져오기 */
	 @ResponseBody
	    @PostMapping(value={"/selectInvestmentListForUser"})
	    public List<HardwareInvestmentVO> selectInvestmentListForUser(@RequestBody int user_id) {
	    	return adminService.selectInvestmentListForUser(user_id);
	    }
	 /* 유저 투자등록 */
	 @ResponseBody
	 @PostMapping(value={"/addNewInvestment"})
	 public String addNewInvestment(@RequestBody HardwareInvestmentVO hardwareInvestmentVO, HttpServletRequest request) {
	    
	    return adminService.addNewInvestment(hardwareInvestmentVO,request);
	    }   
	 /* 유저 투자 수정 (개인) */
	 @ResponseBody
	 @PostMapping(value={"/updateInvestmentUser"})
	 public String updateinvestmentUser(@RequestBody HardwareInvestmentVO hardwareInvestmentVO, HttpServletRequest request) {
	    
	    return adminService.updateinvestmentUser(hardwareInvestmentVO,request);
	    }   
	 
	 /*----------------------------------*/
	 /* -----------productmanager 기능----------*/
	 /* ---------------------------------*/
	  
	 /* 상품 정보 수정 */
	 @ResponseBody
	 @PostMapping(value={ "/updateProduct" })
	 public String updateProduct(MultipartHttpServletRequest request) throws ParseException {
		   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   String hw_product_name = request.getParameter("hw_product_name");
		   String city = request.getParameter("city");
		   
		   String recruitment_start_date_string = request.getParameter("recruitment_start_date");
		   String preparation_start_date_string = request.getParameter("preparation_start_date");
		   String service_start_date_string = request.getParameter("service_start_date");
		   String service_end_date_string = request.getParameter("service_end_date");
		   String details = request.getParameter("details");
		   float total_budget_fil = Float.parseFloat(request.getParameter("total_budget_fil"));
		   String hw_product_status = request.getParameter("hw_product_status");
		   int hw_product_id = Integer.parseInt(request.getParameter("hw_product_id"));
		   
		   MultipartFile file = request.getFile("file");
	        String filePathString ="";
	        
	        if (file != null && !file.isEmpty()) {
	            try {
	                String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	                java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDirectory);
	                if (!java.nio.file.Files.exists(uploadPath)) {
	                    java.nio.file.Files.createDirectories(uploadPath);
	                }

	                java.nio.file.Path filePath = uploadPath.resolve(hw_product_id+fileName);
	                file.transferTo(filePath.toFile());
	                filePathString = "/img/"+hw_product_id+fileName;
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }else {
	        	filePathString="no_change";
	        }
		   
		   HardwareProductVO hardwareProductVO = new HardwareProductVO();
		   hardwareProductVO.setHw_product_id(hw_product_id);
		   hardwareProductVO.setCity(city);
		   if(recruitment_start_date_string!=null&&!recruitment_start_date_string.equals("")) {
			   hardwareProductVO.setRecruitment_start_date(dateFormat.parse(recruitment_start_date_string));
		   }
		   if(preparation_start_date_string!=null&&!preparation_start_date_string.equals("")) {
			   hardwareProductVO.setPreparation_start_date(dateFormat.parse(preparation_start_date_string));

		   }
		   if(service_start_date_string!=null&&!service_start_date_string.equals("")) {
			   hardwareProductVO.setService_start_date(dateFormat.parse(service_start_date_string));
		   }
		   if(service_end_date_string!=null&&!service_end_date_string.equals("")) {
			   hardwareProductVO.setService_end_date(dateFormat.parse(service_end_date_string));
		   }   
		   hardwareProductVO.setDetails(details);
		   hardwareProductVO.setTotal_budget_fil(total_budget_fil);
		   hardwareProductVO.setHw_product_status(hw_product_status);
		   hardwareProductVO.setPicture_url(filePathString);
		   hardwareProductVO.setHw_product_name(hw_product_name);
		   
		   return adminService.updateProduct(hardwareProductVO, request);
	 }
	 /* 상품 정보 추가 */
	 @ResponseBody
	 @PostMapping(value={ "/addProduct" })
	 public String addProduct(MultipartHttpServletRequest request) throws ParseException {
		   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   String hw_product_name = request.getParameter("hw_product_name");
		   String city = request.getParameter("city");
		   
		   String recruitment_start_date_string = request.getParameter("recruitment_start_date");
		   String preparation_start_date_string = request.getParameter("preparation_start_date");
		   String service_start_date_string = request.getParameter("service_start_date");
		   String service_end_date_string = request.getParameter("service_end_date");
		   String details = request.getParameter("details");
		   float total_budget_fil = Float.parseFloat(request.getParameter("total_budget_fil"));
		   String hw_product_status = request.getParameter("hw_product_status");

		   HardwareProductVO hardwareProductVO = new HardwareProductVO();
		   hardwareProductVO.setCity(city);
		   if(recruitment_start_date_string!=null&&!recruitment_start_date_string.equals("")) {
			   hardwareProductVO.setRecruitment_start_date(dateFormat.parse(recruitment_start_date_string));
		   }
		   if(preparation_start_date_string!=null&&!preparation_start_date_string.equals("")) {
			   hardwareProductVO.setPreparation_start_date(dateFormat.parse(preparation_start_date_string));

		   }
		   if(service_start_date_string!=null&&!service_start_date_string.equals("")) {
			   hardwareProductVO.setService_start_date(dateFormat.parse(service_start_date_string));
		   }
		   if(service_end_date_string!=null&&!service_end_date_string.equals("")) {
			   hardwareProductVO.setService_end_date(dateFormat.parse(service_end_date_string));
		   }   
		   hardwareProductVO.setDetails(details);
		   hardwareProductVO.setTotal_budget_fil(total_budget_fil);
		   hardwareProductVO.setHw_product_status(hw_product_status);
		   hardwareProductVO.setHw_product_name(hw_product_name);
		   
		   return adminService.addProduct(hardwareProductVO, request);
	 }
	 /*---------------------------------------------*/
	 /* -----------HardwareRewardmanger 기능 ----------*/
	 /* --------------------------------------------*/
	 
	 /*배분 상세 내역 조회*/
	 @ResponseBody
     @PostMapping(value={"/selectRewardSharingDetailListById"})
     public List<HardwareRewardSharingDetailVO> selectRewardSharingDetailListById(@RequestBody int reward_sharing_id) {
     List<HardwareRewardSharingDetailVO> resultList = adminService.selectRewardSharingDetailListById(reward_sharing_id);
        return resultList;
     }
}

	 
		


