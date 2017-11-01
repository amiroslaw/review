package xyz.miroslaw.review.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.miroslaw.review.exception.NotFoundException;
import xyz.miroslaw.review.model.Category;
import xyz.miroslaw.review.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public Iterable<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> getObjective(@PathVariable int id) {
        return Optional.ofNullable(categoryRepository.findOne(id))
                .map(e -> ResponseEntity.ok().body(e))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public List<Category> findCategoryByName(@PathVariable String name) {
        return categoryRepository.findByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(@RequestBody Category category) {
        categoryRepository.save(category);
    }

    @PutMapping("/{id}")
    public void updateCategory(@RequestBody Category objective, @PathVariable int id) {
        if (objective.getId() != id) {
            throw new NotFoundException("mismatch id" );
        }
        Category old = categoryRepository.findOne(id);
        if (old == null) {
            throw new NotFoundException("Item not found: id - " + id);
        }
        categoryRepository.save(objective);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable int id) {
        Category old = categoryRepository.findOne(id);
        if (old == null) {
            throw new NotFoundException("Item not found: id - " + id);
        }
        categoryRepository.delete(id);
    }
}
