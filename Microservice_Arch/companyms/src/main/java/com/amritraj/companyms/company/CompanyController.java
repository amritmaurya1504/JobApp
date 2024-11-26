package com.amritraj.companyms.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController (CompanyService companyService){
        this.companyService = companyService;
    }

    // Get all companies
    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<CompanyDTO> companies = companyService.findAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    // Get company by ID
    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable String companyId) {
        CompanyDTO company = companyService.findCompanyById(companyId);
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    // Create a new company
    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO company) {
        CompanyDTO createdCompany = companyService.createCompany(company);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    // Update a company
    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyDTO> updateCompany(@RequestBody CompanyDTO company,
                                           @PathVariable String companyId) {
        CompanyDTO updatedCompany = companyService.updateCompany(company, companyId);
        return new ResponseEntity<>(updatedCompany, HttpStatus.CREATED);
    }

    // Delete a company
    @DeleteMapping("/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable String companyId) {
       companyService.deleteCompany(companyId);
       return new ResponseEntity<>("Company deleted successfully!",
               HttpStatus.OK);
    }
}
