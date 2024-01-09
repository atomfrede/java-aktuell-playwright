package com.github.atomfrede.playwrightjavaaktuell.todo_item;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


@NoRepositoryBean
public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {

    long nextId();
    int countAllByCompleted(boolean completed);

    List<TodoItem> findAllByCompleted(boolean completed);
}
