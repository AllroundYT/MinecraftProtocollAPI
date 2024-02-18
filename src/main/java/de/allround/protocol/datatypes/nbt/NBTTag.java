package de.allround.protocol.datatypes.nbt;

import de.allround.protocol.datatypes.ByteBuffer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract sealed class NBTTag permits ByteArrayNBTTag, ByteNBTTag, CompoundNBTTag, DoubleNBTTag, EndNBTTag, FloatNBTTag, IntArrayNBTTag, IntNBTTag, ListNBTTag, LongArrayNBTTag, LongNBTTag, ShortNBTTag, StringNBTTag {
    private final String name;


    public NBTTag(String name) {
        this.name = name;
    }

    public static @NotNull NBTTag readNamedTag(@NotNull ByteBuffer buffer) {
        byte tagType = buffer.read();
        if (tagType == 0) {
            return new EndNBTTag();
        } else {
            String name = readString(buffer);
            return readPayload(tagType, name, buffer);
        }
    }

    public static NBTTag readTag(ByteBuffer buffer) {
        byte tagType = buffer.read();
        if (tagType == 0) {
            return new EndNBTTag();
        } else {
            return readPayload(tagType, "", buffer);
        }
    }

    public static String stringify(NBTTag NBTTag, int depth) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(NBTTag.getClass().getSimpleName()).append("('").append(NBTTag.getName()).append("'): ");

        switch (NBTTag.getType()) {
            case 0 -> stringBuilder.append("END\n");

            case 1 -> stringBuilder.append(((ByteNBTTag) NBTTag).getValue()).append("\n");

            case 2 -> stringBuilder.append(((ShortNBTTag) NBTTag).getValue()).append("\n");
            case 3 -> stringBuilder.append(((IntNBTTag) NBTTag).getValue()).append("\n");

            case 4 -> stringBuilder.append(((LongNBTTag) NBTTag).getValue()).append("\n");

            case 5 -> stringBuilder.append(((FloatNBTTag) NBTTag).getValue()).append("\n");

            case 6 -> stringBuilder.append(((DoubleNBTTag) NBTTag).getValue()).append("\n");

            case 7 -> stringBuilder.append(Arrays.toString(((ByteArrayNBTTag) NBTTag).getValue())).append("\n");

            case 8 -> stringBuilder.append(((StringNBTTag) NBTTag).getValue()).append("\n");

            case 9 -> {
                List<NBTTag> listValue = ((ListNBTTag) NBTTag).getValue();
                stringBuilder.append(listValue.size()).append(" entries\n");
                appendIndentation(stringBuilder, depth);
                stringBuilder.append("{\n");
                for (NBTTag innerNBTTag : listValue) {
                    appendIndentation(stringBuilder, depth + 1);
                    stringBuilder.append(stringify(innerNBTTag, depth + 1));
                }
                appendIndentation(stringBuilder, depth);
                stringBuilder.append("}\n");
            }

            case 10 -> {
                List<NBTTag> compoundValue = ((CompoundNBTTag) NBTTag).getValue();
                stringBuilder.append(compoundValue.size()).append(" entries\n");
                appendIndentation(stringBuilder, depth);
                stringBuilder.append("{\n");
                for (NBTTag innerNBTTag : compoundValue) {
                    appendIndentation(stringBuilder, depth + 1);
                    stringBuilder.append(stringify(innerNBTTag, depth + 1));
                }
                appendIndentation(stringBuilder, depth);
                stringBuilder.append("}\n");
            }

            case 11 -> stringBuilder.append(Arrays.toString(((IntArrayNBTTag) NBTTag).getValue())).append("\n");

            case 12 -> stringBuilder.append(Arrays.toString(((LongArrayNBTTag) NBTTag).getValue())).append("\n");

            default -> stringBuilder.append("Unknown NBTTag type\n");

        }

        return stringBuilder.toString();
    }

    private static void appendIndentation(StringBuilder stringBuilder, int depth) {
        stringBuilder.append("  ".repeat(Math.max(0, depth)));
    }

    @Contract("_, _, _ -> new")
    public static @NotNull NBTTag readPayload(byte tagType, String name, ByteBuffer buffer) {
        switch (tagType) {
            case 0 -> {
                return new EndNBTTag();
            }
            case 1 -> {
                return new ByteNBTTag(name, buffer.read());
            }
            case 2 -> {
                return new ShortNBTTag(name, buffer.readShort());
            }
            case 3 -> {
                return new IntNBTTag(name, buffer.readInteger());
            }
            case 4 -> {
                return new LongNBTTag(name, buffer.readLong());
            }
            case 5 -> {
                return new FloatNBTTag(name, buffer.readFloat());
            }
            case 6 -> {
                return new DoubleNBTTag(name, buffer.readDouble());
            }
            case 7 -> {
                int length = buffer.readVarInt();
                return new ByteArrayNBTTag(name, buffer.readArray(length));
            }
            case 8 -> {
                return new StringNBTTag(name, readString(buffer));
            }
            case 9 -> {
                byte listTagType = buffer.read();
                int listLength = buffer.readInteger();
                List<NBTTag> list = new ArrayList<>();
                for (int i = 0; i < listLength; i++) {
                    list.add(readPayload(listTagType, "", buffer));
                }
                return new ListNBTTag(name, listTagType, list);
            }
            case 10 -> {
                List<NBTTag> compoundList = new ArrayList<>();
                NBTTag NBTTag;
                while (!((NBTTag = readNamedTag(buffer)) instanceof EndNBTTag)) {
                    compoundList.add(NBTTag);
                }
                return new CompoundNBTTag(name, compoundList);
            }
            case 11 -> {
                int intArrayLength = buffer.readInteger();
                int[] intArray = new int[intArrayLength];
                for (int i = 0; i < intArrayLength; i++) {
                    intArray[i] = buffer.readInteger();
                }
                return new IntArrayNBTTag(name, intArray);
            }
            case 12 -> {
                int longArrayLength = buffer.readInteger();
                long[] longArray = new long[longArrayLength];
                for (int i = 0; i < longArrayLength; i++) {
                    longArray[i] = buffer.readLong();
                }
                return new LongArrayNBTTag(name, longArray);
            }
            default -> throw new IllegalArgumentException(STR."Unknown NBTTag type: \{tagType}");
        }
    }

    public static String readString(ByteBuffer buffer) {
        short length = buffer.readShort();
        return new String(buffer.readArray(length));
    }

    public static void writeTag(ByteBuffer buffer, NBTTag NBTTag) {
        if (!(NBTTag instanceof EndNBTTag)) {
            buffer.write(NBTTag.getType());
            writeString(buffer, NBTTag.getName());
        }
        if (!(NBTTag instanceof EndNBTTag)) {
            writePayload(buffer, NBTTag);
        }
    }

    public static <T extends NBTTag> void writePayload(ByteBuffer buffer, T NBTTag) {
        switch (NBTTag.getType()) {
            case 1 -> buffer.write(((ByteNBTTag) NBTTag).getValue());
            case 2 -> buffer.write(((ShortNBTTag) NBTTag).getValue());
            case 3 -> buffer.write(((IntNBTTag) NBTTag).getValue());
            case 4 -> buffer.write(((LongNBTTag) NBTTag).getValue());
            case 5 -> buffer.write(((FloatNBTTag) NBTTag).getValue());
            case 6 -> buffer.write(((DoubleNBTTag) NBTTag).getValue());
            case 7 -> {
                ByteArrayNBTTag byteArrayTag = (ByteArrayNBTTag) NBTTag;
                buffer.write(byteArrayTag.getValue().length);
                buffer.write(byteArrayTag.getValue());
            }
            case 8 -> writeString(buffer, ((StringNBTTag) NBTTag).getValue());
            case 9 -> {
                ListNBTTag listTag = (ListNBTTag) NBTTag;
                buffer.write(listTag.getTagId());
                buffer.write(listTag.getValue().size());
                for (NBTTag innerNBTTag : listTag.getValue()) {
                    writePayload(buffer, innerNBTTag);
                }
            }
            case 10 -> {
                CompoundNBTTag compoundTag = (CompoundNBTTag) NBTTag;
                for (NBTTag innerNBTTag : compoundTag.getValue()) {
                    writeTag(buffer, innerNBTTag);
                }
                buffer.write((byte) 0); // End NBTTag
            }
            case 11 -> {
                IntArrayNBTTag intArrayTag = (IntArrayNBTTag) NBTTag;
                buffer.write(intArrayTag.getValue().length);
                for (int value : intArrayTag.getValue()) {
                    buffer.write(value);
                }
            }
            case 12 -> {
                LongArrayNBTTag longArrayTag = (LongArrayNBTTag) NBTTag;
                buffer.write(longArrayTag.getValue().length);
                for (long value : longArrayTag.getValue()) {
                    buffer.write(value);
                }
            }
            default -> throw new IllegalArgumentException(STR."Unknown NBTTag type: \{NBTTag.getType()}");
        }
    }

    public static void writeString(@NotNull ByteBuffer buffer, @NotNull String value) {
        buffer.write((short) value.length());
        buffer.write(value.getBytes());
    }

    public String getName() {
        return name;
    }

    public abstract byte getType();

    @Override
    public final String toString() {
        return stringify(this, 0);
    }
}
