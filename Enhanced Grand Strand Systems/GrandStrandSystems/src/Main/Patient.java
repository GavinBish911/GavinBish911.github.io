/*************************
 * Name: 	Gavin Bish
 * Course: 	CS-499
 * Date: 	Current Date
 * Description: This is the Patient class. It extends Contact and adds medical information.
 *************************/

package Main;

import java.io.Serializable;

public class Patient extends Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    private String phoneNumber;
    private String emailAddress;
    private String insuranceInfo;
    private final String patientID;

    // CONSTRUCTOR
    public Patient(String firstName, String lastName, String number, String address,
                   String phoneNumber, String emailAddress, String insuranceInfo) {
        super(firstName, lastName, number, address);
        this.patientID = getContactID(); // Use the contactID as patientID

        // Initialize the additional fields with validation
        setPhoneNumber(phoneNumber);
        setEmailAddress(emailAddress);
        setInsuranceInfo(insuranceInfo);
    }

    // Getter for patientID
    public String getPatientID() {
        return patientID;
    }

    // Getters and setters for the additional fields
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            this.phoneNumber = "5555555555";
        } else if (phoneNumber.length() != 10) {
            this.phoneNumber = "5555555555";
        } else {
            this.phoneNumber = phoneNumber;
        }
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.isEmpty()) {
            this.emailAddress = "no-email@example.com";
        } else if (emailAddress.length() > 50) {
            this.emailAddress = emailAddress.substring(0, 50);
        } else {
            this.emailAddress = emailAddress;
        }
    }

    public String getInsuranceInfo() {
        return insuranceInfo;
    }

    public void setInsuranceInfo(String insuranceInfo) {
        if (insuranceInfo == null || insuranceInfo.isEmpty()) {
            this.insuranceInfo = "No Insurance";
        } else if (insuranceInfo.length() > 50) {
            this.insuranceInfo = insuranceInfo.substring(0, 50);
        } else {
            this.insuranceInfo = insuranceInfo;
        }
    }
}