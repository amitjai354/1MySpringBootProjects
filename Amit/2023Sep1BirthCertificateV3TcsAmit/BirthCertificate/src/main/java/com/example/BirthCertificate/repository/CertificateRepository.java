package com.example.BirthCertificate.repository;

import com.example.BirthCertificate.model.CertificateModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<CertificateModel, Integer> {
    List<CertificateModel> findByCertificateNumber(int certificateNumber);
}
