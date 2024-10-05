package com.example.BirthCertificate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class CertificateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private int certificateNumber;
    private String applicantName;
    private String dateOfBirth;
    private String gender;
    private String fatherName;
    private String motherName;
    private String address;
    private String nationality;
    private String verificationStatus = "pending";

    @ManyToOne()
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    //@JsonIgnore
    @JsonIgnoreProperties(value = {"username", "password", "email", "role", "enabled", "authorities", "accountNonLocked", "accountNonExpired", "credentialsNonExpired"})
    //@JsonIgnoreProperties(value = {"role", "authorities"})
    private UserModel doctor;
    //private int doctorId;

    //foreign key Integer doctor id given here as well,
    //so if write UserModel doctor, this will create fk doctorId of integer type in table
    //but in getter, setter, constructor.. it should be UserModel everwhere not doctorId

    //{
    //    "id": 1,
    //    "certificateNumber": 268,
    //    "applicantName": "Richard Bebb",
    //    "dateOfBirth": "26-02-1979",
    //    "gender": "Male",
    //    "fatherName": "George McCurry",
    //    "motherName": "Rose Belle Ashley",
    //    "address": "Beverly Hill, Los Angeles",
    //    "nationality": "American",
    //    "verificationStatus": "pending",
    //    "doctor": {
    //        "id": 1
    //    }
    //}
    //if creating foreign key by UserModel doctor in CertificateModel and ignoring all properties except id

    public CertificateModel() {
    }

    //public CertificateModel(int id, int certificateNumber, String applicantName, String dateOfBirth, String gender, String fatherName, String motherName, String address, String nationality, String verificationStatus, int doctorId) {
    public CertificateModel(int id, int certificateNumber, String applicantName, String dateOfBirth, String gender, String fatherName, String motherName, String address, String nationality, String verificationStatus, UserModel doctor) {
        this.id = id;
        this.certificateNumber = certificateNumber;
        this.applicantName = applicantName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.address = address;
        this.nationality = nationality;
        this.verificationStatus = verificationStatus;
        //this.doctorId = doctorId;
        this.doctor=doctor;
    }

    public CertificateModel(int certificateNumber, String applicantName, String dateOfBirth, String gender, String fatherName, String motherName, String address, String nationality) {
        this.certificateNumber = certificateNumber;
        this.applicantName = applicantName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.fatherName = fatherName;
        this.motherName = motherName;
        this.address = address;
        this.nationality = nationality;
    }

    public CertificateModel(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(int certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

//    public int getDoctorId() {
//        return doctorId;
//    }
    public UserModel getDoctor() {
        return doctor;
    }

//    public void setDoctorId(int doctorId) {
//        this.doctorId = doctorId;
//    }
    public void setDoctor(UserModel doctor) {
        this.doctor = doctor;
    }
}
