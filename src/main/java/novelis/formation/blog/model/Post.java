package novelis.formation.blog.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor // creates default constructor (no arguments)
@AllArgsConstructor // creates a constructor with all fields.
@Builder // enables building the entity by field, instead of using all fields in constructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000)
    private String content;

    private String authorId; // will come from JWT later

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true) // orphanRemoval = true → deleting comment from list removes it from DB
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();


    // helpers for mapStruct
    public long getLikesCount() {
        return likes != null ? likes.size() : 0;
    }

    public long getCommentsCount() {
        return comments != null ? comments.size() : 0;
    }
}
