import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MensajeRepositoryTest {

    private MensajeRepository repo;

    @BeforeEach
    void setUp() {
        // BD en memoria distinta para cada test
        repo = new MensajeRepository("jdbc:h2:mem:" + UUID.randomUUID() + ";DB_CLOSE_DELAY=-1");
    }

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
    void guardarTextoNullSeRecuperaComoNull() {
        repo.guardar(null, "vacio");

        Mensaje m = repo.listar().get(0);

        assertNull(m.getTexto());
        assertEquals("vacio", m.getResultado());
    }
}
