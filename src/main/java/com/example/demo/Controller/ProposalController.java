package com.example.demo.Controller;

import com.example.demo.Model.Comment;
import com.example.demo.Model.Proposal;
import com.example.demo.Model.User;
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

    @RequestMapping(path = "/proposal/list")
    public String proposal(HttpSession session,
                           ModelMap modelMap) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Proposal> proposals = proposalService.getAll();
            modelMap.addAttribute("user", user);
            modelMap.addAttribute("proposals", proposals);
            return "proposal_list";
        } else {
            return "redirect:/user/login?referrer=/proposal/list";
        }
    }

    @RequestMapping("/proposal/collect")
    public String collect(HttpSession session,
                          ModelMap modelMap) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Proposal> proposals = proposalService.getCollectProposals();
            modelMap.addAttribute("user", user);
            modelMap.addAttribute("proposals", proposals);
            return "proposal_list";
        } else {
            return "redirect:/user/login?referrer=/proposal/collect";
        }
    }

    @RequestMapping("/proposal/recommend")
    public String recommend(HttpSession session,
                            ModelMap modelMap) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Proposal> proposals = proposalService.getRecommendProposals();
            modelMap.addAttribute("user", user);
            modelMap.addAttribute("proposals", proposals);
            return "proposal_list";
        } else {
            return "redirect:/user/login?referrer=/proposal/recommend";
        }
    }

    @RequestMapping("/proposal/recommend/{pid}")
    public String recommend(HttpSession session,
                            ModelMap modelMap,
                            @PathVariable("pid") int pid) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (user.getRole() == 1 || user.getRole() == 2) {
                proposalService.updateStatus(pid, 1);
            }
            modelMap.addAttribute("user", user);
            return "redirect:/proposal/view/" + pid;
        } else {
            return "redirect:/user/login?referrer=/proposal/recommend/" + pid;
        }
    }

    @RequestMapping("/proposal/record")
    public String record(HttpSession session,
                         ModelMap modelMap) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Proposal> proposals = proposalService.getRecordProposals();
            modelMap.addAttribute("user", user);
            modelMap.addAttribute("proposals", proposals);
            return "proposal_list";
        } else {
            return "redirect:/user/login?referrer=/proposal/record";
        }
    }

    @RequestMapping("/proposal/record/{pid}")
    public String record(HttpSession session,
                         ModelMap modelMap,
                         @PathVariable("pid") int pid) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (user.getRole() == 1 || user.getRole() == 3) {
                proposalService.updateStatus(pid, 2);
            }
            modelMap.addAttribute("user", user);
            return "redirect:/proposal/view/" + pid;
        } else {
            return "redirect:/user/login?referrer=/proposal/record/" + pid;
        }
    }

    @RequestMapping("/proposal/register")
    public String register(HttpSession session,
                           ModelMap modelMap) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Proposal> proposals = proposalService.getRegisterProposals();
            modelMap.addAttribute("user", user);
            modelMap.addAttribute("proposals", proposals);
            return "proposal_list";
        } else {
            return "redirect:/user/login?referrer=/proposal/register";
        }
    }

    @RequestMapping("/proposal/register/{pid}")
    public String register(HttpSession session,
                           ModelMap modelMap,
                           @PathVariable("pid") int pid) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (user.getRole() == 1 || user.getRole() == 4) {
                proposalService.updateStatus(pid, 3);
            }
            modelMap.addAttribute("user", user);
            return "redirect:/proposal/view/" + pid;
        } else {
            return "redirect:/user/login?referrer=/proposal/register/" + pid;
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
            return "redirect:/user/login?referrer=/proposal/my";
        }
    }

    @RequestMapping(path = "/proposal/search")
    public String search(HttpSession session,
                         ModelMap modelMap,
                         @RequestParam(value = "name", required = false) String name) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            if (name == null) {
                name = "";
            }
            List<Proposal> proposals = proposalService.getProposalsByName(name);
            modelMap.addAttribute("user", user);
            modelMap.addAttribute("proposals", proposals);
            return "proposal_list";
        } else {
            return "redirect:/user/login?referrer=/proposal/search";
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
            return "redirect:/user/login?referrer=/proposal/new";
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
            } else if (pid == -2) {
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("err_msg", "不能发布超过三篇未推荐提案");
                return "proposal_new";
            } else {
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("err_msg", "发布失败");
                return "proposal_new";
            }
        } else {
            return "redirect:/user/login?referrer=/proposal/new";
        }
    }

    @RequestMapping(path = "/proposal/save/{pid}", method = RequestMethod.GET)
    public String updateProposal(HttpSession session,
                                 ModelMap modelMap,
                                 @PathVariable("pid") int pid) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Proposal proposal = proposalService.getProposalByPid(pid);
            if (proposal.getAuthor() == user.getUid() || user.getRole() == 1) {
                modelMap.addAttribute("user", user);
                modelMap.addAttribute("proposal", proposal);
                return "proposal_new";
            } else {
                return "redirect:/proposal/view/" + pid;
            }
        } else {
            return "redirect:/user/login?referrer=/proposal/save/" + pid;
        }
    }

    @RequestMapping(path = "/proposal/save", method = RequestMethod.POST)
    public String updateProposal(HttpSession session,
                                 ModelMap modelMap,
                                 @RequestParam("pid") int pid,
                                 @RequestParam("content") String content) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            Proposal proposal = proposalService.getProposalByPid(pid);
            if (proposal.getAuthor() == user.getUid() || user.getRole() == 1) {
                int r = proposalService.update(pid, content);
                if (r > 0) {
                    return "redirect:/proposal/view/" + pid;
                } else {
                    modelMap.addAttribute("user", user);
                    modelMap.addAttribute("err_msg", "修改失败");
                    return "proposal_new";
                }
            } else {
                return "redirect:/proposal/view/" + pid;
            }
        } else {
            return "redirect:/user/login?referrer=/proposal/save/" + pid;
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
            return "redirect:/user/login?referrer=/proposal/view/" + pid;
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

