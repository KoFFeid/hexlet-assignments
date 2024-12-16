package exercise.controller;

import exercise.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.exception.ResourceNotFoundException;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    CommentRepository commentRepository;

    @GetMapping(path = "")
    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long id){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("comment with id-" + id + " not find"));
        return ResponseEntity.ok(comment);
    }


    @PostMapping(path = "")
    public ResponseEntity<Comment> addComment(@RequestBody Comment data){
        commentRepository.save(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @PutMapping(path="/{id}")
    public ResponseEntity<Comment> updateCpmment(@PathVariable Long id, @RequestBody Comment data){
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        comment.setBody(data.getBody());
        comment.setPostId(data.getPostId());

        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id){
        commentRepository.deleteById(id);
    }
}


// END


//GET /comments — список всех комментариев
//GET /comments/{id} – просмотр конкретного комментария
//POST /comments – создание нового комментария. При успешном создании возвращается статус 201
//PUT /comments/{id} – обновление комментария
//DELETE /comments/{id} – удаление комментария