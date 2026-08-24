/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.medicareadmissionsystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Student
 */
public class HospitalSystem {
    
    private ArrayList<Patient> patients;

    // 4 x 5 = 20 beds
    private Bed[][] ward;

    public HospitalSystem() {

        patients = new ArrayList<>();
        ward = new Bed[4][5];

        int bedNumber = 1;

        for (int row = 0; row < ward.length; row++) {

            for (int column = 0;
                 column < ward[row].length;
                 column++) {

                ward[row][column] =
                        new Bed(String.format("B%02d", bedNumber));

                bedNumber++;
            }
        }
    }

    // Register patient
    public boolean registerPatient(Patient patient) {

        if (findPatient(patient.getPatientId()) != null) {
            return false;
        }

        patients.add(patient);
        return true;
    }

    // Search patient
    public Patient findPatient(String patientId) {

        for (Patient patient : patients) {

            if (patient.getPatientId()
                    .equalsIgnoreCase(patientId)) {

                return patient;
            }
        }

        return null;
    }

    // Update patient
    public boolean updatePatient(
            String patientId,
            String firstName,
            String lastName,
            int age,
            String gender,
            String condition,
            PatientCategory category) {

        Patient patient = findPatient(patientId);

        if (patient == null) {
            return false;
        }

        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setMedicalCondition(condition);

        /*
         * An Inpatient must remain an Inpatient because
         * the Inpatient class contains ward and bed details.
         */
        if (patient instanceof Inpatient
                && category != PatientCategory.INPATIENT) {

            return false;
        }

        patient.setCategory(category);

        return true;
    }

    // Delete patient
    public boolean deletePatient(String patientId) {

        Patient patient = findPatient(patientId);

        if (patient == null) {
            return false;
        }

        if (patient instanceof Inpatient) {

            Inpatient inpatient = (Inpatient) patient;

            if (!inpatient.getBedNumber()
                    .equalsIgnoreCase("Not allocated")) {

                releaseBed(inpatient.getBedNumber());
            }
        }

        return patients.remove(patient);
    }

    // Get all patients
    public ArrayList<Patient> getPatients() {
        return new ArrayList<>(patients);
    }

    // Find bed
    public Bed findBed(String bedNumber) {

        for (int row = 0; row < ward.length; row++) {

            for (int column = 0;
                 column < ward[row].length;
                 column++) {

                if (ward[row][column]
                        .getBedNumber()
                        .equalsIgnoreCase(bedNumber)) {

                    return ward[row][column];
                }
            }
        }

        return null;
    }

    // Allocate bed
    public boolean allocateBed(
            String patientId,
            String bedNumber) {

        Patient patient = findPatient(patientId);

        if (!(patient instanceof Inpatient)) {
            return false;
        }

        Inpatient inpatient = (Inpatient) patient;

        // Patient already has a bed
        if (!inpatient.getBedNumber()
                .equalsIgnoreCase("Not allocated")) {

            return false;
        }

        Bed bed = findBed(bedNumber);

        if (bed == null || bed.isOccupied()) {
            return false;
        }

        bed.allocate(inpatient);

        return true;
    }

    // Release bed
    public boolean releaseBed(String bedNumber) {

        Bed bed = findBed(bedNumber);

        if (bed == null || !bed.isOccupied()) {
            return false;
        }

        bed.release();

        return true;
    }

    // Get available beds
    public ArrayList<Bed> getAvailableBeds() {

        ArrayList<Bed> availableBeds = new ArrayList<>();

        for (int row = 0; row < ward.length; row++) {

            for (int column = 0;
                 column < ward[row].length;
                 column++) {

                if (!ward[row][column].isOccupied()) {

                    availableBeds.add(ward[row][column]);
                }
            }
        }

        return availableBeds;
    }

    // Get occupied beds
    public ArrayList<Bed> getOccupiedBeds() {

        ArrayList<Bed> occupiedBeds = new ArrayList<>();

        for (int row = 0; row < ward.length; row++) {

            for (int column = 0;
                 column < ward[row].length;
                 column++) {

                if (ward[row][column].isOccupied()) {

                    occupiedBeds.add(ward[row][column]);
                }
            }
        }

        return occupiedBeds;
    }

    // Count patients
    public int getTotalPatients() {
        return patients.size();
    }

    // Count occupied beds
    public int getOccupiedBedCount() {
        return getOccupiedBeds().size();
    }

    // Sort by surname
    public Patient[] sortPatientsBySurname() {

        Patient[] sortedPatients =
                patients.toArray(new Patient[0]);

        Arrays.sort(
                sortedPatients,
                Comparator.comparing(
                        Patient::getLastName,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

        return sortedPatients;
    }

    // Sort by Patient ID
    public Patient[] sortPatientsById() {

        Patient[] sortedPatients =
                patients.toArray(new Patient[0]);

        Arrays.sort(
                sortedPatients,
                Comparator.comparing(
                        Patient::getPatientId,
                        String.CASE_INSENSITIVE_ORDER
                )
        );

        return sortedPatients;
    }

    // Display ward
    public void displayWardLayout() {

        System.out.println("\n========== WARD LAYOUT ==========");

        for (int row = 0; row < ward.length; row++) {

            for (int column = 0;
                 column < ward[row].length;
                 column++) {

                Bed bed = ward[row][column];

                if (bed.isOccupied()) {

                    System.out.print(
                            bed.getBedNumber()
                            + "[OCCUPIED]   ");

                } else {

                    System.out.print(
                            bed.getBedNumber()
                            + "[AVAILABLE]  ");
                }
            }

            System.out.println();
        }
    }

    // Display patients
    public void displayPatients(Patient[] patientArray) {

        System.out.println("\n========== PATIENTS ==========");

        if (patientArray.length == 0) {

            System.out.println(
                    "No registered patients.");

            return;
        }

        for (Patient patient : patientArray) {

            System.out.println(
                    patient.displayDetails());

            System.out.println("-------------------------");
        }
    }

    // Display available beds
    public void displayAvailableBeds() {

        System.out.println(
                "\n========== AVAILABLE BEDS ==========");

        ArrayList<Bed> beds = getAvailableBeds();

        if (beds.isEmpty()) {

            System.out.println(
                    "No beds are currently available.");

            return;
        }

        for (Bed bed : beds) {

            System.out.print(
                    bed.getBedNumber() + " ");
        }

        System.out.println();
    }

    // Display occupied beds
    public void displayOccupiedBeds() {

        System.out.println(
                "\n========== OCCUPIED BEDS ==========");

        ArrayList<Bed> beds = getOccupiedBeds();

        if (beds.isEmpty()) {

            System.out.println(
                    "No beds are currently occupied.");

            return;
        }

        for (Bed bed : beds) {

            Inpatient patient = bed.getPatient();

            System.out.println(
                    bed.getBedNumber()
                    + " -> "
                    + patient.getPatientId()
                    + " "
                    + patient.getFirstName()
                    + " "
                    + patient.getLastName());
        }
    }
}

