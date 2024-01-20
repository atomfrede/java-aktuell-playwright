package com.microsoft.playwright.junit.impl;

import com.microsoft.playwright.PlaywrightException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Utils {

    private Utils() {}

    static <F, T> T convertType(F f, Class<T> t) {
        if (f == null) {
            return null;
        }

        // Make sure shallow copy is sufficient
        if (!t.getSuperclass().equals(Object.class) && !t.getSuperclass().equals(Enum.class)) {
            throw new PlaywrightException("Cannot convert to " + t.getCanonicalName() + " that has superclass " + t.getSuperclass().getCanonicalName());
        }
        if (!f.getClass().getSuperclass().equals(t.getSuperclass())) {
            throw new PlaywrightException("Cannot convert from " + t.getCanonicalName() + " that has superclass " + t.getSuperclass().getCanonicalName());
        }

        if (f instanceof Enum) {
            return (T) Enum.valueOf((Class) t, ((Enum) f).name());
        }

        try {
            T result = t.getDeclaredConstructor().newInstance();
            for (Field toField : t.getDeclaredFields()) {
                // Skip fields added by test coverage tools, see https://github.com/microsoft/playwright-java/issues/802
                if (toField.isSynthetic()) {
                    continue;
                }
                if (Modifier.isStatic(toField.getModifiers())) {
                    throw new RuntimeException("Unexpected field modifiers: " + t.getCanonicalName() + "." + toField.getName() + ", modifiers: " + toField.getModifiers());
                }
                try {
                    Field fromField = f.getClass().getDeclaredField(toField.getName());
                    Object value = fromField.get(f);
                    if (value != null) {
                        toField.set(result, value);
                    }
                } catch (NoSuchFieldException e) {
                    continue;
                }
            }
            return result;
        } catch (Exception e) {
            throw new PlaywrightException("Internal error", e);
        }
    }

    static <T> T clone(T f) {
        if (f == null) {
            return f;
        }
        return convertType(f, (Class<T>) f.getClass());
    }
}
