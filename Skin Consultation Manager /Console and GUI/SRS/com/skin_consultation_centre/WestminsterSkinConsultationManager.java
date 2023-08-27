package com.skin_consultation_centre;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

import com.skin_consultation_centre.views.SkinConsultationManagerGUI;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {
    Scanner input = new Scanner(System.in);
    private static final int DOCTOR_COUNT = 10;
    public ArrayList<Doctor> doctorsList = new ArrayList<>();
    public ArrayList<Patient> patientsList = new ArrayList<>();
    public ArrayList<Consultation> consultationsList = new ArrayList<>();

    private static WestminsterSkinConsultationManager manager;

    public static WestminsterSkinConsultationManager getManager() {
        return manager;
    }
    public static void main(String[] args) {
        manager = new WestminsterSkinConsultationManager();
        manager.run();
    }
    public void run() {
        String option;
        System.out.println("\n**** ==== Welcome to the Westminster Skin Consultation Manager ===== ****\n");
        loadDataFromFile();
        do {
            System.out.println("\n***==== CONSOLE MENU ======= ***\n");
            System.out.println("\n----------------------------------------------------------------------------------\n");
            System.out.println("Enter 1 to Add A New Doctor ");
            System.out.println("Enter 2 to Delete A Doctor ");
            System.out.println("Enter 3 to  List the  Doctors ");
            System.out.println("Enter 4 to Save Information In File ");
            System.out.println("Enter 5 to Open GUI ");
            System.out.println("Enter o to Exit");
            System.out.println("\n-----------------------------------------------------------------------------------\n");

            System.out.print("Enter  Your Option : ");
            option = input.next();

            switch (option) {
                case "0":
                    break;

                case "1":
                    addDoctor();
                    break;

                case "2":
                    deleteDoctor();
                    break;

                case "3":
                    viewDoctors();
                    break;

                case "4":
                    saveDataToFile();
                    break;

                case "5":
                    startGUI();
                    break;

                default:
                    System.out.println("  ENTER A VALID INPUT.");
                    break;
            }

        } while (!"0".equals(option));
        System.out.println("*** === EXITING PROGRAM ==== ***");
    }

    public boolean isText(String name) {
        return name.matches("[a-zA-Z]+");
    }

    public boolean isNumber(String number){
        try{
            Integer.parseInt(number);
            return true;
        }
        catch(NumberFormatException ex){
            return false;
        }
    }
    @Override



    public void addDoctor() {


            int i=0;
            String name="";
            String surname="";
            String specialisation="";
            String medicalLicenseNumber="";
            LocalDate dob;


            System.out.println("\n*** ========== ADD A NEW DOCTOR ============***");

            while(true){
                System.out.print("Name: ");
                name = input.next();
                if(isText(name)){

                    break;

                }
                else{
                    System.out.println("Please Enter Valid Input");
                }
            }


            while(true){
                System.out.print(" Surname: ");
                surname = input.next();
                if(isText(surname)){

                    break;

                }
                else{
                    System.out.println("Please Enter Valid Input ");
                }
            }


            while(true){
                try{
                    System.out.print("Date Of Birth (YYYY-MM-DD): ");
                    dob = LocalDate.parse(input.next());

                    break;

                }catch (DateTimeParseException es){
                    System.out.println("Please Enter Valid Input");
                }
            }

            System.out.print("Mobile Phone Number: ");
            String mobileNumber = input.next();


            while(true){
                System.out.print("Medical License Number: ");
                medicalLicenseNumber = input.next();
                if(isNumber(medicalLicenseNumber)){

                    break;
                }
                else {
                    System.out.println("Please Enter Valid Input");
                }
           }



           while(true){
               System.out.print("Specialisation: ");
               specialisation = input.next();
               if(isText(specialisation)){

                   break;
               }
               else{
                   System.out.println("Please Enter Valid Input");
               }
           }


            Doctor newDoctor = new Doctor(name, surname, dob, mobileNumber,Integer.parseInt(medicalLicenseNumber), specialisation);
            if (doctorsList.size() <= DOCTOR_COUNT) {
            doctorsList.add(newDoctor);
            System.out.println("****======DOCTOR ADDED SUCCESSFULLY======****");
            }
            else {
            System.out.println("The Center Can Only  assign  10 Doctors.");
        }


    }

    @Override
    public void deleteDoctor() {
        boolean foundFlag = false;
        System.out.println("\n*** ========DELETE DOCTOR ========= ***");
        System.out.print("Medical License Number Of Doctor To Be Deleted: ");
        int medicalLicenseNumber = input.nextInt();
        for (Doctor d : doctorsList) {
            if (d.getMedicalLicenseNumber() == medicalLicenseNumber) {
                foundFlag = true;
                Doctor currentDoctor = d;
                doctorsList.remove(currentDoctor);
                System.out.println(
                        "Doctor With Medical License Number " + medicalLicenseNumber + " DELETED SUCCESSFULLY");
                System.out.println(currentDoctor);
                System.out.println("Number  of Doctors In Centre: " + doctorsList.size());
                break;
            }
        }

        if (!foundFlag) {
            System.out.println("Doctor With Medical License Number " + medicalLicenseNumber + " is not there");
        }
    }

    @Override
    public void viewDoctors() {
        System.out.println("\n*** =========LIST OF DOCTORS ========= ***");
        if (doctorsList.isEmpty()) {
            System.out.println("*****NO DOCTORS TO BE FOUND****");
        } else {
            System.out.println();
            doctorsList.sort((d1, d2) -> d1.getSurname().compareTo(d2.getSurname()));
            for (Doctor doctor : doctorsList) {
                System.out.println(doctor);
            }
        }
    }

    @Override
    public void saveDataToFile() {
        File doctorsFile = new File("doctors");

        try (FileOutputStream fileOutput = new FileOutputStream(doctorsFile)) {
            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);

            for (Doctor doctor : doctorsList) {
                objectOutput.writeObject(doctor);
            }

            objectOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println(" Saved To File: doctors");
        System.out.println();

    }

    @Override
    public void loadDataFromFile() {
        System.out.println("Trying To Load Data From File: doctors");
        File doctorData = new File("doctors");
        try {
            FileInputStream fileInput = new FileInputStream(doctorData);

            try (ObjectInputStream objectInput = new ObjectInputStream(fileInput)) {
                while (true) {
                    Doctor doctor = (Doctor) objectInput.readObject();
                    if (doctor != null) {
                        doctorsList.add(doctor);
                    } else {
                        break;
                    }
                }

            } catch (EOFException e) {
                System.out.println("Doctor Data Loaded From File");
            }

            catch (Exception e) {
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            System.out.println(" DaTa File NotFound");
        }
    }

    public static JFrame startGUI() {
        SkinConsultationManagerGUI gui = new SkinConsultationManagerGUI();
        return gui.start();
    }
    
}