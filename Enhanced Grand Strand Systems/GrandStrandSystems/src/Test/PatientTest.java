/***********************
 * Name: 	Gavin Bish
 * Course: 	CS-499
 * Date: 	Current Date
 * Description: This is the test class for Patient.
 *************************/

package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Main.Patient;

class PatientTest {

    @Test
    @DisplayName("Patient inherits Contact fields and has additional fields")
    void testPatientCreation() {
        Patient patient = new Patient("John", "Doe", "1234567890", "123 Main St",
                "5555555555", "john@example.com", "Blue Cross");

        // Test inherited fields from Contact
        assertEquals("John", patient.getFirstName());
        assertEquals("Doe", patient.getLastName());
        assertEquals("1234567890", patient.getNumber());
        assertEquals("123 Main St", patient.getAddress());

        // Test new fields
        assertEquals("5555555555", patient.getPhoneNumber());
        assertEquals("john@example.com", patient.getEmailAddress());
        assertEquals("Blue Cross", patient.getInsuranceInfo());
        assertNotNull(patient.getPatientID());
    }

    @Test
    @DisplayName("Patient phone number validation works")
    void testPhoneNumberValidation() {
        Patient patient = new Patient("John", "Doe", "1234567890", "123 Main St",
                null, "email", "insurance");
        assertEquals("5555555555", patient.getPhoneNumber(), "Phone number should default when null");

        patient.setPhoneNumber("123456");
        assertEquals("5555555555", patient.getPhoneNumber(), "Phone number should default when not 10 digits");

        patient.setPhoneNumber("1234567890");
        assertEquals("1234567890", patient.getPhoneNumber(), "Valid phone number should be accepted");
    }

    @Test
    @DisplayName("Patient email validation works")
    void testEmailValidation() {
        Patient patient = new Patient("John", "Doe", "1234567890", "123 Main St",
                "5555555555", null, "insurance");
        assertEquals("no-email@example.com", patient.getEmailAddress(), "Email should default when null");

        String longEmail = new String(new char[60]).replace("\0", "a") + "@example.com";
        patient.setEmailAddress(longEmail);
        assertEquals(50, patient.getEmailAddress().length(), "Email should be truncated when too long");

        patient.setEmailAddress("john.doe@example.com");
        assertEquals("john.doe@example.com", patient.getEmailAddress(), "Valid email should be accepted");
    }

    @Test
    @DisplayName("Patient insurance info validation works")
    void testInsuranceValidation() {
        Patient patient = new Patient("John", "Doe", "1234567890", "123 Main St",
                "5555555555", "email", null);
        assertEquals("No Insurance", patient.getInsuranceInfo(), "Insurance should default when null");

        String longInsurance = new String(new char[60]).replace("\0", "a");
        patient.setInsuranceInfo(longInsurance);
        assertEquals(50, patient.getInsuranceInfo().length(), "Insurance info should be truncated when too long");

        patient.setInsuranceInfo("Blue Cross Blue Shield");
        assertEquals("Blue Cross Blue Shield", patient.getInsuranceInfo(), "Valid insurance info should be accepted");
    }
}