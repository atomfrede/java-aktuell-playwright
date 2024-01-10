package com.github.atomfrede.playwrightjavaaktuell.todo_item;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;


@NoRepositoryBean
public interface TodoItemRepository extends CrudRepository<TodoItem, Long> {

    long nextId(String user);
    int countAllByCompleted(String user, boolean completed);

    List<TodoItem> findAllByCompleted(String user, boolean completed);

    Iterable<TodoItem> findAll(String user);

    long count(String user);


}
