/*************************
 * Name: 	Gavin Bish
 * Course: 	CS-499
 * Date: 	Current Date
 * Description: This is the updated AppointmentService class. It now includes
 * enhanced search functionality and patient linking.
 *************************/

package Main;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class AppointmentService {
	// ArrayList to hold the list of appointments
	public ArrayList<Appointment> appointmentList = new ArrayList<>();

	// Reference to PatientService for patient lookups
	private PatientService patientService;

	// Constructor with PatientService
	public AppointmentService(PatientService patientService) {
		this.patientService = patientService;
	}

	// Default constructor for backward compatibility
	public AppointmentService() {
		this.patientService = null;
	}

	// Adds a new appointment with patient link
	public void addAppointment(Date appointmentDate, String appointmentDesc,
							   String patientID, String reason) {
		Appointment appointment = new Appointment(appointmentDate, appointmentDesc,
				patientID, reason);
		appointmentList.add(appointment);
	}

	// Delete appointment
	public void deleteAppointment(String appointmentID) {
		for (int counter = 0; counter < appointmentList.size(); counter++) {
			if (appointmentList.get(counter).getAppointmentID().equals(appointmentID)) {
				appointmentList.remove(counter);
				break;
			}
			if (counter == appointmentList.size() - 1) {
				System.out.println("Appointment ID: " + appointmentID + " not found.");
			}
		}
	}

	// Display the full list of appointments to the console for error checking
	public void displayAppointmentList() {
		for (Appointment element : appointmentList) {
			System.out.println("\t Appointment ID: " + element.getAppointmentID());
			System.out.println("\t Appointment Date: " + element.getAppointmentDate());
			System.out.println("\t Appointment Description: " + element.getAppointmentDesc());
			if (element.getPatientID() != null) {
				System.out.println("\t Patient ID: " + element.getPatientID());
				System.out.println("\t Reason: " + element.getReason());
			}
			System.out.println();
		}
	}

	// Using Appointment ID, return an appointment object
	public Appointment getAppointment(String appointmentID) {
		Appointment appointment = null;
		for (Appointment element : appointmentList) {
			if (element.getAppointmentID().equals(appointmentID)) {
				appointment = element;
				break;
			}
		}
		return appointment;
	}

	// Update the appointment date
	public void updateAppointmentDate(Date updatedDate, String appointmentID) {
		for (int counter = 0; counter < appointmentList.size(); counter++) {
			if (appointmentList.get(counter).getAppointmentID().equals(appointmentID)) {
				appointmentList.get(counter).setAppointmentDate(updatedDate);
				break;
			}
			if (counter == appointmentList.size() - 1) {
				System.out.println("Appointment ID: " + appointmentID + " not found.");
			}
		}
	}

	// Update the appointment description
	public void updateAppointmentDesc(String updatedString, String appointmentID) {
		for (int counter = 0; counter < appointmentList.size(); counter++) {
			if (appointmentList.get(counter).getAppointmentID().equals(appointmentID)) {
				appointmentList.get(counter).setAppointmentDesc(updatedString);
				break;
			}
			if (counter == appointmentList.size() - 1) {
				System.out.println("Appointment ID: " + appointmentID + " not found.");
			}
		}
	}

	// Update the appointment's patient ID
	public void updateAppointmentPatient(String patientID, String appointmentID) {
		for (Appointment appointment : appointmentList) {
			if (appointment.getAppointmentID().equals(appointmentID)) {
				appointment.setPatientID(patientID);
				break;
			}
		}
	}

	// Update the appointment reason
	public void updateAppointmentReason(String reason, String appointmentID) {
		for (Appointment appointment : appointmentList) {
			if (appointment.getAppointmentID().equals(appointmentID)) {
				appointment.setReason(reason);
				break;
			}
		}
	}

	// ENHANCED SEARCH FUNCTIONALITY

	// Search appointments by various criteria
	public ArrayList<Appointment> searchAppointments(String searchQuery) {
		ArrayList<Appointment> results = new ArrayList<>();

		if (searchQuery == null || searchQuery.trim().isEmpty()) {
			return results;
		}

		String query = searchQuery.trim().toLowerCase();

		// Check if it's a date format
		boolean isDate = isDateFormat(query);

		// Go through all appointments
		for (Appointment appointment : appointmentList) {
			// Check appointment date if the query looks like a date
			if (isDate) {
				SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				String appointmentDateStr = formatter.format(appointment.getAppointmentDate()).toLowerCase();
				if (appointmentDateStr.contains(query)) {
					results.add(appointment);
					continue;
				}
			}

			// Check if query matches time
			SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
			String appointmentTimeStr = timeFormatter.format(appointment.getAppointmentDate()).toLowerCase();
			if (appointmentTimeStr.contains(query)) {
				results.add(appointment);
				continue;
			}

			// Check appointment description
			if (appointment.getAppointmentDesc().toLowerCase().contains(query)) {
				results.add(appointment);
				continue;
			}

			// Check appointment reason
			if (appointment.getReason() != null &&
					appointment.getReason().toLowerCase().contains(query)) {
				results.add(appointment);
				continue;
			}

			// If we have a patient service and patient ID, check patient details
			if (patientService != null && appointment.getPatientID() != null) {
				Patient patient = patientService.getPatient(appointment.getPatientID());
				if (patient != null) {
					// Check patient first name
					if (patient.getFirstName().toLowerCase().contains(query)) {
						results.add(appointment);
						continue;
					}

					// Check patient last name
					if (patient.getLastName().toLowerCase().contains(query)) {
						results.add(appointment);
						continue;
					}

					// Check patient phone number
					if (patient.getPhoneNumber().contains(query)) {
						results.add(appointment);
						continue;
					}

					// Check patient email
					if (patient.getEmailAddress().toLowerCase().contains(query)) {
						results.add(appointment);
						continue;
					}
				}
			}
		}

		return results;
	}

	// Helper method to check if a string might be a date format
	private boolean isDateFormat(String str) {
		// Check for common date formats
		String[] formats = {
				"MM/dd/yyyy", "M/d/yyyy", "MM/dd/yy", "yyyy-MM-dd", "MM-dd-yyyy"
		};

		for (String format : formats) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				dateFormat.setLenient(false);
				dateFormat.parse(str);
				return true;
			} catch (ParseException e) {
				// Not this format, try the next one
			}
		}
		return false;
	}

	// Get all appointments for a specific patient
	public ArrayList<Appointment> getPatientAppointments(String patientID) {
		ArrayList<Appointment> patientAppointments = new ArrayList<>();

		for (Appointment appointment : appointmentList) {
			if (appointment.getPatientID() != null && appointment.getPatientID().equals(patientID)) {
				patientAppointments.add(appointment);
			}
		}

		return patientAppointments;
	}
}