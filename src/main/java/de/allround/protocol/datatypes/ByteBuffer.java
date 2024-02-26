package de.allround.protocol.datatypes;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import de.allround.protocol.datatypes.nbt.EndNBTTag;
import de.allround.protocol.datatypes.nbt.NBTTag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.StringReader;
import java.util.*;

/**
 * This class is used for sending data over network.
 * It provides simple access to read and write functions for a resizable byte array.
 */
public class ByteBuffer {

    //var int
    private static final int SEGMENT_BITS = 0x7F;
    private static final int CONTINUE_BIT = 0x80;
    final int IDENTIFIER_MAX_LENGTH = 32767;
    int JSON_MAX_LENGTH = 262144;
    private int writeCursor, readCursor;
    private byte[] bytes;

    /**
     * Creates an empty bytebuffer.
     */
    public ByteBuffer() {
        this.bytes = new byte[0];
    }

    /**
     * Creates an empty bytebuffer with the given size.
     */
    public ByteBuffer(int initialSize) {
        this.bytes = new byte[initialSize];
    }

    /**
     * Creates a bytebuffer containing the given byte array.
     */
    public ByteBuffer(byte @NotNull [] bytes) {
        this.bytes = new byte[bytes.length];
        for (byte b : bytes) {
            write(b);
        }
    }

    /**
     * Creates a bytebuffer containing the given bytebuffers.
     */
    public ByteBuffer(ByteBuffer @NotNull ... buffers) {
        this.bytes = new byte[0];
        for (ByteBuffer buffer : buffers) {
            for (Byte b : buffer.bytes) {
                this.write(b);
            }
        }
    }

    /**
     * Creates a bytebuffer containing the given {@link java.nio.ByteBuffer}.
     */
    public ByteBuffer(java.nio.@NotNull ByteBuffer nioBuffer) {
        nioBuffer.rewind();
        int length = nioBuffer.remaining();
        this.bytes = new byte[length];
        write(nioBuffer);
    }

    public static int fixedPointNumber(double d) {
        return (int) (d * 32.0D);
    }

    public static double fixedPointNumber(int i) {
        return i / 32.0D;
    }

    @Override
    public String toString() {
        return Arrays.toString(getArray());
    }

    public int getWriteCursor() {
        return writeCursor;
    }

    public int getReadCursor() {
        return readCursor;
    }

    public boolean hasRemaining() {
        return getRemaining() > 0;
    }

    public ByteBuffer clone() {
        return new ByteBuffer(bytes).setCursor(writeCursor, true).setCursor(readCursor, false);
    }

    @Contract(value = "_ -> new", pure = true)
    private byte @NotNull [] toBytes(short value) {
        return new byte[]{
                (byte) (value >>> 8), (byte) value
        };
    }

