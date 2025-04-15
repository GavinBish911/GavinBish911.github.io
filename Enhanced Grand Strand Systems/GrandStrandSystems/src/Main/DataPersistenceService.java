package Main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataPersistenceService {

    // Save appointments to a file
    public boolean saveAppointments(List<EnhancedAppointment> appointments) {
        String filePath = System.getProperty("appointments.file", "appointments.dat");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(new ArrayList<>(appointments));
            return true;
        } catch (IOException e) {
            System.err.println("Error saving appointments: " + e.getMessage());
            return false;
        }
    }

    // Load appointments from a file
    @SuppressWarnings("unchecked")
    public List<EnhancedAppointment> loadAppointments() {
        String filePath = System.getProperty("appointments.file", "appointments.dat");
        File file = new File(filePath);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<EnhancedAppointment>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading appointments: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Save patients to a file
    public boolean savePatients(List<Patient> patients) {
        String filePath = System.getProperty("patients.file", "patients.dat");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(new ArrayList<>(patients));
            return true;
        } catch (IOException e) {
            System.err.println("Error saving patients: " + e.getMessage());
            return false;
        }
    }

    // Load patients from a file
    @SuppressWarnings("unchecked")
    public List<Patient> loadPatients() {
        String filePath = System.getProperty("patients.file", "patients.dat");
        File file = new File(filePath);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Patient>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading patients: " + e.getMessage());
            return new ArrayList<>();
        }
    }

}