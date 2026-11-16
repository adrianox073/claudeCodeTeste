package com.example.untitled.controller;

import com.example.untitled.exception.ComentarioDuplicadoException;
import com.example.untitled.model.Mensaje;
import com.example.untitled.service.MensajeService;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mensajes")
public class MensajeController {

    private final MensajeService service;

    public MensajeController(MensajeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Mensaje> listar() {
        return service.listar();
    }

    /** Evalua el texto y lo guarda; 409 si el comentario ya existe. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mensaje evaluar(@RequestParam(required = false) String texto) {
        return service.agregar(texto);
    }

    @ExceptionHandler(ComentarioDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> duplicado(ComentarioDuplicadoException e) {
        return Map.of("error", e.getMessage());
    }
}
