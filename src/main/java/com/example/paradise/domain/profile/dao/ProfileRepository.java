package com.example.paradise.domain.profile.dao;

import com.example.paradise.domain.profile.domain.Profile;
import com.example.paradise.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);
}
