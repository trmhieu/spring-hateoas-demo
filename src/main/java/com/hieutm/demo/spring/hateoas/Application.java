package com.hieutm.demo.spring.hateoas;

import com.hieutm.demo.spring.hateoas.models.Comment;
import com.hieutm.demo.spring.hateoas.models.Post;
import com.hieutm.demo.spring.hateoas.repositories.CommentRepository;
import com.hieutm.demo.spring.hateoas.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@SpringBootApplication
@EnableEntityLinks
//@EnableHypermediaSupport(type=HypermediaType.HAL)
public class Application {

  @Autowired
  private PostRepository postRepository;
  @Autowired
  private CommentRepository commentRepository;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public CommandLineRunner initDatabase() {
    return args -> {
      Post post1 = new Post("Post 1", "This is content of post 1");
      Post post2 = new Post("Post 2", "This is content of post 2");
      Post post3 = new Post("Post 3", "This is content of post 3");
      Comment comment1 = new Comment("Comment 1", post1);
      Comment comment2 = new Comment("Comment 2", post1);
      Comment comment3 = new Comment("Comment 3", post1);
      Comment comment4 = new Comment("Comment 4", post2);
      postRepository.save(post1);
      postRepository.save(post2);
      postRepository.save(post3);
      commentRepository.save(comment1);
      commentRepository.save(comment2);
      commentRepository.save(comment3);
      commentRepository.save(comment4);
    };
  }
}
