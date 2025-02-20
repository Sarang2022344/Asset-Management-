package com.asset.management.repository;

import com.asset.management.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company,Long> {

        Optional<Company> findById(Long companyId);


}
