package com.DoctorAssisstent.PatientPortal2.controller;

import com.DoctorAssisstent.PatientPortal2.dto.*;
import com.DoctorAssisstent.PatientPortal2.model.Hospital;
import com.DoctorAssisstent.PatientPortal2.repository.Hospitalrepo;
import com.DoctorAssisstent.PatientPortal2.service.ForumService;
import com.DoctorAssisstent.PatientPortal2.service.PatientService;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;



@Controller
public class WebController {

    private final PatientService patientService;
    private final ForumService forumService;
    private final Hospitalrepo hospitalrepo;

    public WebController(PatientService ps, ForumService fs, Hospitalrepo hr) {
        this.patientService = ps;
        this.forumService = fs;
        this.hospitalrepo = hr;
    }

    @GetMapping("/")
    public String index() { return "index"; }

    @GetMapping("/login")
    public String loginPage() { return "login"; }

    @GetMapping("/signup")
    public String signupPage() { return "signup"; }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    @PostMapping("/web/login")
    public String handleLogin(@RequestParam String name, @RequestParam String password, HttpSession session) {
        try {
            UserDto u = patientService.loginuser(name, password);
            session.setAttribute("LOGGED_IN_USER_ID", u.getId());
            return "redirect:/dashboard";
        } catch (Exception e) {
            return "redirect:/login?error";
        }
    }

    @PostMapping("/web/signup")
    public String handleSignup(@RequestParam String name, @RequestParam String mobno, @RequestParam String password, HttpSession session) {
        try {
            UserSignupRequest request = new UserSignupRequest(name, mobno, password);
            UserDto u = patientService.signupuser(request);
            session.setAttribute("LOGGED_IN_USER_ID", u.getId()); // Log in immediately
            return "redirect:/dashboard";
        } catch (Exception e) {
            return "redirect:/signup?error";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("LOGGED_IN_USER_ID");
        if (userId == null) return "redirect:/login";
        
        UserDto user = patientService.getAllusers().stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst().orElse(null);
        
        if(user == null) return "redirect:/login";
        model.addAttribute("user", user);
        return "dashboard";
    }
    @GetMapping("/web/ai-result")
    public String showResult() {
        return "ai-result";
    }

    @PostMapping("/web/upload")
    public String postImage(
        @RequestParam("image") MultipartFile imgFile,
        RedirectAttributes redirectAttributes,
        HttpSession httpSession
    ) {
        Long userid = (Long) httpSession.getAttribute("LOGGED_IN_USER_ID");
        if(userid==null) return "redirect:/login";
        if (imgFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
        redirectAttributes.addFlashAttribute("messageType", "error"); // For the red icon
        return "redirect:/web/profile";
        }
        try {
            String fileName = patientService.saveImage(
                imgFile.getInputStream(), 
                imgFile.getOriginalFilename(), 
                imgFile.getContentType(), 
                userid
            );
            redirectAttributes.addFlashAttribute("message","success");
            return "redirect:/web/ai-result";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "Internal Server Error: Could not read file.");
        redirectAttributes.addFlashAttribute("messageType", "error");
        return "redirect:/web/profile";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        redirectAttributes.addFlashAttribute("messageType", "error");
        return "redirect:/web/profile";
        }
    }
    
    // @GetMapping("/web/forum")
    // public String forumList(Model model) {
    //     model.addAttribute("discussions", forumService.getAllDiscussion());
    //     model.addAttribute("hospitals", hospitalrepo.findAll());
    //     return "forum";
    // }

    @GetMapping("/web/hospitals")
    public String listHospitals(@RequestParam(required = false) String name,
                                @RequestParam(required = false) Integer ratings, 
                                @RequestParam(required = false) String city, 
                                @RequestParam(required = false) Double lat,
                                @RequestParam(required = false) Double lon,
                                @RequestParam(required = false) Double radius,
                                Model model) {
        List<Hospital> hospitals = patientService.findHospital(name,ratings,city,lat,lon,radius); 
        model.addAttribute("hospitals", hospitals);
        return "hospitals";
    }
    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("LOGGED_IN_USER_ID");
        if (userId == null) return "redirect:/login";
        
        UserDto user = patientService.getAllusers().stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst().orElse(null);
                
        model.addAttribute("user", user);
        return "profile";
    }
}