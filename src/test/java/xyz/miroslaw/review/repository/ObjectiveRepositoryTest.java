package xyz.miroslaw.review.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.miroslaw.review.model.Objective;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataJpaTest
public class ObjectiveRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ObjectiveRepository repository;

    @Test
    public void findById_ShouldReturnObjective() {
        LocalDate localDate= LocalDate.of(2017,10,22);
        Date date = Date.valueOf(localDate);
        entityManager.persist(new Objective(date, 52.6f, "success", "failure", "comment"));
        Objective objective = repository.findOne(1);

        assertEquals(objective.getComment(), "comment");
        assertEquals(objective.getSuccess(), "success");
        assertEquals(objective.getFailure(), "failure");
        assertEquals(objective.getTotalDuration(), 52.6, 0.001);
        assertEquals(objective.getDate(), date);
    }
}