package exercise.controller.users;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

import static exercise.Data.getPosts;

// BEGIN

@RestController
@RequestMapping("/api")
public class PostsController{

    List<Post> posts = getPosts();

    @GetMapping("/users/{userId}/posts")
    public List<Post> showPosts(@PathVariable String userId){
        List<Post> userPosts = posts.stream().filter(p-> p.getUserId() == Integer.parseInt(userId) ).toList();
        return  userPosts;
    }

    @PostMapping("/users/{userId}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post createPost(@PathVariable String userId,
                                           @RequestBody Post Data ){
        Post data = new Post();

        data.setSlug(Data.getSlug());
        data.setTitle(Data.getTitle());
        data.setBody(Data.getBody());
        data.setUserId(Integer.parseInt(userId));

        posts.add(data);
        return data;
    }

}

// END
