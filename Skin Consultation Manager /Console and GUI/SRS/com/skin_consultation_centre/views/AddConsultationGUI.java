package com.skin_consultation_centre.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.skin_consultation_centre.Consultation;
import com.skin_consultation_centre.Doctor;
import com.skin_consultation_centre.Patient;
import com.skin_consultation_centre.WestminsterSkinConsultationManager;

public class AddConsultationGUI {

        JFrame frame;
        JPanel mainPanel;
        JPanel doctorAvailabilityPanel;
        JPanel patientDetailsPanel;
        JPanel consultationDetailsPanel;
        JPanel formsPanel;
        JPanel buttonsPanel;
        JPanel panel1;
        JPanel panel2;
        JPanel panel3;
        JPanel panel4;
        JPanel panel5;
        JPanel panel6;
        JPanel panel7;
        JPanel panel8;
        JPanel panel9;
        JPanel panel10;
        JLabel dateLabel;
        JLabel timeLabel;
        JLabel heading1;
        JLabel heading2;
        JLabel heading3;
        JLabel heading4;
        JLabel patientNameLabel;
        JLabel patientSurnameLabel;
        JLabel patientMobileNumberLabel;
        JLabel patientDOBLabel;
        JLabel patientIdLabel;
        JLabel consultationHoursLabel;
        JLabel consultationCostLabel;
        JLabel consultationNotesLabel;
        JTextField patientNameField;
        JTextField patientSurnameField;
        JTextField patientDOBField;
        JTextField patientMobileNumberField;
        JTextField patientIdField;
        JTextField consultationCostField;

        JLabel doctorAvailableLabel;
        JComboBox<String> appointmentYearComboBx;
        JComboBox<String> appointmentMonthComboBx;
        JComboBox<String> appointmentDayComboBx;
        JComboBox<String> dobYearComboBx;
        JComboBox<String> dobMonthComboBx;
        JComboBox<String> dobDayComboBx;
        JComboBox<String> timeComboBx;
        JComboBox<String> consultationHoursComboBx;
        JButton btnCheckAvailability;
        JButton btnSaveConsultation;
        JButton btnResetForm;

        JTextArea consultationNotesTextArea;

