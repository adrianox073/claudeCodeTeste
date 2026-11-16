import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PruebaTest {

    @Test
    void nullDevuelveVacio() {
        assertEquals("vacio", Prueba.describir(null));
    }

    @Test
    void cadenaVaciaDevuelveVacio() {
        assertEquals("vacio", Prueba.describir(""));
    }

    @Test
    void textoSeDevuelveTalCual() {
        assertEquals("hola", Prueba.describir("hola"));
    }
}
