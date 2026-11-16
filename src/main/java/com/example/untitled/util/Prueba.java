package com.example.untitled.util;

public final class Prueba {

    private Prueba() {
    }

    public static String describir(String x) {
        if (x == null || x.isEmpty()) {
            return "vacio";
        }
        return x;
    }
}
