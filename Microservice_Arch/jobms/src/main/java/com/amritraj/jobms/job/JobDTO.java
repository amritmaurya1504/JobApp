package com.amritraj.jobms.job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {
    private String jobId;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;
    private String companyId;
}
