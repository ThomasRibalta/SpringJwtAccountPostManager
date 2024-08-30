package dev.thomasrba.um.service;

import dev.thomasrba.um.entity.PostRequest;
import dev.thomasrba.um.entity.post.Post;
import dev.thomasrba.um.entity.user.Role;
import dev.thomasrba.um.entity.user.User;
import dev.thomasrba.um.exception.BadPermissionException;
import dev.thomasrba.um.exception.IncompletePostException;
import dev.thomasrba.um.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void registerPost(PostRequest postRequest, Principal userConnected) {
        var user = (User) ((UsernamePasswordAuthenticationToken)userConnected).getPrincipal();

        if (postRequest.getTitle() == null || postRequest.getTitle().isEmpty()
                || postRequest.getContent() == null || postRequest.getContent().isEmpty())
            throw new IncompletePostException("Incomplete post for create !");
        var post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .user(user)
                .category(postRequest.getCategory())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        postRepository.save(post);
    }

    public void updatePost(PostRequest postRequest, Principal userConnected) {
        var user = (User) ((UsernamePasswordAuthenticationToken)userConnected).getPrincipal();
        if (postRequest.getTitle() == null || postRequest.getTitle().isEmpty()
                || postRequest.getContent() == null || postRequest.getContent().isEmpty()
                || postRequest.getId() == null || postRequest.getId() <= 0)
            throw new IncompletePostException("Incomplete post for update !");
        Optional<Post> oPost = postRepository.findById(Long.valueOf(postRequest.getId()));
        if (oPost.isPresent()) {
            Post post = oPost.get();
            if (post.getUser().getId().equals(user.getId()) || user.getRole() == Role.ADMIN) {
                post.setTitle(postRequest.getTitle());
                post.setContent(postRequest.getContent());
                post.setCategory(postRequest.getCategory());
                post.setUpdatedAt(new Date());
                postRepository.save(post);
            } else
                throw new BadPermissionException("Permission denied to update this post");
        }
        else
            throw new EntityNotFoundException("Post not found");
    }

    public Post getPost(PostRequest postRequest) {
        if (postRequest.getId() == null || postRequest.getId() <= 0)
            throw new IncompletePostException("Incomplete post for retrieve !");
        return postRepository.findById(Long.valueOf(postRequest.getId())).orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    public void deletePost(PostRequest postRequest, Principal userConnected) {
        var user = (User) ((UsernamePasswordAuthenticationToken)userConnected).getPrincipal();

        if (postRequest.getId() == null || postRequest.getId() <= 0)
            throw new IncompletePostException("Incomplete post for delete !");
        Optional<Post> oPost = postRepository.findById(Long.valueOf(postRequest.getId()));
        if (oPost.isPresent()) {
            Post post = oPost.get();
            if (post.getUser().getId().equals(user.getId()) || user.getRole() == Role.ADMIN) {
                postRepository.delete(post);
            }else
                throw new BadPermissionException("Permission denied to delete this post");
        }else
            throw new EntityNotFoundException("Post not found");
    }
}
