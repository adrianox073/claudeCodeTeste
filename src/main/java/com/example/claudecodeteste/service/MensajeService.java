package com.example.claudecodeteste.service;

import com.example.claudecodeteste.exception.ComentarioDuplicadoException;
import com.example.claudecodeteste.model.Mensaje;
import com.example.claudecodeteste.repository.MensajeRepository;
import com.example.claudecodeteste.util.Prueba;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MensajeService {

    private final MensajeRepository repo;

    public MensajeService(MensajeRepository repo) {
        this.repo = repo;
    }

    public List<Mensaje> listar() {
        return repo.listar();
    }

    /**
     * Evalua el texto con Prueba.describir y lo guarda.
     *
     * @throws ComentarioDuplicadoException si ya existe un comentario igual
     *         (sin distinguir mayusculas ni espacios al principio o al final)
     */
    public Mensaje agregar(String texto) {
        if (repo.existeTexto(texto)) {
            throw new ComentarioDuplicadoException(texto);
        }
        return repo.guardar(texto, Prueba.describir(texto));
    }
}
