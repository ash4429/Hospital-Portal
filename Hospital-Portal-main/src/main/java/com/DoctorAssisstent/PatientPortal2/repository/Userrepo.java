package com.DoctorAssisstent.PatientPortal2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DoctorAssisstent.PatientPortal2.model.User;


public interface Userrepo extends JpaRepository<User,Long>{

    Optional<User> findByName(String name);

    boolean existsByName(String name);

}
