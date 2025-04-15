package Main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EnhancedAppointmentService extends AppointmentService {
    private final PatientService patientService;  // Reference to patient service to access patient data
    private final DataPersistenceService persistenceService;

    public EnhancedAppointmentService(PatientService patientService) {
        super(patientService); // Call the parent constructor with patientService
        this.patientService = patientService;
        this.persistenceService = new DataPersistenceService(); // Initialize the persistenceService
        loadAppointments(); // Load existing appointments
    }

    // Load appointments from persistence
    private void loadAppointments() {
        List<EnhancedAppointment> loadedAppointments = persistenceService.loadAppointments();
        // Clear existing appointments list and add all loaded appointments
        appointmentList.clear();
        appointmentList.addAll(loadedAppointments);
    }

    // Save appointments to persistence
    private void saveAppointments() {
        // Convert appointmentList to List<EnhancedAppointment>
        List<EnhancedAppointment> enhancedList = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            if (appointment instanceof EnhancedAppointment) {
                enhancedList.add((EnhancedAppointment) appointment);
            }
        }
        persistenceService.saveAppointments(enhancedList);
    }

    // Method to add appointment with patient ID
    public void addAppointmentWithPatient(String date, String time, String description, String patientID) {
        // Create a Date object from the date and time strings
        // This is a simplified implementation
        Date appointmentDate = new Date(); // In a real implementation, parse date and time

        // Create and add the appointment
        EnhancedAppointment appointment = new EnhancedAppointment(appointmentDate, description, patientID, "Consultation");
        appointmentList.add(appointment);

        // Save to persistence
        saveAppointments();
    }

    @Override
    public void addAppointment(Date appointmentDate, String appointmentDesc, String patientID, String reason) {
        EnhancedAppointment appointment = new EnhancedAppointment(appointmentDate, appointmentDesc, patientID, reason);
        appointmentList.add(appointment);
        saveAppointments();
    }

    // Override the delete method to ensure we save
    @Override
    public void deleteAppointment(String appointmentID) {
        super.deleteAppointment(appointmentID);
        saveAppointments();
    }

    /**
     * Search appointments based on a query string that can match various fields
     *
     * @param searchQuery The string to search for
     * @return List of matching appointments
     */
    @Override
    public ArrayList<Appointment> searchAppointments(String searchQuery) {
        ArrayList<Appointment> results = new ArrayList<>();

        // If empty search, return all appointments
        if (searchQuery == null || searchQuery.isEmpty()) {
            for (Appointment appointment : appointmentList) {
                if (appointment instanceof EnhancedAppointment) {
                    results.add((EnhancedAppointment) appointment);
                }
            }
            return results;
        }

        // Search for matching appointments
        for (Appointment appointment : appointmentList) {
            if (appointment instanceof EnhancedAppointment) {
                EnhancedAppointment enhancedAppointment = (EnhancedAppointment) appointment;

                // Check if any fields match the search query
                if (enhancedAppointment.getAppointmentID().contains(searchQuery) ||
                        enhancedAppointment.getAppointmentDesc().contains(searchQuery) ||
                        enhancedAppointment.getPatientID().contains(searchQuery) ||
                        enhancedAppointment.getReason().contains(searchQuery)) {

                    results.add(enhancedAppointment);
                }
            }
        }

        return results;
    }

    /**
     * Get the patient associated with an appointment
     * @param appointmentID The ID of the appointment
     * @return The patient associated with the appointment, or null if not found
     */
    public Patient getPatientForAppointment(String appointmentID) {
        Appointment appointment = getAppointment(appointmentID);
        if (appointment != null && patientService != null) {
            return patientService.getPatient(appointment.getPatientID());
        }
        return null;
    }
}