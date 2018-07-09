package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.Proposal;
import com.example.demo.model.User;
import com.example.demo.Service.CommentService;
import com.example.demo.Service.ProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProposalController {

    @Autowired
    ProposalService proposalService;

    @Autowired
    CommentService commentService;

    @RequestMapping(path = " /proposal/list")
    public String proposal(HttpSession session,
                           ModelMap modelMap) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Proposal> proposals = proposalService.getAll();
            modelMap.addAttribute("user", user);
            modelMap.addAttribute("proposals", proposals);
            return "proposal_list";
        } else {
            return "redirect:/user/login";
        }
    }

    @RequestMapping(path = "/proposal/my")
    public String myProposal(HttpSession session,
                             ModelMap modelMap) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Proposal> proposals = proposalService.getProposalsByUid(user.getUid());
            modelMap.addAttribute("user", user);
            modelMap.addAttribute("proposals", proposals);
            return "proposal_list";
        } else {
            return "redirect:/user/login";
        }
    }

    @RequestMapping(path = "/proposal/search")
    public String search(HttpSession session,
                         ModelMap modelMap,
                         @RequestParam("name") String name) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Proposal> proposals = proposalService.getProposalsByName(name);
            modelMap.addAttribute("user", user);
            modelMap.addAttribute("proposals", proposals);
            return "proposal_list";
        } else {
            return "redirect:/user/login";
        }
    }

    @RequestMapping(path = "/proposal/new", method = RequestMethod.GET)
    public String newProposal(HttpSession session,
                              ModelMap modelMap) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            modelMap.addAttribute("user", user);
            return "proposal_new";
        } else {
            return "redirect:/user/login";
        }
    }

    @RequestMapping(path = "/proposal/new", method = RequestMethod.POST)
    public String newProposal(HttpSession session,
                              ModelMap modelMap,
                              @RequestParam("name") String name,
                              @RequestParam("content") String content) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            int pid = proposalService.add(user.getUid(), name, content);
            if (pid > 0) {
                return "redirect:/proposal/view/" + pid;
            } else {
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("err_msg", "发布失败");
                return "proposal_new";
            }
        } else {
            return "redirect:/user/login";
        }
    }

    @RequestMapping(path = "/proposal/view/{pid}")
    public String view(HttpSession session,
                       ModelMap modelMap, @PathVariable("pid") int pid) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Proposal proposal = proposalService.getProposalByPid(pid);
            List<Comment> comments = commentService.getCommentsByPid(pid);
            modelMap.addAttribute("user", user);
            modelMap.addAttribute("proposal", proposal);
            modelMap.addAttribute("comments", comments);
            return "proposal_view";
        } else {
            return "redirect:/user/login";
        }
    }

    @RequestMapping(path = "/proposal/comment")
    public String comment(HttpSession session,
                          ModelMap modelMap,
                          @RequestParam("content") String content,
                          @RequestParam("pid") int pid,
                          @RequestParam("opinion") String opinion) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            commentService.comment(pid, user.getUid(), content, opinion);
            return "redirect:/proposal/view/" + pid;
        } else {
            return "redirect:/user/login";
        }
    }
}
