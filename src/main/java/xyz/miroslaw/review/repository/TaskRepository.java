package xyz.miroslaw.review.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.miroslaw.review.model.Objective;
import xyz.miroslaw.review.model.Task;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    List<Task> findByName(String name);
    List<Task> findAllByObjective(Objective objective); //or by int id
}
