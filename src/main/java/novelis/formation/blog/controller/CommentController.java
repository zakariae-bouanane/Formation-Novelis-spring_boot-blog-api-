package novelis.formation.blog.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import novelis.formation.blog.DTO.comment.CommentRequestDto;
import novelis.formation.blog.DTO.comment.CommentResponseDto;
import novelis.formation.blog.service.Comment.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> create(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequestDto dto) {

        CommentResponseDto response = commentService.create(postId, dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Page<CommentResponseDto>> getByPost(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(
                        Sort.Direction.fromString(sort[1]),
                        sort[0]
                )
        );

        return ResponseEntity.ok(
                commentService.getByPost(postId, pageable)
        );
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long postId,
            @PathVariable Long commentId) {

        commentService.delete(commentId);

        return ResponseEntity.noContent().build();
    }
}
