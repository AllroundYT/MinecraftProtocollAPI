package de.allround.misc;

public interface Promise<T> {
    void complete(T result);
    void complete();
    void complete(Throwable throwable);
}
