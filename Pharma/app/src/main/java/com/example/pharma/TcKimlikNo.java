package com.example.pharma;

public class TcKimlikNo {

    public static Boolean Dogrula(String tckimlik) {
        if (tckimlik.length() != 11)
            return false;

        if (tckimlik.charAt(0) == '0')
            return false;

        int[] hane = new int[11];
        int toplam = 0;
        for (int i = 0; i < 11; i++) {
            hane[i] = Integer.parseInt(String.valueOf(tckimlik.charAt(i)));
            toplam += hane[i];
        }
        toplam -= hane[10];
        if ((toplam % 10) != hane[10])
            return false;

        int ciftler = hane[0] + hane[2] + hane[4] + hane[6] + hane[8];
        int tekler = hane[1] + hane[3] + hane[5] + hane[7];

        if (((ciftler * 7) + (tekler * 9)) % 10 != hane[9])
            return false;
        if ((ciftler * 8) % 10 != hane[10])
            return false;

        return true;
    }


}