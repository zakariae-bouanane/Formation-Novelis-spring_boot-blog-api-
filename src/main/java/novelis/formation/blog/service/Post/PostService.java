package novelis.formation.blog.service.Post;

import novelis.formation.blog.DTO.post.PostRequestDto;
import novelis.formation.blog.DTO.post.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    PostResponseDto create(PostRequestDto dto);

    Page<PostResponseDto> search(String keyword, Pageable pageable);

    PostResponseDto getById(Long id);

    Page<PostResponseDto> getAll(Pageable pageable);

    PostResponseDto update(Long id, PostRequestDto dto);

    void delete(Long id);
}
