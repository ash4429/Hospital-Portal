package com.DoctorAssisstent.PatientPortal2.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.DoctorAssisstent.PatientPortal2.model.Hospital;

public interface Hospitalrepo extends JpaRepository<Hospital,Long>{

    List<Hospital> findAll(Specification<Hospital> hospital);

}
