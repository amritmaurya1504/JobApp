package com.amritraj.companyms.company.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewMessage {
    private String reviewId;
    private String title;
    private String description;
    private double rating;
    private String companyId;
}
