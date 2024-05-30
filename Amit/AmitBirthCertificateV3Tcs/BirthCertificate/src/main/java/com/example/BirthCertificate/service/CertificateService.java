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

import java.util.List;

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

    //4th mistake.. did not put db operation in try catch so 500 error
    //5th mistake..did not write proper request matcher and anyrequest.permit all
    //so security context holder gives class cast exception as no user details is set in security context

    //6th mistake:Be very carefull in Path variable directly pass id.. /1, no need to write /id=1 this is wrong


    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private UserService userService;

    @Autowired
    LoginService loginService;

    //to add certificate using certificate model
    //created 201, bad request 400
    public ResponseEntity<Object> postCertificate(CertificateModel certificateModel){
        try{
            UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userModel=userService.getUserByEmail(userModel.getEmail());
            //userModel=(UserModel) loginService.loadUserByUsername(userModel.getEmail())
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
        try{
            UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<CertificateModel> certificateModelFromDb = certificateRepository.findByCertificateNumber(certificateNumber);
            if (certificateModelFromDb.isEmpty()){
                return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("No data available!!");
            }
            return ResponseEntity.ok(certificateModelFromDb);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
        }

    }

    //to update certificate verification status
    //ok 200, bad request 400
    //Be very carefull in Path variable directly pass id.. /1, no need to write /id=1 this is wrong
    public ResponseEntity<Object> updateCertificate(int id, CertificateModel certificateModel){
        try{
            UserModel userModel=(UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            try{
                CertificateModel certificateModelFromDb=certificateRepository.findById(id).orElse(null);
                if (certificateModelFromDb==null){
                    return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Certificate not available for given id");
                }
                certificateModelFromDb.setVerificationStatus(certificateModel.getVerificationStatus());
                try{
                    certificateModelFromDb=certificateRepository.save(certificateModelFromDb);
                    return ResponseEntity.ok(certificateModelFromDb);
                }
                catch (Exception e){
                    return ResponseEntity.badRequest().body("Sql Error In Updating Certificate");
                }
            }
            catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    //to delete certificate by using id
    //only the doctor of that particular certificate can delete
    //no content 204, bad request 400, forbidden 403
    public ResponseEntity<Object> deleteCertificate(int id){
        return null;
    }
}
