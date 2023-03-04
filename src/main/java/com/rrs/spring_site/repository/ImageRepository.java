package com.rrs.spring_site.repository;

import com.rrs.spring_site.models.Photo;
import com.rrs.spring_site.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Photo, Long> {
}
