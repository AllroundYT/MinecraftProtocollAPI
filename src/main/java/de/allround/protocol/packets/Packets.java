package de.allround.protocol.packets;

import de.allround.protocol.packets.handshake.server.Handshake;
import de.allround.protocol.packets.status.client.PingResponse;
import de.allround.protocol.packets.status.client.StatusResponse;
import de.allround.protocol.packets.status.server.PingRequest;
import de.allround.protocol.packets.status.server.StatusRequest;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class Packets {
    public static final class Serverbound {
        private static final Map<Integer, Supplier<Packet>> HANDSHAKE = new HashMap<>();
        private static final Map<Integer, Supplier<Packet>> STATUS = new HashMap<>();
        private static final Map<Integer, Supplier<Packet>> LOGIN = new HashMap<>();
        private static final Map<Integer, Supplier<Packet>> PLAY = new HashMap<>();

        static {
            HANDSHAKE.put(0x00, Handshake::new);

            STATUS.put(0x00, StatusRequest::new);
            STATUS.put(0x01, PingRequest::new);
        }

        public static Packet getStatus(int id, ByteBuffer buffer){
            return STATUS.get(id).get().readDataFields(buffer);
        }

        public static Packet getStatus(int id){
            return STATUS.get(id).get();
        }

        public static Packet getHandshake(int id, ByteBuffer buffer) {
            return HANDSHAKE.get(id).get().readDataFields(buffer);
        }

        public static Packet getHandshake(int id) {
            return HANDSHAKE.get(id).get();
        }

        public static Packet getLogin(int id, ByteBuffer buffer){
            return LOGIN.get(id).get().readDataFields(buffer);
        }

        public static Packet getLogin(int id){
            return LOGIN.get(id).get();
        }

        public static Packet getPlay(int id, ByteBuffer buffer) {
            return PLAY.get(id).get().readDataFields(buffer);
        }

        public static Packet getPlay(int id) {
            return PLAY.get(id).get();
        }
    }

    public static final class Clientbound {
        private static final Map<Integer, Supplier<Packet>> STATUS = new HashMap<>();
        private static final Map<Integer, Supplier<Packet>> LOGIN = new HashMap<>();
        private static final Map<Integer, Supplier<Packet>> PLAY = new HashMap<>();

        static {
            STATUS.put(0x00, StatusResponse::new);
            STATUS.put(0x01, PingResponse::new);
        }

        public static Packet getStatus(int id, ByteBuffer buffer){
            return STATUS.get(id).get().readDataFields(buffer);
        }

        public static Packet getStatus(int id){
            return STATUS.get(id).get();
        }

        public static Packet getLogin(int id, ByteBuffer buffer){
            return LOGIN.get(id).get().readDataFields(buffer);
        }

        public static Packet getLogin(int id){
            return LOGIN.get(id).get();
        }

        public static Packet getPlay(int id, ByteBuffer buffer) {
            return PLAY.get(id).get().readDataFields(buffer);
        }

        public static Packet getPlay(int id) {
            return PLAY.get(id).get();
        }
    }
}
