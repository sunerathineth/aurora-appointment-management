package components;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Doctor {
    private static int idCounter = 1;
    private String name;
    private int doctorID;
    private Map<String, Set<String>> appointmentShedules = new HashMap<>();

    public Doctor(String name) {
        this.doctorID = idCounter;
        this.name = name;
    }

    public String getName() { return name; }
    
    // Generate time slots in the format of "hour:minute - hour:minute"

    private List<String> generateTimeSlots(int startHour, int endHour) {
        List<String> slots = new ArrayList<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime currentTime = LocalTime.of(startHour, 0);
        LocalTime endTime = LocalTime.of(endHour, 0);

        while (currentTime.isBefore(endTime)) {
            LocalTime slotEndTime = currentTime.plusMinutes(15);
            slots.add(currentTime.format(timeFormatter) + " - " + slotEndTime.format(timeFormatter));
            currentTime = slotEndTime;
        }

        return slots;
    }
    
    // Get all the available slots for the selection
    
    public List<String> getAvailableSlots(String dayOfWeek) {
        List<String> allSlots = new ArrayList<>();

        switch (dayOfWeek.toLowerCase()) {
            case "monday":
                allSlots.addAll(generateTimeSlots(10, 13));
                break;
            case "wednesday":
                allSlots.addAll(generateTimeSlots(14, 17));
                break;
            case "friday":
                allSlots.addAll(generateTimeSlots(16, 20));
                break;
            case "saturday":
                allSlots.addAll(generateTimeSlots(9, 13));
                break;
            default:
                return allSlots;
        }

        String date = "";
        Set<String> bookedSlots = appointmentShedules.getOrDefault(date, new HashSet<>());
        allSlots.removeAll(bookedSlots);

        return allSlots;
    }
    
    // Book slots when making appointments or updating

    public boolean bookSlot(String date, String time) {
        appointmentShedules.putIfAbsent(date, new HashSet<>());
        Set<String> bookedTimeslots = appointmentShedules.get(date);

        if (bookedTimeslots.contains(time)) {
            System.out.println("Time slot " + time + " on " + date + " is already booked.");
            return false;
        } else {
            bookedTimeslots.add(time);
            System.out.println("Booked time slot " + time + " on " + date + " successfully.");
            return true;
        }
    }
    
    // Cancel the booked slots when deleting or updating appointments

    public void cancelSlot(String date, String time) {
        Set<String> bookedTimeslots = appointmentShedules.get(date);
        if (bookedTimeslots != null && bookedTimeslots.remove(time)) {
            System.out.println("Cancelled booking for time slot " + time + " on " + date + " successfully.");
        } else {
            System.out.println("No booking found for time slot " + time + " on " + date + ".");
        }
    }
    
    // Check if the slots are availalbe for the selection

    public boolean isSlotAvailable(String date, String time) {
        return !appointmentShedules.getOrDefault(date, new HashSet<>()).contains(time);
    }
}
