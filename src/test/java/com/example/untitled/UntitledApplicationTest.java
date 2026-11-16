package com.example.untitled;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/** Levanta el contexto completo (DatosDeEjemplo incluido) y prueba la API. */
@SpringBootTest
@AutoConfigureMockMvc
class UntitledApplicationTest {

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
    void postEvaluaYGuarda() throws Exception {
        mvc.perform(post("/mensajes").param("texto", "adios"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.texto").value("adios"))
                .andExpect(jsonPath("$.resultado").value("adios"));

        mvc.perform(post("/mensajes").param("texto", ""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.resultado").value("vacio"));
    }
}
