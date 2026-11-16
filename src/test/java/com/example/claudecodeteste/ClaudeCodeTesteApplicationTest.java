package com.example.claudecodeteste;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/** Levanta el contexto completo (DatosDeEjemplo incluido) y prueba la API. */
@SpringBootTest
@AutoConfigureMockMvc
class ClaudeCodeTesteApplicationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void alArrancarHayDatosDeEjemplo() throws Exception {
        mvc.perform(get("/mensajes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].resultado").value("vacio"))
                .andExpect(jsonPath("$[2].texto").value("hola"));
    }

    @Test
    void sirveLaPaginaDeComentarios() throws Exception {
        mvc.perform(get("/index.html"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("id=\"form\"")));
    }

    @Test
    void postComoFormularioDeLaPagina() throws Exception {
        // La pagina envia application/x-www-form-urlencoded
        mvc.perform(post("/mensajes")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("texto=desde+la+web"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.texto").value("desde la web"));
    }

    @Test
    void postEvaluaYGuarda() throws Exception {
        mvc.perform(post("/mensajes").param("texto", "adios"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.texto").value("adios"))
                .andExpect(jsonPath("$.resultado").value("adios"));

    }

    @Test
    void postDuplicadoDevuelve409() throws Exception {
        mvc.perform(post("/mensajes").param("texto", "repetido"))
                .andExpect(status().isCreated());

        mvc.perform(post("/mensajes").param("texto", "  REPETIDO "))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("El comentario \"REPETIDO\" ya existe"));
    }

    @Test
    void postDeUnDatoDeEjemploDevuelve409() throws Exception {
        // "hola" y "" se guardan al arrancar (DatosDeEjemplo)
        mvc.perform(post("/mensajes").param("texto", "hola"))
                .andExpect(status().isConflict());
        mvc.perform(post("/mensajes").param("texto", ""))
                .andExpect(status().isConflict());
    }
}
