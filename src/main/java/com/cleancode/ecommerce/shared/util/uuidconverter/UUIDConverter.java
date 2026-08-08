package com.cleancode.ecommerce.shared.util.uuidconverter;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDConverter {

    public static byte[] toBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public static UUID fromBytes(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long mostSig = bb.getLong();
        long leastSig = bb.getLong();
        return new UUID(mostSig, leastSig);
    }
}
