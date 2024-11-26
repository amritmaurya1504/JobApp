package com.amritraj.reviewms.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private String reviewId;
    private String title;
    private String description;
    private double rating;
    private String companyId;
}
