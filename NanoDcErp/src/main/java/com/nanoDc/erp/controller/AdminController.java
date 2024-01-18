package com.nanoDc.erp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nanoDc.erp.service.AdminService;
import com.nanoDc.erp.vo.InvestmentCategoryVO;
import com.nanoDc.erp.vo.LoginVO;
import com.nanoDc.erp.vo.UserInfoVO;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	 @Autowired
	    private AdminService adminService;
	
	
	
	@GetMapping(value={"/"})
    public ModelAndView adminMain(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/admin/userManager");
        return mav;
    }
	@GetMapping(value={"/login"})
    public ModelAndView adminLogin(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/admin/login/sign-in");
        return mav;
    }
	
	 @GetMapping(value={"/userManager"})
	    public ModelAndView userManager(HttpServletRequest request,Integer init_page) {
	        ModelAndView mav = new ModelAndView();
	        
	        /*if(!adminService.checkSession(request,true)) {
	        	mav.setViewName("redirect:/");
	            return mav;
	        }
	        HttpSession session = request.getSession();
	        LoginVO loginVO = (LoginVO)session.getAttribute("user");
	        */
	        
	        List<UserInfoVO> userInfoList = this.adminService.selectUserInfoList();
	        //List<InvestmentCategoryVO> listInvestmentCategory = this.adminService.getInvestmentCategoryList();
	        //int page = (init_page != null) ? init_page : 0;
	        //mav.addObject("page",page);
	        //mav.addObject("listInvestmentCategory", listInvestmentCategory);
	        mav.addObject("userInfoList", userInfoList);
	        //mav.addObject("loginVO", loginVO);
	        mav.setViewName("views/admin/userManager");
	        return mav;
	    }
}
