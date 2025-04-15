/*************************
 * Name: 	Gavin Bish
 * Course: 	CS-499
 * Date: 	Current Date
 * Description: This is the updated Appointment class. It now includes a link to a patient.
 *************************/

package Main;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import java.io.Serializable;


public class Appointment implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final AtomicLong idGenerator = new AtomicLong();
	private final String appointmentID;
	private Date appointmentDate;
	private String appointmentDesc;
	private String patientID; // New field to link to a patient
	private String reason; // New field for appointment reason

	// Constructor with patient link
	public Appointment(Date appointmentDate, String appointmentDesc, String patientID, String reason) {
		// Generate appointment ID
		this.appointmentID = String.valueOf(idGenerator.getAndIncrement());

		// Set appointment date with validation
		if (appointmentDate == null) {
			this.appointmentDate = new Date(); // Default to current date/time
		} else {
			this.appointmentDate = appointmentDate;
		}

		// Set appointment description with validation
		if (appointmentDesc == null || appointmentDesc.isEmpty()) {
			this.appointmentDesc = "General Appointment";
		} else if (appointmentDesc.length() > 50) {
			this.appointmentDesc = appointmentDesc.substring(0, 50);
		} else {
			this.appointmentDesc = appointmentDesc;
		}

		// Set patient ID
		this.patientID = patientID;

		// Set reason with validation
		if (reason == null || reason.isEmpty()) {
			this.reason = "General Checkup";
		} else if (reason.length() > 100) {
			this.reason = reason.substring(0, 100);
		} else {
			this.reason = reason;
		}
	}

	// Constructor for backward compatibility
	public Appointment(Date appointmentDate, String appointmentDesc) {
		this(appointmentDate, appointmentDesc, null, "General Checkup");
	}

	// Getters
	public String getAppointmentID() {
		return appointmentID;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public String getAppointmentDesc() {
		return appointmentDesc;
	}

	public String getPatientID() {
		return patientID;
	}

	public String getReason() {
		return reason;
	}

	// Setters
	public void setAppointmentDate(Date appointmentDate) {
		if (appointmentDate != null) {
			this.appointmentDate = appointmentDate;
		}
	}

	public void setAppointmentDesc(String appointmentDesc) {
		if (appointmentDesc == null || appointmentDesc.isEmpty()) {
			this.appointmentDesc = "General Appointment";
		} else if (appointmentDesc.length() > 50) {
			this.appointmentDesc = appointmentDesc.substring(0, 50);
		} else {
			this.appointmentDesc = appointmentDesc;
		}
	}

	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

	public void setReason(String reason) {
		if (reason == null || reason.isEmpty()) {
			this.reason = "General Checkup";
		} else if (reason.length() > 100) {
			this.reason = reason.substring(0, 100);
		} else {
			this.reason = reason;
		}
	}
}