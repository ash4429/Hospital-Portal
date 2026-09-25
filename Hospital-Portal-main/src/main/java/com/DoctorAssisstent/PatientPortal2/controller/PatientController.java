package com.DoctorAssisstent.PatientPortal2.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.DoctorAssisstent.PatientPortal2.dto.UserDto;
import com.DoctorAssisstent.PatientPortal2.dto.UserSignupRequest;
import com.DoctorAssisstent.PatientPortal2.model.User;
import com.DoctorAssisstent.PatientPortal2.service.AiAnalysisService;
import com.DoctorAssisstent.PatientPortal2.service.PatientService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/abha")
public class PatientController {

    private final PatientService patientService;
    //private final AiAnalysisService aiAnalysisService;
    public PatientController(PatientService patientService,AiAnalysisService ai) {
        this.patientService = patientService;
        //this.aiAnalysisService=ai;
    }

    @PostMapping({"", "/login"})
    public ResponseEntity<UserDto> loginform(@RequestBody User us, HttpSession session) {
        UserDto u = patientService.loginuser(us.getName(), us.getPassword());
        session.setAttribute("LOGGED_IN_USER_ID", u.getId()); 
        return ResponseEntity.ok(u);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signupform(@RequestBody UserSignupRequest us) {
        UserDto u = patientService.signupuser(us);
        return ResponseEntity.status(HttpStatus.CREATED).body(u);
    }

    @GetMapping("/allusers")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> us = patientService.getAllusers();
        return ResponseEntity.ok(us);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file, HttpSession session) {
        Long userid = (Long) session.getAttribute("LOGGED_IN_USER_ID");
        if(userid==null) throw new RuntimeException("User not logged in...");
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload.");
        }
        try {
            String fileName = patientService.saveImage(
                file.getInputStream(), 
                file.getOriginalFilename(), 
                file.getContentType(), 
                userid
            );
            return ResponseEntity.ok("Image uploaded successfully! Saved as: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process file upload: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(e.getMessage());
        }
    }

    @GetMapping("/getmyimage")
    public ResponseEntity<Resource> getImage(HttpSession session) {
        Long userid = (Long) session.getAttribute("LOGGED_IN_USER_ID");
        if(userid==null) throw new RuntimeException("User not logged in...");
        Resource resource = patientService.findImage(userid);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) 
                .body(resource);
    }

    //  @GetMapping("/analyse-my-report")
    // public ResponseEntity<String> analyse(HttpSession session) throws IOException {
    //     Long userId = (Long) session.getAttribute("LOGGED_IN_USER_ID");
    //     if(userId==null) throw new RuntimeException("User not logged in...");
    //     String filename = patientService.getFilenameByUserId(userId); 
    //     if (filename == null) {
    //         return ResponseEntity.badRequest().body("No image uploaded for this user.");
    //     }
    //     String prompt = "Transcribe all visible text from this document accurately into structured text format.";
    //     String result = aiAnalysisService.analyzePatientImage(filename, prompt);
    //     return ResponseEntity.ok(result);
    // }
}
