package com.example.BirthCertificate.repository;

import com.example.BirthCertificate.model.CertificateModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<CertificateModel, Integer> {
}
