package novelis.formation.blog.service.Comment;

import lombok.RequiredArgsConstructor;
import novelis.formation.blog.DTO.comment.CommentRequestDto;
import novelis.formation.blog.DTO.comment.CommentResponseDto;
import novelis.formation.blog.exception.ResourceNotFoundException;
import novelis.formation.blog.mapper.CommentMapper;
import novelis.formation.blog.model.Comment;
import novelis.formation.blog.model.Post;
import novelis.formation.blog.repository.CommentRepository;
import novelis.formation.blog.repository.PostRepository;
import novelis.formation.blog.service.Security.AuthenticatedUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final AuthenticatedUser authenticatedUser;

    @Override
    public CommentResponseDto create(Long postId, CommentRequestDto dto) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found"));

        Comment comment = commentMapper.toEntity(dto);

        String userId = authenticatedUser.getCurrentUserId();

        comment.setAuthorId(userId);
        comment.setPost(post);

        Comment saved = commentRepository.save(comment);

        return commentMapper.toDto(saved);
    }

    @Override
    public Page<CommentResponseDto> getByPost(Long postId, Pageable pageable) {

        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post not found");
        }

        return commentRepository.findByPostId(postId, pageable)
                .map(commentMapper::toDto);
    }

    @Override
    public void delete(Long commentId) {

        if (!commentRepository.existsById(commentId)) {
            throw new ResourceNotFoundException("Comment not found");
        }

        commentRepository.deleteById(commentId);
    }
}