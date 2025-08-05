package com.ulasalle.gestorcitasconsultaclinicaspring.service.util;

import java.text.Normalizer;

public class TextNormalizationUtils {
    public static String normalizeText(String text) {
        if (text == null) return null;
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}", "").toLowerCase().trim();
    }
    public static boolean normalizedEquals(String text1, String text2) {
        return normalizeText(text1).equals(normalizeText(text2));
    }
}
