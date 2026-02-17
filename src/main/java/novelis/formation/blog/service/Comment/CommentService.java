package novelis.formation.blog.service.Comment;

import novelis.formation.blog.DTO.comment.CommentRequestDto;
import novelis.formation.blog.DTO.comment.CommentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    CommentResponseDto create(Long postId, CommentRequestDto dto);

    Page<CommentResponseDto> getByPost(Long postId, Pageable pageable);

    void delete(Long commentId);
}
