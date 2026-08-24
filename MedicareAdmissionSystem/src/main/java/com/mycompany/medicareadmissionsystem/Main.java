/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.medicareadmissionsystem;
import java.util.Scanner;
/**
 *
 * @author Student
 */

public class Main {

    private static Scanner scanner =
            new Scanner(System.in);

    private static HospitalSystem hospital =
            new HospitalSystem();

    public static void main(String[] args) {

        int choice;

        do {

            displayMenu();

            choice = readInt(
                    "Enter your choice: ");

            try {

                switch (choice) {

                    case 1:
                        registerPatient();
                        break;

                    case 2:
                        searchPatient();
                        break;

                    case 3:
                        updatePatient();
                        break;

                    case 4:
                        deletePatient();
                        break;

                    case 5:
                        hospital.displayPatients(
                                hospital.sortPatientsBySurname());
                        break;

                    case 6:
                        allocateBed();
                        break;

                    case 7:
                        releaseBed();
                        break;

                    case 8:
                        hospital.displayWardLayout();
                        break;

                    case 9:
                        hospital.displayAvailableBeds();
                        break;

                    case 10:
                        hospital.displayOccupiedBeds();
                        break;

                    case 11:
                        displayReports();
                        break;

                    case 12:
                        System.out.println(
                                "Thank you for using MediCare Hospital System.");

                        break;

                    default:
                        System.out.println(
                                "Invalid choice. Please select 1-12.");
                }

            } catch (Exception e) {

                System.out.println(
                        "Error: " + e.getMessage());
            }

        } while (choice != 12);
    }

    private static void displayMenu() {

        System.out.println();
        System.out.println(
                "==========================================");

        System.out.println(
                "       MEDICARE HOSPITAL SYSTEM");

        System.out.println(
                "==========================================");

        System.out.println("1. Register patient");
        System.out.println("2. Search for patient");
        System.out.println("3. Update patient details");
        System.out.println("4. Delete patient");
        System.out.println("5. Display all patients");
        System.out.println("6. Allocate hospital bed");
        System.out.println("7. Release hospital bed");
        System.out.println("8. Display ward layout");
        System.out.println("9. Display available beds");
        System.out.println("10. Display occupied beds");
        System.out.println("11. Reports");
        System.out.println("12. Exit");

        System.out.println(
                "==========================================");
    }

    private static void registerPatient() {

        String id =
                readText("Patient ID: ");

        if (hospital.findPatient(id) != null) {

            System.out.println(
                    "A patient with this ID already exists.");

            return;
        }

        String firstName =
                readText("First name: ");

        String lastName =
                readText("Last name: ");

        int age =
                readInt("Age: ");

        String gender =
                readText("Gender: ");

        String condition =
                readText("Medical condition: ");

        PatientCategory category =
                readCategory();

        Patient patient;

        if (category == PatientCategory.INPATIENT) {

            int wardNumber =
                    readInt("Ward number: ");

            patient = new Inpatient(
                    id,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    wardNumber,
                    "Not allocated"
            );

        } else {

            patient = new Patient(
                    id,
                    firstName,
                    lastName,
                    age,
                    gender,
                    condition,
                    category
            );
        }

        if (hospital.registerPatient(patient)) {

            System.out.println(
                    "Patient registered successfully.");

        } else {

            System.out.println(
                    "Patient registration failed.");
        }
    }

    private static void searchPatient() {

        String id =
                readText("Enter Patient ID: ");

        Patient patient =
                hospital.findPatient(id);

        if (patient == null) {

            System.out.println(
                    "Patient not found.");

        } else {

            System.out.println(
                    "\n" + patient.displayDetails());
        }
    }

    private static void updatePatient() {

        String id =
                readText("Enter Patient ID: ");

        Patient patient =
                hospital.findPatient(id);

        if (patient == null) {

            System.out.println(
                    "Patient not found.");

            return;
        }

        String firstName =
                readText("New first name: ");

        String lastName =
                readText("New last name: ");

        int age =
                readInt("New age: ");

        String gender =
                readText("New gender: ");

        String condition =
                readText("New medical condition: ");

        PatientCategory category =
                readCategory();

        boolean updated =
                hospital.updatePatient(
                        id,
                        firstName,
                        lastName,
                        age,
                        gender,
                        condition,
                        category
                );

        if (updated) {

            System.out.println(
                    "Patient updated successfully.");

        } else {

            System.out.println(
                    "Patient could not be updated.");
        }
    }

    private static void deletePatient() {

        String id =
                readText("Enter Patient ID: ");

        if (hospital.deletePatient(id)) {

            System.out.println(
                    "Patient deleted successfully.");

        } else {

            System.out.println(
                    "Patient not found.");
        }
    }

    private static void allocateBed() {

        String patientId =
                readText("Enter inpatient Patient ID: ");

        Patient patient =
                hospital.findPatient(patientId);

        if (!(patient instanceof Inpatient)) {

            System.out.println(
                    "Only inpatients may be allocated a bed.");

            return;
        }

        hospital.displayAvailableBeds();

        String bedNumber =
                readText(
                        "Enter bed number: ");

        if (hospital.allocateBed(
                patientId,
                bedNumber)) {

            System.out.println(
                    "Bed allocated successfully.");

        } else {

            System.out.println(
                    "Bed allocation failed.");

            System.out.println(
                    "The bed may be occupied or the patient "
                    + "may already have a bed.");
        }
    }

    private static void releaseBed() {

        String bedNumber =
                readText("Enter bed number: ");

        if (hospital.releaseBed(bedNumber)) {

            System.out.println(
                    "Bed released successfully.");

        } else {

            System.out.println(
                    "Bed could not be released.");
        }
    }

    private static void displayReports() {

        System.out.println();
        System.out.println(
                "========== REPORTS ==========");

        System.out.println(
                "Total registered patients: "
                + hospital.getTotalPatients());

        System.out.println(
                "Total occupied beds: "
                + hospital.getOccupiedBedCount());

        hospital.displayAvailableBeds();

        hospital.displayOccupiedBeds();

        System.out.println(
                "\nPatients sorted by Patient ID:");

        hospital.displayPatients(
                hospital.sortPatientsById());

        System.out.println(
                "\nPatients sorted by surname:");

        hospital.displayPatients(
                hospital.sortPatientsBySurname());
    }

    private static PatientCategory readCategory() {

        while (true) {

            System.out.println();
            System.out.println("1. Inpatient");
            System.out.println("2. Outpatient");
            System.out.println("3. Emergency");

            int choice =
                    readInt(
                            "Select patient category: ");

            switch (choice) {

                case 1:
                    return PatientCategory.INPATIENT;

                case 2:
                    return PatientCategory.OUTPATIENT;

                case 3:
                    return PatientCategory.EMERGENCY;

                default:
                    System.out.println(
                            "Invalid category.");
            }
        }
    }

    private static String readText(
            String message) {

        System.out.print(message);

        return scanner.nextLine().trim();
    }

    private static int readInt(
            String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                        scanner.nextLine().trim());

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number.");
            }
        }
    }
}

