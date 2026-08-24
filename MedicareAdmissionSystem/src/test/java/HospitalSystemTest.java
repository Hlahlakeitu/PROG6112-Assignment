/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package com.mycompany.medicareadmissionsystem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HospitalSystemTest {

    // Test 1: Register a patient
    @Test
    public void testRegisterPatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = new Patient(
                "P001",
                "John",
                "Mokoena",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        boolean result =
                hospital.registerPatient(patient);

        assertTrue(result);
        assertEquals(1, hospital.getTotalPatients());
    }


    // Test 2: Search for a patient
    @Test
    public void testSearchPatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = new Patient(
                "P001",
                "John",
                "Mokoena",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        Patient found =
                hospital.findPatient("P001");

        assertNotNull(found);
        assertEquals("John", found.getFirstName());
        assertEquals("Mokoena", found.getLastName());
    }


    // Test 3: Update patient details
    @Test
    public void testUpdatePatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = new Patient(
                "P001",
                "John",
                "Mokoena",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        boolean result =
                hospital.updatePatient(
                        "P001",
                        "James",
                        "Mokoena",
                        30,
                        "Male",
                        "Cold",
                        PatientCategory.OUTPATIENT
                );

        assertTrue(result);

        Patient updated =
                hospital.findPatient("P001");

        assertEquals("James",
                updated.getFirstName());

        assertEquals(30,
                updated.getAge());

        assertEquals("Cold",
                updated.getMedicalCondition());
    }


    // Test 4: Delete a patient
    @Test
    public void testDeletePatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = new Patient(
                "P001",
                "John",
                "Mokoena",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        boolean result =
                hospital.deletePatient("P001");

        assertTrue(result);

        assertNull(
                hospital.findPatient("P001")
        );
    }


    // Test 5: Allocate a bed
    @Test
    public void testAllocateBed() {

        HospitalSystem hospital = new HospitalSystem();

        Inpatient patient =
                new Inpatient(
                        "P001",
                        "John",
                        "Mokoena",
                        30,
                        "Male",
                        "Pneumonia",
                        1,
                        "Not allocated"
                );

        hospital.registerPatient(patient);

        boolean result =
                hospital.allocateBed(
                        "P001",
                        "B01"
                );

        assertTrue(result);

        assertEquals(
                1,
                hospital.getOccupiedBedCount()
        );

        assertEquals(
                "B01",
                patient.getBedNumber()
        );
    }


    // Test 6: Release a bed
    @Test
    public void testReleaseBed() {

        HospitalSystem hospital = new HospitalSystem();

        Inpatient patient =
                new Inpatient(
                        "P001",
                        "John",
                        "Mokoena",
                        30,
                        "Male",
                        "Pneumonia",
                        1,
                        "Not allocated"
                );

        hospital.registerPatient(patient);

        hospital.allocateBed(
                "P001",
                "B01"
        );

        boolean result =
                hospital.releaseBed("B01");

        assertTrue(result);

        assertEquals(
                0,
                hospital.getOccupiedBedCount()
        );

        assertEquals(
                "Not allocated",
                patient.getBedNumber()
        );
    }


    // Test 7: Prevent duplicate Patient IDs
    @Test
    public void testDuplicatePatientId() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient1 =
                new Patient(
                        "P001",
                        "John",
                        "Mokoena",
                        25,
                        "Male",
                        "Flu",
                        PatientCategory.OUTPATIENT
                );

        Patient patient2 =
                new Patient(
                        "P001",
                        "James",
                        "Smith",
                        30,
                        "Male",
                        "Cold",
                        PatientCategory.EMERGENCY
                );

        assertTrue(
                hospital.registerPatient(patient1)
        );

        assertFalse(
                hospital.registerPatient(patient2)
        );
    }


    // Test 8: Prevent allocating an occupied bed
    @Test
    public void testOccupiedBed() {

        HospitalSystem hospital =
                new HospitalSystem();

        Inpatient patient1 =
                new Inpatient(
                        "P001",
                        "John",
                        "Mokoena",
                        30,
                        "Male",
                        "Flu",
                        1,
                        "Not allocated"
                );

        Inpatient patient2 =
                new Inpatient(
                        "P002",
                        "James",
                        "Smith",
                        40,
                        "Male",
                        "Cold",
                        1,
                        "Not allocated"
                );

        hospital.registerPatient(patient1);
        hospital.registerPatient(patient2);

        assertTrue(
                hospital.allocateBed(
                        "P001",
                        "B01"
                )
        );

        assertFalse(
                hospital.allocateBed(
                        "P002",
                        "B01"
                )
        );
    }


    // Test 9: Prevent bed allocation when all beds are occupied
    @Test
    public void testAllBedsOccupied() {

        HospitalSystem hospital =
                new HospitalSystem();

        // Create 21 inpatients
        for (int i = 1; i <= 21; i++) {

            Inpatient patient =
                    new Inpatient(
                            "P" + i,
                            "Patient",
                            "Test" + i,
                            20,
                            "Male",
                            "Condition",
                            1,
                            "Not allocated"
                    );

            hospital.registerPatient(patient);
        }

        // Allocate all 20 beds
        for (int i = 1; i <= 20; i++) {

            assertTrue(
                    hospital.allocateBed(
                            "P" + i,
                            "B" + String.format("%02d", i)
                    )
            );
        }

        // The 21st patient should not get a bed
        assertFalse(
                hospital.allocateBed(
                        "P21",
                        "B01"
                )
        );

        assertEquals(
                20,
                hospital.getOccupiedBedCount()
        );
    }


    // Test 10: Sort patients by surname
    @Test
    public void testSortPatientsBySurname() {

        HospitalSystem hospital =
                new HospitalSystem();

        hospital.registerPatient(
                new Patient(
                        "P001",
                        "John",
                        "Zulu",
                        25,
                        "Male",
                        "Flu",
                        PatientCategory.OUTPATIENT
                )
        );

        hospital.registerPatient(
                new Patient(
                        "P002",
                        "James",
                        "Adams",
                        30,
                        "Male",
                        "Cold",
                        PatientCategory.OUTPATIENT
                )
        );

        hospital.registerPatient(
                new Patient(
                        "P003",
                        "Peter",
                        "Mokoena",
                        28,
                        "Male",
                        "Flu",
                        PatientCategory.OUTPATIENT
                )
        );

        Patient[] sorted =
                hospital.sortPatientsBySurname();

        assertEquals(
                "Adams",
                sorted[0].getLastName()
        );

        assertEquals(
                "Mokoena",
                sorted[1].getLastName()
        );

        assertEquals(
                "Zulu",
                sorted[2].getLastName()
        );
    }
}