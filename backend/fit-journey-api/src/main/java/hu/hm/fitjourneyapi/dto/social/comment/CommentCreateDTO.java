package hu.hm.fitjourneyapi.dto.social.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentCreateDTO {
    @NotBlank
    @Size(min = 1, max = 500)
    private String content;
}
