package de.allround.protocol;

public enum ConnectionState {
    HANDSHAKE,
    STATUS,
    LOGIN,
    PLAY,
    CLOSED;

    public static ConnectionState fromOrdinal(int ordinal) {
        return switch (ordinal) {
            case 0 -> HANDSHAKE;
            case 1 -> STATUS;
            case 2 -> LOGIN;
            case 3 -> PLAY;
            default -> CLOSED;
        };
    }
}
