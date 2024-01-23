package de.allround.protocol.packets.status.client;

import com.google.gson.JsonObject;
import de.allround.protocol.datatypes.DataType;
import de.allround.protocol.packets.Packet;

import java.nio.ByteBuffer;

public class StatusResponse implements Packet {

    private String version;
    private int protocol;
    private final JsonObject players;

    {
        players = new JsonObject();
        players.addProperty("max",100);
        players.addProperty("online",0);
    }
    private String description;
    private boolean enforcesSecureChat;
    private boolean previewsChat;

    public StatusResponse(String version, int protocol, String description, boolean enforcesSecureChat, boolean previewsChat) {
        this.version = version;
        this.protocol = protocol;
        this.description = description;
        this.enforcesSecureChat = enforcesSecureChat;
        this.previewsChat = previewsChat;
    }

    public StatusResponse() {
    }

    @Override
    public int getID() {
        return 0x00;
    }

    @Override
    public ByteBuffer writeDataFields() {
        JsonObject version = new JsonObject();
        version.addProperty("name", this.version);
        version.addProperty("protocol", this.protocol);

        JsonObject players = new JsonObject();
        players.add("players", this.players);

        JsonObject description = new JsonObject();
        description.addProperty("text", this.description);

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("version", version);
        jsonObject.add("players", players);
        jsonObject.add("description", description);
        jsonObject.addProperty("enforcesSecureChat", enforcesSecureChat);
        jsonObject.addProperty("previewsChat", previewsChat);
        return DataType.JSON.write(jsonObject);
    }

    @Override
    public Packet readDataFields(ByteBuffer buffer) {
        JsonObject jsonObject = DataType.JSON.read(buffer).getAsJsonObject();
        return this;
    }
}
