package com.example.untitled.repository;

import com.example.untitled.model.Mensaje;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

/** BD H2 embebida con schema.sql; cada test se deshace al terminar (rollback). */
@JdbcTest
@Import(MensajeRepository.class)
class MensajeRepositoryTest {

    @Autowired
    private MensajeRepository repo;

    @Test
    void bdNuevaEmpiezaVacia() {
        assertTrue(repo.listar().isEmpty());
    }

    @Test
    void guardarDevuelveIdYConservaDatos() {
        Mensaje m = repo.guardar("hola", "hola");

        assertTrue(m.getId() > 0);
        assertEquals("hola", m.getTexto());
        assertEquals("hola", m.getResultado());
    }

    @Test
    void listarDevuelveEnOrdenDeInsercion() {
        repo.guardar("a", "a");
        repo.guardar("", "vacio");
        repo.guardar("b", "b");

        List<Mensaje> mensajes = repo.listar();

        assertEquals(3, mensajes.size());
        assertEquals("a", mensajes.get(0).getTexto());
        assertEquals("", mensajes.get(1).getTexto());
        assertEquals("vacio", mensajes.get(1).getResultado());
        assertEquals("b", mensajes.get(2).getTexto());
    }

    @Test
    void existeTextoIgnoraMayusculasYEspacios() {
        repo.guardar("Hola Mundo", "Hola Mundo");

        assertTrue(repo.existeTexto("Hola Mundo"));
        assertTrue(repo.existeTexto("  hola mundo "));
        assertFalse(repo.existeTexto("hola"));
    }

    @Test
    void existeTextoNullSoloCoincideConNull() {
        assertFalse(repo.existeTexto(null));

        repo.guardar("", "vacio");
        assertFalse(repo.existeTexto(null));

        repo.guardar(null, "vacio");
        assertTrue(repo.existeTexto(null));
    }

    @Test
    void guardarTextoNullSeRecuperaComoNull() {
        repo.guardar(null, "vacio");

        Mensaje m = repo.listar().get(0);

        assertNull(m.getTexto());
        assertEquals("vacio", m.getResultado());
    }
}
