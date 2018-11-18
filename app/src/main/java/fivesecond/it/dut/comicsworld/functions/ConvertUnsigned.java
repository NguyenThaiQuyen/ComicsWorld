package fivesecond.it.dut.comicsworld.functions;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class ConvertUnsigned {
    public static String ConvertString(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll("  ", " ").replaceAll("đ", "d");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}