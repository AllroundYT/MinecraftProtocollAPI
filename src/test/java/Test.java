import de.allround.protocol.datatypes.ByteBuffer;
import de.allround.protocol.datatypes.nbt.Tag;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Test {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = new ByteBuffer(Files.newInputStream(Path.of("D:\\Downloads\\test.nbt")).readAllBytes());
        System.out.println(Tag.readNamedTag(buffer));
    }
}
