package xyz.miroslaw.review.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Objective {
    @Id
    private int id;
    @NotEmpty
    private String name;
    @NotEmpty
    private int duration;
    private Date date;
    private String comment;

    public Objective(int id, String name, int duration, Date date, String comment) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.date = date;
        this.comment = comment;
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
}
