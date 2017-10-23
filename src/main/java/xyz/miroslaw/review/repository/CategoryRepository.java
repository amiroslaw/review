package xyz.miroslaw.review.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.miroslaw.review.model.Category;


import java.util.List;
@Repository
public interface CategoryRepository extends CrudRepository <Category, Integer>{
    List<Category> findByName(String name);
}
