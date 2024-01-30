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
import com.nanoDc.erp.service.UserService;
import com.nanoDc.erp.vo.HardwareInvestmentVO;
import com.nanoDc.erp.vo.HardwareProductVO;
import com.nanoDc.erp.vo.HardwareRewardSharingDetailVO;
import com.nanoDc.erp.vo.InvestmentCategoryVO;
import com.nanoDc.erp.vo.LoginVO;
import com.nanoDc.erp.vo.UserInfoVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	 @Autowired
	    private UserService userService;
	 @Autowired
	    private AdminService adminService;
	
	
	
	 @GetMapping(value={"/login"})
	    public ModelAndView login(HttpServletRequest request) {
	        ModelAndView mav = new ModelAndView();
	        mav.setViewName("views/user/login/sign-in");
	        return mav;
	    }
	
	@GetMapping(value={""})
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/user/dashboard");
        return mav;
    }
	@GetMapping(value={"/dashboard"})
    public ModelAndView dashboard(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/user/dashboard");
        return mav;
    }
	
	@GetMapping(value={"/product"})
    public ModelAndView product(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/user/product");
        return mav;
    }
	
	@GetMapping(value={"/transaction"})
    public ModelAndView transaction(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/user/transaction");
        return mav;
    }
	@GetMapping(value={"/investment"})
    public ModelAndView investment(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        List<HardwareInvestmentVO> investmentList = this.userService.getInvestmentListByUser(1);
        mav.addObject("investmentList", investmentList);
        mav.setViewName("views/user/userApp_investment");
        return mav;
    }
	@GetMapping(value={"/reward"})
    public ModelAndView reward(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        List<HardwareRewardSharingDetailVO> rewardDetailList = userService.selectRewardSharingDetailListByUser(1);
        mav.addObject("rewardDetailList", rewardDetailList);
        mav.setViewName("views/user/reward");
        return mav;
    }
	 @GetMapping(value={"/index"})
	    public ModelAndView test(HttpServletRequest request,Integer init_page) {
	        ModelAndView mav = new ModelAndView();
	        mav.setViewName("views/user/userApp_index");
	        return mav;
	    }
	
}
