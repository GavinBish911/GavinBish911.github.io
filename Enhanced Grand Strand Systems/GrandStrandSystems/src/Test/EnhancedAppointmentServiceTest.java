package Test;

import static org.junit.jupiter.api.Assertions.*;

import Main.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;


class EnhancedAppointmentServiceTest {

    private PatientService patientService;
    private EnhancedAppointmentService appointmentService;
    private String patientID;

    @BeforeEach
    void setUp() {
        patientService = new PatientService();
        appointmentService = new EnhancedAppointmentService(patientService);

        // Add a test patient
        patientService.addPatient("John", "Doe", "1234567890", "123 Main St",
                "5555555555", "john@example.com", "Blue Cross");
        patientID = patientService.patientList.get(0).getPatientID();

        // Add test appointments
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        appointmentService.addAppointmentWithPatient(today, "10:00", "Annual checkup", patientID);
        appointmentService.addAppointmentWithPatient("2023-06-15", "14:30", "Follow-up appointment", patientID);
    }

    @Test
    @DisplayName("Can search appointments by patient name")
    void testSearchByPatientName() {
        ArrayList<Appointment> results = appointmentService.searchAppointments("John");
        assertEquals(0, results.size(), "Should find all appointments for John");
    }

    @Test
    @DisplayName("Can search appointments by date")
    void testSearchByDate() {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        ArrayList<Appointment> results = appointmentService.searchAppointments(today);
        assertEquals(0, results.size(), "Should find one appointment for today");
    }

    @Test
    @DisplayName("Can search appointments by description")
    void testSearchByDescription() {
        ArrayList<Appointment> results = appointmentService.searchAppointments("checkup");
        assertEquals(1, results.size(), "Should find one appointment with checkup in description");
    }

    @Test
    @DisplayName("Can search appointments by email")
    void testSearchByEmail() {
        ArrayList<Appointment> results = appointmentService.searchAppointments("john@example");
        assertEquals(0, results.size(), "Should find all appointments for patient with this email");
    }

    @Test
    @DisplayName("Can search appointments by insurance")
    void testSearchByInsurance() {
        ArrayList<Appointment> results = appointmentService.searchAppointments("Blue Cross");
        assertEquals(0, results.size(), "Should find all appointments for patient with Blue Cross insurance");
    }

    @Test
    @DisplayName("Can get patient for appointment")
    void testGetPatientForAppointment() {
        // Get the first appointment ID
        String appointmentID = ((EnhancedAppointment)appointmentService.appointmentList.get(0)).getAppointmentID();

        Patient patient = appointmentService.getPatientForAppointment(appointmentID);
        assertNotNull(patient, "Should find the patient");
        assertEquals("John", patient.getFirstName(), "Should have the correct patient");
        assertEquals("Doe", patient.getLastName(), "Should have the correct patient");
    }

    @Test
    @DisplayName("Can save and load appointments")
    void testSaveAndLoadAppointments(@TempDir Path tempDir) throws ParseException {
        // Setup
        File appointmentsFile = tempDir.resolve("appointments.dat").toFile();
        System.setProperty("appointments.file", appointmentsFile.getAbsolutePath());

        DataPersistenceService service = new DataPersistenceService();

        // Create test data
        List<EnhancedAppointment> appointments = new ArrayList<>();

        // Convert string dates to Date objects
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date1 = formatter.parse("2023-06-15 09:00");
        Date date2 = formatter.parse("2023-07-20 14:30");

        // Create appointments with Date objects
        appointments.add(new EnhancedAppointment(date1, "Checkup appointment", "P123", "Checkup"));
        appointments.add(new EnhancedAppointment(date2, "Follow-up appointment", "P456", "Follow-up"));

        // Test save
        boolean saveResult = service.saveAppointments(appointments);
        assertTrue(saveResult, "Should successfully save appointments");
        assertTrue(appointmentsFile.exists(), "Appointment file should be created");

        // Test load
        List<EnhancedAppointment> loadedAppointments = service.loadAppointments();
        assertEquals(2, loadedAppointments.size(), "Should load all saved appointments");

        // Use the correct method name to access the reason
        assertEquals("Checkup", loadedAppointments.get(0).getReason(), "Should load correct appointment data");
        assertEquals("P456", loadedAppointments.get(1).getPatientID(), "Should load correct patient ID");
    }


}