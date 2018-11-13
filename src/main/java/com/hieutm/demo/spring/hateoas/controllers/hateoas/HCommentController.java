package com.hieutm.demo.spring.hateoas.controllers.hateoas;

import com.hieutm.demo.spring.hateoas.exception.ResourceNotFoundException;
import com.hieutm.demo.spring.hateoas.models.Comment;
import com.hieutm.demo.spring.hateoas.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("comments")
@AllArgsConstructor
@ExposesResourceFor(Comment.class)
@Profile("hateoas")
public class HCommentController {

  private CommentRepository commentRepository;
  private EntityLinks entityLinks;

  @GetMapping("/{id}")
  public ResponseEntity<Resource<Comment>> getComment(
      @PathVariable Long id) {
    return commentRepository.findById(id).map(comment -> {
      Link selfLink = entityLinks.linkForSingleResource(Comment.class, comment.getId())
          .withSelfRel();
      return ResponseEntity.ok(new Resource<>(comment, selfLink));
    }).orElseThrow(() -> new ResourceNotFoundException("Comment " + id + " does not found"));
  }

}
