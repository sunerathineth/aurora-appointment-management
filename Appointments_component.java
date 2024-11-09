package MainComponents;

import Main.Main;
import SwingModerations.HintTextField;
import SwingModerations.ScrollBar;
import com.toedter.calendar.JDateChooser;
import components.Appointment;
import components.DataRepository;
import static components.DataRepository.createPreliminaryAppointment;
import components.Doctor;
import components.Patient;
import components.Treatment;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableModel;

public class Appointments_component extends javax.swing.JPanel {
    
    // Global accessible variable section
    
    private static boolean isPreliminaryCreated = false;
    private static DefaultTableModel tableModel;
    int appointmentDisposal_ID;
    int appointmentUpdate_ID;
    
    // Accessing data from the central data repository
    
    private List<Treatment> treatments = DataRepository.treatments;
    private List<Doctor> doctors = DataRepository.doctors;
    private List<Appointment> appointments = DataRepository.appointments;
    private List<Patient> patients = DataRepository.patients;
    
    private Treatment selectedTreatment_txt;
    private Doctor selectedDoctor_txt;
    private String dayOfWeek_txt;
    private int year;
    private int month;
    private int day;
    private String appointmentTimeSlot;
    private String getTable_timeSlot;
    private String getTable_date;
    
    private static final int maxNameLength = 30;
    
