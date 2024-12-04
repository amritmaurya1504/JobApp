package com.amritraj.reviewms.review;

import com.amritraj.reviewms.review.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getAllReviews(String companyId);
    ReviewDTO createReview(String companyId, ReviewDTO review);
    ReviewDTO getReviewById(String reviewId);
    ReviewDTO updateReview(String reviewId, ReviewDTO review);
    void deleteReview(String reviewId);
}
