package com.calathea.navi.util;

import java.util.UUID;

public class RequestIdGenerator {
    private static final char[] BASE_64_SYMBOLS = {
        '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
        'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z', '_', '-'
    };

    private static final int BASE_64_SYMBOL_SIZE = 64;
    private static final int BASE_64_SHIFT = 6;
    private static final int BASE_64_MASK = (1 << BASE_64_SHIFT) - 1;
    private static final int ZERO = 0;

    private RequestIdGenerator() {}

    public static String pick() {
        UUID uuid = UUID.randomUUID();
        return toUnsignedBase64String(uuid.getMostSignificantBits())
                + toUnsignedBase64String(uuid.getLeastSignificantBits());
    }

    /**
     * uuid 는 4비트 문자(총 16개 문자셋) 32개로 출력되는데, 6비트 문자(총 64개 문자셋) 21개 또는 22개로 변환하는 함수
     * 1. 랜덤한 64 비트 (Long 타입) 를 만든다.
     * 2. 64 비트에서 6비트(0~64) 씩 짤라서 64개의 문자와 매핑해서 문자 1개로 변환
     * 3. 2번에서 변환한 문자를 뒤에서부터 쌓아서 문자열로 만듬
     * 4. 1개의 문자는 4비트밖에 사용하지 못하여서 16개의 문자로만 매핑됨
     * 5. 입력받은 64비트를 쉬프트하면서 0이 될때까지 수행되다 보니 특정 숫자보다 작은 입력값인 경우 11개가 아닌 10개의 문자만 추출될수 있다.
     */
    public static String toUnsignedBase64String(long number) {
        int charPosition = BASE_64_SYMBOL_SIZE;
        long targetNumber = number;

        char[] buf = new char[BASE_64_SYMBOL_SIZE];
        do {
            buf[--charPosition] = BASE_64_SYMBOLS[(int) (targetNumber & BASE_64_MASK)];
            targetNumber >>>= BASE_64_SHIFT;
        } while (ZERO != targetNumber);

        return new String(buf, charPosition, (BASE_64_SYMBOL_SIZE - charPosition));
    }
}
