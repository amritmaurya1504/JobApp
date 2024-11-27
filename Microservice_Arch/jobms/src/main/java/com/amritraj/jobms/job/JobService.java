package com.amritraj.jobms.job;

import com.amritraj.jobms.job.dto.AddJobDTO;
import com.amritraj.jobms.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findAllJobs();
    JobDTO findJobById(String jobId);
    void deleteJob(String jobId);
    AddJobDTO updateJob(AddJobDTO job, String jobId);
    AddJobDTO createJob(AddJobDTO job);
}
