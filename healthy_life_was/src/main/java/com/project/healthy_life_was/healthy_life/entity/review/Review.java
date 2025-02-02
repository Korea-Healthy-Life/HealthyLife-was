package com.project.healthy_life_was.healthy_life.entity.review;

import com.project.healthy_life_was.healthy_life.entity.product.Product;
import com.project.healthy_life_was.healthy_life.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "p_id", nullable = false)
//    @JsonIgnore
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
//    @JsonIgnore
    private User user;

    @Column(name = "review_rating", nullable = false)
    private Double reviewRating;

    @Column(name = "review_content", nullable = false)
    private String reviewContent;

    @Column(name = "review_create_at", nullable = false)
    private LocalDateTime reviewCreatAt;

    @Column(name = "review_img_url")
    private String reviewImgUrl;

}
