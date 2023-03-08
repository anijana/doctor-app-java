package com.example.Doctor.service;

import com.example.Doctor.dao.PatientRepository;
import com.example.Doctor.model.Doctor;
import com.example.Doctor.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    public void savePatient(Patient patient){
        patientRepository.save(patient);
    }

    public List<Patient> getPatient(String patientId) {
        List<Patient> patientList;

        if(null != patientId){
            patientList = new ArrayList<>();

            patientList.add(patientRepository.findById(Integer.valueOf(patientId)).get());
        } else {
            return patientRepository.findAll();
        }
        return patientList;
    }

    public void updatePatient(Patient newPatient, String patientId) {
            if(patientRepository.findById(Integer.valueOf(patientId)).isPresent()) {
                Patient patient = patientRepository.findById(Integer.valueOf(patientId)).get();
                newPatient.setPatientId(patient.getPatientId());
                patientRepository.save(newPatient);
            }
    }
}
