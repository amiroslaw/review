package xyz.miroslaw.review.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private int id;
    @NotEmpty
    private String name;
    @NotNull
    private float duration;
    @ManyToOne
    private Category category;
    @JsonIgnore
    @ManyToOne
    private Objective objective;

    public Task() {
    }

    public Task(String name, float duration) {
        this.name = name;
        this.duration = duration;
    }

    public Task(String name, float duration, Category category, Objective objective) {
        this(name, duration);
        this.category = category;
        this.objective = objective;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Objective getObjective() {
        return objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }
}
