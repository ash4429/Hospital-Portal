package com.DoctorAssisstent.PatientPortal2.specification;
import org.springframework.stereotype.Component;
import com.DoctorAssisstent.PatientPortal2.model.*;
import com.DoctorAssisstent.PatientPortal2.dto.*;

@Component
public class ForumMapper {
    public DiscussionDto toDiscussionDto(Discussion d) {
        if (d == null) return null;
        DiscussionDto dto = new DiscussionDto();
        dto.setId(d.getId());
        dto.setTitle(d.getTitle());
        dto.setContent(d.getContent());
        dto.setCreatedTime(d.getCreatedTime());
        dto.setAuthorName(d.getAuthor() != null ? d.getAuthor().getName() : "User");
        dto.setHospitalName(d.getHospital() != null ? d.getHospital().getName() : "General");
        return dto;
    }
    public AnswerDto toAnswerDto(Answer a) {
        if (a == null) return null;
        return new AnswerDto(a.getId(), a.getAnswer(), a.getResponder() != null ? a.getResponder().getName() : "User");
    }
}