package novelis.formation.blog.DTO.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;



@Data // generates getters, setters, equals(), toString()
public class PostRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
