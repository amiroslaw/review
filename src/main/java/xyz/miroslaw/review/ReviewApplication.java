package xyz.miroslaw.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import xyz.miroslaw.review.model.Category;
import xyz.miroslaw.review.model.Objective;
import xyz.miroslaw.review.repository.CategoryRepository;
import xyz.miroslaw.review.repository.ObjectiveRepository;


@SpringBootApplication
public class ReviewApplication {
	private static final Logger log = LoggerFactory.getLogger(ReviewApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReviewApplication.class, args);
	}

@Component
public class DataLoader implements ApplicationRunner {
	private CategoryRepository categoryRepository;
	private ObjectiveRepository objectiveRepository;

	@Autowired
	public DataLoader(ObjectiveRepository objectiveRepository, CategoryRepository categoryRepository){
		this.categoryRepository = categoryRepository;
		this.objectiveRepository = objectiveRepository;
	}

	@Override
	public void run(ApplicationArguments args) {
		Category category = new Category("Personal");
        categoryRepository.save(category);
		Objective ob =  new Objective("name", 33, null, "comment", category);
		objectiveRepository.save(ob);
	}

}
}
