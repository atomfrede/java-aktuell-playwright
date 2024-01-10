package com.github.atomfrede.playwrightjavaaktuell.todo_item;

public class TodoItem {

    private Long id;
    private String user;
    private String title;
    private boolean completed;

    public TodoItem(Long id, String title, boolean completed, String user) {
        this.id = id;
        this.title = title;
        this.completed = completed;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUser() {
        return this.user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
