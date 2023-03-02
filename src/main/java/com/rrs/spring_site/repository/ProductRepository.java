package com.rrs.spring_site.repository;

import com.rrs.spring_site.models.Product;
import org.springframework.data.repository.CrudRepository;

// CRUD посилається на створення, читання, оновлення, видалення
public interface ProductRepository extends CrudRepository<Product, Long> {
}