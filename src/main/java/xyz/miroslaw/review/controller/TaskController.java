package xyz.miroslaw.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.miroslaw.review.model.Task;
import xyz.miroslaw.review.repository.TaskRepository;

import java.util.List;

@RestController
@RequestMapping("tasks")
public class TaskController {

    private TaskRepository taskRepository;
    @Autowired
    public TaskController(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public Iterable<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    @GetMapping("/{id}")
    public Task getTask(@PathVariable int id){
        return taskRepository.findOne(id);
    }
    @GetMapping("/name/{name}")
    public List<Task> findObjectiveByName(@PathVariable String name){
        return taskRepository.findByName(name);
    }
    @PutMapping("/{taskId}")
    public void createTask(@RequestBody Task task,  @PathVariable int taskId){
        if (task.getId() != taskId) {
            throw new RuntimeException("mismatch id");
        }
        Task old = taskRepository.findOne(taskId);
        if (old == null) {
            throw new RuntimeException("task not found");
        }
        taskRepository.save(task);
    }
    @DeleteMapping("/{taskId}")
    public  void deleteTask(@PathVariable int taskId){
        Task task = taskRepository.findOne(taskId);
        if (task == null) {
            throw new RuntimeException("task not found");
        }
        taskRepository.delete(task);
    }
}
