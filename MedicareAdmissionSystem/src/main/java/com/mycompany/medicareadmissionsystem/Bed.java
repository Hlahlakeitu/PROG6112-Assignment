/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.medicareadmissionsystem;

/**
 *
 * @author Student
 */
public class Bed {

    private String bedNumber;
    private Inpatient patient;

    public Bed(String bedNumber) {
        this.bedNumber = bedNumber;
    }

    public String getBedNumber() {
        return bedNumber;
    }

    public Inpatient getPatient() {
        return patient;
    }

    public boolean isOccupied() {
        return patient != null;
    }

    public void allocate(Inpatient patient) {

        if (isOccupied()) {
            throw new IllegalStateException(
                    "Bed " + bedNumber + " is already occupied."
            );
        }

        this.patient = patient;
        patient.setBedNumber(bedNumber);
    }

    public void release() {

        if (patient != null) {
            patient.setBedNumber("Not allocated");
        }

        patient = null;
    }
}

