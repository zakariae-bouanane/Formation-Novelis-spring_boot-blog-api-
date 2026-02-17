package novelis.formation.blog.model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseEntity {

    @Column(nullable = false, length = 2000)
    private String content;

    private String authorId;

    @ManyToOne(fetch = FetchType.LAZY) // When you load a Comment, Post is NOT loaded immediately.
    @JoinColumn(nullable = false)
    private Post post;
}

