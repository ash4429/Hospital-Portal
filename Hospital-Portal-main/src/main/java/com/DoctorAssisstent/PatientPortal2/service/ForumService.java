package com.DoctorAssisstent.PatientPortal2.service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jakarta.persistence.criteria.Predicate;
import java.util.regex.Pattern;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.DoctorAssisstent.PatientPortal2.dto.AnswerDto;
import com.DoctorAssisstent.PatientPortal2.dto.DiscussionDto;
import com.DoctorAssisstent.PatientPortal2.dto.UserSignupRequest;
import com.DoctorAssisstent.PatientPortal2.exceptionhandler.ResourceNotFoundException;
import com.DoctorAssisstent.PatientPortal2.model.Answer;
import com.DoctorAssisstent.PatientPortal2.model.Discussion;
import com.DoctorAssisstent.PatientPortal2.model.Hospital;
import com.DoctorAssisstent.PatientPortal2.model.User;
import com.DoctorAssisstent.PatientPortal2.repository.Answerrepo;
import com.DoctorAssisstent.PatientPortal2.repository.Discussionrepo;
import com.DoctorAssisstent.PatientPortal2.repository.Hospitalrepo;
import com.DoctorAssisstent.PatientPortal2.repository.Userrepo;
import com.DoctorAssisstent.PatientPortal2.specification.ForumMapper;

import jakarta.transaction.Transactional;

@Service
public class ForumService {

    private final Answerrepo answerrepo;
    private final Hospitalrepo hospitalrepo;
    private final Discussionrepo discussionrepo;
    private final Userrepo userrepo;
    private final ForumMapper forumMapper;

    public ForumService(Answerrepo answerrepo, Hospitalrepo hospitalrepo, Discussionrepo discussionrepo, Userrepo userrepo, ForumMapper forumMapper) {
        this.answerrepo = answerrepo;
        this.hospitalrepo = hospitalrepo;
        this.discussionrepo = discussionrepo;
        this.forumMapper = forumMapper;
        this.userrepo = userrepo;
    }

    public List<DiscussionDto> getAllDiscussion() {
        return discussionrepo
                .findAllByOrderByCreatedTimeDesc()
                .stream()
                .map(disc -> {
                    DiscussionDto dto = forumMapper.toDiscussionDto(disc);
                    truncateTitleForFeed(dto);
                    return dto;
                })
                .toList();
    }

    public List<DiscussionDto> getAllDiscussionByhospitalid(Long id) {
        return discussionrepo
                .findByHospitalIdOrderByCreatedTimeDesc(id)
                .stream()
                .map(disc -> {
                    DiscussionDto dto = forumMapper.toDiscussionDto(disc);
                    truncateTitleForFeed(dto);
                    return dto;
                })
                .toList();
    }

    public List<DiscussionDto> searchDiscussions(String query) {

        if (query == null || query.isBlank()) {
            return List.of();
        }

        String[] words = query.trim().split("\\s+");

        Specification<Discussion> spec = (root,criteriaquery,criteriaBuilder)->{
            List<Predicate> predicates=new ArrayList<>();
            for(String s:words){
                String pattern = "%" + s.toLowerCase() + "%";
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), pattern));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return discussionrepo
                .findAll(spec)
                .stream()
                .map(disc -> {
                    DiscussionDto dto = forumMapper.toDiscussionDto(disc);
                    truncateTitleForFeed(dto);
                    return dto;
                })
                .toList();
    }

    public DiscussionDto getDiscussionById(Long id) {
        Discussion disc = discussionrepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No Discussion Found with ID: " + id));
        
        DiscussionDto discdto = forumMapper.toDiscussionDto(disc);
        List<Answer> ans = answerrepo.findByDiscussionIdOrderByCreatedTimeAsc(id);
        List<AnswerDto> ansdto = ans.stream().map(forumMapper::toAnswerDto).toList();
        discdto.setAnswer(ansdto);
        return discdto;
    }

    @Transactional
    public DiscussionDto createDiscussion(Long userid, Long hospitalid, String title, String content) {
        User u = userrepo.findById(userid).orElseThrow(() -> new RuntimeException("User Not Found"));
        
        Discussion disc = new Discussion();
        disc.setAuthor(u);
        disc.setContent(content);
        disc.setTitle(title);
        
        String baseSlug = generateSlug(title);
        String uniqueSlug = makeSlugUnique(baseSlug);
        disc.setSlug(uniqueSlug);

        if (hospitalid != null && hospitalid > 0) {
            Hospital hos = hospitalrepo.findById(hospitalid).orElseThrow(() -> new RuntimeException("College Not Found"));
            disc.setHospital(hos);
        }
        return forumMapper.toDiscussionDto(discussionrepo.save(disc));
    }

    @Transactional
    public AnswerDto createAnswer(Long userid, Long discussionid, String answer) {
        User u = userrepo.findById(userid).orElseThrow(() -> new RuntimeException("User Not Found"));
        Discussion disc = discussionrepo.findById(discussionid).orElseThrow(() -> new RuntimeException("Discussion Not Found"));
        
        Answer ans = new Answer();
        ans.setAnswer(answer);
        ans.setResponder(u);
        ans.setDiscussion(disc);
        return forumMapper.toAnswerDto(answerrepo.save(ans));
    }


    @Transactional
    public String signupUser(UserSignupRequest request) {
        if (userrepo.existsByName(request.name())) {
            throw new IllegalArgumentException("Email is already registered!");
        }
        User user = new User();
        user.setName(request.name());
        user.setPassword(request.password()); 
        userrepo.save(user);
        return "Registration successful for user: " + user.getName();
    }

    public User validateUserLogin(String name, String password) {
        User user = userrepo.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password."));
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password.");
        }
        return user;
    }




    private void truncateTitleForFeed(DiscussionDto dto) {
        if (dto.getTitle() != null && dto.getTitle().length() > 60) {
            dto.setTitle(dto.getTitle().substring(0, 57).trim() + "...");
        }
    }

    private String generateSlug(String input) {
        if (input == null || input.isBlank()) return "discussion";
        Pattern nonLatin = Pattern.compile("[^\\w-]");
        Pattern whitespace = Pattern.compile("[\\s]");
        String slug = Normalizer.normalize(input, Normalizer.Form.NFD);
        slug = whitespace.matcher(slug).replaceAll("-");
        slug = nonLatin.matcher(slug).replaceAll("");
        slug = slug.replaceAll("-+", "-").replaceAll("^-|-$", ""); 
        return slug.toLowerCase(Locale.ENGLISH);
    }

    private String makeSlugUnique(String baseSlug) {
        String finalSlug = baseSlug;
        int count = 1;
        while (discussionrepo.existsBySlug(finalSlug)) {
            finalSlug = baseSlug + "-" + count;
            count++;
        }
        return finalSlug;
    }



}
