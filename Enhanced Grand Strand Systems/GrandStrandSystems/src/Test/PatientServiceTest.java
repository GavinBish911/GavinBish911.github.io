/*************************
 * Name: 	Gavin Bish
 * Course: 	CS-499
 * Date: 	Current Date
 * Description: This is the test class for the PatientService.
 *************************/

package Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Main.Patient;
import Main.PatientService;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PatientServiceTest {

    private PatientService patientService;

    @BeforeEach
    void setUp() {
        patientService = new PatientService();
    }

    @Test
    @DisplayName("Can add a new patient")
    void testAddPatient() {
        patientService.addPatient("John", "Doe", "1234567890", "123 Main St",
                "5555555555", "john@example.com", "Blue Cross");

        Assertions.assertEquals(1, patientService.patientList.size(), "Should have added one patient");
        Patient added = patientService.patientList.get(0);
        Assertions.assertEquals("John", added.getFirstName());
        Assertions.assertEquals("Doe", added.getLastName());
        Assertions.assertEquals("1234567890", added.getNumber());
        Assertions.assertEquals("123 Main St", added.getAddress());
        Assertions.assertEquals("5555555555", added.getPhoneNumber());
        Assertions.assertEquals("john@example.com", added.getEmailAddress());
        Assertions.assertEquals("Blue Cross", added.getInsuranceInfo());
    }

    @Test
    @DisplayName("Can retrieve patient by ID")
    void testGetPatient() {
        // Clear any existing patients that might affect the test
        if (patientService.patientList != null) {
            patientService.patientList.clear();
        }

        // Add a patient
        patientService.addPatient("John", "Doe", "1234567890", "123 Main St",
                "5555555555", "john@example.com", "Blue Cross");

        // Verify the patient was added successfully
        Assertions.assertFalse(patientService.patientList.isEmpty(), "Patient was not added to the list");

        // Get the first patient in the list and print details for debugging
        Patient addedPatient = patientService.patientList.get(0);
        System.out.println("Added patient: " + addedPatient);
        System.out.println("Patient ID: " + addedPatient.getPatientID());

        // Store the ID
        String patientID = addedPatient.getPatientID();

        // Verify the ID is not null or empty
        assertNotNull(patientID, "Patient ID should not be null");
        Assertions.assertFalse(patientID.isEmpty(), "Patient ID should not be empty");

        // Retrieve patient by ID
        Patient retrieved = patientService.getPatient(patientID);

        // Verify retrieval was successful
        assertNotNull(retrieved, "Retrieved patient should not be null");

        // Verify patient details match
        Assertions.assertEquals("John", retrieved.getFirstName());
        Assertions.assertEquals("Doe", retrieved.getLastName());
        Assertions.assertEquals(patientID, retrieved.getPatientID());
    }

    @Test
    @DisplayName("Can delete patient")
    void testDeletePatient() {
        patientService.addPatient("John", "Doe", "1234567890", "123 Main St",
                "5555555555", "john@example.com", "Blue Cross");

        String patientID = patientService.patientList.get(0).getPatientID();
        patientService.deletePatient(patientID);

        Assertions.assertEquals(0, patientService.patientList.size(), "Should have deleted the patient");
    }

    @Test
    @DisplayName("Can update phone number")
    void testUpdatePhoneNumber() {
        // Add a patient
        patientService.addPatient("John", "Doe", "1234567890", "123 Main St",
                "5555555555", "john@example.com", "Blue Cross");

        // Make sure patient was added
        Assertions.assertFalse(patientService.patientList.isEmpty(), "Patient list should not be empty");

        // Get the patient ID
        String patientID = patientService.patientList.get(0).getPatientID();

        // Update phone number - check if method is named correctly
        patientService.updatePatientPhone(patientID, "9876543210");
        // Alternative method name if the above doesn't work
        // patientService.updatePatientPhoneNumber(patientID, "9876543210");

        // Get the updated patient
        Patient updated = patientService.getPatient(patientID);

        // Verify phone number was updated
        Assertions.assertEquals("9876543210", updated.getPhoneNumber(), "Phone number should be updated");
    }

    @Test
    @DisplayName("Can update email address")
    void testUpdateEmailAddress() {
        // Add a patient
        patientService.addPatient("John", "Doe", "1234567890", "123 Main St",
                "5555555555", "john@example.com", "Blue Cross");

        // Make sure patient was added
        Assertions.assertFalse(patientService.patientList.isEmpty(), "Patient list should not be empty");

        // Get the patient ID
        String patientID = patientService.patientList.get(0).getPatientID();

        // Update email - check if method is named correctly
        patientService.updatePatientEmail(patientID, "john.doe@newemail.com");
        // Alternative method name if the above doesn't work
        // patientService.updatePatientEmailAddress(patientID, "john.doe@newemail.com");

        // Get the updated patient
        Patient updated = patientService.getPatient(patientID);

        // Verify email was updated
        Assertions.assertEquals("john.doe@newemail.com", updated.getEmailAddress(), "Email address should be updated");
    }

}
