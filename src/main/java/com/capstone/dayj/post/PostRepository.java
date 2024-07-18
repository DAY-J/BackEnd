package com.capstone.dayj.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByPostTag(String postTag);

    List<Post> findByPostTitleContainingOrPostContentContaining(String titleKeyword, String contentKeyword);

    @Modifying
    @Query("UPDATE Post p set p.postView = p.postView + 1 where p.id = :postId")
    void incrementPostView(@Param("postId") int postId);

    @Modifying
    @Query("UPDATE Post p set p.postLike = p.postLike + 1 where p.id = :postId")
    void incrementPostLike(@Param("postId") int postId);
}