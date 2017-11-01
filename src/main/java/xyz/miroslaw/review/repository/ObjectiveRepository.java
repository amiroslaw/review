package xyz.miroslaw.review.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.miroslaw.review.model.Objective;

@Repository
public interface ObjectiveRepository extends CrudRepository<Objective, Integer>{

}
