package com.skin_consultation_centre.views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.skin_consultation_centre.Consultation;
import com.skin_consultation_centre.Doctor;
import com.skin_consultation_centre.WestminsterSkinConsultationManager;

public class SkinConsultationManagerGUI {

    JFrame frame;
    JTable tableDoctors;
    JTable tableRaces;
    JPanel panel1;
    JPanel panel2;
    JPanel panel3;
    JPanel panel4;
    JPanel panel5;
    JPanel consultationsListPanel;
    Doctor selectedDoctor;
    JButton btnSort;
    JButton btnAddConsultation;

    JLabel lbl1;
    JLabel heading1;
    JLabel heading2;
    JScrollPane sp1;
    JScrollPane sp2;

    // Doctor table headers
    private static final String[] doctorsTableColumns = { "Name", "Surname", "Date of birth", "Mobile number",
            "Medical license number", "Specialisation" };

    static WestminsterSkinConsultationManager westminsterSkinConsultationManager = WestminsterSkinConsultationManager
            .getManager();

    private void generateDoctorTableData(boolean isSorted) {

        // Sort doctors by surname
        if (isSorted) {
            westminsterSkinConsultationManager.doctorsList.sort((d1, d2) -> d1.getSurname().compareTo(d2.getSurname()));
        }

        DefaultTableModel dtm = (DefaultTableModel) tableDoctors.getModel();
        dtm.setRowCount(0);

        // Add doctors to table
        for (Doctor d : westminsterSkinConsultationManager.doctorsList) {

            String[] rowData = new String[] { d.getName(), d.getSurname(),
                    d.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy MM dd")),
                    String.valueOf(d.getMobileNumber()),
                    String.valueOf(d.getMedicalLicenseNumber()),
                    d.getSpecialisation()
            };

            dtm.addRow(rowData);
        }

        frame.revalidate();
        frame.repaint();
    }

