package com.example.demo.Controller;

import com.example.demo.Model.*;
import com.example.demo.Service.UserService;
import com.example.demo.Util.MultiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "/user/login", method = RequestMethod.GET)
    public String login(ModelMap modelMap, @RequestParam(value = "referrer", required = false) String referrer) {
        if (referrer != null) {
            modelMap.addAttribute("referrer", referrer);
        }
        return "login";
    }

    @RequestMapping(path = "/user/login", method = RequestMethod.POST)
    public String loginAction(ModelMap modelMap,
                              HttpSession session,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam(value = "referrer", required = false) String referrer) {
        MultiResult<User> multiResult = userService.login(username, password);
        if (multiResult.message != null) {
            if (referrer != null) {
                modelMap.addAttribute("referrer", referrer);
            }
            modelMap.addAttribute("message", multiResult.message);
            return "login";
        } else {
            session.setAttribute("user", multiResult.data);
            if (referrer != null) {
                return "redirect:" + referrer;
            } else {
                return "redirect:/index";
            }
        }
    }

    @RequestMapping(path = "/user/register", method = RequestMethod.GET)
    public String register(ModelMap modelMap) {
        List<Committee> committees = userService.getCommittees();
        List<Industry> industries = userService.getIndustries();
        List<Seminar> seminars = userService.getSeminars();
        modelMap.addAttribute("committees", committees);
        modelMap.addAttribute("industries", industries);
        modelMap.addAttribute("seminars", seminars);
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
                                 @RequestParam("committeeid") int committeeid,
                                 @RequestParam("seminarid") int seminarid) {
        User user = new User(username, password, role, name, sex, birthday, address, contact, referrer, industryid, committeeid, seminarid);
        String message = userService.register(user);
        if (message != null) {
            modelMap.addAttribute("message", message);
            return "register";
        } else {
            return "redirect:/index";
        }
    }

    @RequestMapping(path = "/user/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/index";
    }

    @RequestMapping(path = "/user/check", method = RequestMethod.POST)
    public @ResponseBody
    String checkUser(@RequestParam("username") String username) {
        User user = userService.getUserByUsername(username);
        if (user != null) {
            return "invalid";
        } else {
            return "valid";
        }
    }

    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    public String userInfo(ModelMap modelMap,
                           HttpSession session,
                           @RequestParam(value = "err", required = false) String err) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            UserInfo userInfo = userService.getUserInfoByUid(user.getUid());
            if (userInfo == null) {
                userInfo = new UserInfo();
            }
            if (err != null) {
                modelMap.addAttribute("err_msg", "请先完善用户信息");
            }
            modelMap.addAttribute("user", user);
            modelMap.addAttribute("userInfo", userInfo);
            return "user_info";
        } else {
            return "redirect:/user/login?referrer=/user/info";
        }
    }

    @RequestMapping(value = "/user/info", method = RequestMethod.POST)
    public String userInfo(ModelMap modelMap,
                           HttpSession session,
                           @RequestParam(value = "company", required = false) String company,
                           @RequestParam(value = "title", required = false) String title,
                           @RequestParam(value = "position", required = false) String position,
                           @RequestParam(value = "phone", required = false) String phone,
                           @RequestParam(value = "email", required = false) String email,
                           @RequestParam(value = "wechat", required = false) String wechat) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            UserInfo userInfo = new UserInfo(user.getUid(), company, title, position, phone, email, wechat);
            userService.createOrSaveUserInfo(userInfo);
            modelMap.addAttribute("user", user);
            modelMap.addAttribute("userInfo", userInfo);
            return "user_info";
        } else {
            return "redirect:/user/login?referrer=/user/info";
        }
    }

    @RequestMapping(value = "/user/recommend", method = RequestMethod.GET)
    public String recommend(ModelMap modelMap,
                            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            UserInfo userInfo = userService.getUserInfoByUid(user.getUid());
            if (userInfo == null) {
                return "redirect:/user/info?err=noinfo";
            } else {
                List<User> users = userService.getUnVerifyUsers();
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("users", users);
                modelMap.addAttribute("userInfo", userInfo);
                return "user_recommend";
            }
        } else {
            return "redirect:/user/login?referrer=/user/recommend";
        }
    }

    @RequestMapping(value = "/user/recommend", method = RequestMethod.POST)
    public String recommend(ModelMap modelMap,
                            HttpSession session,
                            @RequestParam("uid") int uid,
                            @RequestParam("reason") String reason) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            UserInfo userInfo = userService.getUserInfoByUid(user.getUid());
            if (userInfo == null) {
                return "redirect:/user/info?err=noinfo";
            } else {
                int r = userService.recommend(uid, user.getUid(), reason);
                if (r == -1) {
                    modelMap.addAttribute("err_msg", "不要重复推荐");
                } else if (r == 0) {
                    modelMap.addAttribute("err_msg", "数据库问题");
                } else if (r > 0) {
                    return "redirect:/user/recommend/" + r;
                }
                List<User> users = userService.getUnVerifyUsers();
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("users", users);
                modelMap.addAttribute("userInfo", userInfo);
                return "user_recommend";
            }
        } else {
            return "redirect:/user/login?referrer=/user/recommend";
        }
    }

    @RequestMapping("/user/recommend/{id}")
    public String recommend(ModelMap modelMap,
                            HttpSession session,
                            @PathVariable("id") int id) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Recommendation recommendation = userService.getRecommendationById(id);
            if (recommendation != null) {
                String reason = recommendation.getReason();
                int uid = recommendation.getUid();
                int rid = recommendation.getRid();
                User checkUser = userService.getUserByUid(uid);
                User recommendUser = userService.getUserByUid(rid);
                UserInfo recommendUserInfo = userService.getUserInfoByUid(rid);
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("checkuser", checkUser);
                modelMap.addAttribute("recommenduser", recommendUser);
                modelMap.addAttribute("recommenduserinfo", recommendUserInfo);
                modelMap.addAttribute("reason", reason);
            }
            return "user_verify_view";
        } else {
            return "redirect:/user/login?referrer=/user/recommend/" + id;
        }
    }
}
