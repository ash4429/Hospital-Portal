package com.DoctorAssisstent.PatientPortal2.config;

import com.DoctorAssisstent.PatientPortal2.model.Hospital;
import com.DoctorAssisstent.PatientPortal2.model.Location;
import com.DoctorAssisstent.PatientPortal2.repository.Hospitalrepo;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final Hospitalrepo hospitalrepo;

    public DataInitializer(Hospitalrepo hospitalrepo) {
        this.hospitalrepo = hospitalrepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (hospitalrepo.count() == 0) {
            Hospital h1 = new Hospital();
        h1.setName("ESIC Medical College & Hospital");
        h1.setRatings(4);
        h1.setLocation(new Location("Bihar", "Bihta", 25.5585, 84.8694, "Near Bihta Railway Station"));

        // 2. AIIMS Patna (~25km from you)
        Hospital h2 = new Hospital();
        h2.setName("AIIMS Patna");
        h2.setRatings(5);
        h2.setLocation(new Location("Bihar", "Patna", 25.5601, 85.0694, "Phulwari Sharif"));

        // 3. Paras HMRI Hospital (~28km from you)
        Hospital h3 = new Hospital();
        h3.setName("Paras HMRI Hospital");
        h3.setRatings(4);
        h3.setLocation(new Location("Bihar", "Patna", 25.6083, 85.0917, "Raja Bazar, Bailey Road"));

        // 4. IGIMS Patna (~27km from you)
        Hospital h4 = new Hospital();
        h4.setName("IGIMS Patna");
        h4.setRatings(4);
        h4.setLocation(new Location("Bihar", "Patna", 25.6105, 85.0898, "Sheikhpura"));

        // 5. Ruban Memorial Hospital (~30km from you)
        Hospital h5 = new Hospital();
        h5.setName("Ruban Memorial Hospital");
        h5.setRatings(5);
        h5.setLocation(new Location("Bihar", "Patna", 25.6133, 85.1055, "Patliputra Kurji Road"));

        // 6. PMCH (~35km from you)
        Hospital h6 = new Hospital();
        h6.setName("Patna Medical College and Hospital (PMCH)");
        h6.setRatings(3);
        h6.setLocation(new Location("Bihar", "Patna", 25.6214, 85.1578, "Ashok Rajpath"));

        // 7. Kurji Holy Family Hospital (~32km from you)
        Hospital h7 = new Hospital();
        h7.setName("Kurji Holy Family Hospital");
        h7.setRatings(4);
        h7.setLocation(new Location("Bihar", "Patna", 25.6358, 85.1017, "Sadaqat Ashram Area"));

        // 8. SKMCH Muzaffarpur (~90km from you - SHOULD NOT SHOW in 50km radius)
        Hospital h8 = new Hospital();
        h8.setName("Sri Krishna Medical College (SKMCH)");
        h8.setRatings(3);
        h8.setLocation(new Location("Bihar", "Muzaffarpur", 26.1481, 85.3941, "Umanagar"));
        // ESIC Bihta
        h1.setImageUrl("https://images.unsplash.com/photo-1586773860418-d3b97998c637?q=80&w=400");
        // AIIMS Patna
        h2.setImageUrl("https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?q=80&w=400");
        // Paras HMRI
        h3.setImageUrl("https://images.unsplash.com/photo-1516549655169-df83a0774514?q=80&w=400");
        // IGIMS
        h4.setImageUrl("https://images.unsplash.com/photo-1581056771107-24ca5f033842?q=80&w=400");
        // Ruban Memorial
        h5.setImageUrl("https://images.unsplash.com/photo-1538108149393-fdfd81691935?q=80&w=400");
        // PMCH
        h6.setImageUrl("https://images.unsplash.com/photo-1512678080530-7760d81faba6?q=80&w=400");
        // Kurji Holy Family
        h7.setImageUrl("https://images.unsplash.com/photo-1632833239869-a37e31580662?q=80&w=400");

        hospitalrepo.saveAll(Arrays.asList(h1, h2, h3, h4, h5, h6, h7, h8));

        Hospital h9 = new Hospital();
        h9.setName("Netaji Subhas Medical College & Hospital");
        h9.setRatings(4);
        h9.setLocation(new Location("Bihar", "Bihta", 25.5454, 84.8854, "Amhara, Bihta"));

        // 10. Hi-Tech Emergency Hospital - Near Danapur/Saguna More
        Hospital h10 = new Hospital();
        h10.setName("Hi-Tech Emergency Hospital");
        h10.setRatings(4);
        h10.setLocation(new Location("Bihar", "Patna", 25.5944, 85.0401, "Saguna More, Danapur"));

        // 11. Mediversal Hospital - Kankarbagh area
        Hospital h11 = new Hospital();
        h11.setName("Mediversal Multi Super Speciality Hospital");
        h11.setRatings(5);
        h11.setLocation(new Location("Bihar", "Patna", 25.6033, 85.1442, "Doctors' Colony, Kankarbagh"));

        // 12. Mahavir Cancer Sansthan - Phulwari Sharif
        Hospital h12 = new Hospital();
        h12.setName("Mahavir Cancer Sansthan");
        h12.setRatings(4);
        h12.setLocation(new Location("Bihar", "Patna", 25.5780, 85.0885, "Phulwari Sharif"));

        // 13. Ford Hospital - New Bypass Road
        Hospital h13 = new Hospital();
        h13.setName("Ford Hospital & Research Centre");
        h13.setRatings(4);
        h13.setLocation(new Location("Bihar", "Patna", 25.5912, 85.1490, "Khemnichak, New Bypass Road"));

        // 14. Jeevak Heart Hospital - Kankarbagh
        Hospital h14 = new Hospital();
        h14.setName("Jeevak Heart Hospital");
        h14.setRatings(4);
        h14.setLocation(new Location("Bihar", "Patna", 25.6010, 85.1462, "Doctors' Colony, Kankarbagh"));

        // 15. Sahyog Hospital - Patliputra Colony
        Hospital h15 = new Hospital();
        h15.setName("Sahyog Hospital");
        h15.setRatings(4);
        h15.setLocation(new Location("Bihar", "Patna", 25.6198, 85.1112, "Patliputra Colony"));

        // 16. Sadar Hospital, Ara - To the West (Approx 35-40km from Bihta)
        Hospital h16 = new Hospital();
        h16.setName("Sadar Hospital Ara");
        h16.setRatings(3);
        h16.setLocation(new Location("Bihar", "Ara", 25.5567, 84.6675, "Ara Junction Road, Bhojpur"));

        h9.setImageUrl("https://images.unsplash.com/photo-1512678080530-7760d81faba6?q=80&w=400");

        h10.setImageUrl("https://images.unsplash.com/photo-1516549655169-df83a0774514?q=80&w=400");

        h11.setImageUrl("https://images.unsplash.com/photo-1581056771107-24ca5f033842?q=80&w=400");
        
        h12.setImageUrl("https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?q=80&w=400");
        
        h13.setImageUrl("https://images.unsplash.com/photo-1538108149393-fdfd81691935?q=80&w=400");
        
        h14.setImageUrl("https://images.unsplash.com/photo-1629909613654-28e377c37b09?q=80&w=400");
       
        h15.setImageUrl("https://images.unsplash.com/photo-1632833239869-a37e31580662?q=80&w=400");
        
        h16.setImageUrl("https://images.unsplash.com/photo-1504439468489-c8920d796a29?q=80&w=400");
        
        
        hospitalrepo.saveAll(Arrays.asList(h9, h10, h11, h12, h13, h14, h15, h16));
        }
    }
}