package dev.thomasrba.um.repository;

import dev.thomasrba.um.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
