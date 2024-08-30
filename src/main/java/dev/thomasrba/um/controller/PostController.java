package dev.thomasrba.um.controller;

import dev.thomasrba.um.entity.PostRequest;
import dev.thomasrba.um.entity.post.Post;
import dev.thomasrba.um.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(path="/api/v1/post")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public void createPost(@RequestBody PostRequest postRequest, Principal userConnected) {
        postService.registerPost(postRequest, userConnected);
    }

    @PutMapping
    public void updatePost(@RequestBody PostRequest postRequest, Principal userConnected) {
        postService.updatePost(postRequest, userConnected);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Post getPost(@RequestBody PostRequest postRequest) {
        return postService.getPost(postRequest);
    }

    @DeleteMapping
    public void deletePost(@RequestBody PostRequest postRequest, Principal userConnected) {
        postService.deletePost(postRequest, userConnected);
    }
}
