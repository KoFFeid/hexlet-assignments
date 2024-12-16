package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController{

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<Post> getPosts(){
        return postRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Post getPost(@PathVariable Long id){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        return post;
    }

    @PostMapping(path = "")
    public ResponseEntity<Post> addPost(@RequestBody Post data){
        Post newPost = new Post();
        newPost.setTitle(data.getTitle());
        newPost.setBody(data.getBody());

        postRepository.save(newPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post data){
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        post.setBody(data.getBody());
        post.setTitle(data.getTitle());

        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @DeleteMapping(path = "/{id}")
    public void deletePost(@PathVariable Long id){
        commentRepository.deleteByPostId(id);
        postRepository.deleteById(id);
    }

}

// END
//GET /posts — список всех постов
//        GET /posts/{id} – просмотр конкретного поста
//        POST /posts – создание нового поста. При успешном создании возвращается статус 201
//        PUT /posts/{id} – обновление поста
//        DELETE /posts/{id} – удаление поста. При удалении поста удаляются все комментарии этого поста