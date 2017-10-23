package xyz.miroslaw.review.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    @JsonManagedReference
    @OneToMany(mappedBy = "category")
    private List<Objective> objectives;

    public Category(){}

    public Category(String name) {
        this.name = name;
    }
    public Category(String name, List<Objective> objectives) {
        this.name = name;
        this.objectives = objectives;
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

    public List<Objective> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }
}
