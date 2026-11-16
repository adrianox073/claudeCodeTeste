package com.example.untitled;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Al arrancar guarda unos textos de ejemplo, como hacia antes Prueba.main. */
@Component
public class DatosDeEjemplo implements CommandLineRunner {

    private final MensajeRepository repo;

    public DatosDeEjemplo(MensajeRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        for (String x : new String[] {null, "", "hola"}) {
            repo.guardar(x, Prueba.describir(x));
        }
    }
}