    public Appointments_component() {
        initComponents();
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        dateChooser.setDate(new Date());
        dateFilterChooser.setDate(new Date());
        
        ScrollBar customScrollBar = new ScrollBar();
        customScrollBar.setPreferredSize(new Dimension(10, Integer.MAX_VALUE));
        
        // Different event listeners for different purposes like search, scroll, etc.

        customScrollBar.setUI(new BasicScrollBarUI() {
            // Upgraded the basic scrollbar
            
            private final Dimension d = new Dimension();

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return new JButton() {
                    @Override public Dimension getPreferredSize() {
                        return d;
                    }
                };
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new JButton() {
                    @Override public Dimension getPreferredSize() {
                        return d;
                    }
                };
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Scrollbar background should be changed into hash color later
                
                g2.setColor(new Color(255, 255, 255, 0));
                g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
                g2.dispose();
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color color;
                if (isDragging) {
                    color = new Color(101, 178, 252);
                } else if (isThumbRollover()) {
                    color = new Color(197, 233, 252, 255);
                } else {
                    color = new Color(20, 86, 204, 180);
                }

                g2.setPaint(color);
                g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);
                g2.setPaint(Color.WHITE);
                g2.drawRoundRect(r.x, r.y, r.width, r.height, 10, 10);
                g2.dispose();
            }

            @Override
            protected void setThumbBounds(int x, int y, int width, int height) {
                super.setThumbBounds(x, y, width, height);
                scrollbar.repaint();
            }
        });

        jScrollPane1.setVerticalScrollBar(customScrollBar);
        customScrollBar.setOpaque(false);
        
        dateFilterChooser.addPropertyChangeListener("date", evt -> {
            if (evt.getNewValue() != null) {
                filterAppointmentsByDate();
                filterCB.removeAllItems();
                filterCB.addItem("By Date");
                
                searchAppointmentsTF.setFocusable(false);
                
                javax.swing.Timer timer = new javax.swing.Timer(100, e -> searchAppointmentsTF.setFocusable(true));
                timer.setRepeats(false);
                timer.start();
            }
        });
    
        dateChooser.addPropertyChangeListener("date", evt -> {
            Date selectedDate = dateChooser.getDate();
            if (selectedDate != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(selectedDate);
                String dayOfWeek = new SimpleDateFormat("EEEE").format(calendar.getTime());
            }
        });
        
        dateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getNewValue() != null) {
                    load_TimeSlots_into_Combobox();
                }
            }
        });
        
        if (!isPreliminaryCreated) {
            isPreliminaryCreated = true;
        }
        
        // Loading initial components and data
        
        load_DoctorList_into_Combobox();
        load_TreatmentList_into_Combobox();
        
        initializeAppointmentsTable();
        
        appointment_table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = appointment_table.getSelectedRow();
                if (selectedRow >= 0) {
                    displaySelectedRowDetails(selectedRow);
                }
            }
        });
        
        dateChooser.addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getNewValue() != null) {
                    load_TimeSlots_into_Combobox();
                    timeSlotsCB.setSelectedIndex(0);
                }
            }
        });
        
        searchAppointmentsTF.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                searchAppointmentsBy_AppointmentID_or_PatientName();
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                searchAppointmentsBy_AppointmentID_or_PatientName();
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                searchAppointmentsBy_AppointmentID_or_PatientName();
            }
        });
        
        refreshTable();
    }
    
    // Appointment search by date filter, which is an upgraded version of the old console version
    
    public void filterAppointmentsByDate() {
        tableModel.setRowCount(0);

        Date selectedDate = dateFilterChooser.getDate();
        if (selectedDate == null) {
            refreshTable();
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String selectedDateString = dateFormat.format(selectedDate);

        boolean hasResults = false;

        for (Appointment appointment : DataRepository.appointments) {
            String appointmentDate = appointment.getDate();

            if (appointmentDate.equals(selectedDateString)) {
                int appointmentID = appointment.getAppointmentID();
                String doctorName = appointment.getDoctor().getName();
                String patientName = appointment.getPatient().getName();
                String patientNIC = appointment.getPatient().getNIC();
                String dayOfWeek = appointment.getDayOfWeek();
                String timeSlot = appointment.getTimeSlot();
                String treatmentName = appointment.getTreatment().getName();
                String registrationFeeStatus = appointment.getRegistrationStatus() ? "Paid" : "Unpaid";
                String status = appointment.isComplete() ? "Completed" : "Pending";

                tableModel.addRow(new Object[]{appointmentID, doctorName, patientName, patientNIC, dayOfWeek, appointmentDate, timeSlot, treatmentName, registrationFeeStatus, status});
                hasResults = true;
            }
        }

        if (!hasResults) {
            tableModel.addRow(new Object[] {"No results found", "", "", "", "", "", "", "", "", ""});
        }
    }
    
    // Search appointments by appointment ID or patient name
    
    public void searchAppointmentsBy_AppointmentID_or_PatientName() {
        String userSearchingText = searchAppointmentsTF.getText().trim();
        
        if (userSearchingText.isEmpty()) {
            filterCB.removeAllItems();
            filterCB.addItem("Automatic Filter");
            refreshTable();
            return;
        }
        
        if (userSearchingText.equals("Appointment ID or Patient Name")) {
            return;
        }
        
        List<Appointment> filteredAppointments = new ArrayList<>();
        boolean isNumericSearch = userSearchingText.chars().allMatch(Character::isDigit);
        
        // Automatic filter management
        
        if (isNumericSearch) {
            filterCB.removeAllItems();
            filterCB.addItem("By Appointment ID");
            int searchID = Integer.parseInt(userSearchingText);
            
            for (Appointment appointment : DataRepository.appointments) {
                if (String.valueOf(appointment.getAppointmentID()).startsWith(String.valueOf(searchID))) {
                    filteredAppointments.add(appointment);
                }
            }
        } else {
            filterCB.removeAllItems();
            filterCB.addItem("By Patient Name");
            for (Appointment appointment : DataRepository.appointments) {
                if (appointment.getPatientName().toLowerCase().contains(userSearchingText.toLowerCase())) {
                    filteredAppointments.add(appointment);
                }
            }
        }
        
        showResultsOfSearch_in_Table(filteredAppointments);
    }
    
    // Search results are done through this
    
    public void showResultsOfSearch_in_Table(List<Appointment> results) {
        tableModel.setRowCount(0);

        if (results.isEmpty()) {
            tableModel.addRow(new Object[] {"No results found", "", "", "", "", "", "", "", "", ""});
        } else {
            for (Appointment appointment : results) {
                String registrationFee = appointment.getRegistrationStatus() ? "Paid" : "Unpaid";
                System.out.println(registrationFee + " This");
                tableModel.addRow(new Object[] {
                    appointment.getAppointmentID(),
                    appointment.getDoctor().getName(),
                    appointment.getPatient().getName(),
                    appointment.getPatient().getNIC(),
                    appointment.getDayOfWeek(),
                    appointment.getDate(),
                    appointment.getTimeSlot(),
                    appointment.getTreatment().getName(),
                    registrationFee,
                    appointment.isComplete() ? "Completed" : "Pending"
                });
            }
        }
    }
    
    // Checking if a row is selected from the table
    
    public boolean isRowSelected() {
        if (appointment_table.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Please select a record from the table.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
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
    
    // A few selection getters
    
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
        appointmentTimeSlot = null;
    }
    
    // This method is used to get details of the selected table row and assign or set them
    
    private void displaySelectedRowDetails(int rowIndex) {
        int appointmentID = (int) appointment_table.getValueAt(rowIndex, 0);
        String doctorName = (String) appointment_table.getValueAt(rowIndex, 1);
        String patientName = (String) appointment_table.getValueAt(rowIndex, 2);
        String patientNIC = (String) appointment_table.getValueAt(rowIndex, 3);
        String dayOfWeek = (String) appointment_table.getValueAt(rowIndex, 4);
        String date = (String) appointment_table.getValueAt(rowIndex, 5);
        String timeSlot = (String) appointment_table.getValueAt(rowIndex, 6);
        String treatment = (String) appointment_table.getValueAt(rowIndex, 7);
        String registrationFee = (String) appointment_table.getValueAt(rowIndex, 8);
        String status = (String) appointment_table.getValueAt(rowIndex, 9);

        appointmentDisposal_ID = appointmentID;
        appointmentUpdate_ID = appointmentID;

        IDlbl.setText(Integer.toString(appointmentID));
        patientNameTB.setText(patientName);
        treatmentTypeCB.setSelectedItem(treatment);
        doctorTypeCB.setSelectedItem(doctorName);
        
        getTable_timeSlot = timeSlot;
        getTable_date = date;

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");
            Date parsedDate = dateFormat.parse(date);
            dateChooser.setDate(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        load_TimeSlots_into_Combobox();
    }
    
    // This is called in constructor to initialilze the appointment table

    private void initializeAppointmentsTable() {
        if (tableModel == null) {
            String[] columnHeaders = {"ID", "Doctor", "Patient", "NIC", "Day", "Date", "Time", "Treatment", "Reg Fee", "Status"};
            tableModel = new DefaultTableModel(columnHeaders, 0);
            appointment_table.setModel(tableModel);
            populateTableWithAppointments();
        } else {
            appointment_table.setModel(tableModel);
        }
    }
    
    private String formatTimeWithAmPm(String appointmentTime) {
        int hour = Integer.parseInt(appointmentTime.split(":")[0]);
        int minute = Integer.parseInt(appointmentTime.split(":")[1]);
        LocalTime startTime = LocalTime.of(hour, minute);

        LocalTime endTime = startTime.plusMinutes(15);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        
        return String.format("%s - %s", startTime.format(formatter), endTime.format(formatter));
    }
    
    // Appointments table is filled with retrieved details

    private void populateTableWithAppointments() {
        for (Appointment appointment : DataRepository.appointments) {
            int appointmentID = appointment.getAppointmentID();
            String doctorName = appointment.getDoctor().getName();
            String patientName = appointment.getPatient().getName();
            String patientNIC = appointment.getPatient().getNIC();
            String dayOfWeek = appointment.getDayOfWeek();
            String date = appointment.getDate();
            String timeSlot = appointment.getTimeSlot();
            String treatmentName = appointment.getTreatment().getName();
            String registrationFee = appointment.getRegistrationStatus() ? "Paid" : "Unpaid";
            String status = appointment.isComplete() ? "Completed" : "Pending";

            tableModel.addRow(new Object[]{appointmentID, doctorName, patientName, patientNIC, dayOfWeek, date, timeSlot, treatmentName, registrationFee, status});
        }
    }
    
    // Foundational blueprint or the idea of process
    
    public static void searchAppointment_Version1(List<Appointment> appointments, Scanner scanner) {
        System.out.println("Search by: ");
        System.out.println("1. Appointment ID");
        System.out.println("2. Patient Name");
        System.out.print("Choose an option (1 or 2): ");
        
        int choice = Integer.parseInt(scanner.nextLine());

        if (choice == 1) {
            System.out.print("Enter Appointment ID: ");
            int appointmentId = Integer.parseInt(scanner.nextLine());

            boolean found = false;
            for (Appointment appointment : appointments) {
                if (appointment.getAppointmentID() == appointmentId) {
                    appointment.displayAppointment();
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("No appointment found with ID: " + appointmentId);
            }

        } else if (choice == 2) {
            System.out.print("Enter Patient Name: ");
            String patientName = scanner.nextLine().toLowerCase();

            boolean found = false;
            for (Appointment appointment : appointments) {
                if (appointment.getPatientName().toLowerCase().contains(patientName)) {
                    appointment.displayAppointment();
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No appointment found for patient: " + patientName);
            }

        } else {
            System.out.println("Invalid option selected.");
        }   
    }
    
    // This method refreshes the appointments table
    
    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Appointment appointment : DataRepository.appointments) {
            int appointmentID = appointment.getAppointmentID();
            String doctorName = appointment.getDoctor().getName();
            String patientName = appointment.getPatient().getName();
            String patientNIC = appointment.getPatient().getNIC();
            String dayOfWeek = appointment.getDayOfWeek();
            String date = appointment.getDate();
            String timeSlot = appointment.getTimeSlot();
            String treatmentName = appointment.getTreatment().getName();
            String registrationFee = appointment.getRegistrationStatus() ? "Paid" : "Unpaid";
            String status = appointment.isComplete() ? "Completed" : "Pending";

            tableModel.addRow(new Object[]{appointmentID, doctorName, patientName, patientNIC, dayOfWeek, date, timeSlot, treatmentName, registrationFee, status});
        }
    }
    
    // Find the appointment by its selected id
    
    private Appointment findAppointmentByID(int appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID() == appointmentID) {
                return appointment;
            }
        }
        return null;
    }
    
    // Update appointment information
    
    public void updateAppointment() {
        Appointment appointmentToEdit = findAppointmentByID(appointmentUpdate_ID);

        if (appointmentToEdit != null) {
            String newPatientName = patientNameTB.getText();

            if (newPatientName.trim().length() > maxNameLength) {
                JOptionPane.showMessageDialog(this, "Patient name must be " + maxNameLength + " characters or less.", "Invalid Name", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Treatment newTreatment = treatmentSelection();
            Doctor newDoctor = doctorSelection();
            Date selectedDate = dateChooser.getDate();

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
            String newDayOfWeek = dayFormat.format(selectedDate);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String newDate = dateFormat.format(selectedDate);
            String newTimeSlot = timeSlotSelection();

            int response = JOptionPane.showConfirmDialog(Appointments_component.this, "Change Appointment Time: " + appointmentDisposal_ID + " ?", "Change timeslot?", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                appointmentToEdit.setDoctor(newDoctor, newDate, newTimeSlot);
                appointmentToEdit.editAppointment(newDayOfWeek, newTimeSlot, newTreatment);
            }
            
            appointmentToEdit.setTreatment(newTreatment);
            appointmentToEdit.setPatientName(appointmentToEdit.getPatient(), newPatientName);

            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Appointment not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Refreshes time slots showing only the available slots
    
    private void refreshTimeSlotComboBox(Doctor doctor, String date) {
        timeSlotsCB.removeAllItems();
        for (String slot : doctor.getAvailableSlots(date)) {
            timeSlotsCB.addItem(slot);
        }
        if (timeSlotsCB.getItemCount() > 0) {
            timeSlotsCB.setSelectedIndex(0);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox2 = new javax.swing.JComboBox<>();
        roundBorders1 = new SwingModerations.roundBorders();
        jScrollPane1 = new javax.swing.JScrollPane();
        appointment_table = new SwingModerations.Table();
        searchAppointmentsTF = new javax.swing.JTextField();
        filterCB = new javax.swing.JComboBox<>();
        removeAppointmentBtn = new javax.swing.JButton();
        roundBorders2 = new SwingModerations.roundBorders();
        jLabel8 = new javax.swing.JLabel();
        treatmentTypeCB = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        dateChooser = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        doctorTypeCB = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        patientNameTB = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        timeSlotsCB = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        IDlbl = new javax.swing.JLabel();
        updateAppointmentBtn = new javax.swing.JButton();
        completeAppointment = new javax.swing.JButton();
        dateFilterChooser = new com.toedter.calendar.JDateChooser();
        reset = new javax.swing.JButton();

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(953, 695));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(953, 695));

        roundBorders1.setBackground(new java.awt.Color(255, 255, 255));
        roundBorders1.setMaximumSize(new java.awt.Dimension(939, 695));
        roundBorders1.setPreferredSize(new java.awt.Dimension(939, 695));

        jScrollPane1.setBorder(null);

        appointment_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(appointment_table);

        searchAppointmentsTF.setForeground(new java.awt.Color(153, 153, 153));
        searchAppointmentsTF.setText("Appointment ID or Patient Name");
        searchAppointmentsTF.setToolTipText("");
        searchAppointmentsTF.setMargin(new java.awt.Insets(2, 5, 2, 5));
        searchAppointmentsTF.setMaximumSize(new java.awt.Dimension(117, 23));
        searchAppointmentsTF.setName(""); // NOI18N
        searchAppointmentsTF.setPreferredSize(new java.awt.Dimension(117, 23));
        searchAppointmentsTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchAppointmentsTFFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchAppointmentsTFFocusLost(evt);
            }
        });

        filterCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Automatic Filter" }));
        filterCB.setBorder(null);
        filterCB.setMaximumSize(new java.awt.Dimension(117, 23));
        filterCB.setPreferredSize(new java.awt.Dimension(117, 23));

        removeAppointmentBtn.setBackground(new java.awt.Color(255, 255, 255));
        removeAppointmentBtn.setText("Remove");
        removeAppointmentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAppointmentBtnActionPerformed(evt);
            }
        });

        roundBorders2.setBackground(new java.awt.Color(250, 250, 250));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Treatment type");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Date");

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

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Doctor");

        doctorTypeCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doctorTypeCBActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Name");

        patientNameTB.setForeground(new java.awt.Color(51, 51, 51));
        patientNameTB.setMargin(new java.awt.Insets(2, 5, 2, 5));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Appointment ID :");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Time slot");
        jLabel14.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        IDlbl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        IDlbl.setText("ID");

        javax.swing.GroupLayout roundBorders2Layout = new javax.swing.GroupLayout(roundBorders2);
        roundBorders2.setLayout(roundBorders2Layout);
        roundBorders2Layout.setHorizontalGroup(
            roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundBorders2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundBorders2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(IDlbl))
                    .addGroup(roundBorders2Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(patientNameTB)
                    .addComponent(timeSlotsCB, 0, 193, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(doctorTypeCB, 0, 188, Short.MAX_VALUE)
                    .addComponent(treatmentTypeCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19))
        );
        roundBorders2Layout.setVerticalGroup(
            roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundBorders2Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundBorders2Layout.createSequentialGroup()
                        .addGroup(roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(IDlbl))
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundBorders2Layout.createSequentialGroup()
                        .addGroup(roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(patientNameTB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundBorders2Layout.createSequentialGroup()
                        .addGroup(roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(treatmentTypeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)))
                .addGroup(roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(roundBorders2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(doctorTypeCB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel14)
                            .addComponent(timeSlotsCB, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel9))
                .addGap(23, 23, 23))
        );

        updateAppointmentBtn.setBackground(new java.awt.Color(255, 255, 255));
        updateAppointmentBtn.setText("Update");
        updateAppointmentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateAppointmentBtnActionPerformed(evt);
            }
        });

        completeAppointment.setBackground(new java.awt.Color(255, 255, 255));
        completeAppointment.setText("Complete");
        completeAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completeAppointmentActionPerformed(evt);
            }
        });

        dateFilterChooser.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                dateFilterChooserAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        dateFilterChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateFilterChooserPropertyChange(evt);
            }
        });

        reset.setBackground(new java.awt.Color(255, 255, 255));
        reset.setText("Reset");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundBorders1Layout = new javax.swing.GroupLayout(roundBorders1);
        roundBorders1.setLayout(roundBorders1Layout);
        roundBorders1Layout.setHorizontalGroup(
            roundBorders1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundBorders1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundBorders1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(roundBorders2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundBorders1Layout.createSequentialGroup()
                        .addComponent(searchAppointmentsTF, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(filterCB, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dateFilterChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reset)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(completeAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(updateAppointmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(removeAppointmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundBorders1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 933, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundBorders1Layout.setVerticalGroup(
            roundBorders1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundBorders1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundBorders1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateFilterChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundBorders1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchAppointmentsTF, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(filterCB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(removeAppointmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(updateAppointmentBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(completeAppointment, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(roundBorders2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundBorders1, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundBorders1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void removeAppointmentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAppointmentBtnActionPerformed
        if (!isRowSelected()) {
            return;
        }
        
        int reponse = JOptionPane.showConfirmDialog(Appointments_component.this, "Remove appointment: " + appointmentDisposal_ID + " ?", "Remove Appointment", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (reponse == JOptionPane.YES_OPTION) {
            DataRepository.deleteAppointment(appointmentDisposal_ID);
            refreshTable();
        }
    }//GEN-LAST:event_removeAppointmentBtnActionPerformed

    private void dateChooserAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_dateChooserAncestorAdded

    }//GEN-LAST:event_dateChooserAncestorAdded

    private void dateChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateChooserPropertyChange

    }//GEN-LAST:event_dateChooserPropertyChange

    private void doctorTypeCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doctorTypeCBActionPerformed
        load_TimeSlots_into_Combobox();
    }//GEN-LAST:event_doctorTypeCBActionPerformed

    private void updateAppointmentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateAppointmentBtnActionPerformed
        if (!isRowSelected()) {
            return;
        }
        
        DateChecker dateCheckingMethod = new DateChecker();
        boolean isValidDate = dateCheckingMethod.isSelectedDateValid(dateChooser);
        
        // Execute only if a valid date is selected
        
        if (isValidDate == true) {
            if (patientNameTB.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the patient's name.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
        
            int reponse = JOptionPane.showConfirmDialog(Appointments_component.this, "You are about to update appointment ID: " + appointmentDisposal_ID, "Update Appointment", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (reponse == JOptionPane.YES_OPTION) {

                updateAppointment();
                JOptionPane.showMessageDialog(this, "Appointment updated successfully.", "Appointment Update", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a valid date");
        }
    }//GEN-LAST:event_updateAppointmentBtnActionPerformed

    private void completeAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completeAppointmentActionPerformed
        if (!isRowSelected()) {
            return;
        }

        Appointment appointmentToComplete = findAppointmentByID(appointmentUpdate_ID);
        
        // Execute only if the appointment id is not null

        // Generate the invoice which is accessed from the data repository

        if (appointmentToComplete != null) {
            if (appointmentToComplete.isComplete()) {
                if (!appointmentToComplete.getRegistrationStatus()) {
                    appointmentToComplete.completeRegistrationFee();
                }
                
                appointmentToComplete.generateInvoice();
            } else {
                int response = JOptionPane.showConfirmDialog(Appointments_component.this, "Did the patient receive treatment?\nComplete " + appointmentDisposal_ID + " ?", "Complete Appointment",
                                                             JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    appointmentToComplete.completeAppointment();
                    appointmentToComplete.completeRegistrationFee();
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Appointment marked as completed successfully.", "Appointment Complete", JOptionPane.INFORMATION_MESSAGE);

                    appointmentToComplete.generateInvoice();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No appointment selected or appointment not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_completeAppointmentActionPerformed

    private void searchAppointmentsTFFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchAppointmentsTFFocusGained
        if (searchAppointmentsTF.getText().equals("Appointment ID or Patient Name")) {
            searchAppointmentsTF.setText("");
            searchAppointmentsTF.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_searchAppointmentsTFFocusGained

    private void searchAppointmentsTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchAppointmentsTFFocusLost
        if (searchAppointmentsTF.getText().equals("")) {
            searchAppointmentsTF.setText("Appointment ID or Patient Name");
            searchAppointmentsTF.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_searchAppointmentsTFFocusLost

    private void dateFilterChooserAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_dateFilterChooserAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_dateFilterChooserAncestorAdded

    private void dateFilterChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateFilterChooserPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_dateFilterChooserPropertyChange

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        dateFilterChooser.setDate(new Date());
        searchAppointmentsTF.setText("");
        
        refreshTable();
        
        filterCB.removeAllItems();
        filterCB.addItem("Automatic Filter");
        searchAppointmentsTF.setText("Appointment ID or Patient Name");
        
        reset.requestFocus();
    }//GEN-LAST:event_resetActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IDlbl;
    private SwingModerations.Table appointment_table;
    private javax.swing.JButton completeAppointment;
    private com.toedter.calendar.JDateChooser dateChooser;
    private com.toedter.calendar.JDateChooser dateFilterChooser;
    private javax.swing.JComboBox<String> doctorTypeCB;
    private javax.swing.JComboBox<String> filterCB;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField patientNameTB;
    private javax.swing.JButton removeAppointmentBtn;
    private javax.swing.JButton reset;
    private SwingModerations.roundBorders roundBorders1;
    private SwingModerations.roundBorders roundBorders2;
    private javax.swing.JTextField searchAppointmentsTF;
    private javax.swing.JComboBox<String> timeSlotsCB;
    private javax.swing.JComboBox<String> treatmentTypeCB;
    private javax.swing.JButton updateAppointmentBtn;
    // End of variables declaration//GEN-END:variables
}
