# Enhancement 1: Advanced Search Functionality and Patient Data Linking (Java)

This repository showcases the **first major enhancement** to our Java-based appointment scheduling and contact management system. The primary goals of Enhancement 1 are:

- **Link appointments to specific patients** (via `patientID`), providing a more complete view of each booking.  
- **Expand appointment details** with a *reason* field for improved scheduling context.  
- **Add robust search capabilities** in `AppointmentService` so users can look up appointments by date, time, patient info, or reason.  
- **Integrate data persistence** for loading and saving appointments/patients to disk.

---
[Grand Strand Systems](https://github.com/GavinBish911/GavinBish911.github.io/tree/Grand-Strand-Systems/Enhanced%20Grand%20Strand%20Systems)
---

## Table of Contents

1. [Overview](#overview)  
2. [Key Features](#key-features)  
3. [Setup and Installation](#setup-and-installation)  
4. [Usage](#usage)  
5. [Contributing](#contributing)
6. [Diffs](#diffs)  

---

## Overview

Previously, the application included basic classes (`Contact`, `Appointment`, `Task`, etc.) and services (e.g., `AppointmentService`, `ContactService`) for creating and managing records. However, appointments were not **fully linked** to patient data, and searching capabilities were minimal. With **Enhancement 1**, the system now:

- **Associates each appointment** with a `patientID`, allowing direct lookups for appointments related to a specific person.  
- **Introduces advanced search** logic that matches by date/time formats, partial text in description, appointment reason, or patient details (name, phone, email, insurance).  
- **Improves data persistence** through the `DataPersistenceService`, saving/loading appointments and patients from files. This is crucial for retaining records between sessions.

These improvements make the system more versatile for healthcare or administrative scenarios where scheduling requires quick lookups and cross-referencing of patient data.

---

## Key Features

1. **Patient-Linked Appointments**  
   - `Appointment` class now contains `patientID` and `reason`, enabling more detailed scheduling.  
   - `AppointmentService` methods updated to handle creation and editing of these new fields.

2. **Enhanced Search**  
   - `searchAppointments` in `AppointmentService` can parse potential **date/time** formats, look for keywords in **descriptions** or **reasons**, and even query **patient-specific** information (e.g., phone/email).  
   - Simplifies locating relevant appointments, especially in larger data sets.

3. **Data Persistence**  
   - `DataPersistenceService` can **save** and **load** `appointments.dat` and `patients.dat`.  
   - Ensures user data persists across runs, making the application suitable for real-world usage.

4. **Separate Services for Contacts and Patients**  
   - `ContactService` retains core contact functionality, while `PatientService` extends contact attributes (insurance, email, etc.).  
   - `AppointmentService` seamlessly references the `PatientService` to enrich appointment data with patient details.

5. **Backward Compatibility**  
   - `Appointment` and `AppointmentService` preserve existing constructors and methods.  
   - Legacy usage remains functional while new features are easily accessible.

---

## Setup and Installation

1. **Clone the Repository**  
   ```bash
   git clone https://github.com/YourUsername/Enhancement1-AdvancedSearchAndPatientData.git
   cd Enhancement1-AdvancedSearchAndPatientData
   ```
2. **Compile the Java Files**
   - Ensure you have Java 8+ installed
   - From the project root directory, compile using:
      ```bash
      javac Main/*.java Test/*.java
      ```
    - This Command compiles all .java files in both Main and Test directories
   
3. Run the Application
   - Depending on your entry point (e.g., MainApplication or a test runner), run with:
      ```bash
      java Main.YourAppEntryPoint
      ```

4. Run the Tests
   - The test files (e.g., AppointmentServiceTest.java, ContactServiceTest.java) can be executed with:
   - (assuming you have JUnit on the classpath or are using an IDE that supports JUnit.)
      ```bash
      java org.junit.platform.console.ConsoleLauncher --class-path . --scan-class-path
      ```

## Usage

1. Create and Link Appointments
   - Use AppointmentService.addAppointment to schedule new appointments. You can specify date, description, patientID, and reason.
     
2. Search Appointments
   - Call AppointmentService.searchAppointments("some text or date") to look for matching records:     
      - Date format detection (e.g., MM/dd/yyyy, yyyy-MM-dd, etc.).
      - Times (e.g., "09:30") or partial text in descriptions or reason (e.g., "checkup").
      - Patient-based fields, including first/last name, phone, email, or insurance (when PatientService is linked).

4. Manage Patients and Contacts
   - PatientService: Add, delete, update phone/email, etc.
   - ContactService: Manage simpler contact entries, focusing on name, phone, and address.

5. Data Persistence
   - When the application closes or at specific intervals, call DataPersistenceService.saveAppointments() and DataPersistenceService.savePatients() to persist your records.
   - On startup, load them again with loadAppointments() / loadPatients().
  

## Contributing

We welcome contributions! Feel free to open issues or submit pull requests:

   1. Fork the repository and create a feature branch.
   2. Commit your changes with clear and descriptive messages.
   3. Submit a pull request outlining your modifications and their benefits.

Thank you for exploring Enhancement 1!
If you have any questions or suggestions, please reach out via the Issues tab or contact the maintainer directly. Your feedback is valuable in improving the system further.

## Diffs
- [Contact](https://github.com/GavinBish911/GavinBish911.github.io/compare/Grand-Strand-Systems?expand=1#diff-cff0905156e36a45c2896571f693f40859bd1ca9c3e3627f540f7e00e80603dd)
- [Task](https://github.com/GavinBish911/GavinBish911.github.io/compare/Grand-Strand-Systems?expand=1#diff-e6ec5369d250e613acc106d620ad13de8da5d7ba8175d3fb6f8850da48f8b5ea)
- [Appointment Service Test](https://github.com/GavinBish911/GavinBish911.github.io/compare/Grand-Strand-Systems?expand=1#diff-72fbfbded1986ae5cd47e1dad44a2bdb9a0cca44c36fce387eb32f03c07a833f)
- [Contact Service Test](https://github.com/GavinBish911/GavinBish911.github.io/compare/Grand-Strand-Systems?expand=1#diff-ee6e8b22cec0e4f79792e2f65a947d51fbe579382f00e703db39d7a0c14c5c62)
