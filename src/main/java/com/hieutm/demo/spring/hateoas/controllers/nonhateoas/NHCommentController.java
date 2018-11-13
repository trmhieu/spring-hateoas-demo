package com.hieutm.demo.spring.hateoas.controllers.nonhateoas;

import com.hieutm.demo.spring.hateoas.exception.ResourceNotFoundException;
import com.hieutm.demo.spring.hateoas.models.Comment;
import com.hieutm.demo.spring.hateoas.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Profile("non-hateoas")
public class NHCommentController {

  private CommentRepository commentRepository;

  @GetMapping("comments/{id}")
  public ResponseEntity<Comment> getComment(
      @PathVariable Long id) {
    return commentRepository.findById(id).map(comment -> ResponseEntity.ok(comment))
        .orElseThrow(() -> new ResourceNotFoundException("Comment " + id + " does not found"));

  }

}
