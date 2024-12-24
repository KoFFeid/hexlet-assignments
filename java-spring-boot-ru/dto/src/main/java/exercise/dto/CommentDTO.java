package exercise.dto;

import lombok.Getter;
import lombok.Setter;

// BEGIN
@Setter
@Getter
public class CommentDTO {
    private Long id;
    private String body;

    public CommentDTO(Long id, String body){
        this.id = id;
        this.body = body;
    }

    public CommentDTO() {
    }
}
// END
