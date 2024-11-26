package com.amritraj.jobms.job;

import java.util.List;

public interface JobService {
    List<JobDTO> findAllJobs();
    JobDTO findJobById(String jobId);
    void deleteJob(String jobId);
    JobDTO updateJob(JobDTO job, String jobId);
    JobDTO createJob(JobDTO job);
}
