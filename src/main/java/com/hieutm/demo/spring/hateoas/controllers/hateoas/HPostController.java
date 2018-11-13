package com.hieutm.demo.spring.hateoas.controllers.hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.hieutm.demo.spring.hateoas.exception.ResourceNotFoundException;
import com.hieutm.demo.spring.hateoas.models.Comment;
import com.hieutm.demo.spring.hateoas.models.Post;
import com.hieutm.demo.spring.hateoas.repositories.CommentRepository;
import com.hieutm.demo.spring.hateoas.repositories.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@ExposesResourceFor(Post.class)
@AllArgsConstructor
@Profile("hateoas")
public class HPostController {

  private PostRepository postRepository;
  private CommentRepository commentRepository;
  private EntityLinks entityLinks;

  @GetMapping("/{id}")
  public ResponseEntity<Resource<Post>> getPost(@PathVariable Long id) {

    return postRepository.findById(id).map(post -> {
      Link self = entityLinks.linkForSingleResource(Post.class, id).withSelfRel();
      Link comments = linkTo(methodOn(HPostController.class).getCommentsOfPost(id))
          .withRel("comments");
      return new ResponseEntity<>(new Resource<>(post, self, comments), HttpStatus.CREATED);
    }).orElseThrow(() -> new ResourceNotFoundException("Post " + id + " does not found"));
  }

  @GetMapping("/{id}/comments")
  public ResponseEntity<Resources<Resource<Comment>>> getCommentsOfPost(@PathVariable Long id) {
    if (!postRepository.existsById(id)) {
      new ResourceNotFoundException("Post " + id + " does not found");
    }

    // create list of resource comment
    List<Resource<Comment>> comments = commentRepository.findByPostId(id).stream().map(comment ->
        new Resource<>(comment,
            entityLinks.linkForSingleResource(Comment.class, comment.getId())
                .withSelfRel()))
        .collect(Collectors.toList());

    // create link to self
    Link selfLink = linkTo(methodOn(HPostController.class).getCommentsOfPost(id))
        .withSelfRel();

    // create link to posts
    Link postLink = entityLinks.linkForSingleResource(Post.class, id).withRel("posts");
    return ResponseEntity.ok(new Resources<>(comments, selfLink, postLink));

  }

  @GetMapping("")
  public ResponseEntity<Resources<Resource<Post>>> getAllPosts() {
    // create collection of resource
    List<Resource<Post>> postResources = postRepository.findAll().stream().map(
        post -> new Resource<>(post,
            entityLinks.linkForSingleResource(Post.class, post.getId()).withSelfRel()))
        .collect(Collectors.toList());

    Resources<Resource<Post>> resources = new Resources<>(postResources,
        linkTo(methodOn(HPostController.class).getAllPosts()).withSelfRel());
    return ResponseEntity.ok(resources);
  }

  @GetMapping(value = "", params = {"page"})
  public ResponseEntity<Resources<Resource<Post>>> getAll(@RequestParam("page") int page) {

    Pageable pageable = PageRequest.of(page, 2);
    Page<Post> postPage = postRepository.findAll(pageable);

    // create collection of resource
    List<Resource<Post>> postResources = postPage.getContent().stream()
        .map(post ->
            new Resource<>(post, entityLinks.linkForSingleResource(Post.class, post.getId())
                .withSelfRel()))
        .collect(Collectors.toList());

    Resources<Resource<Post>> resources = new Resources<>(postResources,
        linkTo(methodOn(HPostController.class).getAll(page))
            .withSelfRel());

    // add previous link if exist previous page
    if (postPage.hasPrevious()) {
      resources.add(linkTo(methodOn(HPostController.class).getAll(page - 1))
          .withRel("previous"));
    }

    // add next link if exist next page
    if (postPage.hasNext()) {
      resources.add(linkTo(methodOn(HPostController.class).getAll(page + 1))
          .withRel("next"));
    }
    return ResponseEntity.ok(resources);
  }


}
