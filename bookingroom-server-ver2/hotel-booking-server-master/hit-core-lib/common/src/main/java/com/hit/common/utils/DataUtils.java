package com.hit.common.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

@Log4j2
@UtilityClass
public class DataUtils {

    private final Random random = new Random();

    public static String parserJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.writeValueAsString(data);
        } catch (Exception e) {
            return "";
        }
    }

    public String generateOTP(Integer length) {
        String numbers = "0123456789";

        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            otp.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        return otp.toString();
    }

    public static String formatCurrency(BigInteger amount) {
        Locale locale = Locale.forLanguageTag("vi-VN");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
        symbols.setDecimalSeparator('.');
        DecimalFormat currencyFormatter = new DecimalFormat("###,###,###", symbols);
        return currencyFormatter.format(amount);
    }
}
