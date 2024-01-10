package com.github.atomfrede.playwrightjavaaktuell.todo_item.web;

import com.github.atomfrede.playwrightjavaaktuell.todo_item.TodoItem;
import com.github.atomfrede.playwrightjavaaktuell.todo_item.TodoItemNotFoundException;
import com.github.atomfrede.playwrightjavaaktuell.todo_item.TodoItemRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class TodoItemController {

    private TodoItemRepository repository;

    public TodoItemController(TodoItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public String index(Model model, Principal principal) {
        addAttributesForIndex(model, principal.getName(), ListFilter.ALL);
        return "index";
    }

    @GetMapping("/active")
    public String indexActive(Model model, Principal principal) {
        addAttributesForIndex(model, principal.getName(), ListFilter.ACTIVE);
        return "index";
    }

    @GetMapping("/completed")
    public String indexCompleted(Model model, Principal principal) {
        addAttributesForIndex(model, principal.getName(), ListFilter.COMPLETED);
        return "index";
    }

    private void addAttributesForIndex(Model model,
                                       String user,
                                       ListFilter listFilter) {
        model.addAttribute("item", new TodoItemFormData());
        model.addAttribute("filter", listFilter);
        model.addAttribute("todos", getTodoItems(listFilter, user));
        model.addAttribute("totalNumberOfItems", repository.count(user));
        model.addAttribute("numberOfActiveItems", getNumberOfActiveItems(user));
        model.addAttribute("numberOfCompletedItems", getNumberOfCompletedItems(user));
    }

    @PostMapping
    public String addNewTodoItem(@Valid @ModelAttribute("item") TodoItemFormData formData, Principal principal) {
        repository.save(new TodoItem(repository.nextId(principal.getName()), formData.getTitle(), false, principal.getName()));

        return "redirect:/";
    }

    @PutMapping("/{id}/toggle")
    public String toggleSelection(@PathVariable("id") Long id, Principal principal) {
        TodoItem todoItem = repository.findById(id)
                .orElseThrow(() -> new TodoItemNotFoundException(id));

        todoItem.setCompleted(!todoItem.isCompleted());
        repository.save(todoItem);
        return "redirect:/";
    }

    @PutMapping("/toggle-all")
    public String toggleAll(Principal principal) {
        Iterable<TodoItem> todoItems = repository.findAll(principal.getName());
        for (TodoItem todoItem : todoItems) {
            todoItem.setCompleted(!todoItem.isCompleted());
            repository.save(todoItem);
        }
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id) {
        repository.deleteById(id);

        return "redirect:/";
    }

    @DeleteMapping("/completed")
    public String deleteCompletedItems(Principal principal) {
        List<TodoItem> items = repository.findAllByCompleted(principal.getName(), true);
        for (TodoItem item : items) {
            repository.deleteById(item.getId());
        }
        return "redirect:/";
    }

    private List<TodoItemDto> getTodoItems(ListFilter filter, String user) {

        return switch (filter) {
            case ALL -> convertToDto(repository.findAll(user));
            case ACTIVE -> convertToDto(repository.findAllByCompleted(user, false));
            case COMPLETED -> convertToDto(repository.findAllByCompleted(user, true));
        };
    }

    private List<TodoItemDto> convertToDto(Iterable<TodoItem> todoItems) {
        List<TodoItemDto> results = new ArrayList<>();
        todoItems.forEach(it -> {
            TodoItemDto todoItemDto = new TodoItemDto(it.getId(), it.getTitle(), it.isCompleted());
            results.add(todoItemDto);
        });
        return results;
    }

    private int getNumberOfActiveItems(String user) {
        return repository.countAllByCompleted(user, false);
    }

    private int getNumberOfCompletedItems(String user) {
        return repository.countAllByCompleted(user, true);
    }

    public static record TodoItemDto(long id, String title, boolean completed) {
    }

    public enum ListFilter {
        ALL,
        ACTIVE,
        COMPLETED
    }
}
