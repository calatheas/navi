package com.calathea.navi.util;

import java.nio.charset.StandardCharsets;

public class StringEncoder {
    public static String utf8To8859(String utf8) {
        /**
         * utf-8 가변길이, euc-kr 2바이트
         * ISO_8859_1 8비트 고정, 아스키의 확장판, 완전하게 포함되지 않는 언어가 있다.
         * ISO_8859_1 이용하면 한글도 되는듯... 자세한 설명은 생략한다.
         */
        byte[] originalBytes = utf8.getBytes(StandardCharsets.UTF_8);
        return new String(originalBytes, StandardCharsets.ISO_8859_1);
    }
}
