package com.calathea.navi.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SymmetricEncrypterTest {
    private Encrypter encrypter = new SymmetricEncrypter("4c30958f876f4518a8f3868e2adc76e5");

    @Test
    @Disabled
    public void generateAndPrintKey() {
        SymmetricEncrypter.generateAndPrintKey();
    }

    @Test
    public void encryptAndDecrypt() {
        //given
        String target = "I'm korean.";
        String targetKor = "나는 대한민국 사람입니다.";

        //when
        String encTarget = encrypter.encrypt(target);
        String encTargetKor = encrypter.encrypt(targetKor);
        String decTarget = encrypter.decrypt(encTarget);
        String decTargetKor = encrypter.decrypt(encTargetKor);

        Assertions.assertEquals(target, decTarget);
        Assertions.assertEquals(targetKor, decTargetKor);
    }
}
