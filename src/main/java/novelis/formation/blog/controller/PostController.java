package novelis.formation.blog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import novelis.formation.blog.DTO.post.PostRequestDto;
import novelis.formation.blog.DTO.post.PostResponseDto;
import novelis.formation.blog.service.Post.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor // ?
public class PostController {

    private final PostService postService;


    @GetMapping("/search")
    public ResponseEntity<Page<PostResponseDto>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                postService.search(keyword, pageable)
        );
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> create(
            @Valid @RequestBody PostRequestDto dto) {

        PostResponseDto response = postService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getById(@PathVariable Long id) {

        return ResponseEntity.ok(postService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.getAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody PostRequestDto dto) {

        return ResponseEntity.ok(postService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
