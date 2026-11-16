package com.example.untitled.controller;

import com.example.untitled.model.Mensaje;
import com.example.untitled.repository.MensajeRepository;
import com.example.untitled.util.Prueba;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mensajes")
public class MensajeController {

    private final MensajeRepository repo;

    public MensajeController(MensajeRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Mensaje> listar() {
        return repo.listar();
    }

    /** Evalua el texto con Prueba.describir y guarda el resultado. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mensaje evaluar(@RequestParam(required = false) String texto) {
        return repo.guardar(texto, Prueba.describir(texto));
    }
}
