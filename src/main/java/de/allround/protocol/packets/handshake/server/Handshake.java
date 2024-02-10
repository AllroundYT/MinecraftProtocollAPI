package de.allround.protocol.packets.handshake.server;

import de.allround.protocol.ConnectionState;
import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.ReadablePacket;
import org.jetbrains.annotations.NotNull;


public class Handshake implements ReadablePacket {
    private int protocolVersion;
    private String serverAddress;
    private short serverPort;
    private ConnectionState nextState;

    public ConnectionState getNextState() {
        return nextState;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public short getServerPort() {
        return serverPort;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    @Override
    public int getID() {
        return 0x00;
    }

    @Override
    public Handshake read(@NotNull ByteBuffer buffer) {
        protocolVersion = buffer.readVarInt();
        serverAddress = buffer.readString();
        serverPort = buffer.readShort();
        nextState = ConnectionState.fromOrdinal(buffer.readVarInt());
        return this;
    }
}
