package com.github.atomfrede.playwrightjavaaktuell.page_objects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.AriaRole;
import org.assertj.core.api.Assertions;

public class TodoPage {

    private String baseUrl;
    private Page page;
    private Locator pageHeading;
    private Locator titleInput;
    private Locator todoListItems;
    private Locator completeAllTodosToggle;
    private Locator todoCount;
    private Locator clearCompleted;
    private Locator logoutButton;

    public TodoPage(Page page, String baseUrl) {
        this.baseUrl = baseUrl;
        this.page = page;

        pageHeading = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("todos"));
        titleInput = page.getByPlaceholder("What needs to be done?");
        todoListItems = page.locator(".todo-list");
        completeAllTodosToggle = page.locator("#toggle-all");
        todoCount = page.locator(".todo-count");
        clearCompleted = page.locator(".clear-completed");
        logoutButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign out"));
    }

    public void navigate() {
        page.navigate(baseUrl);

        PlaywrightAssertions.assertThat(pageHeading).isVisible();
    }

    public void createNewTodo(String title) {
        titleInput.fill(title);
        titleInput.press("Enter");

        PlaywrightAssertions.assertThat(todoListItems).isVisible();

    }

    public void completeAllTodos() {
        completeAllTodosToggle.click();

        Assertions.assertThat(todoCount.innerText()).isEqualTo("0 items left");
    }

    public void completeFirstTodo() {
        String todoCountBefore = todoCount.innerText();
        page.locator(".toggle").first().click();

        Assertions.assertThat(todoCount.innerText()).isNotEqualTo(todoCountBefore);
        clearCompleted.click();
    }

    public void logout() {
        logoutButton.click();
        PlaywrightAssertions.assertThat(page.getByRole(AriaRole.ALERT)).isVisible();
    }
}
