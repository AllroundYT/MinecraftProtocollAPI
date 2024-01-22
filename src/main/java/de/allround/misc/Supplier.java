package de.allround.misc;

@FunctionalInterface
public interface Supplier<T> {
    T get() throws Exception;
}
