package components;

import javax.swing.JOptionPane;

public class Appointment {
    private static int idCounter = 1;
    private int appointmentID;
    private Doctor doctor;
    private Patient patient;
    private Treatment treatment;
    private String dayOfWeek;
    private String timeSlot;
    private double registrationFee = 500.0;
    private boolean isComplete;
    private String date;
    private boolean registrationAccepted;
    private double taxRate = 0.025;

    public Appointment(Doctor doctor, Patient patient, Treatment treatment, String dayOfWeek, String timeSlot, boolean isComplete, String date, boolean registrationAccepted) {
        this.appointmentID = idCounter++;
        this.doctor = doctor;
        this.patient = patient;
        this.treatment = treatment;
        this.dayOfWeek = dayOfWeek;
        this.timeSlot = timeSlot;
        this.isComplete = isComplete;
        this.date = date;
        this.registrationAccepted = registrationAccepted;
    }
    
    // Appointment getters

    public Doctor getDoctor() { return doctor; }
    public Patient getPatient() { return patient; }
    public String getDayOfWeek() { return dayOfWeek; }
    public String getTimeSlot() { return timeSlot; }
    public boolean isComplete() { return isComplete; }
    public Treatment getTreatment() { return treatment; }
    public String getTreatmentType() { return treatment.getName(); }
    public String getDate() { return date; }
    public int getAppointmentID() { return appointmentID; }
    public String getPatientName() { return patient.getName(); }
    public String getPatientNIC() { return patient.getNIC(); }
    public double getRegistrationFee() { return registrationFee; }
    public boolean getRegistrationStatus() { return registrationAccepted; }
    
    // Appointment setters
    
    public void setDate(String date) { this.date = date; }
    public void setPatientName(Patient patient, String newName) { patient.setName(newName); }
    public void setTreatment(Treatment treatment) { this.treatment = treatment; }
    public void setTreatmentType(Treatment treatment, String newTreatment) { treatment.setName(newTreatment); }

    // Complete appointment and registration fee
    
    public void completeAppointment() { this.isComplete = true; }
    public void completeRegistrationFee() { this.registrationAccepted = true; }
    
    public void editAppointment(String newDay, String newTime, Treatment newTreatment) {
        this.dayOfWeek = newDay;
        this.timeSlot = newTime;
        this.treatment = newTreatment;
        System.out.println("Appointment updated successfully");
    }
    
    public void editAppointment(int appointmentID, String newPatientName, Treatment newTreatment, String newDate, String newTimeSlot, Doctor newDoctor) {
        this.appointmentID = appointmentID;
        this.patient.setName(newPatientName);
        this.treatment = newTreatment;
        this.date = newDate;
        this.timeSlot = newTimeSlot;
        this.doctor = newDoctor;

        System.out.println("Appointment updated successfully:");
        System.out.println("Appointment ID: " + appointmentID);
        System.out.println("Patient Name: " + newPatientName);
        System.out.println("Treatment Type: " + newTreatment.getName());
        System.out.println("Date: " + newDate);
        System.out.println("Time Slot: " + newTimeSlot);
        System.out.println("Doctor: " + newDoctor.getName());
    }

    public void displayAppointment() {
        System.out.println("\nAppointment Details:");
        System.out.println("Appointment ID: " + appointmentID);
        System.out.println("Doctor: " + doctor.getName());
        System.out.println("Patient: " + patient.getName());
        System.out.println("Appointment Day: " + dayOfWeek);
        System.out.println("Appointment Time: " + timeSlot);
        System.out.println("Treatment: " + treatment.getName());
        System.out.println("Registration Fee: " + registrationFee);
        System.out.println("Appointment Completed: " + isComplete);
        System.out.println("Appointment Date: " + date);
    }
    
    // Calculate total fee
    
    public double calculateTotalFee() {
        double treatmentFee = treatment.getPrice();
        double taxAmount = treatmentFee * taxRate;
        return (Math.round((treatmentFee + taxAmount) * 100.0) / 100.0) + registrationFee;
    }
    
    // Calculate total fee and generate invoice
    
    public void generateInvoice() {
        double treatmentFee = treatment.getPrice();
        double taxAmount = treatmentFee * taxRate;
            
        double totalAmount = (Math.round((treatmentFee + taxAmount) * 100.0) / 100.0) + registrationFee;
        
        String invoiceMessage = String.format(
                "Invoice\n" +
                "Patient: %s\n" +
                "Doctor: %s\n" +
                "Treatment: %s\n" +
                "Registration Fee: %.2f LKR\n" +
                "Treatment Fee: %.2f LKR\n" +
                "Tax: %.2f LKR\n" +
                "Total Amount: %.2f LKR\n",
                
                patient.getName(),
                doctor.getName(),
                treatment.getName(),
                registrationFee,
                treatmentFee,
                taxAmount,
                totalAmount
        );
        
        JOptionPane.showMessageDialog(null, invoiceMessage, "Invoice", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public String toString() {
        return "Appointment{" +
                "ID: " + appointmentID +
                "Doctor: " + doctor.getName() +
                ", Patient: " + patient.getName() +
                ", Treatment: " + treatment.getName() +
                ", Day: " + dayOfWeek +
                ", Time: " + timeSlot +
                ", Completed: " + isComplete +
                ", Date: " + date +
                '}';
    }
    
    // Set a new time slot, date, and a new doctor
    
    public void setDoctor(Doctor newDoctor, String newDate, String newTimeSlot) {
        if (this.doctor != null) {
            this.doctor.cancelSlot(this.date, this.timeSlot);
        }

        this.doctor = newDoctor;
        this.date = newDate;
        this.timeSlot = newTimeSlot;

        newDoctor.bookSlot(newDate, newTimeSlot);
    }

    public void setTimeSlot(String newTimeSlot) {
        this.timeSlot = newTimeSlot;
    }
}