    // Generate consultations list
    public void generateConsultationListData() {
        if (westminsterSkinConsultationManager.consultationsList.isEmpty()) {
            // Show consultations label
            panel5 = new JPanel();
            panel5.setPreferredSize(new Dimension(800, 40));
            panel5.setBorder(null);
            panel5.setBackground(new java.awt.Color(49, 63, 125));
            JLabel lbl = new JLabel("No consultations for now", JLabel.CENTER);
            lbl.setFont(new Font("Sans-serif", Font.BOLD, 20));
            lbl.setPreferredSize(new Dimension(600, 100));
            lbl.setForeground(new Color(213, 6, 6));
            panel5.add(lbl);
            consultationsListPanel.add(panel5);

        } else {
            consultationsListPanel.removeAll();
            // Add consultations to list
            for (Consultation c : westminsterSkinConsultationManager.consultationsList) {
                JPanel itemPanel = new JPanel();
                itemPanel.setPreferredSize(new Dimension(800, 10));
                itemPanel.setBackground(Color.WHITE);
                // itemPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

                JLabel lbl1 = new JLabel(c.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm")));
                lbl1.setFont(new Font("Sans-serif", Font.BOLD, 13));
                lbl1.setBorder(new EmptyBorder(0, 0, 0, 20));
                itemPanel.add(lbl1);

                JLabel lbl2 = new JLabel(String.format("Patient %d with Dr. %s %s", c.getPatient().getPatientId(),
                        c.getDoctor().getName(), c.getDoctor().getSurname()));
                lbl2.setPreferredSize(new Dimension(450, 10));
                lbl2.setFont(new Font("Sans-serif", Font.PLAIN, 13));
                lbl2.setBorder(new EmptyBorder(0, 15, 0, 40));
                itemPanel.add(lbl2);

                JButton btn1 = new JButton("View");
                btn1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openViewConsultationFrame(c);
                    }
                });

                itemPanel.add(btn1);
                consultationsListPanel.add(itemPanel);
            }
        }

        // Update frame
        SwingUtilities.updateComponentTreeUI(frame);
        frame.revalidate();
        frame.repaint();

    }

    public JFrame start() {
        // GUI elements

        // Main frame --------------------------------------------------------------
        frame = new JFrame("Skin Consultation Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(false);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        // ----------------------------------------------------------------------------

        // fix
        panel4 = new JPanel();
        // panel4.setPreferredSize(new Dimension(frame.getWidth(), 10));
        panel4.setBorder(new EmptyBorder(0, 20, 0, 20));

        // Panel 1 -------------------------------------------------------------------
        panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(frame.getWidth(), 40));
        panel1.setBorder(new EmptyBorder(20, 60, 0, 50));
        panel1.setBackground(new java.awt.Color(23, 37, 92));
        lbl1 = new JLabel("Westminster Skin Consultation Manager GUI", JLabel.LEFT);
        lbl1.setPreferredSize(new Dimension(frame.getWidth(), 80));
        lbl1.setFont(new Font("Sans-serif", Font.BOLD, 30));
        lbl1.setForeground(new Color(241, 241, 241));
        panel1.add(lbl1);
        frame.getContentPane().add(panel1);
        // ----------------------------------------------------------------------------

        // Panel 2 -------------------------------------------------------------------
        panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(frame.getWidth(), 60));
        panel2.setBorder(new EmptyBorder(0, 50, 0, 50));
        panel2.setBackground(new java.awt.Color(49, 63, 125));
        heading1 = new JLabel("Doctors list", JLabel.CENTER);
        heading1.setPreferredSize(new Dimension(frame.getWidth(), 50));
        heading1.setBorder(new EmptyBorder(0, 0, 20, 0));
        heading1.setFont(new Font("Sans-serif", Font.BOLD, 27));
        heading1.setForeground(new Color(234, 234, 234));
        panel2.add(heading1);

        btnSort = new JButton("Sort by last name (A-Z)");
        btnSort.setFont(new Font("Sans-serif", Font.BOLD, 13));
        btnSort.setPreferredSize(new Dimension(220, 20));
        btnSort.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBtnSortClick();
            }
        });
        panel2.add(btnSort);
        frame.getContentPane().add(panel2);
        // ----------------------------------------------------------------------------

        // Scrollpane 1 with doctor table ----------------------------------------------
        tableDoctors = new JTable(new DefaultTableModel(doctorsTableColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tableDoctors.setPreferredScrollableViewportSize(new Dimension(400, 100));
        tableDoctors.setShowHorizontalLines(true);
        tableDoctors.setRowHeight(28);
        tableDoctors.setDragEnabled(false);
        tableDoctors.setSelectionMode(0);
        tableDoctors.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                setSelectedDoctor(
                        Integer.parseInt(tableDoctors.getValueAt(tableDoctors.getSelectedRow(), 4).toString()));
                btnAddConsultation.setEnabled(true);

            }
        });
        generateDoctorTableData(false);
        sp1 = new JScrollPane(tableDoctors);
        sp1.setBorder(new EmptyBorder(5, 30, 5, 30));
        frame.getContentPane().add(sp1);
        // ----------------------------------------------------------------------------

        // Panel 3 -------------------------------------------------------------------
        panel3 = new JPanel();
        panel3.setPreferredSize(new Dimension(frame.getWidth(), 20));
        panel3.setBorder(new EmptyBorder(20, 50, 0, 50));
        panel3.setBackground(new java.awt.Color(49, 63, 125));

        // Add consultation button ---------------------------------------------------
        btnAddConsultation = new JButton("Book a consultation");
        btnAddConsultation.setFont(new Font("Sans-serif", Font.BOLD, 13));
        btnAddConsultation.setPreferredSize(new Dimension(150, 40));
        btnAddConsultation.setForeground(Color.black);
        btnAddConsultation.setEnabled(false);
        btnAddConsultation.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBtnAddConsultationClick();

            }
        });
        panel3.add(btnAddConsultation);
        frame.getContentPane().add(panel3);

        // Panel 4 -------------------------------------------------------------------
        panel4 = new JPanel();
        panel4.setPreferredSize(new Dimension(frame.getWidth(), 10));
        panel4.setBorder(new EmptyBorder(0, 60, 0, 50));
        panel4.setBackground(new java.awt.Color(49, 63, 125));
        heading2 = new JLabel("Consultations list", JLabel.LEFT);
        heading2.setPreferredSize(new Dimension(frame.getWidth(), 20));
        heading2.setFont(new Font("Sans-serif", Font.ITALIC, 24));
        heading2.setForeground(new Color(222, 222, 224));
        panel4.add(heading2);
        frame.getContentPane().add(panel4);

        // Scrollpane 2 with consultations list ---------------------------------------
        consultationsListPanel = new JPanel();
        consultationsListPanel.setLayout(new BoxLayout(consultationsListPanel, BoxLayout.Y_AXIS));
        consultationsListPanel.setBackground(Color.white);
        generateConsultationListData();
        sp2 = new JScrollPane(consultationsListPanel);
        sp2.setPreferredSize(new Dimension(800, 120));
        sp2.setBackground(Color.WHITE);
        sp2.setBorder(null);
        frame.getContentPane().add(sp2);
        // ----------------------------------------------------------------------------

        frame.setVisible(true);
        return frame;
    }

    private void handleBtnSortClick() {
        generateDoctorTableData(true);
    }

    private void handleBtnAddConsultationClick() {
        if (selectedDoctor != null) {
            openAddConsultationFrame();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a doctor first");
        }
    }

    private void setSelectedDoctor(int medicalLicenseNumber) {
        for (Doctor d : westminsterSkinConsultationManager.doctorsList) {
            if (d.getMedicalLicenseNumber() == medicalLicenseNumber) {
                selectedDoctor = d;
                break;
            }
        }
    }

    private JFrame openAddConsultationFrame() {
        AddConsultationGUI gui = new AddConsultationGUI(this, selectedDoctor);
        return gui.start();
    }

    private JFrame openViewConsultationFrame(Consultation consultation) {
        ViewConsultationGUI gui = new ViewConsultationGUI(consultation);
        return gui.start();
    }
}
