package com.amritraj.reviewms.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // Get all reviews
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews(@RequestParam String companyId) {
        List<ReviewDTO> reviews = reviewService.getAllReviews(companyId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // Create a new review
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestParam String companyId ,
                                                  @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO createdReview = reviewService.createReview(companyId, reviewDTO);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }


    // Get a specific review by ID
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable String reviewId) {
        ReviewDTO review = reviewService.getReviewById(reviewId);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
    // Update a review
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable String reviewId,
                                               @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO updatedReview = reviewService.updateReview(reviewId, reviewDTO);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    // Delete a review
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable String reviewId) {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>("Review deleted successfully!",
                HttpStatus.OK);
    }
}