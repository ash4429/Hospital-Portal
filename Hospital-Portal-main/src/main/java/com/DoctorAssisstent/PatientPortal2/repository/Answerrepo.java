package com.DoctorAssisstent.PatientPortal2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.DoctorAssisstent.PatientPortal2.model.Answer;

public interface Answerrepo extends JpaRepository<Answer,Long>{
    List<Answer> findByDiscussionIdOrderByCreatedTimeAsc(Long id);

    @Query("SELECT a FROM Discussion d JOIN FETCH Answer a ORDER BY d.createdTime ASC")
    List<Answer> findDiscussions(Long id);
}
