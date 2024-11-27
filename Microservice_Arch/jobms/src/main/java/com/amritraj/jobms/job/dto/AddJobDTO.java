package com.amritraj.jobms.job.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddJobDTO {
    private String jobId;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;
    private String companyId;
}
