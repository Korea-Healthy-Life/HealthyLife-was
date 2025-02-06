package com.project.healthy_life_was.healthy_life.entity.whishList;

import com.project.healthy_life_was.healthy_life.entity.user.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "wish_list")
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "wish_list_id")
    private Long wishListId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "wishList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WishListItem> wishListItems;
}
