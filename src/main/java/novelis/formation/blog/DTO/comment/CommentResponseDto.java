package novelis.formation.blog.DTO.comment;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentResponseDto {

    private Long id;
    private String content;
    private String authorId;
    private LocalDateTime createdAt;
}