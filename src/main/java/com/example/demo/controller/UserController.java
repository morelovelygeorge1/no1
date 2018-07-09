package com.example.demo.controller;

import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import com.example.demo.Util.MultiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/user/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(path = "/user/login", method = RequestMethod.POST)
    public String loginAction(ModelMap modelMap,
                              HttpSession session,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password) {
        MultiResult<User> multiResult = userService.login(username, password);
        if (multiResult.message != null) {
            modelMap.addAttribute("message", multiResult.message);
            return "login";
        } else {
            session.setAttribute("user", multiResult.data);
            return "redirect:/index";
        }
        /*
        User user = userDao.getUserByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                if(user.getStatus() == 0){
                    modelMap.addAttribute("message", "您还未通过审核");
                    return "login";
                }else if(user.getStatus() == 1){
                    session.setAttribute("user", user);
                    return "redirect:/index";
                }else if (user.getStatus() == 2){
                    modelMap.addAttribute("message", "您的申请被拒绝");
                    return "login";
                }else{
                    modelMap.addAttribute("message", "您的状态未知,请联系管理员");
                    return "login";
                }
            } else {
                modelMap.addAttribute("message", "密码错误");
                return "login";
            }
        } else {
            modelMap.addAttribute("message", "用户名不存在");
            return "login";
        }
        */
    }

    @RequestMapping(path = "/user/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(path = "/user/register", method = RequestMethod.POST)
    public String registerAction(ModelMap modelMap,
                                 HttpSession session,
                                 @RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 @RequestParam(value = "role", defaultValue = "0") int role,
                                 @RequestParam("name") String name,
                                 @RequestParam("sex") String sex,
                                 @RequestParam("birthday") String birthday,
                                 @RequestParam(value = "address", required = false) String address,
                                 @RequestParam("contact") String contact,
                                 @RequestParam(value = "referrer", required = false) String referrer,
                                 @RequestParam("industryid") int industryid,
                                 @RequestParam("committeeid") int committeeid) {
        User user = new User(username, password, role, name, sex, birthday, address, contact, referrer, industryid, committeeid);
        String message = userService.register(user);
        if (message != null) {
            modelMap.addAttribute("message", message);
            return "register";
        } else {
            return "redirect:/index";
        }
    }

    @RequestMapping(path = "/user/logout")
    String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/index";
    }

    @RequestMapping(path = "/user/check", method = RequestMethod.POST)
    @ResponseBody
    String checkUser(@RequestParam("username") String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return "invalid";
        } else {
            return "valid";
        }
    }
}