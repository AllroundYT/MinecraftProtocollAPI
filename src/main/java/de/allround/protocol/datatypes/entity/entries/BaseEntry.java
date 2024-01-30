package de.allround.protocol.datatypes.entity.entries;

import de.allround.protocol.datatypes.DataType;
import de.allround.protocol.datatypes.entity.types.BaseData;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.BitSet;

public class BaseEntry implements EntryType<BaseData>{
    private static final byte ON_FIRE_MASK = 0x01;
    private static final byte CROUCHING_MASK = 0x02;
    private static final byte SPRINTING_MASK = 0x08;
    private static final byte SWIMMING_MASK = 0x10;
    private static final byte INVISIBLE_MASK = 0x20;
    private static final byte GLOWING_MASK = 0x40;
    private static final byte FLYING_MASK = (byte) 0x80;

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public ByteBuffer write(@NotNull BaseData baseData) {
        byte resultByte = 0;

        resultByte = (byte) (baseData.isOnFire() ? resultByte | ON_FIRE_MASK : resultByte & ~ON_FIRE_MASK);
        resultByte = (byte) (baseData.isCrouching() ? resultByte | CROUCHING_MASK : resultByte & ~CROUCHING_MASK);
        resultByte = (byte) (baseData.isSprinting() ? resultByte | SPRINTING_MASK : resultByte & ~SPRINTING_MASK);
        resultByte = (byte) (baseData.isSwimming() ? resultByte | SWIMMING_MASK : resultByte & ~SWIMMING_MASK);
        resultByte = (byte) (baseData.isInvisible() ? resultByte | INVISIBLE_MASK : resultByte & ~INVISIBLE_MASK);
        resultByte = (byte) (baseData.isGlowing() ? resultByte | GLOWING_MASK : resultByte & ~GLOWING_MASK);
        resultByte = (byte) (baseData.isFlyingWithElytra() ? resultByte | FLYING_MASK : resultByte & ~FLYING_MASK);


        return DataType.BYTE.write(resultByte);
    }

    @Override
    public BaseData read(ByteBuffer buffer) {
        final byte data = DataType.BYTE.read(buffer);
        BaseData baseData = new BaseData();
        baseData.setOnFire((data & ON_FIRE_MASK) != 0);
        baseData.setCrouching((data & CROUCHING_MASK) != 0);
        baseData.setSprinting((data & SPRINTING_MASK) != 0);
        baseData.setSwimming((data & SWIMMING_MASK) != 0);
        baseData.setInvisible((data & INVISIBLE_MASK) != 0);
        baseData.setGlowing((data & GLOWING_MASK) != 0);
        baseData.setFlyingWithElytra((data & FLYING_MASK) != 0);
        return baseData;
    }
}
