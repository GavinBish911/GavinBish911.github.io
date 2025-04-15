/*************************
 * Name: 	Gavin Bish
 * Course: 	CS-320
 * Date: 	Octber 5tj, 2024
 * Description: This is the Appointment class. It creates and stores appointment information.
 *************************/

package Main;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class Appointment {
	private static AtomicLong idGenerator = new AtomicLong();
	private final String appointmentID;
	private Date appointmentDate;
	private String appointmentDesc;

	// CONSTRUCTORS
	/*
	 * Constructor takes appointment ID, appointment date, and appointment
	 * description as parameters. All parameters are checked if null or empty. If
	 * either exists, the field is filled with the phrase "NULL" to protect data
	 * integrity as a placeholder. Appointment ID is truncated to a maximum of 10
	 * characters, appointment date to 20 characters, and description to 50
	 * characters.
	 *
	 */

	@SuppressWarnings("deprecation")
	public Appointment(Date appointmentDate, String appointmentDesc) {

		// Appointment ID
		// Appointment ID is generated when the constructor is called. It is set as a
		// final variable and has
		// no other getter or setter so there should be no way to change it.
		// The idGenerator is static to prevent duplicates across all tasks.
		this.appointmentID = String.valueOf(idGenerator.getAndIncrement());

		if (appointmentDate == null) {
			this.appointmentDate = new Date(2022, Calendar.JANUARY, 1);
		} else if (appointmentDate.before(new Date())) {
			throw new IllegalArgumentException("Cannot make appointment before current date.");
		} else {
			this.appointmentDate = appointmentDate;
		}

		if (appointmentDesc == null || appointmentDesc.isEmpty()) {
			this.appointmentDesc = "NULL";
		} else if (appointmentDesc.length() > 50) {
			this.appointmentDesc = appointmentDesc.substring(0, 50);
		} else {
			this.appointmentDesc = appointmentDesc;
		}
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public String getAppointmentDesc() {
		return appointmentDesc;
	}

	// GETTERS
	public String getAppointmentID() {
		return appointmentID;
	}

	// SETTERS
	/*
	 * The setters follow the same rules as the constructor.
	 */
	@SuppressWarnings("deprecation")
	public void setAppointmentDate(Date appointmentDate) {
		if (appointmentDate == null) {
			appointmentDate = new Date(2022, Calendar.JANUARY, 1);
		} else if (appointmentDate.before(new Date())) {
			throw new IllegalArgumentException("Cannot make appointment before current date.");
		} else {
			this.appointmentDate = appointmentDate;
		}
	}

	public void setAppointmentDesc(String appointmentDesc) {
		if (appointmentDesc == null || appointmentDesc.isEmpty()) {
			this.appointmentDesc = "NULL";
		} else if (appointmentDesc.length() > 50) {
			this.appointmentDesc = appointmentDesc.substring(0, 50);
		} else {
			this.appointmentDesc = appointmentDesc;
		}
	}
}