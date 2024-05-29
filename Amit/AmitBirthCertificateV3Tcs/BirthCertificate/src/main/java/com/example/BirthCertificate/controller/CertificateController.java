package com.example.BirthCertificate.controller;

import com.example.BirthCertificate.model.CertificateModel;
import com.example.BirthCertificate.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/certificate")
@RestController
public class CertificateController {

    //3 mistakes I did,
    // did not write H2 database dependency

    //did not write property correctly , missed S from NON_KEYWORDS=user
    
    //SecurityContext Holder is giving class cast exception..  did noty pass token, and when passed key passed
    // was Jwt but in Filter key is Authentication so user details was not set in security context holder
    //SecurityContextHolder takes user details from loadUserByUsername

    // UserModel from Security context can be proxy data so first get data from db..
    //same for category.. non persisted data, get category from db first

    @Autowired
    private CertificateService certificateService;

    @PostMapping("/add")
    public ResponseEntity<Object> postCertificate(@RequestBody CertificateModel certificateModel){
        return certificateService.postCertificate(certificateModel);
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getCertificate(@RequestParam("certificateNumber") int certificateNumber){
        return certificateService.getCertificate(certificateNumber);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> updateCertificate(@PathVariable("id") int id, @RequestBody CertificateModel certificateModel){
        return certificateService.updateCertificate(id, certificateModel);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteCertificate(@PathVariable("id") int id){
        return certificateService.deleteCertificate(id);
    }
}
