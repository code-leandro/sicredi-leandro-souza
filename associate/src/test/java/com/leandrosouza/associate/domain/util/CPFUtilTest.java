package com.leandrosouza.associate.domain.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CPFUtilTest {

    @Test
    void isValidCPF() {
        List<String> validCPFs = new ArrayList<>();
        validCPFs.add("00000000191");
        validCPFs.add("78529434030");
        validCPFs.add("04247999010");
        for (String cpf : validCPFs) {
            Assertions.assertThat(CPFUtil.isValidCPF(cpf)).isTrue();
        }
    }

    @Test
    public void isNotValidCPF() {
        List<String> notValidCPFs = new ArrayList<>();
        notValidCPFs.add("0000000192");
        notValidCPFs.add("78529434031");
        notValidCPFs.add("04247999011");
        for (String cpf : notValidCPFs) {
            Assertions.assertThat(CPFUtil.isValidCPF(cpf)).isFalse();
        }
    }
}