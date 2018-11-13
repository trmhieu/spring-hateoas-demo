package com.hieutm.demo.spring.hateoas.repositories;

import com.hieutm.demo.spring.hateoas.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
