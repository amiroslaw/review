package xyz.miroslaw.review.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import xyz.miroslaw.review.model.Category;
import xyz.miroslaw.review.repository.CategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/categories")
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
    public List<Category> findCategoryByName(@PathVariable String name){
        return categoryRepository.findByName(name);
    }

    @RequestMapping(method = RequestMethod.POST)
//    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void createCategory(@RequestBody Category category){
        categoryRepository.save(category);
    }

    @PutMapping("/{id}")
    public void updateCategory(@RequestBody Category objective, @PathVariable int id){
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
    public void deleteCategory(@PathVariable int id){
        Category old = categoryRepository.findOne(id);
        if (old == null) {
            throw new RuntimeException("objective not found");
        }
        categoryRepository.delete(id);
    }
}
