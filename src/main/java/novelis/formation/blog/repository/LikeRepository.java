package novelis.formation.blog.repository;

import novelis.formation.blog.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByPostIdAndUserId(Long postId, String userId); /// Checks if a like already exists for a specific post and a specific user.

    long countByPostId(Long postId); /// Counts how many likes a post has.

    void deleteByPostIdAndUserId(Long postId, String userId); /// Deletes the like of a specific user for a specific post.
}
