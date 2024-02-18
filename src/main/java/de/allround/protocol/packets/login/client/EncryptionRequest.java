package de.allround.protocol.packets.login.client;

import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.WritablePacket;

public record EncryptionRequest(String serverId,byte[] publicKey,byte[] verifyToken) implements WritablePacket {

    @Override
    public int getID() {
        return 0x01;
    }

    @Override
    public ByteBuffer write() {
        return new ByteBuffer()
                .write(serverId, 20)
                .writeVarInt(publicKey.length)
                .write(publicKey)
                .writeVarInt(verifyToken.length)
                .write(verifyToken);
    }
}
