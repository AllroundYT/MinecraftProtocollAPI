package de.allround.protocol;

import de.allround.protocol.datatypes.ByteBuffer;

public enum ConnectionState {
    HANDSHAKE,
    STATUS,
    LOGIN,
    CONFIGURATION,
    PLAY,
    CLOSED;

    public static ConnectionState fromOrdinal(int ordinal) {
        return switch (ordinal) {
            case 0 -> HANDSHAKE;
            case 1 -> STATUS;
            case 2 -> LOGIN;
            case 3 -> CONFIGURATION;
            case 4 -> PLAY;
            default -> CLOSED;
        };
    }
}
