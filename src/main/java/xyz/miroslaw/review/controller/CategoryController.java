package xyz.miroslaw.review.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import xyz.miroslaw.review.model.Category;
import xyz.miroslaw.review.repository.CategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public Iterable<Category> getAllCategory(){
        return categoryRepository.findAll();
    }

    @GetMapping("{id}")
    public Category getObjective(@PathVariable int id){
        return categoryRepository.findOne(id);
    }

    @GetMapping("/name/{name}")
    public List<Category> findObjectiveByName(@PathVariable String name){
        return categoryRepository.findByName(name);
    }

    @PostMapping
    public void create(@RequestBody Category objective){
        categoryRepository.save(objective);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Category objective, @PathVariable int id){
        if (objective.getId() != id) {
            throw new RuntimeException("mismatch id");
        }
        Category old = categoryRepository.findOne(id);
        if (old == null) {
            throw new RuntimeException("objective not found");
        }
        categoryRepository.save(objective);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        Category old = categoryRepository.findOne(id);
        if (old == null) {
            throw new RuntimeException("objective not found");
        }
        categoryRepository.delete(id);
    }
}
