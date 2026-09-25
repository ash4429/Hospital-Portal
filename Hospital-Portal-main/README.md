# Hospital Portal

A comprehensive, production-ready **Hospital Management Portal** built using the **Spring Boot** framework and **Maven**. This centralized enterprise application digitizes medical workflows, facilitates real-time doctor-patient interactions, and automates administrative, scheduling, and health records management tasks safely and securely.

---

## 🎯 What is the Project?

The **Hospital Portal** is an all-in-one digital healthcare ecosystem designed to link patients, medical practitioners, and clinical administrators. By moving away from legacy paperwork, this system operates as a single source of truth for hospital operations. 

It organizes the healthcare journey into three distinct role-based experiences:
*   **Patient Dashboard:** Self-service portal to book appointments, review medical prescriptions, and securely download diagnostic uploads.
*   **Doctor Station:** Clinical view allowing physicians to manage their schedules, review comprehensive patient histories, and update e-prescriptions.
*   **Admin Command Center:** System-wide oversight to manage staff allocation, monitor hospital metrics, oversee file directories, and ensure strict data security.

---

## 💡 Problems Solved by the Portal

Traditional healthcare facilities struggle with fragmented data, operational delays, and excessive administrative workloads. This system directly solves those pain points:

1. **Elimination of Fragmented Paper Records:** Legacy paper charts are easily lost, damaged, or misread. This platform digitizes electronic health records (EHR) under explicit patient IDs, maintaining permanent, accessible, and structured medical logs.
2. **Reduction in Appointment Redundancy & No-Shows:** Manual scheduling often results in overbooking, double bookings, or forgotten consultations. The integrated scheduling calendar provides real-time availability updates, optimizing doctor timetables and minimizing patient wait times.
3. **Bottlenecks in Medical Document Delivery:** Patients traditionally have to travel back to clinics just to collect physical lab results. The dedicated multi-part file subsystem handles document storage natively, allowing diagnostic forms to be seamlessly reviewed or downloaded remotely.
4. **Administrative Overload on Staff:** Automating front-desk tasks such as patient registration, queue organization, and staff shift tracking frees up nurses and receptionists to focus on critical, in-person patient care.
5. **Data Mismanagement & Security Risks:** Leaving physical documents unattended exposes sensitive Patient Health Information (PHI). Role-based access ensures that only authenticated individuals can access restricted folders and records.

---

## 🛠️ Deep Dive: Complete Tech Stack

The architecture leverages standard enterprise tools to guarantee maximum system runtime, clean modular boundaries, and scalable data streaming.

### 1. Core Backend (Business Logic Layer)
*   **Java 17+:** Utilizes strong typing, modern concurrency APIs, and object-oriented architectures to ensure stable backend compilation.
*   **Spring Boot:** The cornerstone framework providing embedded server utilities, auto-configuration modules, and fast REST API construction.
*   **Spring Data JPA / Hibernate:** Object-Relational Mapping (ORM) framework that seamlessly maps complex Java entity schemas into standard SQL database configurations, eliminating manual boilerplate SQL creation.

### 2. Frontend & User Interface (Presentation Layer)
*   **Thymeleaf Templates / HTML5:** Server-side templating engine perfectly coupled with Spring Boot to render web components securely and fast.
*   **CSS3 & Bootstrap:** Responsive frontend configuration styling that adapts gracefully across web browsers, tablets, and smartphone layouts.
*   **JavaScript (ES6+):** Empowers async form checking, dynamic calendar actions, and seamless AJAX data payloads without refreshing pages.

### 3. File Processing & Automation Subsystems
*   **Spring Multipart Configuration:** Natively processes heavy medical file attachments, images, and PDF uploads, funneling files smoothly into the server's filesystem (`uploads/` directory).
*   **Apache Maven Build Engine (`pom.xml`):** Controls software compilation lifecycle, dependency mapping, version control safety, and environment packaging.
*   **Maven Wrapper (`mvnw`, `mvnw.cmd`):** Ensures that the application boots identically on any machine without requiring pre-installed system Maven binaries.
*   **.hintrc Code Quality Linter:** Enforces uniform syntax styling, strict syntax validation, and codebase structural compliance.

### 4. Database & Storage Layer (Target Environment)
*   **Relational Database Engine:** Optimized for relational database engines like **MySQL**, **PostgreSQL**, or embedded **H2** configurations to cleanly map structural links between relational entities (e.g., matching a `Patient` ID to specific `Appointment` and `Prescription` blocks).

---

## 📁 Repository Structure

```text
├── .mvn/                   # Maven wrapper configuration files
├── src/                    # Main application source code
│   ├── main/
│   │   ├── java/           # Java backend source files (Controllers, Services, Models)
│   │   └── resources/      # Application configurations (application.properties, static assets)
│   └── test/               # Unit and integration test suites
├── uploads/                # Directory for storing uploaded medical reports and media
├── .gitattributes          # Git configuration for path attributes
├── .gitignore              # Files and directories excluded from version control
├── .hintrc                 # Code quality and linting configuration
├── mvnw                    # Maven wrapper script for Linux/macOS
├── mvnw.cmd                # Maven wrapper script for Windows
└── pom.xml                 # Maven Project Object Model file specifying project dependencies
```

---

## 📋 Prerequisites

Before running this project, ensure you have the following installed:
*   **Java Development Kit (JDK):** Version 17 or higher
*   **Database:** MySQL, PostgreSQL, or access to an active SQL instance

---

## ⚙️ Setup & Installation

### 1. Clone the Repository
```bash
git clone https://github.com
cd Hospital-Portal
```

### 2. Configure Environment Properties
Navigate to `src/main/resources/application.properties` and update your data configuration details:
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA/Hibernate Properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# File Upload Configurations
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB
```

### 3. Create the Uploads Directory
Ensure that the `uploads/` directory exists in the root of your project directory to handle incoming patient reports smoothly.

---

## 🏃 Running the Application

**For Linux / macOS:**
```bash
chmod +x mvnw
./mvnw spring-boot:run
```

**For Windows:**
```cmd
mvnw.cmd spring-boot:run
```

Once running, navigate to **`http://localhost:8080`** in your browser.

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.
