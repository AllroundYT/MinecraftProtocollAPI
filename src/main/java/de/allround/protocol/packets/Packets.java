package de.allround.protocol.packets;

import de.allround.protocol.ConnectionState;
import de.allround.protocol.packets.handshake.server.Handshake;
import de.allround.protocol.packets.status.client.PingResponse;
import de.allround.protocol.packets.status.client.StatusResponse;
import de.allround.protocol.packets.status.server.PingRequest;
import de.allround.protocol.packets.status.server.StatusRequest;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Packets {
    public static final class Serverbound {
        private static final Map<Integer, Supplier<ReadablePacket>> HANDSHAKE = new HashMap<>();
        private static final Map<Integer, Supplier<ReadablePacket>> STATUS = new HashMap<>();
        private static final Map<Integer, Supplier<ReadablePacket>> LOGIN = new HashMap<>();
        private static final Map<Integer, Supplier<ReadablePacket>> PLAY = new HashMap<>();

        static {
            HANDSHAKE.put(0x00, Handshake::new);

            STATUS.put(0x00, StatusRequest::new);
            STATUS.put(0x01, PingRequest::new);
        }

        public static ReadablePacket get(@NotNull ConnectionState state, int id) {
            return switch (state) {
                case CLOSED -> null;
                case PLAY -> getPlay(id);
                case LOGIN -> getLogin(id);
                case STATUS -> getStatus(id);
                case HANDSHAKE -> getHandshake(id);
            };
        }

        public static ReadablePacket get(ConnectionState state, int id, ByteBuffer buffer) {
            return get(state, id).read(buffer);
        }

        public static ReadablePacket getStatus(int id, ByteBuffer buffer) {
            return STATUS.get(id).get().read(buffer);
        }

        public static ReadablePacket getStatus(int id) {
            return STATUS.get(id).get();
        }

        public static ReadablePacket getHandshake(int id, ByteBuffer buffer) {
            return HANDSHAKE.get(id).get().read(buffer);
        }

        public static ReadablePacket getHandshake(int id) {
            return HANDSHAKE.get(id).get();
        }

        public static ReadablePacket getLogin(int id, ByteBuffer buffer) {
            return LOGIN.get(id).get().read(buffer);
        }

        public static ReadablePacket getLogin(int id) {
            return LOGIN.get(id).get();
        }

        public static ReadablePacket getPlay(int id, ByteBuffer buffer) {
            return PLAY.get(id).get().read(buffer);
        }

        public static ReadablePacket getPlay(int id) {
            return PLAY.get(id).get();
        }
    }

    public static final class Clientbound {
        private static final Map<Integer, Supplier<WritablePacket>> STATUS = new HashMap<>();
        private static final Map<Integer, Supplier<WritablePacket>> LOGIN = new HashMap<>();
        private static final Map<Integer, Supplier<WritablePacket>> PLAY = new HashMap<>();

        static {
            STATUS.put(0x00, StatusResponse::new);
            STATUS.put(0x01, PingResponse::new);
        }

        public static WritablePacket get(@NotNull ConnectionState state, int id) {
            return switch (state) {
                case CLOSED, HANDSHAKE -> null;
                case PLAY -> getPlay(id);
                case LOGIN -> getLogin(id);
                case STATUS -> getStatus(id);
            };
        }

        public static WritablePacket getStatus(int id) {
            return STATUS.get(id).get();
        }

        public static WritablePacket getLogin(int id) {
            return LOGIN.get(id).get();
        }

        public static WritablePacket getPlay(int id) {
            return PLAY.get(id).get();
        }
    }
}
