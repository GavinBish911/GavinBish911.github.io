package Main;

import java.io.Serializable;
import java.util.Date;

public class EnhancedAppointment extends Appointment implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient Appointment appointment; // Mark as transient since we don't need to serialize this
    private String patientID;

    public EnhancedAppointment(Date appointmentDate, String appointmentDesc, String patientID, String reason) {
        super(appointmentDate, appointmentDesc, patientID, reason);
        // Initialize the appointment field with a new instance
        this.appointment = new Appointment(appointmentDate, appointmentDesc, patientID, reason);
        // Store the patient ID
        this.patientID = patientID;
    }

    // Override readObject to reinitialize the appointment field after deserialization
    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        // Recreate appointment from our own state after deserialization
        this.appointment = new Appointment(getAppointmentDate(), getAppointmentDesc(), this.patientID, getReason());
    }

    // Delegate methods to the contained appointment or use superclass methods if appointment is null
    public String getAppointmentID() {
        return super.getAppointmentID();  // Use parent implementation
    }

    public Date getAppointmentDate() {
        return super.getAppointmentDate();  // Use parent implementation
    }

    public String getAppointmentDesc() {
        return super.getAppointmentDesc();  // Use parent implementation
    }

    public String getReason() {
        return super.getReason();  // Use parent implementation instead of delegating
    }

    public void setAppointmentDate(Date date) {
        super.setAppointmentDate(date);
        if (appointment != null) {
            appointment.setAppointmentDate(date);
        }
    }

    public void setAppointmentDesc(String desc) {
        super.setAppointmentDesc(desc);
        if (appointment != null) {
            appointment.setAppointmentDesc(desc);
        }
    }

    public void setReason(String reason) {
        super.setReason(reason);
        if (appointment != null) {
            appointment.setReason(reason);
        }
    }

    // Methods for patientID
    @Override
    public String getPatientID() {
        return patientID;
    }

    @Override
    public void setPatientID(String patientID) {
        if (patientID != null && !patientID.isEmpty()) {
            this.patientID = patientID;
            super.setPatientID(patientID);
            if (appointment != null) {
                appointment.setPatientID(patientID);
            }
        }
    }

    // Add these methods to match the interface used in the test
    public String getDate() {
        // Return date as string (would format properly in production code)
        return getAppointmentDate().toString();
    }
}