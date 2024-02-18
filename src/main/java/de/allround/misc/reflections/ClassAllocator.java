package de.allround.misc.reflections;

import org.jetbrains.annotations.Nullable;
import sun.misc.Unsafe;

import java.lang.reflect.InvocationTargetException;

public final class ClassAllocator {

    public static final Unsafe unsafe;

    static {
        try {
            var field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (IllegalAccessException | NoSuchFieldException var1) {
            throw new RuntimeException(var1);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> @Nullable T allocate(Class<T> tClass) {
        try {
            return (T) unsafe.allocateInstance(tClass);
        } catch (InstantiationException e) {
            try {
                // default empty constructor
                return tClass.getConstructor().newInstance();
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException ex) {
                System.out.println(STR."Failed to allocate class \{tClass.getName()} using unsafe");
                ex.printStackTrace(System.err);
            }
        }
        return null;
    }
}