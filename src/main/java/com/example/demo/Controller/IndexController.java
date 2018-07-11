package com.example.demo.controller;

import com.example.demo.Model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @RequestMapping("/index")
    public String index(ModelMap modelMap,
                        @RequestParam(value = "name",required = false)String name){
        User user = new User();
        user.setUsername("Hi");
        modelMap.addAttribute("user",user);
        return "index";
    }
}
