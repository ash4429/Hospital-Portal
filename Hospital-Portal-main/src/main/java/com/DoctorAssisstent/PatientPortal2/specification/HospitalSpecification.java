package com.DoctorAssisstent.PatientPortal2.specification;

import com.DoctorAssisstent.PatientPortal2.model.Hospital;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class HospitalSpecification { // Corrected to PascalCase

    public static Specification<Hospital> getHospital( // Corrected to camelCase
            String name,
            Integer minRatings, 
            String city
        ) { 
                
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")), "%" + name.trim().toLowerCase() + "%"
                ));
            }
            
            if (city != null && !city.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("location").get("city")), city.trim().toLowerCase()
                ));
            }
            
            if (minRatings != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ratings"), minRatings));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}