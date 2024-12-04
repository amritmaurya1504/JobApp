package com.amritraj.companyms.company.impl;

import com.amritraj.companyms.company.Company;
import com.amritraj.companyms.company.clients.ReviewClient;
import com.amritraj.companyms.company.dto.CompanyDTO;
import com.amritraj.companyms.company.CompanyRepo;
import com.amritraj.companyms.company.CompanyService;
import com.amritraj.companyms.company.dto.ReviewMessage;
import com.amritraj.companyms.company.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepo companyRepo;
    private final ModelMapper modelMapper;
    private final ReviewClient reviewClient;

    @Autowired
    public CompanyServiceImpl(CompanyRepo companyRepo,
                              ModelMapper modelMapper,
                              ReviewClient reviewClient){
        this.companyRepo = companyRepo;
        this.modelMapper = modelMapper;
        this.reviewClient = reviewClient;
    }


    @Override
    public List<CompanyDTO> findAllCompanies() {
        List<Company> allCompanies = companyRepo.findAll();
        return allCompanies.stream().map(
                (company -> this.modelMapper.map(company, CompanyDTO.class))
        ).toList();
    }

    @Override
    public CompanyDTO findCompanyById(String companyId) {
        Company company = companyRepo.findById(companyId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Company with ID " + companyId + " not found")
        );

        return this.modelMapper.map(company, CompanyDTO.class);
    }

    @Override
    public CompanyDTO createCompany(CompanyDTO company) {

        company.setCompanyId(UUID.randomUUID().toString());
        return this.modelMapper.map(companyRepo.save(this.modelMapper.map(
                company, Company.class
        )), CompanyDTO.class);
    }

    @Override
    public CompanyDTO updateCompany(CompanyDTO company, String companyId) {

        Company company1 = companyRepo.findById(companyId).orElseThrow(
                () -> new ResourceNotFoundException("Company with ID " + companyId + " not found")
        );

        company1.setName(company.getName());
        company1.setDescription(company.getDescription());
        return this.modelMapper.map(company1, CompanyDTO.class);
    }

    @Override
    public void deleteCompany(String companyId) {
        Company company = companyRepo.findById(companyId).orElseThrow(
                () -> new ResourceNotFoundException("Company with ID " + companyId + " not found")
        );

        companyRepo.delete(company);
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        Company company = companyRepo.findById(reviewMessage.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Company with id " + reviewMessage.getCompanyId() +
                                " not found!"
                ));

        double averageRating = reviewClient.getAverageRatingForCompany(
                reviewMessage.getCompanyId()
        );
        System.out.println("Average Rating: " + averageRating);
        company.setRating(averageRating);
        companyRepo.save(company);
    }
}
