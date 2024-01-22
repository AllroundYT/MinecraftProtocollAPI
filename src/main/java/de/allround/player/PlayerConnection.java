package de.allround.player;

public class PlayerConnection {
    public static enum ConnectionState {
        HANDSHAKE,
        STATUS,
        LOGIN,
        PLAY,
        CLOSED
    }
}
