package xyz.miroslaw.review.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Objective {
    @Id
    @GeneratedValue
    private int id;
    @NotEmpty
    private String name;
    private int duration;
    private Date date;
    @Column(columnDefinition = "TEXT")
    private String comment;
    @JsonBackReference
    @ManyToOne
//    @JoinColumn(name = "category_id")
    private Category category;

    // private constructor needed by JPA
    public  Objective(){};

    public Objective(String name, int duration, Date date, String comment) {
        this.name = name;
        this.duration = duration;
        this.date = date;
        this.comment = comment;
    }
    public Objective(String name, int duration, Date date, String comment, Category category) {
        this(name, duration, date, comment);
        this.category = category;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
