package xyz.miroslaw.review.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.miroslaw.review.model.Category;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CategoryRepository repository;

    @Test
    public void findById_ShouldReturnCategory() {
        entityManager.persist(new Category("Personal"));
        Category category = repository.findOne(1);

        assertEquals(category.getName(), "Personal");
    }

    @Test
    public void findByName_ShouldReturnCategory() {
        entityManager.persist(new Category("Personal"));
        List<Category> category = repository.findByName("Personal");

        assertEquals(category.get(0).getName(), "Personal");
    }
}