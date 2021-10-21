package com.calathea.navi.sample;

import com.calathea.navi.util.Encrypter;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDTest {
    @Test
    public void uuid() {
        // 전체 128bit
        UUID uuid = UUID.randomUUID();

        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(uuid.getMostSignificantBits());
        byte[] highBytes = buffer.array();
        String highString = Encrypter.byteArrayToHexString(highBytes);
        ByteBuffer buffer2 = ByteBuffer.allocate(Long.BYTES);
        buffer2.putLong(uuid.getLeastSignificantBits());
        byte[] lowBytes = buffer2.array();
        String lowString = Encrypter.byteArrayToHexString(lowBytes);

        System.out.println("uuid : "+uuid.toString());
        // 4bit * 32 = 128 bit
        System.out.println("총 4bit 문자(0~f) 로 나타내는 경우 글자 수 : "+uuid.toString().replace("-", "").length());
        System.out.println("상위 64bit 숫자 : "+uuid.getMostSignificantBits());
        System.out.println("상위 64bit 숫자 : "+uuid.getLeastSignificantBits());
        System.out.println("상위 64bit 문자 : "+highString);
        System.out.println("하위 64bit 숫자 : "+lowString);
    }
}
