/*
 * Decompiled with CFR 0.151.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  javax.servlet.http.HttpSession
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.security.crypto.password.PasswordEncoder
 *  org.springframework.stereotype.Controller
 *  org.springframework.validation.BindingResult
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.ModelAttribute
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.servlet.ModelAndView
 *  org.springframework.web.servlet.mvc.support.RedirectAttributes
 */
package com.nanoDc.erp.controller;

import org.springframework.stereotype.Controller;

@Controller
public class InvestmentController {
	/*
    @Autowired
    private InvestmentService investmentService;
    @Autowired
    private PasswordEncoder pwEncoder;
    
    @Value("${upload.directory}")
    private String uploadDirectory;
   

    @GetMapping(value={"/"})
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/authentication/simple/sign-in");
        return mav;
    }
    @GetMapping(value={"/index"})
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/index");
        return mav;
    }
    @GetMapping(value={"/notifications"})
    public ModelAndView notifications(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/notifications");
        return mav;
    }
    
    

    @GetMapping(value={"/logoutUser"})
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping(value={"/login.do"})
    private String doLogin(LoginVO loginVO, BindingResult result, RedirectAttributes redirect, HttpServletRequest request, HttpServletResponse response) throws Exception {
        LoginVO lvo = this.investmentService.findAdminByAdminId(loginVO.getId());
        HttpSession session = request.getSession();
        String rawPw = "";
        String encodePw = "";
        if (lvo != null) {
            rawPw = loginVO.getPassword();
            if (this.pwEncoder.matches((CharSequence)rawPw, encodePw = lvo.getPassword())) {
                lvo.setPassword("");
                lvo.setAdmin(true);
                session.setAttribute("user", (Object)lvo);
                return "redirect:/main";
            }
        }
        redirect.addFlashAttribute("loginError", "아이디와 페스워드를 확인해주세요");
        return "redirect:/";
    }
    
    @ResponseBody  
    @PostMapping(value={"/updateFundRequest"})
    public String updateFundRequest(@RequestBody WalletWithdrawalVO walletWithdrawalVO, HttpServletRequest request) {
        
        
        if ("승인".equals(walletWithdrawalVO.getIs_request_state())) {
            return investmentService.approveFundRequest(walletWithdrawalVO);
        } else if ("거절".equals(walletWithdrawalVO.getIs_request_state())) {
            return investmentService.declineFundRequest(walletWithdrawalVO);
        }

        return "failed:invalid_request_state";
    	}
    @ResponseBody  
    @PostMapping(value={"/checkAdminBalance"})
    public float checkAdminBalance(HttpServletRequest request) {
        float balance = investmentService.walletCheck();
        return balance;
    	}
    @ResponseBody  
    @PostMapping(value={"/addNewProduct"})
    public InvestmentCategoryVO addNewProduct(@RequestBody InvestmentCategoryVO investmentCategoryVO,  HttpServletRequest request) {
    	if(!investmentService.checkSession(request,true)) {
    		investmentCategoryVO.setResponse("success");
    		return investmentCategoryVO;
    	}
        return investmentService.addNewProductCategory(investmentCategoryVO);
    }
    
    @ResponseBody
    @PostMapping(value={"/updateProduct"})
    public InvestmentCategoryVO updateProduct(@RequestBody InvestmentCategoryVO investmentCategoryVO, HttpServletRequest request) {
    	if(!investmentService.checkSession(request,true)) {
    		investmentCategoryVO.setResponse("success");
    		return investmentCategoryVO;
    	}
    	return investmentService.updateProductCategory(investmentCategoryVO);
    }
    @ResponseBody
    @PostMapping(value={"/selectInvestmentListForUser"})
    public List<InvestmentVO> selectInvestmentListForUser(@RequestBody int user_id) {
    	return investmentService.selectInvestmentListForUser(user_id);
    }
    @ResponseBody
    @PostMapping(value={"/addNewUser"})
    public String addNewUser(@RequestBody UserInfoVO userInfoVO, HttpServletRequest request) {
    	if(!investmentService.checkSession(request,true)) {
    		return "failed:session_closed";
    	}
    	return investmentService.addNewUser(userInfoVO,request);
    }   
    @ResponseBody
    @PostMapping(value={"/addNewInvestment"})
    public String addNewInvestment(@RequestBody InvestmentVO investmentVO, HttpServletRequest request) {
    	if(!investmentService.checkSession(request,true)) {
    		return "failed:session_closed";
    	}
    	return investmentService.addNewInvestment(investmentVO,request);
    }  
    @ResponseBody
    @PostMapping(value={"/modifyInvestment"})
    public String modifyInvestment(@RequestBody InvestmentVO investmentVO, HttpServletRequest request) {
    	if(!investmentService.checkSession(request,true)) {
    		return "failed:session_closed";
    	}
    	return investmentService.modifyInvestment(investmentVO,request);
    } 
    @ResponseBody
    @PostMapping(value={"/deleteInvestment"})
    public String deleteInvestment(@RequestBody InvestmentVO investmentVO, HttpServletRequest request) {
    	if(!investmentService.checkSession(request,true)) {
    		return "failed:session_closed";
    	}
    	return investmentService.deleteInvestment(investmentVO,request);
    } 
    @ResponseBody
    @PostMapping(value={"/selectUserMemo"})
    public List<MemoVO> selectUserMemo(@RequestBody UserInfoVO userInfoVO) {
    	return investmentService.selectUserMemo(userInfoVO);
    } 
    
    @ResponseBody
    @PostMapping(value={"/selectTokenDetailList"})
    public List<TokenPaidDetailVO> selectTokenDetailList(@RequestBody TokenPaidVO tokenPaidVO) {
    	return investmentService.selectTokenDetailList(tokenPaidVO);	
    } 
    
    
    @ResponseBody
    @PostMapping(value = { "/updateUser" })
    public String updateUser(MultipartHttpServletRequest request) {
        if (!investmentService.checkSession(request,true)) {
            return "failed:session_closed";
        }
        String user_email = request.getParameter("user_email");
        String user_phone = request.getParameter("user_phone");
        String user_name = request.getParameter("user_name");
        String user_status = request.getParameter("user_status");
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        MultipartFile file = request.getFile("file");
        String filePathString ="";
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());

                java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDirectory);
                if (!java.nio.file.Files.exists(uploadPath)) {
                    java.nio.file.Files.createDirectories(uploadPath);
                }

                java.nio.file.Path filePath = uploadPath.resolve(user_id+fileName);
                file.transferTo(filePath.toFile());
                filePathString = "/profile/"+user_id+fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
        	filePathString="no_change";
        }

        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUser_email(user_email);
        userInfoVO.setUser_phone(user_phone);
        userInfoVO.setUser_name(user_name);
        userInfoVO.setUser_status(user_status);
        userInfoVO.setUser_id(user_id);
        userInfoVO.setProfile_picture_url(filePathString);

        return investmentService.updateUser(userInfoVO, request);
    }
    @ResponseBody
    @PostMapping(value={"/regMemo"})
    public String regMemo(@RequestBody MemoVO memoVO, HttpServletRequest request) {
    	if(!investmentService.checkSession(request,true)) {
    		return "failed:session_closed";
    	}
    	return investmentService.regMemo(memoVO,request);
    }  
    @ResponseBody  //테스트용
    @PostMapping(value={"/payoutConfirm"})
    public String payoutConfirm(@RequestBody List<InvestmentVO> listInvestment, HttpServletRequest request) {
    	if(!investmentService.checkSession(request,true)) {
    		return "failed:session_closed";
    	}
    	String cmd = "lotus wallet list";
        String[] command = {"/bin/sh","-c",cmd};
        String result = ""; 
        try 
        {
        	ProcessBuilder processBuilder = new ProcessBuilder(command);
        	
        	Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            
            while ((line = reader.readLine()) != null) {
            	result +=line+"\n";
            }
            int exitCode = process.waitFor(); 
            if (exitCode != 0) {
            	result = "Command failed";
            }
         } 
        catch (Exception e) 
        {     
        	result = "ERROR";
        }
        return result; 
    } 
    
    @ResponseBody
    @PostMapping(value={"/addNewTokenPaid"})
    public String addNewTokenPaid(@RequestBody List<InvestmentVO> listInvestment, HttpServletRequest request) {
    	if(!investmentService.checkSession(request,true)) {
    		return "failed:session_closed";
    	}
        return investmentService.addNewTokenPaidInfo(listInvestment ,request);
    }
    @ResponseBody
    @PostMapping(value={"/updateTokenPaid"})
    public String updateTokenPaid(@RequestBody TokenPaidVO tokenPaidVO, HttpServletRequest request) {
    	if(!investmentService.checkSession(request,true)) {
    		return "failed:session_closed";
    	}
        return investmentService.updateTokenPaid(tokenPaidVO);
    }
    @ResponseBody
    @PostMapping(value={"/updateAnnouncements"})
    public String update_announcement(@RequestBody EventsAnnouncementVO eventsAnnouncementVO, HttpServletRequest request) {
    	if(!investmentService.checkSession(request,true)) {
    		return "failed:session_closed";
    	}
    	 HttpSession session = request.getSession();
    	LoginVO loginVO = (LoginVO)session.getAttribute("user");
    	eventsAnnouncementVO.setAdmin(loginVO.getId());
    	return investmentService.insert_Events_or_Announcement(eventsAnnouncementVO);
    }
    
    @ResponseBody
    @PostMapping(value={"/updateEvents"})
    public String update_Events(@RequestBody EventsAnnouncementVO eventsAnnouncementVO, HttpServletRequest request) {
    	if(!investmentService.checkSession(request,true)) {
    		return "failed:session_closed";
    	}
    	HttpSession session = request.getSession();
    	LoginVO loginVO = (LoginVO)session.getAttribute("user");
    	eventsAnnouncementVO.setAdmin(loginVO.getId());
    	return investmentService.insert_Events_or_Announcement(eventsAnnouncementVO);
    }
    
    
    
    @GetMapping(value={"/userManager"})
    public ModelAndView userManager(HttpServletRequest request,String sb) {
        ModelAndView mav = new ModelAndView();
        
        if(!investmentService.checkSession(request,true)) {
        	mav.setViewName("redirect:/");
            return mav;
        }
        HttpSession session = request.getSession();
        LoginVO loginVO = (LoginVO)session.getAttribute("user");
        List<UserInfoVO> userInfoList = this.investmentService.selectUserInfoList();
        List<InvestmentCategoryVO> listInvestmentCategory = this.investmentService.getInvestmentCategoryList();
        mav.addObject("sb", sb);
        mav.addObject("listInvestmentCategory", listInvestmentCategory);
        mav.addObject("userInfoList", userInfoList);
        mav.addObject("loginVO", loginVO);
        mav.setViewName("views/userManager");
        return mav;
    }
    @GetMapping(value={"/fundRequest"})
    public ModelAndView fundRequest(HttpServletRequest request,String sb) {
        ModelAndView mav = new ModelAndView();
        if(!investmentService.checkSession(request,true)) {
        	mav.setViewName("redirect:/");
            return mav;
        }
        HttpSession session = request.getSession();
        LoginVO loginVO = (LoginVO)session.getAttribute("user");
        List<WalletWithdrawalVO> requestFilList = this.investmentService.getRequestFilList();
        mav.addObject("sb", sb);
        mav.addObject("requestFilList", requestFilList);
        mav.addObject("loginVO", loginVO);
        mav.addObject("loading",true);
        mav.setViewName("views/fundRequest");
        
        return mav;
    }
    @GetMapping(value={"/main"})
    public ModelAndView main(HttpServletRequest request, String category,String sb) {
        ModelAndView mav = new ModelAndView();
        
        if(!investmentService.checkSession(request,true)) {
        	mav.setViewName("redirect:/");
            return mav;
        }
        HttpSession session = request.getSession();
        LoginVO loginVO = (LoginVO)session.getAttribute("user");
        List<InvestmentCategoryVO> listInvestmentCategory = this.investmentService.getInvestmentCategoryList();
        List<InvestmentVO> listInvestment = category == null || category.equals("0") ? this.investmentService.getInvestmentList() : this.investmentService.getInvestmentListByCategory(Integer.parseInt(category));
        mav.addObject("sb", sb);
        mav.addObject("listInvestment", listInvestment);
        mav.addObject("listInvestmentCategory", listInvestmentCategory);
        mav.addObject("loginVO", loginVO);
        mav.setViewName("views/investmentManager");
        return mav;
    }
    @GetMapping(value={"/payoutManager"})
    public ModelAndView payoutManager(HttpServletRequest request, String category,String sb) {
        ModelAndView mav = new ModelAndView();
        if(!investmentService.checkSession(request,true)) {
        	mav.setViewName("redirect:/");
            return mav;
        }
        HttpSession session = request.getSession();
        LoginVO loginVO = (LoginVO)session.getAttribute("user");
        
        List<TokenPaidVO> tokenPaidVOList = investmentService.selectTokenPaidVOList();
        
        
        mav.addObject("sb", sb);
        mav.addObject("tokenPaidVOList", tokenPaidVOList);
        mav.addObject("loginVO", loginVO);
        mav.setViewName("views/payoutManager");
        return mav;
    }
    
    @GetMapping(value={"/events"})
    public ModelAndView userAppEvents(HttpServletRequest request,String sb) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if(!investmentService.checkSession(request,true)) {
        	mav.setViewName("redirect:/");
            return mav;
        }
        LoginVO loginVO = (LoginVO)session.getAttribute("user");
        List<EventsAnnouncementVO> eventList = investmentService.selectEvents();
        mav.addObject("eventList", eventList);
        mav.addObject("sb", sb);
        mav.addObject("loginVO", loginVO);
        mav.setViewName("views/events");
        return mav;
    }
   @GetMapping(value={"/announcements"})
    public ModelAndView userAppAnnouncements(HttpServletRequest request,String sb) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession();
        if(!investmentService.checkSession(request,true)) {
        	mav.setViewName("redirect:/");
            return mav;
        }
        LoginVO loginVO = (LoginVO)session.getAttribute("user");
        List<EventsAnnouncementVO> announcementList = investmentService.selectAnnouncements();
        mav.addObject("announcementList", announcementList);
        mav.addObject("sb", sb);
        mav.addObject("loginVO", loginVO);
        mav.setViewName("views/announcements");
        return mav;
    }
    
    public String truncateString(String input, int maxLength) {
        if (input.length() <= maxLength) {
            return input; 
        } else {
            return input.substring(0, maxLength) + "...";
        }
    }
     */ 
}  

