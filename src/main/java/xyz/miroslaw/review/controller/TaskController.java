package xyz.miroslaw.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.miroslaw.review.exception.NotFoundException;
import xyz.miroslaw.review.model.Task;
import xyz.miroslaw.review.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tasks")
public class TaskController {

    private TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable int id) {
        return Optional.ofNullable(taskRepository.findOne(id))
                .map(task -> ResponseEntity.ok().body(task))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public List<Task> findTasksByName(@PathVariable String name) {
        return taskRepository.findByName(name);
    }

    @PutMapping("/{taskId}")
    public void updateTask(@RequestBody Task task, @PathVariable int taskId) {
        if (task.getId() != taskId) {
            throw new NotFoundException("mismatch id");
        }
        Task old = taskRepository.findOne(taskId);
        if (old == null) {
            throw new NotFoundException("Item not found: id - " + taskId);
        }
        taskRepository.save(task);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable int taskId) {
        Task task = taskRepository.findOne(taskId);
        if (task == null) {
            throw new NotFoundException("Item not found: id - " + taskId);
        }
        taskRepository.delete(task);
    }
}
