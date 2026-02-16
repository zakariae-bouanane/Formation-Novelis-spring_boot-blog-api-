package novelis.formation.blog.mapper.post;

import novelis.formation.blog.DTO.post.PostRequestDto;
import novelis.formation.blog.DTO.post.PostResponseDto;
import novelis.formation.blog.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post toEntity(PostRequestDto dto);

    @Mapping(source = "likesCount", target = "likesCount")
    @Mapping(source = "commentsCount", target = "commentsCount")
    PostResponseDto toDto(Post post);
}

