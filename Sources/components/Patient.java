package components;

public class Patient {
    private static int idCounter = 1;
    private int patientID;
    private String name;
    private String email;
    private String phoneNumber;
    private String patientAge;
    private String patientNIC;

    public Patient(int patientID, String name, String email, String phoneNumber, String patientAge, String patientNIC) {
        this.patientID = patientID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.patientAge = patientAge;
        this.patientNIC = patientNIC;
    }
    
    // Important getters

    public int getPatientID() { return patientID; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAge() { return patientAge; }
    public String getNIC() { return patientNIC; }
    
    // Setters

    public void setPatientID(int patientID) { this.patientID = patientID; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setAge(String patientAge) { this.patientAge = patientAge; }

    public void displayRecord() {
        System.out.println("Patient ID: " + patientID);
        System.out.println("Patient Name: " + name);
        System.out.println("Patient Email: " + email);
        System.out.println("Patient Phone Number: " + phoneNumber);
        System.out.println("Patient Age: " + patientAge);
    }
}
