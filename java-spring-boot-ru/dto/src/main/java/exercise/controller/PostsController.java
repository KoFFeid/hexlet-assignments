package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN

@RestController
@RequestMapping(path = "/posts")
public class PostsController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    private PostDTO toDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setBody(post.getBody());

        List<CommentDTO> comments = commentRepository
                .findByPostId(post.getId())
                .stream()
                .map( c -> {
                    return new CommentDTO(c.getId(), c.getBody());
                }).toList();

        dto.setComments(comments);
        dto.setTitle(post.getTitle());
        return dto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PostDTO> getAllPosts(){

        List<Post> posts = postRepository.findAll();

        List<PostDTO> result = posts.stream()
                .map(this::toDTO)
                .toList();

        return result;
    }

    @GetMapping(path = "/{id}")
    public PostDTO getPost(@PathVariable Long id){
        Post post = postRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        PostDTO dto = toDTO(post);
        return dto;
    }


}
//          GET /posts — cписок всех постов
//          GET /posts/{id} — просмотр конкретного поста
// END
