package com.example.untitled;

import java.time.LocalDateTime;

public final class Mensaje {
    private final long id;
    private final String texto;
    private final String resultado;
    private final LocalDateTime fecha;

    public Mensaje(long id, String texto, String resultado, LocalDateTime fecha) {
        this.id = id;
        this.texto = texto;
        this.resultado = resultado;
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public String getResultado() {
        return resultado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return id + " | " + texto + " -> " + resultado + " | " + fecha;
    }
}
