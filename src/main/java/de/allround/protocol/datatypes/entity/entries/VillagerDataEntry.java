package de.allround.protocol.datatypes.entity.entries;

import de.allround.misc.ByteBufferHelper;
import de.allround.protocol.datatypes.DataType;
import de.allround.protocol.datatypes.entity.types.VillagerData;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public class VillagerDataEntry implements EntryType<VillagerData> {

    @Override
    public int getId() {
        return 18;
    }

    @Override
    public ByteBuffer write(@NotNull VillagerData villagerData) {
        return ByteBufferHelper.combine(
                DataType.VAR_INT.write(villagerData.villagerType()),
                DataType.VAR_INT.write(villagerData.villagerProfession()),
                DataType.VAR_INT.write(villagerData.level())
        );
    }

    @Override
    public VillagerData read(ByteBuffer buffer) {
        return new VillagerData(
                DataType.VAR_INT.read(buffer),
                DataType.VAR_INT.read(buffer),
                DataType.VAR_INT.read(buffer)
        );
    }
}
