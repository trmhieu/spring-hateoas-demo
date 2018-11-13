package com.hieutm.demo.spring.hateoas.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  private String title;
  @NotNull
  private String content;

  public Post(@NotNull String title, @NotNull String content) {
    this.title = title;
    this.content = content;
  }
}
