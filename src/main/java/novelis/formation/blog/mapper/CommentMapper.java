package novelis.formation.blog.mapper;

import novelis.formation.blog.DTO.comment.CommentRequestDto;
import novelis.formation.blog.DTO.comment.CommentResponseDto;
import novelis.formation.blog.model.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toEntity(CommentRequestDto dto);

    CommentResponseDto toDto(Comment comment);
}
