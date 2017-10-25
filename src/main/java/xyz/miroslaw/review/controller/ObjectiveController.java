package xyz.miroslaw.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.miroslaw.review.model.Category;
import xyz.miroslaw.review.model.Task;
import xyz.miroslaw.review.repository.CategoryRepository;
import xyz.miroslaw.review.repository.ObjectiveRepository;
import xyz.miroslaw.review.model.Objective;
import xyz.miroslaw.review.repository.TaskRepository;

import java.util.List;

@RestController
@RequestMapping("/aims")
public class ObjectiveController {

    private ObjectiveRepository objectiveRepository;
    private TaskRepository taskRepository;


    @Autowired
    public ObjectiveController(ObjectiveRepository objectiveRepository, TaskRepository taskRepository) {
        this.objectiveRepository = objectiveRepository;
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public Iterable<Objective> getAllObjectives() {
        return objectiveRepository.findAll();
    }

    @GetMapping("{id}")
    public Objective getObjective(@PathVariable int id) {
        return objectiveRepository.findOne(id);
    }

    @PostMapping
    public void createObjective(@RequestBody Objective objective) {
        objectiveRepository.save(objective);
        objective.getTasks().forEach(e -> {
            e.setObjective(objective);
            taskRepository.save(e);
        });
    }

    @PutMapping("/{id}")
    public void updateObjective(@RequestBody Objective objective, @PathVariable int id) {
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
    public void deleteObjective(@PathVariable int id) {
        Objective objective = objectiveRepository.findOne(id);
        if (objective == null) {
            throw new RuntimeException("objective not found");
        }
        objectiveRepository.delete(objective);
    }

    @GetMapping("/{objectiveId}/tasks")
    public Iterable<Task> getTasksByObjective(@PathVariable int objectiveId) {
        Objective objective = objectiveRepository.findOne(objectiveId);
        if (objective == null) throw new RuntimeException("objective not found");
        return taskRepository.findAllByObjective(objective);
    }

    @PostMapping("/{objectiveId}/tasks")
    public void createTask(@RequestBody Task task, @PathVariable int objectiveId) {
        Objective objective = objectiveRepository.findOne(objectiveId);
        if (objective == null) throw new RuntimeException("objective not found");
        task.setObjective(objective);
        taskRepository.save(task);
    }


}
