package com.example.untitled.exception;

/** Se lanza al intentar guardar un comentario que ya existe en la BD. */
public class ComentarioDuplicadoException extends RuntimeException {

    public ComentarioDuplicadoException(String texto) {
        super(texto == null
                ? "Ya existe un comentario sin texto"
                : "El comentario \"" + texto.trim() + "\" ya existe");
    }
}
