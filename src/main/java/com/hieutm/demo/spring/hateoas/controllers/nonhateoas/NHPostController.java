package com.hieutm.demo.spring.hateoas.controllers.nonhateoas;

import com.hieutm.demo.spring.hateoas.exception.ResourceNotFoundException;
import com.hieutm.demo.spring.hateoas.models.Comment;
import com.hieutm.demo.spring.hateoas.models.Post;
import com.hieutm.demo.spring.hateoas.repositories.CommentRepository;
import com.hieutm.demo.spring.hateoas.repositories.PostRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
@Profile("non-hateoas")
public class NHPostController {

  private PostRepository postRepository;

  private CommentRepository commentRepository;


  @GetMapping("/{id}")
  public ResponseEntity<Post> getPost(@PathVariable Long id) {
    return postRepository.findById(id).map(post -> ResponseEntity.ok(post))
        .orElseThrow(() -> new ResourceNotFoundException("Post " + id + " does not found"));
  }

  @GetMapping("")
  public ResponseEntity<List<Post>> getAllPosts() {
    return ResponseEntity.ok(postRepository.findAll());
  }

  @GetMapping(value = "", params = {"page"})
  public ResponseEntity<List<Post>> getAll(@RequestParam("page") int page) {
    Pageable pageable = PageRequest.of(page, 2);
    return ResponseEntity.ok(postRepository.findAll(pageable).getContent());

  }

  @GetMapping("/{id}/comments")
  public ResponseEntity<List<Comment>> getCommentsOfPost(@PathVariable Long id) {
    if (!postRepository.existsById(id)) {
      new ResourceNotFoundException("Post " + id + " does not found");
    }
    return ResponseEntity.ok(commentRepository.findByPostId(id));

  }

}
