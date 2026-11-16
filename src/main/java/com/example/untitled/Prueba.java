package com.example.untitled;

public final class Prueba {

    private Prueba() {
    }

    static String describir(String x) {
        if (x == null || x.isEmpty()) {
            return "vacio";
        }
        return x;
    }
}
