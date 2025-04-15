/*************************
 * Name: 	Gavin Bish
 * Course: 	CS-499
 * Date: 	Current Date
 * Description: This is the PatientService class. It manages a list of patients and provides
 * operations for adding, retrieving, updating, and deleting patient information.
 *************************/

package Main;

import java.util.ArrayList;
import java.util.List;

public class PatientService {
    // ArrayList to hold the list of patients
    public List<Patient> patientList;
    private final DataPersistenceService persistenceService;

    public PatientService() {
        this.persistenceService = new DataPersistenceService();
        loadPatients(); // Load patients when service is created
    }

    // Load patients from persistence
    private void loadPatients() {
        patientList = persistenceService.loadPatients();
    }

    // Save patients to persistence
    private void savePatients() {
        persistenceService.savePatients(patientList);
    }

    // Add a new patient
    public void addPatient(String firstName, String lastName, String patientID, String address,
                           String phoneNumber, String emailAddress, String insuranceInfo) {
        Patient newPatient = new Patient(firstName, lastName, patientID, address,
                phoneNumber, emailAddress, insuranceInfo);
        patientList.add(newPatient);
        savePatients(); // Save changes
    }

    // Delete patient by ID
    public void deletePatient(String patientID) {
        patientList.removeIf(patient -> patient.getPatientID().equals(patientID));
        savePatients(); // Save changes
    }

    // Get patient by ID
    public Patient getPatient(String patientID) {
        Patient patient = null;
        for (Patient element : patientList) {
            if (element.getPatientID().equals(patientID)) {
                patient = element;
                break;
            }
        }
        return patient;
    }

    // Display all patients (for debugging)
    public void displayPatientList() {
        for (Patient element : patientList) {
            System.out.println("\t Patient ID: " + element.getPatientID());
            System.out.println("\t First Name: " + element.getFirstName());
            System.out.println("\t Last Name: " + element.getLastName());
            System.out.println("\t Phone Number: " + element.getPhoneNumber());
            System.out.println("\t Email: " + element.getEmailAddress());
            System.out.println("\t Insurance: " + element.getInsuranceInfo());
            System.out.println("\t Address: " + element.getAddress() + "\n");
        }
    }

    // Update patient's phone number
    public void updatePatientPhone(String patientID, String newPhoneNumber) {
        for (Patient patient : patientList) {
            if (patient.getPatientID().equals(patientID)) {
                patient.setPhoneNumber(newPhoneNumber);
                savePatients(); // Save changes
                return;
            }
        }
    }

    // Update patient's email address
    public void updatePatientEmail(String patientID, String newEmailAddress) {
        for (Patient patient : patientList) {
            if (patient.getPatientID().equals(patientID)) {
                patient.setEmailAddress(newEmailAddress);
                savePatients(); // Save changes
                return;
            }
        }
    }

    // Update patient's insurance information
    public void updateInsuranceInfo(String patientID, String insuranceInfo) {
        for (Patient patient : patientList) {
            if (patient.getPatientID().equals(patientID)) {
                patient.setInsuranceInfo(insuranceInfo);
                break;
            }
        }
    }

    // Add or update patient (handles both new and existing patients)
    public void addOrUpdatePatient(String patientID, String firstName, String lastName,
                                   String number, String address, String phoneNumber,
                                   String emailAddress, String insuranceInfo) {
        Patient existingPatient = getPatient(patientID);

        if (existingPatient != null) {
            // Update existing patient
            existingPatient.setFirstName(firstName);
            existingPatient.setLastName(lastName);
            existingPatient.setNumber(number);
            existingPatient.setAddress(address);
            existingPatient.setPhoneNumber(phoneNumber);
            existingPatient.setEmailAddress(emailAddress);
            existingPatient.setInsuranceInfo(insuranceInfo);
        } else {
            // Add new patient
            addPatient(firstName, lastName, number, address,
                    phoneNumber, emailAddress, insuranceInfo);
        }
    }
}