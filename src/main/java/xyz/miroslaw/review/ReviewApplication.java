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
import xyz.miroslaw.review.model.Task;
import xyz.miroslaw.review.repository.CategoryRepository;
import xyz.miroslaw.review.repository.ObjectiveRepository;
import xyz.miroslaw.review.repository.TaskRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


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
	private TaskRepository taskRepository;
	@Autowired
	public DataLoader(TaskRepository taskRepository, ObjectiveRepository objectiveRepository, CategoryRepository categoryRepository){
		this.categoryRepository = categoryRepository;
		this.objectiveRepository = objectiveRepository;
		this.taskRepository = taskRepository;
	}

	@Override
	public void run(ApplicationArguments args) {
//		loadData();
	}

	private void loadData() {
		Category category = new Category("Personal");
		categoryRepository.save(category);
		LocalDate localDate= LocalDate.of(2017,10,22);
		Date date = Date.valueOf(localDate);
		Objective ob =  new Objective(date, 52.6f, "main task", "forex", "comment");
		objectiveRepository.save(ob);
		List<Task> tasks = Arrays.asList(
				new Task("project", 12, category, ob),
				new Task("website", 8, category, ob)
		);
		taskRepository.save(tasks);


	}

}
}
