package com.example.demo.Controller;

import com.example.demo.Model.Recommendation;
import com.example.demo.Model.User;
import com.example.demo.Model.UserInfo;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {

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
                modelMap.addAttribute("err_msg", "您不是管理员");
                return "admin_error";
            }
        } else {
            return "redirect:/user/login?referrer=/admin/index";
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
                modelMap.addAttribute("err_msg", "您不是管理员");
                return "admin_error";
            }
        } else {
            return "redirect:/user/login?referrer=/user/manage";
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
                modelMap.addAttribute("err_msg", "您不是管理员");
                return "admin_error";
            }
        } else {
            return "redirect:/user/login?referrer=/user/verify";
        }
    }

    @RequestMapping(path = "/user/view/{uid}")
    String view(HttpSession session,
                ModelMap modelMap,
                @PathVariable("uid") int uid) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (user.getRole() == 1) {
                User checkUser = userService.getUserByUid(uid);
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("checkuser", checkUser);
                Recommendation recommendation = userService.getRecommendationByUid(checkUser.getUid());
                if (recommendation != null) {
                    String reason = recommendation.getReason();
                    int rid = recommendation.getRid();
                    User recommendUser = userService.getUserByUid(rid);
                    UserInfo recommendUserInfo = userService.getUserInfoByUid(rid);
                    modelMap.addAttribute("recommenduser", recommendUser);
                    modelMap.addAttribute("recommenduserinfo", recommendUserInfo);
                    modelMap.addAttribute("reason", reason);
                }
                return "user_verify_view";
            } else {
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("err_msg", "您不是管理员");
                return "admin_error";
            }
        } else {
            return "redirect:/user/login?referrer=/user/view/" + uid;
        }
    }

    @RequestMapping("/user/verify/{uid}")
    String verifyUser(HttpSession session,
                      ModelMap modelMap,
                      @PathVariable("uid") int uid,
                      @RequestParam("status") int status) {
        if (status == 1) {
            userService.changeStatus(uid,1);
            return "redirect:/user/verify";
        } else if (status == 2) {
            userService.changeStatus(uid,2);
            return "redirect:/user/verify";
        }else{
            return "redirect:/user/verify";
        }
    }
}