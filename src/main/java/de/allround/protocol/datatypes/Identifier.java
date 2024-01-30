package de.allround.protocol.datatypes;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Identifier {
    public static final String NAMESPACE_REGEX;
    public static final String VALUE_REGEX;

    static {
        NAMESPACE_REGEX = "[a-z0-9.-_]";
        VALUE_REGEX = "[a-z0-9.-_/]";
    }

    private final String namespace;
    private final String value;

    private Identifier(String namespace, String value) {
        this.namespace = namespace;
        this.value = value;
    }

    @Contract("_ -> new")
    public static @NotNull Identifier of(@NotNull String identifier) {
        return of(identifier.split(":")[0], identifier.split(":")[1]);
    }


    @Contract("_ -> new")
    public static @NotNull Identifier minecraft(@NotNull String value) {
        return of("minecraft", value);
    }

    @Contract("_, _ -> new")
    public static @NotNull Identifier of(@NotNull String namespace, String value) {
        if (!namespace.matches(NAMESPACE_REGEX) || !value.matches(VALUE_REGEX)) {
            throw new IllegalStateException("Identifiers has to match \"" + NAMESPACE_REGEX + ":" + VALUE_REGEX+"\"");
        }

        return new Identifier(namespace, value);
    }

    public String toString() {
        return (namespace + ":" + value);
    }
}