    @Contract(value = "_ -> new", pure = true)
    private byte @NotNull [] toBytes(int value) {
        return new byte[]{
                (byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value
        };
    }

    @Contract(value = "_ -> new", pure = true)
    private byte @NotNull [] toBytes(long value) {
        return new byte[]{
                (byte) (value >>> 56),
                (byte) (value >>> 48),
                (byte) (value >>> 40),
                (byte) (value >>> 32),
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value
        };
    }

    /**
     * Returns a new ByteBuffer with only the bytes from before the given index (excluded).
     *
     * @param index The index where to split
     * @return A new ByteBuffer
     */
    public ByteBuffer getBefore(int index) {
        if (index >= bytes.length) return this;
        if (index < 0) return new ByteBuffer();
        byte[] newBuffersBytes = new byte[index + 1];
        System.arraycopy(bytes, 0, newBuffersBytes, 0, index + 1);
        return new ByteBuffer(newBuffersBytes);
    }

    @Contract("_ -> new")
    private byte @NotNull [] toBytes(double value) {
        return toBytes(Double.doubleToLongBits(value));
    }

    @Contract("_ -> new")
    private byte @NotNull [] toBytes(float value) {
        return toBytes(Float.floatToIntBits(value));
    }

    @Contract(value = "_ -> new", pure = true)
    private byte @NotNull [] toBytesUnsigned(short value) {
        return toBytes(value & 0xFFFFL);
    }

    @Contract(value = "_ -> new", pure = true)
    private byte @NotNull [] toBytesUnsigned(int value) {
        return toBytes(value & 0xFFFFFFFFL);
    }

    @Contract(pure = true)
    private byte @NotNull [] toBytesUnsigned(long value) {
        byte[] result = new byte[Long.BYTES];
        for (int i = 0; i < Long.BYTES; i++) {
            result[i] = (byte) (value >>> (i * 8));
        }
        return result;
    }

    @Contract(pure = true)
    private short toShort(byte @NotNull [] bytes) {
        return (short) ((bytes[0] << 8) | (bytes[1] & 0xFF));
    }

    @Contract(pure = true)
    private int toInteger(byte @NotNull [] bytes) {
        return (bytes[0] << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
    }

    @Contract(pure = true)
    private long toLong(byte @NotNull [] bytes) {
        return ((long) bytes[0] << 56) | ((long) (bytes[1] & 0xFF) << 48) | ((long) (bytes[2] & 0xFF) << 40) | ((long) (bytes[3] & 0xFF) << 32) | ((long) (bytes[4] & 0xFF) << 24) | ((long) (bytes[5] & 0xFF) << 16) | ((long) (bytes[6] & 0xFF) << 8) | ((long) bytes[7] & 0xFF);
    }


    private double toDouble(byte[] bytes) {
        return Double.longBitsToDouble(toLong(bytes));
    }

    private float toFloat(byte[] bytes) {
        return Float.intBitsToFloat(toInteger(bytes));
    }

    private void addSize(int addition) {
        setSize(getSize() + addition);
    }

    private boolean isInside(int pos) {
        return bytes.length > pos;
    }


    /**
     * Gets the size of this bytebuffer
     *
     * @return the size of this bytebuffers underlying array.
     */
    public int getSize() {
        return bytes.length;
    }

    private void setSize(int size) {
        if (bytes.length >= size) return;
        this.bytes = Arrays.copyOf(bytes, size);
    }

    /**
     * Sets the position of both cursors.
     *
     * @param pos The new position.
     * @return This bytebuffer instance for fluent api design.
     */
    public ByteBuffer setCursor(int pos) {
        this.writeCursor = pos;
        this.readCursor = pos;
        return this;
    }

    /**
     * Sets the position of the selected cursor.
     *
     * @param pos    The new position.
     * @param cursor true: write cursor; false: read cursor
     * @return This bytebuffer instance for fluent api design.
     */
    public ByteBuffer setCursor(int pos, boolean cursor) {
        if (cursor) {
            writeCursor = pos;
        } else {
            readCursor = pos;
        }
        return this;
    }

    /**
     * Gets the underlying array of the bytebuffer.
     *
     * @return This bytebuffer instance for fluent api design.
     */
    public byte[] getArray() {
        return bytes;
    }

    /**
     * Resets the position of both cursors to zero.
     *
     * @return This bytebuffer instance for fluent api design.
     */
    public ByteBuffer reset() {
        this.writeCursor = 0;
        this.readCursor = 0;
        return this;
    }

    /**
     * Resets the position of the selected cursor to zero.
     *
     * @param cursor true: write cursor; false: read cursor;
     * @return This bytebuffer instance for fluent api design.
     */
    public ByteBuffer reset(boolean cursor) {
        if (cursor) {
            writeCursor = 0;
        } else {
            readCursor = 0;
        }
        return this;
    }

    /**
     * Writes a byte at the write cursor's position and increases the write cursor's position
     *
     * @param b The byte to write at the write cursor's position
     * @return This bytebuffer instance for fluent api design.
     */
    public ByteBuffer write(byte b) {
        setSize(writeCursor + 1);
        bytes[writeCursor++] = b;
        return this;
    }

    private byte @NotNull [] nioBufferToArray(java.nio.@NotNull ByteBuffer nioBuffer) {
        nioBuffer.rewind();
        int length = nioBuffer.remaining();
        byte[] array = new byte[length];
        nioBuffer.get(array);
        return array;
    }

    /**
     * Writes a {@link java.nio.ByteBuffer} at the write cursor's position and increases the write cursor's position
     *
     * @param nioBuffer The {@link java.nio.ByteBuffer} to write at the write cursor's position
     * @return This bytebuffer instance for fluent api design.
     */
    public ByteBuffer write(java.nio.@NotNull ByteBuffer nioBuffer) {
        return write(nioBufferToArray(nioBuffer));
    }

    /**
     * Writes a byte array at the write cursor's position and increases the write cursor's position
     *
     * @param array The byte array to write at the write cursor's position
     * @return This bytebuffer instance for fluent api design.
     */
    public ByteBuffer write(byte @NotNull [] array) {
        for (byte b : array) {
            write(b);
        }
        return this;
    }

    /**
     * Writes a bytebuffer at the write cursor's position and increases the write cursor's position
     *
     * @param buffer The bytebuffer to write at the write cursor's position
     * @return This bytebuffer instance for fluent api design.
     */
    public ByteBuffer write(@NotNull ByteBuffer buffer) {
        return write(buffer.getArray());
    }

    /**
     * Writes a byte at the given position and increases the write cursor's position
     *
     * @param pos The position of the byte
     * @param b   The byte to write at the given position
     * @return This bytebuffer instance for fluent api design.
     */
    public ByteBuffer write(int pos, byte b) {
        writeCursor = pos;
        write(b);
        return this;
    }

    /**
     * Writes a byte array at the given position and increases the write cursor's position
     *
     * @param pos   The position of the byte array
     * @param array The byte array to write at the given position
     * @return This bytebuffer instance for fluent api design.
     */
    public ByteBuffer write(int pos, byte @NotNull [] array) {
        writeCursor = pos;
        for (byte b : array) {
            write(b);
        }
        return this;
    }


    public ByteBuffer write(int pos, @NotNull ByteBuffer buffer) {
        return write(pos, buffer.getArray());
    }


    public ByteBuffer write(int pos, java.nio.@NotNull ByteBuffer nioBuffer) {
        return write(pos, nioBufferToArray(nioBuffer));
    }

    /**
     * Shifts all bytes, above the given position, one index upwards, inserts a byte at the given position and increases the write cursor's position
     *
     * @param pos The position of the byte
     * @param b   The byte to insert at the given position
     * @return This bytebuffer instance for fluent api design.
     */
    public ByteBuffer insert(int pos, byte b) {

        byte[] bytesAfter = readRemaining(pos);
        write(pos, b);
        write(bytesAfter);

        return this;
    }

    /**
     * Shifts all bytes, above the given position, one index upwards, inserts a byte array at the given position and increases the write cursor's position
     *
     * @param pos   The position of the byte array
     * @param array The byte array to insert at the given position
     * @return This bytebuffer instance for fluent api design.
     */
    public ByteBuffer insert(int pos, byte[] array) {

        byte[] bytesAfter = readRemaining(pos);
        write(pos, array);
        write(bytesAfter);

        return this;
    }


    public ByteBuffer insert(int pos, ByteBuffer buffer) {

        byte[] bytesAfter = readRemaining(pos);
        write(pos, buffer);
        write(bytesAfter);

        return this;
    }

    /**
     * Shifts all bytes, above the given position, one index upwards, inserts a {@link java.nio.ByteBuffer} at the given position and increases the write cursor's position
     *
     * @param pos       The position of the {@link java.nio.ByteBuffer}
     * @param nioBuffer The {@link java.nio.ByteBuffer} to insert at the given position
     * @return This bytebuffer instance for fluent api design.
     */
    public ByteBuffer insert(int pos, java.nio.@NotNull ByteBuffer nioBuffer) {

        byte[] bytesAfter = readRemaining(pos);
        write(pos, nioBuffer);
        write(bytesAfter);

        return this;
    }

    /**
     * Shifts all bytes, above the write cursor's position, one index upwards, inserts a byte at the write cursor's position
     *
     * @param b The byte to insert at the write cursor's position
     * @return This bytebuffer instance for fluent api design.
     */
    public ByteBuffer insert(byte b) {

        byte[] bytesAfter = readRemaining(writeCursor);
        write(b);
        write(bytesAfter);

        return this;
    }

    /**
     * Shifts all bytes, above the write cursor's position, one index upwards, inserts a byte array at the write cursor's position
     *
     * @param array The byte array to insert at the write cursor's position
     * @return This bytebuffer instance for fluent api design.
     */
    public ByteBuffer insert(byte[] array) {

        byte[] bytesAfter = readRemaining(writeCursor);
        write(array);
        write(bytesAfter);

        return this;
    }

    /**
     * Shifts all bytes, above the write cursor's position, one index upwards, inserts a {@link java.nio.ByteBuffer} at the write cursor's position
     *
     * @param nioBuffer The {@link java.nio.ByteBuffer} to insert at the write cursor's position
     * @return This bytebuffer instance for fluent api design.
     */
    public ByteBuffer insert(java.nio.@NotNull ByteBuffer nioBuffer) {

        byte[] bytesAfter = readRemaining(writeCursor);
        write(nioBuffer);
        write(bytesAfter);

        return this;
    }

    public ByteBuffer insert(ByteBuffer buffer) {

        byte[] bytesAfter = readRemaining(writeCursor);
        write(buffer);
        write(bytesAfter);

        return this;
    }

    public byte readByte() {
        return bytes[readCursor++];
    }

    public byte readByte(int pos) {
        readCursor = pos;
        return bytes[readCursor++];
    }

    public byte[] readArray(int pos, int length) {
        readCursor = pos;
        byte[] array = new byte[length];
        for (int i = 0; i < length; i++) {
            array[i] = readByte();
        }
        return array;
    }

    public byte[] readRemaining(int pos) {
        readCursor = pos;
        return readRemaining();
    }

    public byte[] readRemaining() {
        byte[] bytesAfter = new byte[getSize() - readCursor];
        if (bytes.length - readCursor >= 0) {
            System.arraycopy(bytes, readCursor, bytesAfter, 0, bytes.length - readCursor);
        }
        return bytesAfter;
    }

    public byte[] readArray(int length) {
        byte[] array = new byte[length];
        for (int i = 0; i < length; i++) {
            array[i] = readByte();
        }
        return array;
    }

    public ByteBuffer write(boolean value) {
        write((byte) (value ? 1 : 0));
        return this;
    }

    public ByteBuffer write(int pos, boolean value) {
        write(pos, (byte) (value ? 1 : 0));
        return this;
    }

    public ByteBuffer insert(boolean value) {
        insert((byte) (value ? 1 : 0));
        return this;
    }

    public ByteBuffer insert(int pos, boolean value) {
        insert(pos, (byte) (value ? 1 : 0));
        return this;
    }

    public boolean readBoolean() {
        return readByte() == 1;
    }

    public boolean readBoolean(int pos) {
        return readByte(pos) == 1;
    }

    public ByteBuffer write(short value) {
        write(toBytes(value));
        return this;
    }

    public ByteBuffer write(int pos, short value) {
        write(pos, toBytes(value));
        return this;
    }

    public ByteBuffer insert(short value) {
        insert(toBytes(value));
        return this;
    }

    public ByteBuffer insert(int pos, short value) {
        insert(pos, toBytes(value));
        return this;
    }

    public short readShort() {
        return toShort(readArray(2));
    }

    public short readShort(int pos) {
        return toShort(readArray(pos, 2));
    }

    public ByteBuffer write(int value) {
        write(toBytes(value));
        return this;
    }

    public ByteBuffer write(int pos, int value) {
        write(pos, toBytes(value));
        return this;
    }

    public ByteBuffer insert(int value) {
        insert(toBytes(value));
        return this;
    }

    public ByteBuffer insert(int pos, int value) {
        insert(pos, toBytes(value));
        return this;
    }

    public int readInteger() {
        return toInteger(readArray(4));
    }

    public int readInteger(int pos) {
        return toInteger(readArray(pos, 4));
    }

    public ByteBuffer write(long value) {
        write(toBytes(value));
        return this;
    }

    public ByteBuffer write(int pos, long value) {
        write(pos, toBytes(value));
        return this;
    }

    public ByteBuffer insert(long value) {
        insert(toBytes(value));
        return this;
    }

    public ByteBuffer insert(int pos, long value) {
        insert(pos, toBytes(value));
        return this;
    }

    public long readLong() {
        return toLong(readArray(8));
    }

    public long readLong(int pos) {
        return toLong(readArray(pos, 8));
    }

    public ByteBuffer write(double value) {
        write(toBytes(value));
        return this;
    }

    public ByteBuffer write(int pos, double value) {
        write(pos, toBytes(value));
        return this;
    }

    public ByteBuffer insert(double value) {
        insert(toBytes(value));
        return this;
    }

    public ByteBuffer insert(int pos, double value) {
        insert(pos, toBytes(value));
        return this;
    }

    public double readDouble() {
        return toDouble(readArray(8));
    }

    public double readDouble(int pos) {
        return toDouble(readArray(pos, 8));
    }

    public ByteBuffer write(float value) {
        write(toBytes(value));
        return this;
    }

    public ByteBuffer write(int pos, float value) {
        write(pos, toBytes(value));
        return this;
    }

    public ByteBuffer insert(float value) {
        insert(toBytes(value));
        return this;
    }

    public ByteBuffer insert(int pos, float value) {
        insert(pos, toBytes(value));
        return this;
    }

    public float readFloat() {
        return toFloat(readArray(4));
    }

    public float readFloat(int pos) {
        return toFloat(readArray(pos, 4));
    }

    public ByteBuffer write(@NotNull UUID value) {
        return write(value.getMostSignificantBits()).write(value.getLeastSignificantBits());
    }

    public ByteBuffer write(int pos, @NotNull UUID value) {
        writeCursor = pos;
        return write(value);
    }

    public ByteBuffer insert(@NotNull UUID value) {
        return insert(value.getMostSignificantBits()).write(value.getLeastSignificantBits());
    }

    public ByteBuffer insert(int pos, @NotNull UUID value) {
        writeCursor = pos;
        return insert(value);
    }

    public UUID readUUID() {
        return new UUID(readLong(), readLong());
    }

    public UUID readUUID(int pos) {
        readCursor = pos;
        return readUUID();
    }

    public ByteBuffer writeVarInt(int value) {
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                write((byte) value);
                return this;
            }

            write((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }
    }

    public ByteBuffer writeVarInt(int pos, int value) {
        writeCursor = pos;
        writeVarInt(value);
        return this;
    }

    public ByteBuffer insertVarInt(int value) {
        List<Byte> byteList = new ArrayList<>();
        while (true) {
            if ((value & ~SEGMENT_BITS) == 0) {
                byteList.add((byte) value);
                break;
            }

            byteList.add((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));

            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
        }

        byte[] tempArray = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            tempArray[i] = byteList.get(i);
        }
        insert(tempArray);
        return this;
    }

    public ByteBuffer insertVarInt(int pos, int value) {
        writeCursor = pos;
        return insertVarInt(value);
    }

    public int readVarInt() {
        int value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = readByte();
            value |= (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 32) throw new RuntimeException("VarInt is too big");
        }

        return value;
    }

