package com.amritraj.reviewms.review.impl;

import com.amritraj.reviewms.review.exceptions.IllegalArgumentExc;
import com.amritraj.reviewms.review.exceptions.ResourceNotFoundException;
import com.amritraj.reviewms.review.Review;
import com.amritraj.reviewms.review.ReviewDTO;
import com.amritraj.reviewms.review.ReviewRepo;
import com.amritraj.reviewms.review.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepo reviewRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewServiceImpl (ReviewRepo reviewRepository,
                              ModelMapper modelMapper){
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ReviewDTO> getAllReviews(String companyId) {
        List<Review> allReviews = reviewRepository.findByCompanyId(companyId);
        if(allReviews.isEmpty()) return List.of();

        return allReviews.stream().map((review) ->
                this.modelMapper.map(review, ReviewDTO.class)).toList();
    }

    @Override
    public ReviewDTO createReview(String companyId, ReviewDTO reviewDTO) {
        if(companyId == null || reviewDTO == null){
            if(reviewDTO == null){
                throw new IllegalArgumentExc("Review data cannot be null!");
            }
            throw new IllegalArgumentExc("Company data cannot be null!");
        }
        reviewDTO.setReviewId(UUID.randomUUID().toString());
        reviewDTO.setCompanyId(companyId);
        return this.modelMapper.map(reviewRepository.save(this.modelMapper
                .map(reviewDTO,Review.class)), ReviewDTO.class);
    }

    @Override
    public ReviewDTO getReviewById(String reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Review with id " + reviewId + " not found!"
                ));
        return this.modelMapper.map(review, ReviewDTO.class);
    }

    @Override
    public ReviewDTO updateReview(String reviewId,
                                  ReviewDTO reviewDTO) {

        if(reviewDTO == null){
            throw new IllegalArgumentException("Review data cannot be null!");
        }

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Review with id " + reviewId + " not found!"
                ));

        review.setTitle(reviewDTO.getTitle());
        review.setDescription(reviewDTO.getDescription());
        review.setRating(reviewDTO.getRating());

        return this.modelMapper.map(reviewRepository.save(review), ReviewDTO.class);
    }

    @Override
    public void deleteReview(String reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Review with id " + reviewId + " not found!"
                ));
        reviewRepository.delete(review);
    }
}
