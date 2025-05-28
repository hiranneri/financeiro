package br.com.mesadigital.financeiro.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {

    public static final DateTimeFormatter PATTERN_PT_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static final DateTimeFormatter PATTERN_US = DateTimeFormatter.ofPattern("dd-MM-yyyy'T'HH:mm:ss");

    public static String toPTBR(LocalDateTime dataHora) {
        return dataHora.format(PATTERN_PT_BR);
    }

    public static LocalDateTime toPTBR(String dataHora) {
        return LocalDateTime.parse(dataHora, PATTERN_US);
    }
}
