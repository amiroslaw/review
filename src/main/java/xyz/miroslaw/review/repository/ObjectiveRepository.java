package xyz.miroslaw.review.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.miroslaw.review.model.Objective;

import java.util.List;

@Repository
public interface ObjectiveRepository extends CrudRepository<Objective, Integer>{
    List<Objective> findByName(String name);
}
