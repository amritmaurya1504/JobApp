package com.amritraj.jobms.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {


    private final JobService jobService;

    @Autowired
    public JobController (JobService jobService){
        this.jobService = jobService;
    }

    // Get all jobs
    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<JobDTO> jobs = jobService.findAllJobs();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    // Get a job by ID
    @GetMapping("/{jobId}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable String jobId) {
        JobDTO job = jobService.findJobById(jobId);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    // Create a new job
    @PostMapping
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO job) {
        JobDTO createdJob = jobService.createJob(job);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    // Update an existing job
    @PutMapping("/{jobId}")
    public ResponseEntity<JobDTO> updateJob(@RequestBody JobDTO job, @PathVariable String jobId) {
        JobDTO updatedJob = jobService.updateJob(job, jobId);
        return new ResponseEntity<>(updatedJob, HttpStatus.OK);
    }

    // Delete a job
    @DeleteMapping("/{jobId}")
    public ResponseEntity<?> deleteJob(@PathVariable String jobId) {
        jobService.deleteJob(jobId);
        return new ResponseEntity<>("Job deleted successfully!", HttpStatus.OK);
    }
}
