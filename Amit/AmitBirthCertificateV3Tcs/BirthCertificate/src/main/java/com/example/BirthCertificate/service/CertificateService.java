package com.example.BirthCertificate.service;

import com.example.BirthCertificate.model.CertificateModel;
import com.example.BirthCertificate.model.UserModel;
import com.example.BirthCertificate.repository.CertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CertificateService {

    //if do not write @Service here as not needed this service initially for creating token
    //but will give error as we have written @Autowired here inside class
    //and error given is for login service while we have we already written @Service there
    //so better in starting only write all the annotations after writing config class codes
    //keep writing when writting code somewhere for config classes

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private UserService userService;

    //to add certificate using certificate model
    //created 201, bad request 400
    public ResponseEntity<Object> postCertificate(CertificateModel certificateModel){
        //UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel userModel = (UserModel) userDetails;
        //UserModel userModelFromDb=userService.getUserByEmail(userModel.getEmail());
        userModel=userService.getUserByEmail(userModel.getEmail());
        certificateModel.setDoctor(userModel);
        certificateModel.setVerificationStatus("pending");
        certificateModel=certificateRepository.save(certificateModel);
        return ResponseEntity.ok(certificateModel);
    }

    //to get certificate details based on request parameter
    //ok 200, bad request 400
    public ResponseEntity<Object> getCertificate(int certificateNumber){
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return null;
    }

    //to update certificate verification status
    //ok 200, bad request 400
    public ResponseEntity<Object> updateCertificate(int id, CertificateModel certificateModel){
        return null;
    }

    //to delete certificate by using id
    //only the doctor of that particular certificate can delete
    //no content 204, bad request 400, forbidden 403
    public ResponseEntity<Object> deleteCertificate(int id){
        return null;
    }
}
