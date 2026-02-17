package novelis.formation.blog.controller;

import lombok.RequiredArgsConstructor;
import novelis.formation.blog.service.Like.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<Void> like(@PathVariable Long postId) {

        likeService.likePost(postId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unlike(@PathVariable Long postId) {

        likeService.unlikePost(postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(@PathVariable Long postId) {

        return ResponseEntity.ok(
                likeService.countLikes(postId)
        );
    }
}

