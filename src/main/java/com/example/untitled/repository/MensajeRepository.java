package com.example.untitled.repository;

import com.example.untitled.model.Mensaje;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class MensajeRepository {

    private final JdbcTemplate jdbc;

    public MensajeRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Mensaje guardar(String texto, String resultado) {
        LocalDateTime fecha = LocalDateTime.now();
        KeyHolder keys = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO mensaje (texto, resultado, fecha) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, texto);
            ps.setString(2, resultado);
            ps.setTimestamp(3, Timestamp.valueOf(fecha));
            return ps;
        }, keys);
        return new Mensaje(keys.getKey().longValue(), texto, resultado, fecha);
    }

    public List<Mensaje> listar() {
        return jdbc.query(
                "SELECT id, texto, resultado, fecha FROM mensaje ORDER BY id",
                (rs, n) -> new Mensaje(
                        rs.getLong("id"),
                        rs.getString("texto"),
                        rs.getString("resultado"),
                        rs.getTimestamp("fecha").toLocalDateTime()));
    }
}
