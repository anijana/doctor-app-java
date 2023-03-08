package com.example.Doctor.controller;

import com.example.Doctor.dao.DoctorRepository;
import com.example.Doctor.model.Doctor;
import com.example.Doctor.model.Patient;
import com.example.Doctor.service.PatientService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
public class PatientController {
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PatientService patientService;

    @PostMapping(value = "/patient")
    public String savePatient(@RequestBody String patientRequest){
        JSONObject jsonObject = new JSONObject(patientRequest);

        Patient patient = setPatient(jsonObject);
        patientService.savePatient(patient);

        return "save patient";

    }

    @GetMapping(value = "/patient")
    public List<Patient> getPatient(@Nullable @RequestParam String doctorId,@Nullable @RequestParam String patientId){
        return patientService.getPatient(patientId);
    }

    @PutMapping(value = "/user/{userId}")
    public ResponseEntity<String> updatePatient(@PathVariable String patientId, @RequestBody String patientData){
        Patient patient = setPatient(patientData);
        patientService.updatePatient(patient,patientId);
        return new ResponseEntity("user updated", HttpStatus.OK);
    }

    private Patient setPatient(JSONObject jsonObject) {
        Patient patient = new Patient();
        patient.setPatientName(jsonObject.getString("patientName"));
        patient.setPatientId(jsonObject.getInt("patientId"));
        patient.setAge(jsonObject.getInt("age"));
        patient.setPhoneNum(jsonObject.getString("phoneNumber"));
        patient.setDiseaseType(jsonObject.getString("diseaseType"));
        patient.setGender(jsonObject.getString("gender"));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        patient.setAdmitDate(timestamp);

        String doctorId = jsonObject.getString("doctorId");

        Doctor doctor = doctorRepository.findById(Integer.valueOf(doctorId)).get();

        patient.setDoctorId(doctor);

        return patient;

    }
}
