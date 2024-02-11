package de.allround.protocol.datatypes.nbt;

import de.allround.protocol.datatypes.ByteBuffer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract sealed class Tag permits ByteArrayTag, ByteTag, CompoundTag, DoubleTag, EndTag, FloatTag, IntArrayTag, IntTag, ListTag, LongArrayTag, LongTag, ShortTag, StringTag {
    private final String name;


    public Tag(String name) {
        this.name = name;
    }

    public static @NotNull Tag readNamedTag(@NotNull ByteBuffer buffer) {
        byte tagType = buffer.read();
        if (tagType == 0) {
            return new EndTag();
        } else {
            String name = readString(buffer);
            return readPayload(tagType, name, buffer);
        }
    }

    public static Tag readTag(ByteBuffer buffer) {
        byte tagType = buffer.read();
        if (tagType == 0) {
            return new EndTag();
        } else {
            return readPayload(tagType, "", buffer);
        }
    }

    public static String stringify(Tag tag, int depth) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tag.getClass().getSimpleName()).append("('").append(tag.getName()).append("'): ");

        switch (tag.getType()) {
            case 0 -> stringBuilder.append("END\n");

            case 1 -> stringBuilder.append(((ByteTag) tag).getValue()).append("\n");

            case 2 -> stringBuilder.append(((ShortTag) tag).getValue()).append("\n");
            case 3 -> stringBuilder.append(((IntTag) tag).getValue()).append("\n");

            case 4 -> stringBuilder.append(((LongTag) tag).getValue()).append("\n");

            case 5 -> stringBuilder.append(((FloatTag) tag).getValue()).append("\n");

            case 6 -> stringBuilder.append(((DoubleTag) tag).getValue()).append("\n");

            case 7 -> stringBuilder.append(Arrays.toString(((ByteArrayTag) tag).getValue())).append("\n");

            case 8 -> stringBuilder.append(((StringTag) tag).getValue()).append("\n");

            case 9 -> {
                List<Tag> listValue = ((ListTag) tag).getValue();
                stringBuilder.append(listValue.size()).append(" entries\n");
                appendIndentation(stringBuilder, depth);
                stringBuilder.append("{\n");
                for (Tag innerTag : listValue) {
                    appendIndentation(stringBuilder, depth + 1);
                    stringBuilder.append(stringify(innerTag, depth + 1));
                }
                appendIndentation(stringBuilder, depth);
                stringBuilder.append("}\n");
            }

            case 10 -> {
                List<Tag> compoundValue = ((CompoundTag) tag).getValue();
                stringBuilder.append(compoundValue.size()).append(" entries\n");
                appendIndentation(stringBuilder, depth);
                stringBuilder.append("{\n");
                for (Tag innerTag : compoundValue) {
                    appendIndentation(stringBuilder, depth + 1);
                    stringBuilder.append(stringify(innerTag, depth + 1));
                }
                appendIndentation(stringBuilder, depth);
                stringBuilder.append("}\n");
            }

            case 11 -> stringBuilder.append(Arrays.toString(((IntArrayTag) tag).getValue())).append("\n");

            case 12 -> stringBuilder.append(Arrays.toString(((LongArrayTag) tag).getValue())).append("\n");

            default -> stringBuilder.append("Unknown tag type\n");

        }

        return stringBuilder.toString();
    }

    private static void appendIndentation(StringBuilder stringBuilder, int depth) {
        stringBuilder.append("  ".repeat(Math.max(0, depth)));
    }

    @Contract("_, _, _ -> new")
    public static @NotNull Tag readPayload(byte tagType, String name, ByteBuffer buffer) {
        switch (tagType) {
            case 0 -> {
                return new EndTag();
            }
            case 1 -> {
                return new ByteTag(name, buffer.read());
            }
            case 2 -> {
                return new ShortTag(name, buffer.readShort());
            }
            case 3 -> {
                return new IntTag(name, buffer.readInteger());
            }
            case 4 -> {
                return new LongTag(name, buffer.readLong());
            }
            case 5 -> {
                return new FloatTag(name, buffer.readFloat());
            }
            case 6 -> {
                return new DoubleTag(name, buffer.readDouble());
            }
            case 7 -> {
                int length = buffer.readVarInt();
                return new ByteArrayTag(name, buffer.readArray(length));
            }
            case 8 -> {
                return new StringTag(name, readString(buffer));
            }
            case 9 -> {
                byte listTagType = buffer.read();
                int listLength = buffer.readInteger();
                List<Tag> list = new ArrayList<>();
                for (int i = 0; i < listLength; i++) {
                    list.add(readPayload(listTagType, "", buffer));
                }
                return new ListTag(name, listTagType, list);
            }
            case 10 -> {
                List<Tag> compoundList = new ArrayList<>();
                Tag tag;
                while (!((tag = readNamedTag(buffer)) instanceof EndTag)) {
                    compoundList.add(tag);
                }
                return new CompoundTag(name, compoundList);
            }
            case 11 -> {
                int intArrayLength = buffer.readInteger();
                int[] intArray = new int[intArrayLength];
                for (int i = 0; i < intArrayLength; i++) {
                    intArray[i] = buffer.readInteger();
                }
                return new IntArrayTag(name, intArray);
            }
            case 12 -> {
                int longArrayLength = buffer.readInteger();
                long[] longArray = new long[longArrayLength];
                for (int i = 0; i < longArrayLength; i++) {
                    longArray[i] = buffer.readLong();
                }
                return new LongArrayTag(name, longArray);
            }
            default -> throw new IllegalArgumentException(STR."Unknown tag type: \{tagType}");
        }
    }

    public static String readString(ByteBuffer buffer) {
        short length = buffer.readShort();
        return new String(buffer.readArray(length));
    }

    public static void writeTag(ByteBuffer buffer, Tag tag) {
        if (!(tag instanceof EndTag)) {
            buffer.write(tag.getType());
            writeString(buffer, tag.getName());
        }
        if (!(tag instanceof EndTag)) {
            writePayload(buffer, tag);
        }
    }

    public static <T extends Tag> void writePayload(ByteBuffer buffer, T tag) {
        switch (tag.getType()) {
            case 1 -> buffer.write(((ByteTag) tag).getValue());
            case 2 -> buffer.writeShort(((ShortTag) tag).getValue());
            case 3 -> buffer.writeInteger(((IntTag) tag).getValue());
            case 4 -> buffer.writeLong(((LongTag) tag).getValue());
            case 5 -> buffer.writeFloat(((FloatTag) tag).getValue());
            case 6 -> buffer.writeDouble(((DoubleTag) tag).getValue());
            case 7 -> {
                ByteArrayTag byteArrayTag = (ByteArrayTag) tag;
                buffer.writeInteger(byteArrayTag.getValue().length);
                buffer.write(byteArrayTag.getValue());
            }
            case 8 -> writeString(buffer, ((StringTag) tag).getValue());
            case 9 -> {
                ListTag listTag = (ListTag) tag;
                buffer.write(listTag.getTagId());
                buffer.writeInteger(listTag.getValue().size());
                for (Tag innerTag : listTag.getValue()) {
                    writePayload(buffer, innerTag);
                }
            }
            case 10 -> {
                CompoundTag compoundTag = (CompoundTag) tag;
                for (Tag innerTag : compoundTag.getValue()) {
                    writeTag(buffer, innerTag);
                }
                buffer.write((byte) 0); // End tag
            }
            case 11 -> {
                IntArrayTag intArrayTag = (IntArrayTag) tag;
                buffer.writeInteger(intArrayTag.getValue().length);
                for (int value : intArrayTag.getValue()) {
                    buffer.writeInteger(value);
                }
            }
            case 12 -> {
                LongArrayTag longArrayTag = (LongArrayTag) tag;
                buffer.writeInteger(longArrayTag.getValue().length);
                for (long value : longArrayTag.getValue()) {
                    buffer.writeLong(value);
                }
            }
            default -> throw new IllegalArgumentException(STR."Unknown tag type: \{tag.getType()}");
        }
    }

    public static void writeString(ByteBuffer buffer, String value) {
        buffer.writeShort((short) value.length());
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
