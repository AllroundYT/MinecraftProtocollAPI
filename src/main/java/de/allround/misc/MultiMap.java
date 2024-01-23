package de.allround.misc;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;


public class MultiMap<I, T> {
    private final ConcurrentMap<I, List<T>> map;


    public void remove(I i, T t){
        map.getOrDefault(i, Collections.emptyList()).remove(t);
    }

    public boolean isEmpty(){
        return map.isEmpty();
    }

    public MultiMap(ConcurrentMap<I, List<T>> map) {
        this.map = map;
    }

    public MultiMap() {
        this.map = new ConcurrentHashMap<>();
    }

    @SafeVarargs
    public final void add(I index, T... objects) {
        map.compute(index, (i, ts) -> {
            if (ts == null) ts = new ArrayList<>();
            for (T object : List.of(objects)) {
                if (!ts.contains(object)) {
                    ts.add(object);
                }
            }
            return ts;
        });
    }

    public MultiMap<I, T> forEach(BiConsumer<I, List<T>> consumer) {
        map.forEach(consumer);
        return this;
    }

    public List<T> get(I index) {
        return map.getOrDefault(index, Collections.emptyList());
    }
}
