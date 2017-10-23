package xyz.miroslaw.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.miroslaw.review.model.Category;
import xyz.miroslaw.review.repository.CategoryRepository;
import xyz.miroslaw.review.repository.ObjectiveRepository;
import xyz.miroslaw.review.model.Objective;

import java.util.List;

@RestController
@RequestMapping("/aims")
public class ObjectiveController {

    private ObjectiveRepository objectiveRepository;
    private CategoryRepository categoryRepository;
    @Autowired
    public ObjectiveController(ObjectiveRepository objectiveRepository, CategoryRepository categoryRepository){
        this.objectiveRepository = objectiveRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public Iterable<Objective> getAllObjectives(){
        return objectiveRepository.findAll();
    }

    @GetMapping("{id}")
    public Objective getObjective(@PathVariable int id){
        return objectiveRepository.findOne(id);
    }

    @GetMapping("/name/{name}")
    public List<Objective> findObjectiveByName(@PathVariable String name){
        return objectiveRepository.findByName(name);
    }

    @PostMapping
    public void create(@RequestBody Objective objective){
        objectiveRepository.save(objective);
    }

    @PostMapping("/{id}")
    public void createWithCategory(@RequestBody Objective objective, @PathVariable int id){
        objectiveRepository.save(objective);
        Category category = categoryRepository.findOne(id);
                if(category == null) {
            throw new RuntimeException("category not found");
                }
                objective.setCategory(category);
        objectiveRepository.save(objective);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Objective objective, @PathVariable int id){
        if (objective.getId() != id) {
            throw new RuntimeException("mismatch id");
        }
        Objective old = objectiveRepository.findOne(id);
        if (old == null) {
            throw new RuntimeException("objective not found");
        }
        objectiveRepository.save(objective);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        Objective old = objectiveRepository.findOne(id);
        if (old == null) {
            throw new RuntimeException("objective not found");
        }
        objectiveRepository.delete(id);
    }

}
