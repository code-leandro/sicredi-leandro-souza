package com.leandrosouza.associate.domain.util;

public class CPFUtil {

    public static boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.trim().isEmpty())
            return false;

        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() != 11) {
            return false;
        }

        int checkDigit1 = calculateCheckDigit(cpf, 10);
        int checkDigit2 = calculateCheckDigit(cpf, 11);

        return (checkDigit1 == Character.getNumericValue(cpf.charAt(9)) &&
                checkDigit2 == Character.getNumericValue(cpf.charAt(10)));
    }

    private static int calculateCheckDigit(String cpf, int weightStart) {
        int sum = 0;
        for (int i = 0; i < weightStart - 1; i++) {
            sum += (cpf.charAt(i) - '0') * (weightStart - i);
        }
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : (11 - remainder);
    }
}
