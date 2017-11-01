package xyz.miroslaw.review.model;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
public class Objective {
    @Id
    @GeneratedValue
    private int id;
    @NotNull
    private Date date;
    private float totalDuration;
    @Column(columnDefinition = "TEXT")
    private String success;
    @Column(columnDefinition = "TEXT")
    private String failure;
    @Column(columnDefinition = "TEXT")
    private String comment;
    @OneToMany(mappedBy = "objective", cascade = CascadeType.REMOVE)
    private List<Task> tasks;

    Objective(){}
    public Objective(Date date, float totalDuration, String success, String failure, String comment) {
        this.date = date;
        this.totalDuration = totalDuration;
        this.success = success;
        this.failure = failure;
        this.comment = comment;
    }

    public Objective(Date date, float totalDuration, String success, String failure, String comment, List<Task> tasks) {
        this(date, totalDuration, success, failure, comment);
        this.tasks = tasks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(float totalDuration) {
        this.totalDuration = totalDuration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getFailure() {
        return failure;
    }

    public void setFailure(String failure) {
        this.failure = failure;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
