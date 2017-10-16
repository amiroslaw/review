package xyz.miroslaw.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.miroslaw.review.model.Category;
import xyz.miroslaw.review.model.Objective;
import xyz.miroslaw.review.repository.ObjectiveRepository;

@SpringBootApplication
public class ReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewApplication.class, args);
	}
}
