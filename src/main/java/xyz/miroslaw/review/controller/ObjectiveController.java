package xyz.miroslaw.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.miroslaw.review.exception.NotFoundException;
import xyz.miroslaw.review.model.Objective;
import xyz.miroslaw.review.model.Task;
import xyz.miroslaw.review.repository.ObjectiveRepository;
import xyz.miroslaw.review.repository.TaskRepository;

import java.util.Optional;

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
    public ResponseEntity<Objective> getObjective(@PathVariable int id) {
        return Optional.ofNullable(objectiveRepository.findOne(id))
                .map(e -> ResponseEntity.ok().body(e))
                .orElseThrow(() -> new NotFoundException("objective not found: id - " + id));

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
            throw new NotFoundException("mismatch id");
        }
        Objective old = objectiveRepository.findOne(id);
        if (old == null) {
            throw new NotFoundException("Item not found: id - " + id);
        }
        objectiveRepository.save(objective);
    }

    @DeleteMapping("/{id}")
    public void deleteObjective(@PathVariable int id) {
        Objective objective = objectiveRepository.findOne(id);
        if (objective == null) {
            throw new NotFoundException("Item not found: id - " + id);
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
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@RequestBody Task task, @PathVariable int objectiveId) {
        Objective objective = objectiveRepository.findOne(objectiveId);
        if (objective == null) throw new NotFoundException("Item not found: id - " + objectiveId);
        task.setObjective(objective);
        taskRepository.save(task);
    }

}
