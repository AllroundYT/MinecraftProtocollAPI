package de.allround.protocol.packets.status.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.packets.WritablePacket;

import java.util.Map;
import java.util.UUID;

public record StatusResponse(int maxPlayers,
                             int onlinePlayers,
                             Map<UUID, String> samplePlayers,
                             String description,
                             boolean enforcesSecureChat,
                             boolean previewsChat
) implements WritablePacket {


    public StatusResponse(String description, int maxPlayers, int onlinePlayers, Map<UUID, String> samplePlayers) {
        this(maxPlayers, onlinePlayers, samplePlayers, description, false, false);
    }

    @Override
    public int getID() {
        return 0x00;
    }

    @Override
    public ByteBuffer write() {
        JsonObject versionObject = new JsonObject();
        String version = "1.20.4";
        versionObject.addProperty("name", version);
        int protocol = 765;
        versionObject.addProperty("protocol", protocol);

        JsonObject description = new JsonObject();
        description.addProperty("text", this.description);

        JsonObject players = new JsonObject();
        players.addProperty("max", maxPlayers);
        players.addProperty("online", onlinePlayers);
        JsonArray sampleArray = new JsonArray();
        this.samplePlayers.forEach((uuid, s) -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", s);
            jsonObject.addProperty("id", uuid.toString());
            sampleArray.add(jsonObject);
        });
        players.add("sample", sampleArray);

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("version", versionObject);
        jsonObject.add("players", players);
        jsonObject.add("description", description);
        jsonObject.addProperty("enforcesSecureChat", enforcesSecureChat);
        jsonObject.addProperty("previewsChat", previewsChat);
        System.out.println(jsonObject);
        return new ByteBuffer().write(jsonObject);
    }

}