    public int readVarInt(int pos) {
        readCursor = pos;
        return readVarInt();
    }

    public ByteBuffer writeVarLong(long value) {
        while (true) {
            if ((value & ~((long) SEGMENT_BITS)) == 0) {
                write((byte) value);
                return this;
            }

            write((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));

            value >>>= 7;
        }
    }

    public ByteBuffer writeVarLong(int pos, long value) {
        writeCursor = pos;
        writeVarLong(value);
        return this;
    }

    public ByteBuffer insertVarLong(long value) {
        List<Byte> byteList = new ArrayList<>();
        while (true) {
            if ((value & ~((long) SEGMENT_BITS)) == 0) {
                byteList.add((byte) value);
                break;
            }

            byteList.add((byte) ((value & SEGMENT_BITS) | CONTINUE_BIT));

            value >>>= 7;
        }

        byte[] tempArray = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            tempArray[i] = byteList.get(i);
        }
        insert(tempArray);
        return this;
    }

    public ByteBuffer insertVarLong(int pos, long value) {
        writeCursor = pos;
        return insertVarLong(value);
    }

    public long readVarLong() {
        long value = 0;
        int position = 0;
        byte currentByte;

        while (true) {
            currentByte = readByte();
            value |= (long) (currentByte & SEGMENT_BITS) << position;

            if ((currentByte & CONTINUE_BIT) == 0) break;

            position += 7;

            if (position >= 64) throw new RuntimeException("VarLong is too big");
        }

        return value;
    }

    public long readVarLong(int pos) {
        readCursor = pos;
        return readVarLong();
    }

    public ByteBuffer write(@NotNull String value, int length) {
        writeVarInt(length);
        write(stringToByteArrayWithFixedLength(value, length));
        return this;
    }

    public ByteBuffer write(int pos, String value, int length) {
        writeCursor = pos;
        write(value, length);
        return this;
    }

    public ByteBuffer insert(@NotNull String value, int length) {
        insertVarInt(length);
        insert(stringToByteArrayWithFixedLength(value, length));
        return this;
    }

    private byte @NotNull [] stringToByteArrayWithFixedLength(@NotNull String value, int length) {
        byte[] result = new byte[length];
        byte[] stringBytes = value.getBytes();
        for (int i = 0; i < length; i++) {
            if (i > value.length()) {
                result[i] = " ".getBytes()[0];
                continue;
            }
            result[i] = stringBytes[i];
        }
        return result;
    }

    public ByteBuffer insert(int pos, String value, int length) {
        writeCursor = pos;
        return insert(value, length);
    }

    public ByteBuffer write(@NotNull String value) {
        byte[] stringBytes = value.getBytes();
        writeVarInt(stringBytes.length);
        write(stringBytes);
        return this;
    }

    public ByteBuffer write(int pos, String value) {
        writeCursor = pos;
        write(value);
        return this;
    }

    public ByteBuffer insert(@NotNull String value) {
        byte[] stringBytes = value.getBytes();
        insertVarInt(stringBytes.length);
        insert(stringBytes);
        return this;
    }

    public ByteBuffer insert(int pos, String value) {
        writeCursor = pos;
        return insert(value);
    }

    public String readString() {
        int length = readVarInt();
        return new String(readArray(length));
    }

    public String readString(int pos) {
        readCursor = pos;
        return readString();
    }

    public ByteBuffer write(@NotNull JsonElement value) {
        return write(value.toString());
    }

    public ByteBuffer write(int pos, @NotNull JsonElement value) {
        return write(pos, value.toString());
    }

    public ByteBuffer insert(@NotNull JsonElement value) {
        return insert(value.toString());
    }

    public ByteBuffer insert(int pos, @NotNull JsonElement value) {
        return insert(pos, value.toString());
    }

    public JsonElement readJson() {
        JsonReader jsonReader = new JsonReader(new StringReader(readString()));
        jsonReader.setLenient(true);
        return JsonParser.parseReader(jsonReader);
    }

    public JsonElement readJson(int pos) {
        readCursor = pos;
        JsonReader jsonReader = new JsonReader(new StringReader(readString()));
        jsonReader.setLenient(true);
        return JsonParser.parseReader(jsonReader);
    }

    public ByteBuffer write(@NotNull Identifier value) {
        return write(String.copyValueOf(value.toString().toCharArray(),
                                        0,
                                        Math.min(value.toString().length(), IDENTIFIER_MAX_LENGTH)
        ));
    }

    public ByteBuffer write(int pos, @NotNull Identifier value) {
        writeCursor = pos;
        return write(value);
    }

    public ByteBuffer insert(@NotNull Identifier value) {
        return insert(String.copyValueOf(value.toString().toCharArray(),
                                         0,
                                         Math.min(value.toString().length(), IDENTIFIER_MAX_LENGTH)
        ));
    }

    public ByteBuffer insert(int pos, @NotNull Identifier value) {
        writeCursor = pos;
        return insert(value);
    }

    public Identifier readIdentifier() {
        return Identifier.of(readString());
    }

    public Identifier readIdentifier(int pos) {
        return Identifier.of(readString(pos));
    }

    public ByteBuffer write(@NotNull Position value) {
        return write(((value.x() & 0x3FFFFFF) << 38) | ((value.z() & 0x3FFFFFF) << 12) | (value.y() & 0xFFF));
    }

    public ByteBuffer write(int pos, @NotNull Position value) {
        writeCursor = pos;
        return write(value);
    }

    public ByteBuffer insert(@NotNull Position value) {
        return insert(((value.x() & 0x3FFFFFF) << 38) | ((value.z() & 0x3FFFFFF) << 12) | (value.y() & 0xFFF));
    }

    public ByteBuffer insert(int pos, @NotNull Position value) {
        writeCursor = pos;
        return insert(value);
    }

    public Position readPosition() {
        long val = readLong();
        long x = val >> 38;
        long y = val << 52 >> 52;
        long z = val << 26 >> 38;

        return new Position(x, y, z);
    }

    public Position readPosition(int pos) {
        readCursor = pos;
        return readPosition();
    }

    public ByteBuffer write(NBTTag NBTTag){
        NBTTag.writePayload(this, NBTTag);
        return this;
    }

    public ByteBuffer write(int pos, NBTTag NBTTag){
        writeCursor = pos;
        return write(NBTTag);
    }

    public ByteBuffer insert(NBTTag NBTTag){
        ByteBuffer helpBuffer = new ByteBuffer();
        NBTTag.writePayload(helpBuffer, NBTTag);
        insert(helpBuffer);
        return this;
    }

    public ByteBuffer insert(int pos, NBTTag NBTTag){
        writeCursor = pos;
        return insert(NBTTag);
    }

    public NBTTag readNBTTag(){
        return NBTTag.readTag(this);
    }

    public NBTTag readNBTTag(int pos){
        readCursor = pos;
        return readNBTTag();
    }


    public ByteBuffer write(@NotNull Slot slot) {
        write(slot.present());
        if (slot.present()) {
            if (slot.id() != null) {
                writeVarInt(slot.id());

                if (slot.count() != null) {
                    write(slot.count());

                    if (slot.nbt() == null) {
                        write(new EndNBTTag());
                    } else {
                        write( slot.nbt());
                    }
                }
            }
        }
        return this;
    }

    public ByteBuffer write(int pos, @NotNull Slot slot) {
        writeCursor = pos;
        return write(slot);
    }

    public ByteBuffer insert(@NotNull Slot slot) {
        insert(slot.present());
        if (slot.present()) {
            if (slot.id() != null) {
                insertVarInt(slot.id());

                if (slot.count() != null) {
                    insert(slot.count());

                    if (slot.nbt() == null) {
                        insert(new EndNBTTag());
                    } else {
                        insert(slot.nbt());
                    }
                }
            }
        }
        return this;
    }

    public ByteBuffer insert(int pos, @NotNull Slot slot) {
        writeCursor = pos;
        return insert(slot);
    }

    public Slot readSlot() {
        boolean present = readBoolean();
        if (!present) return new Slot(false, null, null, null);
        int id = readVarInt();
        if (!hasRemaining()) return new Slot(true, id, null, null);
        byte count = readByte();
        if (!hasRemaining()) return new Slot(true, id, count, null);
        NBTTag nbtTag = readNBTTag();
        if (nbtTag instanceof EndNBTTag) return new Slot(true, id, count, null);
        return new Slot(true, id, count, nbtTag);
    }

    public Slot readSlot(int pos) {
        readCursor = pos;
        return readSlot();
    }

    public int getRemaining() {
        return getSize() - (readCursor);
    }

    public ByteBuffer write(@NotNull Tag tag){
        write(tag.name());
        writeVarInt(tag.entries().size());
        tag.entries().forEach(this::writeVarInt);
        return this;
    }

    public ByteBuffer write(int pos, Tag tag){
        writeCursor = pos;
        return write(tag);
    }

    public ByteBuffer insert(@NotNull Tag tag){
        insert(tag.name());
        insertVarInt(tag.entries().size());
        tag.entries().forEach(this::insertVarInt);
        return this;
    }

    public ByteBuffer insert(int pos, Tag tag){
        writeCursor = pos;
        return insert(tag);
    }

    public Tag readTag(){
        Identifier identifier = readIdentifier();
        int count = readVarInt();
        List<Integer> entries = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            entries.add(readVarInt());
        }
        return new Tag(identifier, entries);
    }

    public Tag readTag(int pos){
        readCursor = pos;
        return readTag();
    }


    public ByteBuffer write(@NotNull BitSet bitSet){
        long[] longs = bitSet.toLongArray();
        writeVarInt(longs.length);
        for (long aLong : longs) {
            write(aLong);
        }
        return this;
    }

    public ByteBuffer write(int pos, BitSet bitSet){
        writeCursor = pos;
        return write(bitSet);
    }

    public ByteBuffer insert(@NotNull BitSet bitSet){
        long[] longs = bitSet.toLongArray();
        insertVarInt(longs.length);
        for (long aLong : longs) {
            insert(aLong);
        }
        return this;
    }

    public ByteBuffer insert(int pos, BitSet bitSet){
        writeCursor = pos;
        return insert(bitSet);
    }

    public BitSet readBitSet(){
        long[] longs = new long[readVarInt()];
        for (int i = 0; i < longs.length; i++) {
            longs[i] = readLong();
        }
        return BitSet.valueOf(longs);
    }

    public BitSet readBitSet(int pos){
        readCursor = pos;
        return readBitSet();
    }
}
