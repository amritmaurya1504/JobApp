package com.amritraj.reviewms.review.messaging;

import com.amritraj.reviewms.review.Review;
import com.amritraj.reviewms.review.dto.ReviewDTO;
import com.amritraj.reviewms.review.dto.ReviewMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ReviewMessageProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ReviewDTO review){
        ReviewMessage reviewMessage = ReviewMessage.builder()
                .reviewId(review.getReviewId())
                .title(review.getTitle())
                .description(review.getDescription())
                .rating(review.getRating())
                .companyId(review.getCompanyId())
                .build();

        rabbitTemplate.convertAndSend("companyRatingQueue",
                reviewMessage);

    }

}
