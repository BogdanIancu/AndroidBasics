package ro.facemsoft.myfirstapplication.models;

import java.util.Date;

public class ToDoItem {
    public enum Priority { low, medium, high}

    private String title;
    private String description;
    private Date dueDate;
    private Date creationDate;
    private Priority priority;

    public ToDoItem(String title, String description, Date dueDate,
                    Date creationDate, Priority priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.creationDate = creationDate;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
