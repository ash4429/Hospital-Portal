package com.DoctorAssisstent.PatientPortal2.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.DoctorAssisstent.PatientPortal2.dto.UserDto;
import com.DoctorAssisstent.PatientPortal2.dto.UserSignupRequest;
import com.DoctorAssisstent.PatientPortal2.model.Hospital;
import com.DoctorAssisstent.PatientPortal2.model.User;
import com.DoctorAssisstent.PatientPortal2.repository.Hospitalrepo;
import com.DoctorAssisstent.PatientPortal2.repository.Userrepo;
import com.DoctorAssisstent.PatientPortal2.specification.HospitalSpecification;

@Service
public class PatientService {

    private final Userrepo userrepo;
    private final Hospitalrepo hospitalrepo;

    private static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads/";
    public PatientService(Userrepo userrepo,Hospitalrepo h) {
        this.userrepo = userrepo;
        this.hospitalrepo = h;
    }

    private UserDto toUserDto(User us) {
        return new UserDto(us.getId(), us.getName()); 
    }

    public UserDto loginuser(String name, String password) {
        User us = userrepo.findByName(name)
                .orElseThrow(() -> new RuntimeException("Wrong username..."));
        if (!us.getPassword().equals(password)) {
            throw new RuntimeException("Password is Not Correct!!!");
        }
        return toUserDto(us);
    }

    public UserDto signupuser(UserSignupRequest us) {
        if (userrepo.existsByName(us.name())) {
            throw new RuntimeException("Name already taken..");
        }
        User u = new User(us.name(),us.mobno(),us.password());
        return toUserDto(userrepo.save(u));
    }

    public List<UserDto> getAllusers() {
        return userrepo.findAll().stream()
                .map(this::toUserDto)
                .toList();
    }

    public String saveImage(InputStream fileStream, String originalFilename, String contentType, Long userid) {
        User us = userrepo.findById(userid)
                .orElseThrow(() -> new RuntimeException("User Not Found.."));

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed.");
        }

        try {
            Path path = Paths.get(UPLOAD_DIRECTORY);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            String fileName = System.currentTimeMillis() + "_" + originalFilename;
            us.setFilename(fileName);
            userrepo.save(us);

            Path filePath = path.resolve(fileName);
            Files.copy(fileStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store image: " + e.getMessage(), e);
        }
    }

    public Resource findImage(Long userid) {
        User us = userrepo.findById(userid)
                .orElseThrow(() -> new RuntimeException("User Not Found.."));
        
        String filename = us.getFilename();
        if (filename == null) {
            throw new RuntimeException("No profile image set for this user.");
        }

        Path filePath = Paths.get(UPLOAD_DIRECTORY).resolve(filename);
        Resource resource = new FileSystemResource(filePath);
        if (!resource.exists()) {
            throw new RuntimeException("Image file missing from storage.");
        }
        return resource;
    }

    public String getFilenameByUserId(Long userId) {
        User us = userrepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found.."));
        return us.getFilename();
    }
    public List<Hospital> getNearbyHospitals(List<Hospital> hos, Double userLat, Double userLon, Double radiusKm) {
    // 1. Safety check: If ANY required parameter is null, return the original list or empty
    if (hos == null) return new ArrayList<>();
    if (userLat == null || userLon == null) {
        return hos; 
    }
    return hos.stream()
        .filter(h -> h != null && h.getLocation() != null)
        .filter(h -> h.getLocation().getLatitude() != 0.0 && 
                     h.getLocation().getLongitude() != 0.0)
        .map(h -> {
            // Java unboxes userLat/userLon here. We checked for null above, so it's safe.
            double dist = calculateDistance(userLat, userLon, 
                                           h.getLocation().getLatitude(), 
                                           h.getLocation().getLongitude());
            
            // Rounding and setting the distance
            h.setDistance(Math.round(dist * 10.0) / 10.0);
            return h;
        })
        // 4. Filter by radius (Safe because radiusKm and getDistance() are checked/set)
        .filter(h -> h.getDistance() != null && h.getDistance() <= radiusKm)
        // 5. Sort by distance (Closest first)
        .sorted((h1, h2) -> {
            if (h1.getDistance() == null) return 1;
            if (h2.getDistance() == null) return -1;
            return Double.compare(h1.getDistance(), h2.getDistance());
        })
        .collect(Collectors.toList());
}
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // Kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadius * c;
    }

    public List<Hospital> findHospital(String name, Integer ratings,String city, Double lat, Double lon,Double radius) {
        if(radius==null) radius = 50.0;
        return getNearbyHospitals(hospitalrepo.findAll(HospitalSpecification.getHospital(name, ratings, city)),
                                lat,lon,radius
                                );
    }
}