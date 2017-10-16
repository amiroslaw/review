package xyz.miroslaw.review.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.miroslaw.review.model.Objective;

import java.util.List;

public interface ObjectiveRepository extends CrudRepository<Objective, Integer>{
    List<Objective> findByName(String name);
}
