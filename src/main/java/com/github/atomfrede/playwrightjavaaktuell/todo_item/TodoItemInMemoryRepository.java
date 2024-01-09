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

    private Map<Long, TodoItem> todoItems = new HashMap<>();

    @Override
    public long nextId() {
        return todoItems.size() + 10;
    }

    @Override
    public <S extends TodoItem> S save(S entity) {
        todoItems.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends TodoItem> Iterable<S> saveAll(Iterable<S> entities) {
        entities.forEach(this::save);
        return entities;
    }

    @Override
    public Optional<TodoItem> findById(Long id) {
        return todoItems.values().stream().filter(todoItem -> todoItem.getId().equals(id)).findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public Iterable<TodoItem> findAll() {
        return new ArrayList<>(todoItems.values());
    }

    @Override
    public Iterable<TodoItem> findAllById(Iterable<Long> longs) {
        List<Long> ids = new ArrayList<>();
        longs.iterator().forEachRemaining(ids::add);
        return todoItems.values().stream().filter(it -> ids.contains(it.getId())).collect(Collectors.toList());
    }

    @Override
    public long count() {
        return todoItems.size();
    }

    @Override
    public void deleteById(Long id) {
        todoItems.remove(id);
    }

    @Override
    public void delete(TodoItem entity) {
        todoItems.remove(entity.getId());
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
    public int countAllByCompleted(boolean completed) {
        return (int) todoItems.values().stream().filter(it -> it.isCompleted() == completed).count();
    }

    @Override
    public List<TodoItem> findAllByCompleted(boolean completed) {
        return todoItems.values().stream().filter(it -> it.isCompleted() == completed).collect(Collectors.toList());
    }
}
