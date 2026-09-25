package com.DoctorAssisstent.PatientPortal2.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.DoctorAssisstent.PatientPortal2.dto.DiscussionDto;
import com.DoctorAssisstent.PatientPortal2.service.ForumService;
import com.DoctorAssisstent.PatientPortal2.service.PatientService;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/web/forum")
public class ForumWebController {

    private final ForumService forumService;
    private final PatientService patientService; 

    public ForumWebController(ForumService forumService, PatientService PatientService) {
        this.forumService = forumService;
        this.patientService = PatientService;
    }
    @GetMapping
    public String getForumMainPage(@RequestParam(required = false) String query, Model model) {
        List<DiscussionDto> discussions;
        
        if (query != null && !query.trim().isEmpty()) {
            discussions = forumService.searchDiscussions(query);
        } else {
            discussions = forumService.getAllDiscussion();
        }

        model.addAttribute("discussions", discussions);
        model.addAttribute("hospitals", patientService.findHospital(null,null,null,null,null,null)); 
        return "forum";
    }
    @GetMapping("/discussion/{id}")
    public String getSingleDiscussion(@PathVariable Long id, Model model) {
        DiscussionDto discussion = forumService.getDiscussionById(id);
        model.addAttribute("discussion", discussion);
        return "discussion-detail";
    }
    @PostMapping("/create")
    public String createPost(
            HttpSession session,
            @RequestParam(required = false) Long hospitalid,
            @RequestParam String title,
            @RequestParam String content,
            RedirectAttributes redirectAttributes) {

        Long loggedInUserId = (Long) session.getAttribute("LOGGED_IN_USER_ID");
        if (loggedInUserId == null) {
            return "redirect:/login";
        }

        forumService.createDiscussion(loggedInUserId, hospitalid, title, content);
        redirectAttributes.addFlashAttribute("successMessage", "Discussion posted successfully!");
        return "redirect:/web/forum";
    }
    @PostMapping("/discussion/{discussionid}/answer")
    public String postAnswer(
            HttpSession session,
            @PathVariable Long discussionid,
            @RequestParam String answer,
            RedirectAttributes redirectAttributes) {

        Long loggedInUserId = (Long) session.getAttribute("LOGGED_IN_USER_ID");
        if (loggedInUserId == null) {
            return "redirect:/login";
        }

        forumService.createAnswer(loggedInUserId, discussionid, answer);
        return "redirect:/web/forum/discussion/" + discussionid;
    }
}