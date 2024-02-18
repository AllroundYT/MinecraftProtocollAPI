package de.allround.protocol.packets.configuration.server;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;

import java.util.UUID;

public record ResourcePackResponse(UUID uuid, Result result) implements ReadablePacket {


    @Override
    public int getID() {
        return 0x05;
    }

    @Override
    public ReadablePacket read(ByteBuffer buffer) {
        return new ResourcePackResponse(buffer.readUUID(), Result.fromOrdinal(buffer.readVarInt()));
    }

    public enum Result {
        SUCCESSFULLY_DOWNLOADED,
        DECLINED,
        FAILED_TO_DOWNLOAD,
        ACCEPTED,
        DOWNLOADED,
        INVALID_URL,
        FAILED_TO_RELOAD,
        DISCARDED;

        public static Result fromOrdinal(int i){
            return switch (i) {
                case 0 -> SUCCESSFULLY_DOWNLOADED;
                case 1 -> DECLINED;
                case 2 -> FAILED_TO_DOWNLOAD;
                case 3 -> ACCEPTED;
                case 4 -> DOWNLOADED;
                case 5 -> INVALID_URL;
                case 6 -> FAILED_TO_RELOAD;
                case 7 -> DISCARDED;
                default -> null;
            };
        }
    }
}
