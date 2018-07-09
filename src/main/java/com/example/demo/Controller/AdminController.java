package com.example.demo.controller;

import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController{

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/admin/index")
    String index(HttpSession session,
                 ModelMap modelMap) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (user.getRole() == 1) {
                modelMap.addAttribute("user", user);
                return "admin_index";
            } else {
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("err_msg","您不是管理员");
                return "admin_error";
            }
        } else {
            return "redirect:/user/login";
        }
    }

    @RequestMapping(path = "/user/manage")
    String manage(HttpSession session,
                  ModelMap modelMap) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (user.getRole() == 1) {
                List<User> users = userService.getAllUsers();
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("users", users);
                return "user_manage";
            } else {
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("err_msg","您不是管理员");
                return "admin_error";
            }
        } else {
            return "redirect:/user/login";
        }
    }

    @RequestMapping(path = "/user/verify")
    String verify(HttpSession session,
                  ModelMap modelMap) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (user.getRole() == 1) {
                List<User> users = userService.getUnVerifyUsers();
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("users", users);
                return "user_verify";
            } else {
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("err_msg","您不是管理员");
                return "admin_error";
            }
        } else {
            return "redirect:/user/login";
        }
    }

    @RequestMapping(path = "/user/view/{uid}")
    String view(HttpSession session,
                ModelMap modelMap,
                @PathVariable("uid")int uid) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (user.getRole() == 1) {
                User checkUser = userService.getUserByUid(uid);
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("checkuser", checkUser);
                return "user_verify_view";
            } else {
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("err_msg","您不是管理员");
                return "admin_error";
            }
        } else {
            return "redirect:/user/login";
        }
    }
}