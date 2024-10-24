package com.example.paradise.domain.post.domain;

import com.example.paradise.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAuthorIn(Collection<User> author);
    // sns -> twitter 시스템 디자인 리뷰
    // user마다 내가 봐야 하는 post를 보여주는(글 생성시 팔로우하는 사람한테 다 넣어주는 방식)

    @Query("SELECT p FROM Post p JOIN FETCH p.author u WHERE u.id IN :userIds ORDER BY p.createdAt DESC")
    List<Post> findPostsByUsers(List<Long> userIds);
}
