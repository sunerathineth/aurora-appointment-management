package SwingModerations;

import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CustomCalendar extends JPanel {

    public CustomCalendar(int month, int year) {
        setLayout(new GridLayout(5, 7)); // Layout for 5 weeks with 7 days
        initializeCalendar(month, year);
    }

    private void initializeCalendar(int month, int year) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstOfMonth = yearMonth.atDay(1);

        // List to store dates that fall on Mon, Wed, Fri, Sat
        List<LocalDate> selectedDates = new ArrayList<>();

        // Loop through the month and add dates for specified weekdays
        for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            LocalDate date = yearMonth.atDay(day);
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            if (dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.WEDNESDAY ||
                dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY) {
                selectedDates.add(date);
            }
        }

        // Create and add components for each date to the calendar layout
        for (int i = 0; i < 35; i++) {
            // Check if there's a date to add for this position, otherwise add blank space
            if (i < selectedDates.size()) {
                LocalDate date = selectedDates.get(i);
                JButton dateButton = new JButton(String.valueOf(date.getDayOfMonth()));
                dateButton.setToolTipText(date.getDayOfWeek().name());
                add(dateButton);
            } else {
                add(new JLabel("")); // Empty label for non-selected days
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Custom Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        // Customize for the desired month and year
        CustomCalendar calendar = new CustomCalendar(10, 2024);
        
        frame.add(calendar);
        frame.setVisible(true);
    }
}
