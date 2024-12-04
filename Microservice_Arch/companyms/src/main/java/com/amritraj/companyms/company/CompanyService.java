package com.amritraj.companyms.company;

import com.amritraj.companyms.company.dto.CompanyDTO;
import com.amritraj.companyms.company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {

    List<CompanyDTO> findAllCompanies();
    CompanyDTO findCompanyById(String companyId);
    CompanyDTO createCompany(CompanyDTO company);
    CompanyDTO updateCompany(CompanyDTO company, String companyId);
    void deleteCompany(String companyId);
    public void updateCompanyRating(ReviewMessage reviewMessage);
}
