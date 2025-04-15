/*************************
 * Name: 	Gavin Bish
 * Course: 	CS-320
 * Date: 	October 5th, 2024
 * Description: This is the test class for TaskService.
 *************************/

package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import Main.Appointment;
import Main.AppointmentService;

class AppointmentServiceTest {

	/*
	 * The following tests exercise the AppointmentService class. In each test, a
	 * new service is created with default values for each field. Then the service
	 * is requested to make some change to the list of tasks and the result is
	 * tested to ensure the actual field matches what is expected.
	 *
	 * Because each appointment that gets created has a new automatically assigned
	 * appointmentID, and the tests are run based on that appointmentID, the order
	 * in which the tests are run depend on the value of each appointmentID.
	 * Therefore, the @Order annotation is used to keep the tests in a specific
	 * order.
	 *
	 * For evidence that the appointmentID is correct for each test, uncomment the
	 * service.displayAppointmentList(); prior to each assertion so that the records
	 * are displayed on the console for error checking.
	 */

	private Date Date(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		return cal.getTime();
	}


	@Test
	@DisplayName("Test to ensure that service can add an appointment.")
	@Order(4)
	void testAddAppointment() {
		AppointmentService service = new AppointmentService();
		service.addAppointment(Date(2022, Calendar.JANUARY, 1), "Description", "patientID", "reason");
		Appointment appointment = service.appointmentList.get(0);
		String actualId = appointment.getAppointmentID();
		service.deleteAppointment(actualId);
		service.updateAppointmentDate(Date(3022, Calendar.FEBRUARY, 2), actualId);

	}

	@Test
	@DisplayName("Test to ensure that service correctly deletes appointments.")
	@Order(3)
	void testDeleteAppointment() {
		AppointmentService service = new AppointmentService();
		service.addAppointment(Date(2022, Calendar.JANUARY, 1), "Description", "patientID", "reason");

		// Get the actual ID of the appointment we just added
		Appointment appointment = service.appointmentList.get(0);
		String actualId = appointment.getAppointmentID();

		// Delete using the actual ID
		service.deleteAppointment(actualId);

		// Ensure that the AppointmentList is now empty
		ArrayList<Appointment> appointmentListEmpty = new ArrayList<>();
		service.displayAppointmentList();
		assertEquals(service.appointmentList, appointmentListEmpty, "The appointment was not deleted.");
	}


	@Test
	@DisplayName("Test to Update appointment date")
	@Order(1)
	void testUpdateAppointmentDate() {
		AppointmentService service = new AppointmentService();
		service.addAppointment(Date(2022, Calendar.JANUARY, 1), "Description", "patientID", "reason");

		// Get the actual ID of the appointment we just added
		Appointment appointment = service.appointmentList.get(0);
		String actualId = appointment.getAppointmentID();

		// Print original date for debugging
		System.out.println("Original date: " + appointment.getAppointmentDate());

		// Create and store the updated date
		Date updatedDate = Date(3022, Calendar.FEBRUARY, 2);

		// Update using the actual ID
		service.updateAppointmentDate(updatedDate, actualId);

		// Get the updated appointment and print its date for debugging
		Appointment updatedAppointment = service.getAppointment(actualId);
		System.out.println("Updated date: " + updatedAppointment.getAppointmentDate());

		service.displayAppointmentList();

		// Compare timestamps rather than Date objects directly
		assertEquals(updatedDate.getTime(), updatedAppointment.getAppointmentDate().getTime(),
				"Appointment date was not updated.");
	}

	@Test
	@DisplayName("Test to Update appointment description.")
	@Order(2)
	void testUpdateAppointmentDesc() {
		AppointmentService service = new AppointmentService();
		service.addAppointment(Date(2022, Calendar.JANUARY, 1), "Description", "patientID", "reason");

		// Get the actual ID of the appointment we just added
		Appointment appointment = service.appointmentList.get(0);
		String actualId = appointment.getAppointmentID();

		// Update using the actual ID
		service.updateAppointmentDesc("Updated Description", actualId);
		service.displayAppointmentList();

		assertEquals("Updated Description", service.getAppointment(actualId).getAppointmentDesc(),
				"Appointment description was not updated.");
	}
}