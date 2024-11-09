
package MainComponents;

import com.toedter.calendar.JDateChooser;
import components.Appointment;
import components.DataRepository;
import components.Doctor;
import components.Patient;
import components.Treatment;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class NewAppointments_component extends javax.swing.JPanel {
    public NewAppointments_component() {
        initComponents();
        dateChooser.setDate(new Date());
        
        load_DoctorList_into_Combobox();
        load_TreatmentList_into_Combobox();
        
        dateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getNewValue() != null) {
                    load_TimeSlots_into_Combobox();
                }
            }
        });
    }
    
    // Here we access data from data repository
    
    private List<Treatment> treatments = DataRepository.treatments;
    private List<Doctor> doctors = DataRepository.doctors;
    private List<Appointment> appointments = DataRepository.appointments;
    private List<Patient> patients = DataRepository.patients;
    
    // Here are local variables and constants
    
    Treatment selectedTreatment_txt;
    Doctor selectedDoctor_txt;
    String dayOfWeek_txt;
    int year;
    int month;
    int day;
    String fullDate;
    String appointmentTimeSlot;
    boolean registrationPaid = false;
    
    private static final int maxNameLength = 30;
    
    
    // Essential methods
    
    public void load_TreatmentList_into_Combobox() {
        treatmentTypeCB.removeAllItems();
        for (Treatment treatment : treatments) {
            treatmentTypeCB.addItem(treatment.getName());
        }
    }
    
    public void load_DoctorList_into_Combobox() {
        doctorTypeCB.removeAllItems();
        for (Doctor doctor : doctors) {
            doctorTypeCB.addItem(doctor.getName());
        }
    }
    
    public Doctor doctorSelection() {
        Doctor selectedDoctor  = DataRepository.doctors.get(doctorTypeCB.getSelectedIndex());
        return selectedDoctor;
    }
    
    public Treatment treatmentSelection() {
        Treatment selectedTreatment  = DataRepository.treatments.get(treatmentTypeCB.getSelectedIndex());
        return selectedTreatment;
    }
    
    public String timeSlotSelection() {
        String selectedTreatment  = (String)timeSlotsCB.getSelectedItem();
        return selectedTreatment;
    }
    
    public void load_TimeSlots_into_Combobox() {
        Doctor selectedDoctor = doctorSelection();
        Date selectedDate = dateChooser.getDate();

        if (selectedDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            dayOfWeek_txt = new DateFormatSymbols().getWeekdays()[dayOfWeek];

            List<String> availableSlots = selectedDoctor.getAvailableSlots(dayOfWeek_txt);
            List<String> bookedSlots = new ArrayList<>();

            for (Appointment appointment : appointments) {
                if (appointment.getDoctor().equals(selectedDoctor) && appointment.getDate().equals(new SimpleDateFormat("yyyy-MM-dd").format(selectedDate))) {
                    bookedSlots.add(appointment.getTimeSlot());
                    System.out.println("Booked Slot: " + appointment.getTimeSlot());
                }
            }

            availableSlots.removeAll(bookedSlots);
            timeSlotsCB.removeAllItems();
            for (String slot : availableSlots) {
                timeSlotsCB.addItem(slot);
            }

            if (bookedSlots.isEmpty()) {
                System.out.println("No booked slots for this doctor on this date.");
            }
        }
    }
    
    // This function checks if the entered date is one of the available weekdays
    
    public class DateChecker {
        public boolean isSelectedDateValid(JDateChooser dateChooser) {
            Date selectedDate = dateChooser.getDate();

            if (selectedDate == null) {
                return false;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            return (dayOfWeek == Calendar.MONDAY || 
                    dayOfWeek == Calendar.WEDNESDAY || 
                    dayOfWeek == Calendar.FRIDAY || 
                    dayOfWeek == Calendar.SATURDAY);
        }
    }
    
    public void clearFields() {
        patientNameTB.setText("");
        patientAgeTB.setText("");
        patientEmailTB.setText("");
        patientMNTB.setText("");

        treatmentTypeCB.setSelectedIndex(-1);
        doctorTypeCB.setSelectedIndex(-1);
        timeSlotsCB.removeAllItems();

        dateChooser.setDate(new Date());

        selectedTreatment_txt = null;
        selectedDoctor_txt = null;
        dayOfWeek_txt = null;
        year = 0;
        month = 0;
        day = 0;
        fullDate = "";
        appointmentTimeSlot = null;
    }
    
    // One of the important methods. This method creates new appointments
    
    public void setAppointment() {
        DateChecker dateCheckingMethod = new DateChecker();
        boolean isValidDate = dateCheckingMethod.isSelectedDateValid(dateChooser);
        selectedTreatment_txt = treatmentSelection();
        selectedDoctor_txt = doctorSelection();
        appointmentTimeSlot = timeSlotSelection();
        
        // Here we do a number of different data validations

        // Maximum name length is 30

        String patientName = patientNameTB.getText().trim();
        if (!isValidName(patientName)) {
            JOptionPane.showMessageDialog(this, "Patient name should contain only letters and spaces, and be " + maxNameLength + " characters or less.", "Invalid Name", JOptionPane.WARNING_MESSAGE);
            return;
        } if (patientName.trim().length() > maxNameLength) {
            JOptionPane.showMessageDialog(this, "Patient name must be " + maxNameLength + " characters or less.", "Invalid Name", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // The age gap accepted is from 9 to 130
        // Also here we have a try catch to prevent user from entering invalid data types
        
        String patientAge = patientAgeTB.getText().trim();
        int age;
        try {
            age = Integer.parseInt(patientAge);
            if (age < 9 || age > 130) {
                JOptionPane.showMessageDialog(this, "Please enter a realistic age between 9 and 130.", "Invalid Age", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException x) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numerical age.", "Invalid Age", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        
        String patientEmail = patientEmailTB.getText().trim();
        if (!isValidEmail(patientEmail)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.", "Invalid Email", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        
        String patientPhone = patientMNTB.getText().trim();
        if (!isValidPhoneNumber(patientPhone)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number with only numbers.", "Invalid Phone Number", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        
        String patientNIC = patientNICTB.getText().trim();
        
        String selectedDoctorName = (String) doctorTypeCB.getSelectedItem();
        int registrationAccepted = registrationCB.getSelectedIndex();

        // Accept registration fees. Check whether the registration is made or not
        
        registrationPaid = registrationAccepted != 0;

        Date selectedDate = dateChooser.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(selectedDate);

        // Prevent sslecting a past date to ensure validity
        
        if (!isDateValid(selectedDate)) {
            JOptionPane.showMessageDialog(this, "Please select a present or future date.", "Invalid Date", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (isValidDate == true) {
            if (patientNameTB.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the patient's name.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (patientAgeTB.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the patient's age.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (patientEmailTB.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the patient's email.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (patientMNTB.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the patient's phone number.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (patientNICTB.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the patient's National Identity Card Number.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (selectedDoctorName == null || selectedDoctorName.equals("Select a doctor")) {
                JOptionPane.showMessageDialog(this, "Please select a doctor.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (formattedDate == null) {
                JOptionPane.showMessageDialog(this, "Please select a date for the appointment.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (appointmentTimeSlot == null || appointmentTimeSlot.equals("Select a time slot")) {
                JOptionPane.showMessageDialog(this, "Please select a time slot.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Patient newPatient = new Patient(patients.size() + 1, patientName, patientEmail, patientPhone, patientAge, patientNIC);
            patients.add(newPatient);

            Appointment newAppointment = new Appointment(selectedDoctor_txt, newPatient, selectedTreatment_txt, dayOfWeek_txt, appointmentTimeSlot, false, formattedDate, registrationPaid);

            // This time slot validation is only to ensure a strong filter or user won't get a change to select an already selected date
            
            if (selectedDoctor_txt.bookSlot(formattedDate, appointmentTimeSlot)) {
                
                // Creating the appointment after a strong validation
                
                appointments.add(newAppointment);
                
                // Refresh tables after the manipulation
                
                Appointments_component ac = new Appointments_component();
                ac.refreshTable();
                Doctor_component dc = new Doctor_component();
                dc.refreshTable();
                
                JOptionPane.showMessageDialog(this, "Appointment Created");
                
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "The selected time slot is not available.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a valid date");
        }
    }
    
    // Supporting methods for the validation
    
    private boolean isDateValid(Date selectedDate) {
        Calendar today = Calendar.getInstance();

        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.setTime(selectedDate);
        selectedCalendar.set(Calendar.HOUR_OF_DAY, 0);
        selectedCalendar.set(Calendar.MINUTE, 0);
        selectedCalendar.set(Calendar.SECOND, 0);
        selectedCalendar.set(Calendar.MILLISECOND, 0);

        return !selectedCalendar.before(today);
    }
    
    // Using Regex or regular expression technique
    
    private boolean isValidEmail(String email) {
        String emailReg = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern ptn = Pattern.compile(emailReg);
        return ptn.matcher(email).matches();
    }
    
    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("\\d+");
    }
    
    private boolean isValidName(String name) {
        return name.matches("[A-Za-z ]+");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        patient_information_btn = new javax.swing.JButton();
        appointment_information_btn = new javax.swing.JButton();
        jTabbedPane = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        patientNameTB = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        patientAgeTB = new javax.swing.JTextField();
        patientEmailTB = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        patientMNTB = new javax.swing.JTextField();
        registrationCB = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        patientNICTB = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        treatmentTypeCB = new javax.swing.JComboBox<>();
        doctorTypeCB = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        timeSlotsCB = new javax.swing.JComboBox<>();
        dateChooser = new com.toedter.calendar.JDateChooser();
        setAppointmentBtn = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(939, 695));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(939, 695));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setOpaque(false);
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        patient_information_btn.setBackground(new java.awt.Color(255, 255, 255));
        patient_information_btn.setText("Patient Information");
        patient_information_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patient_information_btnActionPerformed(evt);
            }
        });

        appointment_information_btn.setBackground(new java.awt.Color(255, 255, 255));
        appointment_information_btn.setText("Appointment Information");
        appointment_information_btn.setPreferredSize(new java.awt.Dimension(125, 23));
        appointment_information_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appointment_information_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(332, 332, 332)
                .addComponent(patient_information_btn)
                .addGap(18, 18, 18)
                .addComponent(appointment_information_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(299, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(patient_information_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(appointment_information_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 640, 940, 60));

        jTabbedPane.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Patient Information");

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Name");

        patientNameTB.setForeground(new java.awt.Color(51, 51, 51));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Age");

        patientAgeTB.setForeground(new java.awt.Color(51, 51, 51));

        patientEmailTB.setForeground(new java.awt.Color(51, 51, 51));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Email");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Moibile number");

        patientMNTB.setForeground(new java.awt.Color(51, 51, 51));

        registrationCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Unpaid", "Paid" }));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Registration Fee");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("NIC");

        patientNICTB.setForeground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(registrationCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(patientNameTB, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(patientAgeTB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                        .addComponent(patientEmailTB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                        .addComponent(patientMNTB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(patientNICTB, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(patientNameTB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(patientAgeTB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(patientEmailTB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(patientMNTB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(patientNICTB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(registrationCB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 792, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        jTabbedPane.addTab("tab1", jPanel2);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Appointment Information");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Treatment type");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Doctor");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Date");

        doctorTypeCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doctorTypeCBActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Time slot");

        dateChooser.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                dateChooserAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        dateChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateChooserPropertyChange(evt);
            }
        });

        setAppointmentBtn.setBackground(new java.awt.Color(255, 255, 255));
        setAppointmentBtn.setText("Set Appointment");
        setAppointmentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setAppointmentBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(treatmentTypeCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(timeSlotsCB, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(doctorTypeCB, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setAppointmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(treatmentTypeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addGap(11, 11, 11)
                .addComponent(doctorTypeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addGap(11, 11, 11)
                .addComponent(timeSlotsCB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(setAppointmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(244, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 755, Short.MAX_VALUE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane.addTab("tab2", jPanel4);

        jPanel3.add(jTabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 940, 670));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void patient_information_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patient_information_btnActionPerformed
        jTabbedPane.setSelectedIndex(0);
    }//GEN-LAST:event_patient_information_btnActionPerformed

    private void appointment_information_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appointment_information_btnActionPerformed
        jTabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_appointment_information_btnActionPerformed

    private void setAppointmentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setAppointmentBtnActionPerformed
        setAppointment();
    }//GEN-LAST:event_setAppointmentBtnActionPerformed

    private void doctorTypeCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doctorTypeCBActionPerformed
        load_TimeSlots_into_Combobox();
    }//GEN-LAST:event_doctorTypeCBActionPerformed

    private void dateChooserAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_dateChooserAncestorAdded
        
    }//GEN-LAST:event_dateChooserAncestorAdded

    private void dateChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateChooserPropertyChange
        
    }//GEN-LAST:event_dateChooserPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton appointment_information_btn;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JComboBox<String> doctorTypeCB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTextField patientAgeTB;
    private javax.swing.JTextField patientEmailTB;
    private javax.swing.JTextField patientMNTB;
    private javax.swing.JTextField patientNICTB;
    private javax.swing.JTextField patientNameTB;
    private javax.swing.JButton patient_information_btn;
    private javax.swing.JComboBox<String> registrationCB;
    private javax.swing.JButton setAppointmentBtn;
    private javax.swing.JComboBox<String> timeSlotsCB;
    private javax.swing.JComboBox<String> treatmentTypeCB;
    // End of variables declaration//GEN-END:variables
}
