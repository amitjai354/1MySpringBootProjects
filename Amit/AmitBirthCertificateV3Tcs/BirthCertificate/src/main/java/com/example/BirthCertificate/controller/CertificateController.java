package com.example.BirthCertificate.controller;

import com.example.BirthCertificate.model.CertificateModel;
import com.example.BirthCertificate.service.CertificateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/certificate")
public class CertificateController {

    private CertificateService certificateService;

    @PostMapping("/add")
    public ResponseEntity<Object> postCertificate(CertificateModel certificateModel){
        return null;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getCertificate(int certificateNumber){
        return null;
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> updateCertificate(int id, CertificateModel certificateModel){
        return null;
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteCertificate(int id){
        return null;
    }
}
