package com.hieutm.demo.spring.hateoas.repositories;

import com.hieutm.demo.spring.hateoas.models.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByPostId(long postId);

}
