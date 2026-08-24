/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.medicareadmissionsystem;

/**
 *
 * @author Student
 */
public class MedicareAdmissionSystem {

    public static void main(String[] args) {
    
         HospitalSystem hospital = new HospitalSystem();

        System.out.println("=================================");
        System.out.println("   MEDICARE HOSPITAL SYSTEM");
        System.out.println("=================================");

        hospital.displayWardLayout();
    }
   
}
