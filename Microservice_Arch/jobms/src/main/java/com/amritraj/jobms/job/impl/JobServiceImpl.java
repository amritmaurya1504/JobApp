package com.amritraj.jobms.job.impl;

import com.amritraj.jobms.job.exceptions.ResourceNotFoundException;
import com.amritraj.jobms.job.Job;
import com.amritraj.jobms.job.dto.AddJobDTO;
import com.amritraj.jobms.job.JobRepo;
import com.amritraj.jobms.job.JobService;
import com.amritraj.jobms.job.dto.JobDTO;
import com.amritraj.jobms.job.external.Company;
import com.amritraj.jobms.job.external.Review;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    RestTemplate restTemplate;

    private final JobRepo jobRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public JobServiceImpl(JobRepo jobRepo, ModelMapper modelMapper){
        this.jobRepo = jobRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<JobDTO> findAllJobs() {
        List<Job> allJobs = jobRepo.findAll();
        return allJobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public JobDTO findJobById(String jobId) {
        Job job = jobRepo.findById(jobId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Job with id " + jobId + " not found!"
                )
        );
        return convertToDto(job);
    }

    private JobDTO convertToDto(Job job){
        Company company = restTemplate.getForObject(
                "http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId(),
                Company.class);
        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
                "http://REVIEW-SERVICE:8083/reviews?companyId=" + company.getCompanyId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Review>>() {
                }
        );
        List<Review> reviews = reviewResponse.getBody();
        JobDTO jobDTO = this.modelMapper
                .map(job, JobDTO.class);
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);
        return jobDTO;
    }

    @Override
    public void deleteJob(String jobId) {
        Job job = jobRepo.findById(jobId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Job with id " + jobId + " not found!"
                )
        );

        jobRepo.delete(job);
    }

    @Override
    public AddJobDTO updateJob(AddJobDTO addJobDTO, String jobId) {
        Job job = jobRepo.findById(jobId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Job with id " + jobId + " not found!"
                )
        );

        job.setTitle(addJobDTO.getTitle());
        job.setDescription(addJobDTO.getDescription());
        job.setLocation(addJobDTO.getLocation());
        job.setMaxSalary(addJobDTO.getMaxSalary());
        job.setMinSalary(addJobDTO.getMinSalary());
        return this.modelMapper.map(jobRepo.save(job), AddJobDTO.class);
    }

    @Override
    public AddJobDTO createJob(AddJobDTO job) {
        job.setJobId(UUID.randomUUID().toString());
        return this.modelMapper.map(jobRepo.save(this.modelMapper.map(
                job, Job.class
        )), AddJobDTO.class);
    }
}
