package com.github.atomfrede.playwrightjavaaktuell.page_objects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class LoginPage {

    private Page page;
    private String baseUrl;
    private Locator usernameInputField;
    private Locator passwordInputField;
    private Locator submitButton;

    public LoginPage(Page page, String baseUrl) {
        this.page = page;
        this.baseUrl = baseUrl;
        usernameInputField = page.getByPlaceholder("Username");
        passwordInputField = page.getByPlaceholder("Password");
        submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in"));

    }

    public void doLogin(String username, String password) {
        page.navigate(baseUrl);
        usernameInputField.fill(username);
        passwordInputField.fill(password);

        submitButton.click();
    }
}
