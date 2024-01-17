package com.nanoDc.erp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/userLogin")
public class LoginController {

	@GetMapping(value={""})
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/user/login/sign-in");
        return mav;
    }
  /*  @GetMapping(value={"/dashboard"})
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/user/dashboard");
        return mav;
    }*/
    @GetMapping(value={"/notifications"})
    public ModelAndView notifications(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/user/notifications");
        return mav;
    }
   /* @GetMapping(value={"/product"})
    public ModelAndView product(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("views/user/product");
        return mav;
    }*/
	
}
