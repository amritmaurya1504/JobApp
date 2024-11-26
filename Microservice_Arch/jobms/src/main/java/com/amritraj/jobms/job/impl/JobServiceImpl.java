package com.amritraj.jobms.job.impl;

import com.amritraj.jobms.exceptions.ResourceNotFoundException;
import com.amritraj.jobms.job.Job;
import com.amritraj.jobms.job.JobDTO;
import com.amritraj.jobms.job.JobRepo;
import com.amritraj.jobms.job.JobService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class JobServiceImpl implements JobService {

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
        return allJobs.stream().map(
                (job -> this.modelMapper.map(job, JobDTO.class))
        ).toList();
    }

    @Override
    public JobDTO findJobById(String jobId) {
        Job job = jobRepo.findById(jobId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Job with id " + jobId + " not found!"
                )
        );
        return this.modelMapper.map(job, JobDTO.class);
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
    public JobDTO updateJob(JobDTO jobDTO, String jobId) {
        Job job = jobRepo.findById(jobId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Job with id " + jobId + " not found!"
                )
        );

        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setLocation(jobDTO.getLocation());
        job.setMaxSalary(jobDTO.getMaxSalary());
        job.setMinSalary(jobDTO.getMinSalary());
        return this.modelMapper.map(jobRepo.save(job), JobDTO.class);
    }

    @Override
    public JobDTO createJob(JobDTO job) {
        job.setJobId(UUID.randomUUID().toString());
        return this.modelMapper.map(jobRepo.save(this.modelMapper.map(
                job, Job.class
        )), JobDTO.class);
    }
}
