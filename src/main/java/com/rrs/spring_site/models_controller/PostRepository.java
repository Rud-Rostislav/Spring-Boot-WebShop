package com.rrs.spring_site.models_controller;

import com.rrs.spring_site.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
