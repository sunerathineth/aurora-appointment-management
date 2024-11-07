
package components;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataRepository {
    
    // Central data repository structures
    
    public static List<Patient> patients = new ArrayList<>();
    public static List<Doctor> doctors = new ArrayList<>();
    public static List<Appointment> appointments = new ArrayList<>();
    public static List<Treatment> treatments = new ArrayList<>();
    
    // Reset all the data if needed

    public static void resetData() {
        patients.clear();
        doctors.clear();
        appointments.clear();
        treatments.clear();
    }

    public static void createPreliminaryAppointment(
        List<Patient> patients,
        List<Appointment> appointments,
        Doctor doctor,
        List<Treatment> treatments,
        int patientId,
        String patientName,
        String patientEmail,
        String patientPhone,
        String patientAge,
        String patientNIC,
        int treatmentIndex,
        String day,
        String time,
        boolean isFeeCompleted,
        String appointmentDate,
        boolean registrationAccepted
    ) {
        if (doctor.isSlotAvailable(appointmentDate, time)) {
            Patient patient = new Patient(patientId, patientName, patientEmail, patientPhone, patientAge, patientNIC);
            patients.add(patient);

            Treatment treatment = treatments.get(treatmentIndex);
            Appointment appointment = new Appointment(doctor, patient, treatment, day, time, isFeeCompleted, appointmentDate, registrationAccepted);

            doctor.bookSlot(appointmentDate, time);
            appointments.add(appointment);
            System.out.println("Appointment created successfully on " + appointmentDate + " at " + time);
        } else {
            System.out.println("The selected time slot on " + appointmentDate + " is not available.");
        }
    }
    
    public static void createPreliminaryData() {
        // Add the two doctors visiting the clinic
        
        Doctor doctorSunera = new Doctor("Dr. Sunera");
        Doctor doctorThineth = new Doctor("Dr. Thineth");
        
        DataRepository.doctors.add(doctorSunera);
        DataRepository.doctors.add(doctorThineth);
        
        // Add the requested treatment types

        DataRepository.treatments.add(new Treatment("Acne Treatment", 2750.00));
        DataRepository.treatments.add(new Treatment("Skin Whitening", 7650.00));
        DataRepository.treatments.add(new Treatment("Mole Removal", 3850.00));
        DataRepository.treatments.add(new Treatment("Laser Treatment", 12500.00));
        
        // Priliminary example appointments
        
        createPreliminaryAppointment(DataRepository.patients, DataRepository.appointments, doctorThineth, DataRepository.treatments, 12,
                "Isabella Taylor", "isabella.t@example.com", "0786789012", "37", "198701234567", 0, "Monday", "10:00 - 10:15", true, "2024-10-28", true);
        
        createPreliminaryAppointment(DataRepository.patients, DataRepository.appointments, doctorSunera, DataRepository.treatments, 1,
                "John Doe", "john@example.com", "0719427485", "25", "199925678901", 0, "Monday", "10:00 - 10:15", false, "2024-11-04", false);
        
        createPreliminaryAppointment(DataRepository.patients, DataRepository.appointments, doctorSunera, DataRepository.treatments, 2,
                "Alice Smith", "alice@example.com", "0771234567", "30", "199412345678", 1, "Wednesday", "14:00 - 14:15", false, "2024-11-06", true);
        
        createPreliminaryAppointment(DataRepository.patients, DataRepository.appointments, doctorSunera, DataRepository.treatments, 3,
                "Michael Brown", "michael.brown@example.com", "0712345678", "40", "198423456789", 2, "Friday", "16:00 - 16:15", false, "2024-11-08", false);
        
        createPreliminaryAppointment(DataRepository.patients, DataRepository.appointments, doctorThineth, DataRepository.treatments, 4,
                "Sarah Johnson", "sarah.j@example.com", "0787654321", "28", "199678901234", 0, "Saturday", "09:30 - 9:45", false, "2024-11-09", true);
        
        createPreliminaryAppointment(DataRepository.patients, DataRepository.appointments, doctorSunera, DataRepository.treatments, 5,
                "David Lee", "david.lee@example.com", "0759876543", "50", "197456789012", 1, "Monday", "11:00 - 11:15", false, "2024-11-11", false);
        
        createPreliminaryAppointment(DataRepository.patients, DataRepository.appointments, doctorThineth, DataRepository.treatments, 6,
                "Emma Davis", "emma.d@example.com", "0713456789", "32", "199234567890", 3, "Wednesday", "15:30 - 15:45", false, "2024-11-13", false);
        
        createPreliminaryAppointment(DataRepository.patients, DataRepository.appointments, doctorSunera, DataRepository.treatments, 7,
                "Chris Evans", "chris.evans@example.com", "0721234567", "45", "197934567890", 2, "Friday", "17:15 - 17:30", false, "2024-11-15", false);
        
        createPreliminaryAppointment(DataRepository.patients, DataRepository.appointments, doctorThineth, DataRepository.treatments, 8,
                "Sophia White", "sophia.w@example.com", "0732345678", "35", "198945678901", 0, "Saturday", "10:15 - 10:30", false, "2024-11-16", true);
        
        createPreliminaryAppointment(DataRepository.patients, DataRepository.appointments, doctorSunera, DataRepository.treatments, 9,
                "Oliver Green", "oliver.g@example.com", "0743456789", "29", "199567890123", 1, "Monday", "12:30 - 12:45", false, "2024-11-18", false);
        
        createPreliminaryAppointment(DataRepository.patients, DataRepository.appointments, doctorThineth, DataRepository.treatments, 10,
                "Amelia Wilson", "amelia.w@example.com", "0714567890", "27", "199723456789", 3, "Wednesday", "14:45 - 15:00", false, "2024-11-20", true);
        
        createPreliminaryAppointment(DataRepository.patients, DataRepository.appointments, doctorSunera, DataRepository.treatments, 11,
                "Liam Miller", "liam.miller@example.com", "0765678901", "42", "198245678901", 2, "Friday", "16:45 - 17:00", false, "2024-11-22", true);
        
        createPreliminaryAppointment(DataRepository.patients, DataRepository.appointments, doctorThineth, DataRepository.treatments, 13,
                "Isabella Taylor", "isabella.taylor@example.net", "0712345679", "34", "198701234567", 0, "Saturday", "11:00 - 11:15", false, "2024-11-23", true);
        
    }
    
    // Delete appointments by their id
    
    public static void deleteAppointment(int appointmentID_del) {
        if (appointments.isEmpty()) {
            System.out.println("No appointments to delete.");
            return;
        }

        Appointment appointmentToDelete = null;
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID() == appointmentID_del) {
                appointmentToDelete = appointment;
                break;
            }
        }

        if (appointmentToDelete != null) {
            Doctor doctor = appointmentToDelete.getDoctor();
            String date = appointmentToDelete.getDate();
            String timeSlot = appointmentToDelete.getTimeSlot();
            doctor.cancelSlot(date, timeSlot);
            appointments.remove(appointmentToDelete);
            System.out.println("Appointment deleted successfully.");
        } else {
            System.out.println("Appointment with ID " + appointmentID_del + " not found.");
        }
    }

}