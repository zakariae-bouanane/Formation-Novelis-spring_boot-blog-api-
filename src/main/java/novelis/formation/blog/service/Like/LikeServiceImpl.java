package novelis.formation.blog.service.Like;

import lombok.RequiredArgsConstructor;
import novelis.formation.blog.exception.BadRequestException;
import novelis.formation.blog.exception.ResourceNotFoundException;
import novelis.formation.blog.model.Like;
import novelis.formation.blog.model.Post;
import novelis.formation.blog.repository.LikeRepository;
import novelis.formation.blog.repository.PostRepository;
import novelis.formation.blog.service.Security.AuthenticatedUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final AuthenticatedUser currentUserService;


    @Override
    public void likePost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found"));

        String userId = currentUserService.getCurrentUserId();

        boolean alreadyLiked =
                likeRepository.existsByPostIdAndUserId(postId, userId);

        if (alreadyLiked) {
            throw new BadRequestException("Post already liked");
        }

        Like like = Like.builder()
                .post(post)
                .userId(userId)
                .build();

        likeRepository.save(like);
    }

    @Override
    public void unlikePost(Long postId) {

        String userId = currentUserService.getCurrentUserId();

        boolean exists =
                likeRepository.existsByPostIdAndUserId(postId, userId);

        if (!exists) {
            throw new BadRequestException("Post not liked yet");
        }

        likeRepository.deleteByPostIdAndUserId(postId, userId);
    }

    @Override
    public long countLikes(Long postId) {

        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found");
        }

        return likeRepository.countByPostId(postId);
    }
}

