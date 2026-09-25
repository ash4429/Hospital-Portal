package com.DoctorAssisstent.PatientPortal2.service;
import org.springframework.stereotype.Service;

@Service
public class AiAnalysisService {
    public String analyzePatientImage(String fileName, String prompt) {
        return "MedPortal AI Analysis: The report '" + fileName + "' appears to be a standard clinical finding. Please consult a doctor for a full diagnosis.";
    }
}