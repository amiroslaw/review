package xyz.miroslaw.review.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.miroslaw.review.model.Category;


import java.util.List;

public interface CategoryRepository extends CrudRepository <Category, Integer>{
    List<Category> findByName(String name);
}
