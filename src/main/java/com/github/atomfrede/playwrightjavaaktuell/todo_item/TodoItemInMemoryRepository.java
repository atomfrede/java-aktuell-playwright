package com.github.atomfrede.playwrightjavaaktuell.todo_item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TodoItemInMemoryRepository implements TodoItemRepository {

    private Map<String, Map<Long, TodoItem>> todoItems = new HashMap<>();

    private void init(String user) {
        todoItems.putIfAbsent(user, new HashMap<>());
    }

    @Override
    public long nextId(String user) {
        init(user);
        return todoItems.get(user).size() + 10;
    }

    @Override
    public <S extends TodoItem> S save(S entity) {
        init(entity.getUser());
        todoItems.get(entity.getUser()).put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends TodoItem> Iterable<S> saveAll(Iterable<S> entities) {
        entities.forEach(this::save);
        return entities;
    }

    @Override
    public Optional<TodoItem> findById(Long id) {
        return todoItems.values().stream().flatMap(it -> it.values().stream()).filter(todoItem -> todoItem.getId().equals(id)).findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public Iterable<TodoItem> findAll() {
        return todoItems.values().stream().flatMap(it -> it.values().stream()).toList();
    }

    @Override
    public Iterable<TodoItem> findAllById(Iterable<Long> longs) {
        List<Long> ids = new ArrayList<>();
        longs.iterator().forEachRemaining(ids::add);
        return todoItems.values().stream().flatMap(it -> it.values().stream()).filter(it -> ids.contains(it.getId())).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return todoItems.size();
    }

    @Override
    public long count(String user) {
        init(user);
        return todoItems.size();
    }

    @Override
    public void deleteById(Long id) {
        todoItems.forEach((k,v) -> v.remove(id));
    }

    @Override
    public void delete(TodoItem entity) {
        init(entity.getUser());
        todoItems.get(entity.getUser()).remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        longs.forEach(this::deleteById);
    }

    @Override
    public void deleteAll(Iterable<? extends TodoItem> entities) {
        entities.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        todoItems.clear();
    }

    @Override
    public int countAllByCompleted(String user, boolean completed) {
        init(user);
        return (int) todoItems.get(user).values().stream().filter(it -> it.isCompleted() == completed).count();
    }

    @Override
    public List<TodoItem> findAllByCompleted(String user, boolean completed) {
        init(user);
        return todoItems.get(user).values().stream().filter(it -> it.isCompleted() == completed).collect(Collectors.toList());
    }

    @Override
    public Iterable<TodoItem> findAll(String user) {
        init(user);
        return todoItems.get(user).values();
    }
}
