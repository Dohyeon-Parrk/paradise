package com.example.paradise.domain.follow.entity;

import com.example.paradise.common.Timestamped;
import com.example.paradise.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Enumerated(EnumType.STRING)
    private FollowStatus status;

    @Builder
    public Follow(User requester, User receiver) {
        this.requester = requester;
        this.receiver = receiver;
        this.status = FollowStatus.PENDING;
    }
}
