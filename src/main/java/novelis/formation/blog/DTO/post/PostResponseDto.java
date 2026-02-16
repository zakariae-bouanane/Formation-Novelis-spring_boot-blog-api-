package novelis.formation.blog.DTO.post;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String authorId;
    private LocalDateTime createdAt;

    private long likesCount;
    private long commentsCount;


}