        // Static values for form dropdowns
        private String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
                "Oct", "Nov", "Dec" };
        private String[] appointmentTimeSlots = { "09:00", "10:00", "11:00", "12:00", "13:00", "14:00",
                "15:00", "16:00", "17:00", "18:00", "19:00", "20:00" };

        private String[] appointmentHours = { "1", "2", "3", "4", "5" };

        Doctor selectedDoctor;
        SkinConsultationManagerGUI parent;

        static WestminsterSkinConsultationManager westminsterSkinConsultationManager = WestminsterSkinConsultationManager
                .getManager();

        public AddConsultationGUI(SkinConsultationManagerGUI parent, Doctor selectedDoctor) {
                this.parent = parent;
                this.selectedDoctor = selectedDoctor;

        }

        // Methods to generate and update values for form dropdowns --------------------

        private void generateAppointmentYearsDropDown(int currentYear) {
                for (int i = currentYear; i <= currentYear + 3; i++) {
                        appointmentYearComboBx.addItem(String.valueOf(i));
                }
        }

        private void generateAppointmentDaysDropDown(LocalDate date) {
                for (int i = 1; i <= date.lengthOfMonth(); i++) {
                        appointmentDayComboBx.addItem(i < 10 ? "0" + String.valueOf(i) : String.valueOf(i));
                }
        }

        private void generateDobYearsDropDown(int currentYear) {
                for (int i = currentYear - 100; i <= currentYear; i++) {
                        dobYearComboBx.addItem(String.valueOf(i));
                }
        }

        private void generateDobDaysDropDown(LocalDate date) {
                for (int i = 1; i <= date.lengthOfMonth(); i++) {
                        dobDayComboBx.addItem(i < 10 ? "0" + String.valueOf(i) : String.valueOf(i));
                }
        }

        private void updateAppointmentDaysDropDown() {
                String selectedYear = String.valueOf(appointmentYearComboBx.getSelectedItem());
                String selectedMonth = String.valueOf(appointmentMonthComboBx.getSelectedItem());

                String selectedDateString = String.format("%s-%s-%s", selectedYear,
                        selectedMonth, "01");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
                LocalDate selectedDate = LocalDate.parse(selectedDateString, formatter);

                appointmentDayComboBx.removeAllItems();
                generateAppointmentDaysDropDown(selectedDate);
        }

        private void updateDobDaysDropDown() {
                String selectedYear = String.valueOf(dobYearComboBx.getSelectedItem());
                String selectedMonth = String.valueOf(dobMonthComboBx.getSelectedItem());

                String selectedDateString = String.format("%s-%s-%s", selectedYear,
                        selectedMonth, "01");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
                LocalDate selectedDate = LocalDate.parse(selectedDateString, formatter);

                dobDayComboBx.removeAllItems();
                generateDobDaysDropDown(selectedDate);
        }

        // ----------------------------------------------

        public JFrame start() {
                // GUI elements
                // Main frame --------------------
                frame = new JFrame("Add Consultation");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(600, 280);
                frame.setLocationRelativeTo(null);
                frame.setAlwaysOnTop(false);
                frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(),
                        BoxLayout.Y_AXIS));
                // --------------------------------

                // Main panel init and top heading --------------------
                mainPanel = new JPanel();
                mainPanel.setBackground(new java.awt.Color(23, 37, 92));
                mainPanel.setPreferredSize(new Dimension(frame.getWidth(), 200));
                mainPanel.setBorder(new EmptyBorder(25, 20, 5, 20));

                heading1 = new JLabel(
                        String.format("Doctor: %s %s", selectedDoctor.getName(), selectedDoctor.getSurname()),
                        JLabel.CENTER);
                heading1.setPreferredSize(new Dimension(frame.getWidth(), 20));
                heading1.setFont(new Font("Sans-serif", Font.BOLD, 16));
                heading1.setForeground(Color.WHITE);
                mainPanel.add(heading1);
                // --------------------------------

                // Doctor availability panel and appointment dropdowns --------------------
                doctorAvailabilityPanel = new JPanel();
                doctorAvailabilityPanel.setBackground(new java.awt.Color(49, 63, 125));
                doctorAvailabilityPanel.setLayout(new BoxLayout(doctorAvailabilityPanel,
                        BoxLayout.Y_AXIS));
                doctorAvailabilityPanel.setPreferredSize(new Dimension(frame.getWidth(), 60));
                heading2 = new JLabel(
                        "Check doctor availability",
                        JLabel.CENTER);

                heading2.setPreferredSize(new Dimension(frame.getWidth(), 60));
                heading2.setFont(new Font("Sans-serif", Font.ITALIC, 14));
                heading2.setForeground(new Color(255, 255, 255));
                mainPanel.add(heading2);
                //doctorAvailabilityPanel.setBackground(new Color(12, 24, 24));

                panel1 = new JPanel();
                panel1.setBorder(new EmptyBorder(10, 0, 10, 0));
                panel1.setBackground(Color.white);

                dateLabel = new JLabel("Date: ");
                panel1.add(dateLabel);

                // Appointment year dropdown
                appointmentYearComboBx = new JComboBox();
                generateAppointmentYearsDropDown(LocalDate.now().getYear());
                appointmentYearComboBx.setSelectedIndex(0);
                appointmentYearComboBx.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                                updateAppointmentDaysDropDown();
                        }
                });
                panel1.add(appointmentYearComboBx);

                // Appointment month dropdown
                appointmentMonthComboBx = new JComboBox(months);
                appointmentMonthComboBx.setSelectedItem(String.valueOf(LocalDate.now().getMonthValue()));
                appointmentMonthComboBx.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                                updateAppointmentDaysDropDown();
                        }
                });
                panel1.add(appointmentMonthComboBx);

                // Appointment day dropdown
                appointmentDayComboBx = new JComboBox();
                generateAppointmentDaysDropDown(LocalDate.now());
                appointmentDayComboBx.setSelectedIndex(LocalDate.now().getDayOfMonth() - 1);
                panel1.add(appointmentDayComboBx);

                // Appointment time dropdown
                timeLabel = new JLabel("Time: ");
                timeLabel.setBorder(new EmptyBorder(0, 40, 0, 0));
                panel1.add(timeLabel);
                timeComboBx = new JComboBox(appointmentTimeSlots);
                panel1.add(timeComboBx);

                doctorAvailabilityPanel.add(panel1);
                mainPanel.add(doctorAvailabilityPanel);

                // Check doctor availability button
                btnCheckAvailability = new JButton("Check Availability");
                btnCheckAvailability.setForeground(new java.awt.Color(73, 6, 41));
                btnCheckAvailability.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                handleBtnCheckAvailabilityClick();
                        }
                });
                mainPanel.add(btnCheckAvailability);

                // Doctor available label (appears when doctor is available)
                panel2 = new JPanel();
                panel2.setBackground(new java.awt.Color(23, 37, 92));
                doctorAvailableLabel = new JLabel("Doctor is available", JLabel.LEFT);
                doctorAvailableLabel.setPreferredSize(new Dimension(frame.getWidth(), 20));
                doctorAvailableLabel.setFont(new Font("Sans-serif", Font.BOLD, 17));
                doctorAvailableLabel.setForeground(new Color(0, 255, 110));
                panel2.add(doctorAvailableLabel);
                panel2.setVisible(false);
                mainPanel.add(panel2);

                // mainPanel.add(doctorAvailabilityPanel);

                // Patient details panel --------------------------------
                patientDetailsPanel = new JPanel();
                patientDetailsPanel.setBackground(Color.WHITE);
                patientDetailsPanel.setLayout(new BoxLayout(patientDetailsPanel,
                        BoxLayout.Y_AXIS));
                patientDetailsPanel.setPreferredSize(new Dimension(400, 250));

                heading3 = new JLabel("Patient details",
                        JLabel.LEFT);
                heading3.setAlignmentX(JLabel.CENTER_ALIGNMENT);
                heading3.setPreferredSize(new Dimension(frame.getWidth(), 40));
                heading3.setFont(new Font("Sans-serif", Font.ITALIC, 17));
                heading3.setForeground(new Color(5, 44, 68));
                patientDetailsPanel.add(heading3);

                // Patient details fields --------------------
                panel3 = new JPanel();
                panel3.setBackground(Color.WHITE);
                patientNameLabel = new JLabel("Name: ");
                patientNameLabel.setPreferredSize(new Dimension(120, 24));
                patientNameField = new JTextField();

                patientNameField.setPreferredSize(new Dimension(250, 24));
                panel3.add(patientNameLabel);
                panel3.add(patientNameField);
                patientDetailsPanel.add(panel3);

                panel4 = new JPanel();
                panel4.setBackground(Color.WHITE);
                patientSurnameLabel = new JLabel("Surname: ");
                patientSurnameLabel.setPreferredSize(new Dimension(120, 24));
                patientSurnameField = new JTextField();

                patientSurnameField.setPreferredSize(new Dimension(250, 24));
                panel4.add(patientSurnameLabel);
                panel4.add(patientSurnameField);
                patientDetailsPanel.add(panel4);

                panel5 = new JPanel();
                panel5.setBackground(Color.WHITE);
                patientDOBLabel = new JLabel("Date of birth: ");

                patientDOBLabel.setPreferredSize(new Dimension(100, 24));
                panel5.add(patientDOBLabel);

                // DOB dropdowns
                dobYearComboBx = new JComboBox();
                generateDobYearsDropDown(LocalDate.now().getYear());
                dobYearComboBx.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                                updateDobDaysDropDown();
                        }
                });
                panel5.add(dobYearComboBx);

                dobMonthComboBx = new JComboBox(months);
                dobMonthComboBx.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                                updateDobDaysDropDown();
                        }
                });
                panel5.add(dobMonthComboBx);

                dobDayComboBx = new JComboBox();
                updateDobDaysDropDown();
                panel5.add(dobDayComboBx);

                patientDetailsPanel.add(panel5);

                panel6 = new JPanel();
                panel6.setBackground(Color.WHITE);
                patientMobileNumberLabel = new JLabel("Mobile number: ");
                patientMobileNumberLabel.setPreferredSize(new Dimension(120, 24));
                patientMobileNumberField = new JTextField();
                patientMobileNumberField.setPreferredSize(new Dimension(250, 24));
                panel6.add(patientMobileNumberLabel);
                panel6.add(patientMobileNumberField);
                patientDetailsPanel.add(panel6);

                panel7 = new JPanel();
                panel7.setBackground(Color.WHITE);
                patientIdLabel = new JLabel("Patient ID: ");
                patientIdLabel.setPreferredSize(new Dimension(120, 24));
                patientIdField = new JTextField();
                patientIdField.setPreferredSize(new Dimension(250, 24));
                panel7.add(patientIdLabel);
                panel7.add(patientIdField);
                patientDetailsPanel.add(panel7);

                // --------------------------------------------

                // Consultation details panel ----------------
                consultationDetailsPanel = new JPanel();
                consultationDetailsPanel.setBackground(Color.white);
                consultationDetailsPanel.setLayout(new BoxLayout(consultationDetailsPanel,
                        BoxLayout.Y_AXIS));
                consultationDetailsPanel.setPreferredSize(new Dimension(400, 250));

                heading4 = new JLabel("Consultation details",
                        JLabel.LEFT);
                heading4.setAlignmentX(JLabel.CENTER_ALIGNMENT);
                heading4.setPreferredSize(new Dimension(frame.getWidth(), 40));
                heading4.setFont(new Font("Sans-serif", Font.ITALIC, 17));
                heading4.setForeground(new Color(5, 44, 68));
                consultationDetailsPanel.add(heading4);

                // Consultation details fields --------------------

                panel8 = new JPanel();
                panel8.setBackground(Color.white);
                consultationHoursLabel = new JLabel("Duration (hrs): ");
                consultationHoursLabel.setPreferredSize(new Dimension(100, 24));
                consultationHoursComboBx = new JComboBox(appointmentHours);
                consultationHoursComboBx.setPreferredSize(new Dimension(80, 24));
                consultationHoursComboBx.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                                handleConsultationHoursChange(event);
                        }
                });
                panel8.add(consultationHoursLabel);
                panel8.add(consultationHoursComboBx);
                consultationDetailsPanel.add(panel8);

                panel9 = new JPanel();
                panel9.setBackground(Color.white);
                consultationCostLabel = new JLabel("Cost (£): ");
                consultationCostLabel.setPreferredSize(new Dimension(100, 24));
                consultationCostField = new JTextField("15");
                consultationCostField.setPreferredSize(new Dimension(80, 24));
                panel9.add(consultationCostLabel);
                panel9.add(consultationCostField);
                consultationDetailsPanel.add(panel9);

                panel10 = new JPanel();
                panel10.setBackground(Color.WHITE);
                panel10.setBorder(new EmptyBorder(5, 0, 0, 0));
                consultationNotesLabel = new JLabel("Notes: ");
                consultationNotesLabel.setPreferredSize(new Dimension(65, 56));
                consultationNotesTextArea = new JTextArea();
                consultationNotesTextArea.setBackground(Color.ORANGE);
                consultationNotesTextArea.setPreferredSize(new Dimension(280, 100));
                panel10.add(consultationNotesLabel);
                panel10.add(consultationNotesTextArea);
                consultationDetailsPanel.add(panel10);

                // --------------------------------------------

                // Add patient and consultation details panels to forms panel
                formsPanel = new JPanel();
                formsPanel.setBackground(Color.WHITE);
                formsPanel.setLayout(new BoxLayout(formsPanel, BoxLayout.X_AXIS));
                formsPanel.add(patientDetailsPanel);
                formsPanel.add(consultationDetailsPanel);

                // --------------------------------------------
                // Buttons panel -----------------------------
                buttonsPanel = new JPanel();
                buttonsPanel.setBackground(new java.awt.Color(49, 63, 125));
                buttonsPanel.setPreferredSize(new Dimension(800, 100));
                buttonsPanel.setBorder(new EmptyBorder(5, 0, 0, 0));

                btnSaveConsultation = new JButton("SAVE ");
                btnSaveConsultation.setPreferredSize(new Dimension(150, 24));
                btnSaveConsultation.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                handleBtnSaveConsultationClick();
                        }
                });

                btnResetForm = new JButton("RESET");
                btnResetForm.setPreferredSize(new Dimension(150, 24));
                btnResetForm.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                handleBtnResetFormClick();
                        }
                });

                buttonsPanel.add(btnSaveConsultation);
                buttonsPanel.add(btnResetForm);

                // --------------------------------------------

                frame.getContentPane().add(mainPanel);
                frame.setVisible(true);

                return frame;
        }

        private void handleConsultationHoursChange(ActionEvent event) {
                // Update consultation cost field
                JComboBox comboBx = (JComboBox) event.getSource();
                Object selected = comboBx.getSelectedItem();
                int value = Integer.parseInt(selected.toString());
                consultationCostField.setText(String.valueOf(15 + (value - 1) * 25));
        }

        private void handleBtnResetFormClick() {
                // Reset editable form fields
                patientNameField.setText("");
                patientSurnameField.setText("");
                patientMobileNumberField.setText("");
                patientIdField.setText("");
                consultationHoursComboBx.setSelectedIndex(0);
                consultationNotesTextArea.setText("");
        }

        private void handleBtnSaveConsultationClick() {
                String selectedYear = appointmentYearComboBx.getSelectedItem().toString();
                String selectedMonth = appointmentMonthComboBx.getSelectedItem().toString();
                String selectedDay = appointmentDayComboBx.getSelectedItem().toString();
                String selectedTime = timeComboBx.getSelectedItem().toString();

                String selectedDateTimeString = String.format("%s-%s-%s %s", selectedYear,
                        selectedMonth,
                        selectedDay, selectedTime);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm");
                LocalDateTime selectedDateTime = LocalDateTime.parse(selectedDateTimeString, formatter);

                String patientName = patientNameField.getText().trim();
                String patientSurname = patientSurnameField.getText().trim();
                String patientMobileNumber = patientMobileNumberField.getText().trim();
                String patientId = patientIdField.getText().trim();
                String consultationHours = consultationHoursComboBx.getSelectedItem().toString();
                String consultationCost = consultationCostField.getText().trim();
                String consultationNotes = consultationNotesTextArea.getText().trim();

                // Validate form fields
                if (patientName.isEmpty() || patientSurname.isEmpty() || patientMobileNumber.isEmpty()
                        || patientId.isEmpty() || consultationHours.isEmpty()
                        || consultationCost.isEmpty() || consultationNotes.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please fill in all fields");
                        return;
                }

                try {
                        int patientIdInt = Integer.parseInt(patientId);
                        int consultationHoursInt = Integer.parseInt(consultationHours);
                        double consultationCostDouble = Double.parseDouble(consultationCost);

                        String dobYear = String.valueOf(dobYearComboBx.getSelectedItem());
                        String dobMonth = String.valueOf(dobMonthComboBx.getSelectedItem());
                        String dobDay = String.valueOf(dobDayComboBx.getSelectedItem());

                        String patientDobString = String.format("%s-%s-%s", dobYear,
                                dobMonth, dobDay);

                        formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
                        LocalDate patientDob = LocalDate.parse(patientDobString, formatter);

                        // Create patient and consultation objects
                        Patient patient = new Patient(patientName, patientSurname, patientDob,
                                patientMobileNumber, patientIdInt);
                        Consultation consultation = new Consultation(selectedDoctor, patient,
                                selectedDateTime,
                                consultationHoursInt, consultationCostDouble,
                                consultationNotes);

                        // Add patient and consultation to the list
                        westminsterSkinConsultationManager.patientsList.add(patient);
                        westminsterSkinConsultationManager.consultationsList.add(consultation);
                        // westminsterSkinConsultationManager.saveDataToFile();

                        // Update consultation list in parent frame
                        parent.generateConsultationListData();
                        frame.dispose();
                        JOptionPane.showMessageDialog(frame, "Consultation saved successfully");

                } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame,
                                "Please enter a valid number for patient ID and consultation cost");
                } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(frame,
                                "Please enter a valid date for patient DOB");
                }
        }

        private void handleBtnCheckAvailabilityClick() {
                String selectedYear = appointmentYearComboBx.getSelectedItem().toString();
                String selectedMonth = appointmentMonthComboBx.getSelectedItem().toString();
                String selectedDay = appointmentDayComboBx.getSelectedItem().toString();
                String selectedTime = timeComboBx.getSelectedItem().toString();

                String selectedDateTimeString = String.format("%s-%s-%s %s", selectedYear,
                        selectedMonth,
                        selectedDay, selectedTime);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm");
                LocalDateTime selectedDateTime = LocalDateTime.parse(selectedDateTimeString, formatter);
                LocalDateTime now = LocalDateTime.now();

                if (selectedDateTime.compareTo(now) < 0) {
                        JOptionPane.showMessageDialog(null,
                                String.format("Selected date and time: %s is in the past",
                                        selectedDateTimeString),
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                }

                if (!isDoctorAvailable(selectedDoctor, selectedDateTime)) {
                        JOptionPane.showMessageDialog(null,
                                String.format("Doctor %s %s is not available at %s",
                                        selectedDoctor.getName(),
                                        selectedDoctor.getSurname(),
                                        selectedDateTimeString),
                                "Error", JOptionPane.ERROR_MESSAGE);
                        if (!assignFreeDoctorRandomly(selectedDateTime)) {
                                return;
                        }
                }

                // Disable appointment dropdowns and check availability button
                appointmentYearComboBx.setEnabled(false);
                appointmentMonthComboBx.setEnabled(false);
                appointmentDayComboBx.setEnabled(false);
                timeComboBx.setEnabled(false);
                btnCheckAvailability.setEnabled(false);

                // Make frame bigger
                frame.setSize(900, 580);
                frame.setLocationRelativeTo(null);

                // Show doctor available label
                panel2.setVisible(true);

                // Add patient details, consultation details and buttons to frame
                mainPanel.add(formsPanel);
                mainPanel.add(buttonsPanel);

                // Update frame
                SwingUtilities.updateComponentTreeUI(frame);
        }

        private boolean isDoctorAvailable(Doctor selectedDoctor, LocalDateTime selectedDateTime) {
                for (Consultation c : westminsterSkinConsultationManager.consultationsList) {
                        if (c.getDoctor().equals(selectedDoctor)
                                && c.getDateTime().equals(selectedDateTime)) {
                                return false;
                        }
                }
                return true;
        }

        private boolean assignFreeDoctorRandomly(LocalDateTime selectedDateTime) {
                Random random = new Random();
                // Store already generated random indexes to avoid duplicates
                Set<Integer> generatedRandomIndexes = new HashSet<Integer>();

                // To exit the loop when a free doctor is found
                boolean freeDoctorFound = false;

                // To exit the loop when all doctors have been checked
                int tries = 0;
                while (tries < westminsterSkinConsultationManager.doctorsList.size() && !freeDoctorFound) {
                        tries++;
                        int randomIndex;
                        // Generate random index and check if it has already been generated
                        do {
                                randomIndex = random.nextInt(westminsterSkinConsultationManager.doctorsList.size());
                        } while (generatedRandomIndexes.contains(randomIndex));

                        generatedRandomIndexes.add(randomIndex);
                        // Get doctor at random index
                        Doctor currentDoctor = westminsterSkinConsultationManager.doctorsList.get(randomIndex);

                        // Check if doctor is available at selected date and time
                        if (isDoctorAvailable(currentDoctor, selectedDateTime)) {
                                selectedDoctor = currentDoctor;
                                freeDoctorFound = true;
                        }
                }

                // Update doctor label
                heading1.setText(String.format("Doctor: %s %s", selectedDoctor.getName(), selectedDoctor.getSurname()));
                heading1.repaint();
                SwingUtilities.updateComponentTreeUI(frame);

                // Show message to user
                if (freeDoctorFound) {
                        JOptionPane.showMessageDialog(null,
                                String.format("Doctor %s %s has been selected randomly for this consultation",
                                        selectedDoctor.getName(),
                                        selectedDoctor.getSurname()),
                                "Info", JOptionPane.INFORMATION_MESSAGE);
                        return true;
                } else {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm");
                        JOptionPane.showMessageDialog(null,
                                String.format("No doctor is available at %s, please select another date and time",
                                        selectedDateTime.format(formatter)),
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return false;
                }
        }
}
