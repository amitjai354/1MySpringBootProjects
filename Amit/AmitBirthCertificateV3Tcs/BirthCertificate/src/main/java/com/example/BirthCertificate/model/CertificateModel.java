package com.example.BirthCertificate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CertificateModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int certificateNumber;
    private String applicantName;
    private String dateOfBirth;
    private String gender;
    private String fatherName;
    private String motherName;
    private String address;
    private String nationality;
    private String verificationStatus = "pending";
    private int doctorId;

    public CertificateModel() {
    }

    public CertificateModel(int id, int certificateNumber, String applicantName, String dateOfBirth, String gender, String fatherName, String motherName, String address, String nationality, String verificationStatus, int doctorId) {
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
        this.doctorId = doctorId;
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

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
}
