package com.example.BirthCertificate.service;

import com.example.BirthCertificate.model.CertificateModel;
import com.example.BirthCertificate.model.UserModel;
import com.example.BirthCertificate.repository.CertificateRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CertificateService {

    //3 mistakes I did,
    // did not write H2 database dependency

    //did not write property correctly , missed S from NON_KEYWORDS=user

    //SecurityContext Holder is giving class cast exception..  did noty pass token, and when passed key passed
    // was Jwt but in Filter key is Authentication so user details was not set in security context holder
    //SecurityContextHolder takes user details from loadUserByUsername

    // UserModel from Security context can be proxy data so first get data from db..
    //same for category.. non persisted data, get category from db first


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
        try{
            UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userModel=userService.getUserByEmail(userModel.getEmail());
            certificateModel.setDoctor(userModel);
            certificateModel.setVerificationStatus("pending");
            //if after saving once try to save again.. unique/primary key voilation coming
            //certificate number is unique so if try to save same number again.. error
            //better always put db operations in try catch or one try catch on complete api code
            try{
                certificateModel=certificateRepository.save(certificateModel);
            }
            catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Some Sql Exception!!");
            }
            return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(certificateModel);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
        }

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
