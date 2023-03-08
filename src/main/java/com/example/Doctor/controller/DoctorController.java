package com.example.Doctor.controller;

import com.example.Doctor.model.Doctor;
import com.example.Doctor.service.DoctorService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/doctor-app")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @PostMapping(value = "/add-doctor")
    public ResponseEntity<String> addDoctor(@RequestBody String reqDoctor){
        JSONObject json = new JSONObject(reqDoctor);
       List<String> validationList = validateDoctor(json);

        if(validationList.isEmpty()){
            Doctor doctor = setDoctor(json);
            doctorService.addDoctor(doctor);
            return new ResponseEntity<>("Doctor saved", HttpStatus.CREATED);
        }else {

            String[] ans = Arrays.copyOf(validationList.toArray(),validationList.size(),String[].class);

            return new ResponseEntity<>("Please passed mandatory parameter: "+ Arrays.toString(ans), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/doctor")
    public List<Doctor> getDoctor(@Nullable @RequestParam String doctorId){
        return doctorService.getDoctor(doctorId);
    }

    private List<String> validateDoctor(JSONObject json) {
        List<String> errors = new ArrayList<>();

        if(!json.has("doctorId")){
           errors.add("doctorId");
        }

        if(!json.has("doctorName")){
            errors.add("doctorName");
        }

        if(!json.has("specialized")){
            errors.add("specialized");
        }
        return errors;

    }

    public Doctor setDoctor(JSONObject json){
        Doctor doctor = new Doctor();

        String doctorId = json.getString("doctorId");
        doctor.setDoctorId(Integer.valueOf(doctorId));

        String doctorName = json.getString("doctorName");
        doctor.setDoctorName(doctorName);

        String specialized = json.getString("specialized");
        doctor.setSpecialized(specialized);

        if(json.has("experience")){
            String exp = json.getString("experience");
            doctor.setExperience(exp);
        }
        return doctor;
    }
}
