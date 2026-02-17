package novelis.formation.blog.service.Post;

import lombok.RequiredArgsConstructor;
import novelis.formation.blog.exception.ResourceNotFoundException;
import novelis.formation.blog.mapper.PostMapper;
import novelis.formation.blog.model.Post;
import novelis.formation.blog.repository.PostRepository;
import novelis.formation.blog.service.Security.AuthenticatedUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import novelis.formation.blog.DTO.post.*;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final AuthenticatedUser authenticatedUser;


    @Override
    public Page<PostResponseDto> search(String keyword, Pageable pageable) {

        Page<Post> posts =
                postRepository
                        .findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                                keyword,
                                keyword,
                                pageable
                        );

        return posts.map(postMapper::toDto);
    }

    @Override
    public PostResponseDto create(PostRequestDto dto) {

        Post post = postMapper.toEntity(dto);

        String userId = authenticatedUser.getCurrentUserId();
        post.setAuthorId(userId);

        Post saved = postRepository.save(post);

        return postMapper.toDto(saved);
    }

    @Override
    public PostResponseDto getById(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found"));

        return postMapper.toDto(post);
    }

    @Override
    public Page<PostResponseDto> getAll(Pageable pageable) {

        return postRepository.findAll(pageable)
                .map(postMapper::toDto);
    }

    @Override
    public PostResponseDto update(Long id, PostRequestDto dto) {

        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Post not found"));

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        Post updated = postRepository.save(post);

        return postMapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {

        if (!postRepository.existsById(id)) {
            throw new ResourceNotFoundException("Post not found");
        }

        postRepository.deleteById(id);
    }
}

