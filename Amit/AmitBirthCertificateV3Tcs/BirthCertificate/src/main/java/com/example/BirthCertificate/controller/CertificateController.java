package com.example.BirthCertificate.controller;

import com.example.BirthCertificate.model.CertificateModel;
import com.example.BirthCertificate.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/certificate")
@RestController
public class CertificateController {

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

    //Be very carefull in Path variable directly pass id.. /1, no need to write /id=1 this is wrong
    @PatchMapping("/update/{id}")
    public ResponseEntity<Object> updateCertificate(@PathVariable("id") int id, @RequestBody CertificateModel certificateModel){
        return certificateService.updateCertificate(id, certificateModel);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteCertificate(@PathVariable("id") int id){
        return certificateService.deleteCertificate(id);
    }
}
