package xyz.miroslaw.review.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.miroslaw.review.model.Objective;
import xyz.miroslaw.review.model.Task;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskRepositoryTest {
    private static LocalDate localDate = LocalDate.of(2017, 10, 22);
    private static Date date = Date.valueOf(localDate);
    private static final Objective OBJECTIVE = new Objective(date, 52.6f, "success", "failure", "comment");

    private static final List<Task> TASKS = Arrays.asList(
            new Task("jog", 1.2f, null, OBJECTIVE),
            new Task("skating", 3.3f, null, OBJECTIVE));

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TaskRepository repository;

    @Test
    public void findByName() throws Exception {
        entityManager.persist(new Task("jog", 3.3f));
        final List<Task> tasks = repository.findByName("jog");

        assertEquals(1, tasks.size());
        assertEquals(3.3, tasks.get(0).getDuration(), 0.001);
    }

    @Test
    public void findAllByObjective() throws Exception {
        entityManager.persist(OBJECTIVE);
        entityManager.persist(TASKS.get(0));
        entityManager.persist(TASKS.get(1));

        final List<Task> tasks = repository.findAllByObjective(OBJECTIVE);

        assertEquals(2, tasks.size());
        assertEquals("jog", tasks.get(0).getName());
        assertEquals("skating", tasks.get(1).getName());
    }

}