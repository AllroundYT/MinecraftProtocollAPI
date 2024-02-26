import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.nbt.NBTTag;
import de.allround.protocol.packets.ReadablePacket;
import de.allround.protocol.packets.ServerboundPackets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Test {
    public static void main(String[] args) {
        ServerboundPackets.getLogin(0);
    }
}
