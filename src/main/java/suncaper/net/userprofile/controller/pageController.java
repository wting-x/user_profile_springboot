package suncaper.net.userprofile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class pageController {
    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping("/marketproperty")
    public String markrt(){
        return "marketproperty";
    }

    @RequestMapping("/behaviorproperty")
    public String behavior(){
        return "behaviorproperty";
    }

    @RequestMapping("/userprofile")
    public String userprofile(){
        return "userprofile";
    }

    @RequestMapping("/combine")
    public String combine(){
        return "combine";
    }

    @RequestMapping("/search")
    public String search(){
        return "search";
    }

    @RequestMapping("/login")
    public ModelAndView login(){
        ModelAndView mav=new ModelAndView("login");
        boolean s = false;
        mav.addObject("state",s);
        return mav;
    }
}
